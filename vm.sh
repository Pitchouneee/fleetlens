#!/usr/bin/env bash
# vm_ingest_probe.sh — génère un JSON conforme à VmIngestRequest
# Dépendances : bash, awk, sed, jq, iproute2 (ip), ss (ou netstat)

set -u

jq_bin="$(command -v jq || true)"
ip_bin="$(command -v ip || true)"
ss_bin="$(command -v ss || true)"
netstat_bin="$(command -v netstat || true)"

if [[ -z "${jq_bin}" ]]; then
  echo "Erreur: jq est requis. Installe-le (ex: sudo apt-get install -y jq)" >&2
  exit 1
fi

# --- helpers ---
read_first_line() { [[ -r "$1" ]] && head -n1 "$1" || true; }

get_hostname() { hostname; }

get_primary_ipv4() {
  if [[ -n "${ip_bin}" ]]; then
    # ip route get 1.1.1.1 -> ... src X.Y.Z.W ...
    local out
    out="$("$ip_bin" -4 route get 1.1.1.1 2>/dev/null | awk '{for (i=1;i<=NF;i++) if ($i=="src"){print $(i+1); exit}}')"
    if [[ -n "$out" ]]; then echo "$out"; return; fi
  fi
  # fallback
  hostname -I 2>/dev/null | awk '{print $1}'
}

get_os_pretty() {
  if [[ -r /etc/os-release ]]; then
    # shellcheck disable=SC1091
    . /etc/os-release
    [[ -n "${PRETTY_NAME:-}" ]] && echo "$PRETTY_NAME" && return
  fi
  if command -v lsb_release >/dev/null 2>&1; then
    lsb_release -d 2>/dev/null | sed 's/^Description:\s*//'
  fi
}

format_uptime_pretty() {
  local seconds="$1"
  local days hours minutes
  minutes=$((seconds/60))
  hours=$((minutes/60))
  days=$((hours/24))
  hours=$((hours%24))
  minutes=$((minutes%60))
  if (( days > 0 )); then
    printf "%d day%s, %d:%02d" "$days" "$([[ $days -gt 1 ]] && echo "s")" "$hours" "$minutes"
  elif (( hours > 0 )); then
    printf "%d:%02d" "$hours" "$minutes"
  elif (( minutes > 0 )); then
    printf "%d min" "$minutes"
  else
    printf "%d sec" "$seconds"
  fi
}

get_uptime() {
  local s
  s="$(read_first_line /proc/uptime)"
  if [[ -n "$s" ]]; then
    s="${s%% *}"
    # arrondi inférieur
    local sec=${s%.*}
    format_uptime_pretty "$sec"
  else
    uptime -p 2>/dev/null
  fi
}

get_mem_bytes() {
  # imprime "total used" en octets
  # total = MemTotal; used = total - MemAvailable (fallback MemFree+Buffers+Cached)
  awk '
    BEGIN{total=avail=free=buff=cached=0}
    $1=="MemTotal:"{total=$2*1024}
    $1=="MemAvailable:"{avail=$2*1024}
    $1=="MemFree:"{free=$2*1024}
    $1=="Buffers:"{buff=$2*1024}
    $1=="Cached:"{cached=$2*1024}
    END{
      if(total>0){
        if(avail>0){used=total-avail}
        else{used=total-(free+buff+cached)}
        if(used<0) used=0
        printf "%s %s\n", total, used
      }
    }
  ' /proc/meminfo 2>/dev/null
}

get_cpu_cores() {
  if command -v nproc >/dev/null 2>&1; then nproc; else grep -c ^processor /proc/cpuinfo; fi
}

read_proc_stat() {
  # echo "idle total"
  awk '
    $1=="cpu" {
      idle=$5; iowait=$6; if(iowait=="") iowait=0;
      total=0; for(i=2;i<=NF;i++) total+=$i;
      print idle+iowait, total; exit
    }' /proc/stat
}

get_cpu_usage_percent() {
  # échantillonne sur 0.5s
  local d1 t1 d2 t2 idle_delta total_delta usage
  read -r d1 t1 < <(read_proc_stat)
  sleep 0.5
  read -r d2 t2 < <(read_proc_stat)
  idle_delta=$((d2-d1))
  total_delta=$((t2-t1))
  if (( total_delta <= 0 )); then echo ""; return; fi
  # 100*(1 - idle/total)
  usage=$(awk -v i="$idle_delta" -v t="$total_delta" 'BEGIN{printf "%.1f", 100*(1-(i/t))}')
  echo "$usage"
}

get_disk_totals() {
  # somme tous les FS utiles; exclut tmpfs/devtmpfs/squashfs/overlay
  if command -v df >/dev/null 2>&1; then
    df -B1 -x tmpfs -x devtmpfs -x squashfs -x overlay 2>/dev/null \
    | awk 'NR>1 {t+=$2; u+=$3} END{if(t>0) printf "%s %s\n", t, u}'
  fi
}

get_networks_json() {
  # renvoie un tableau JSON d’objets {name, ipAddress, macAddress}
  if [[ -z "${ip_bin}" ]]; then echo "[]" ; return; fi
  local names
  names="$("$ip_bin" -o link show 2>/dev/null | awk -F': ' '{print $2}')"
  [[ -z "$names" ]] && { echo "[]"; return; }

  local tmpfile
  tmpfile="$(mktemp)"
  : > "$tmpfile"
  while IFS= read -r name; do
    [[ -z "$name" ]] && continue
    # IPv4 CIDR -> IP seule
    ip4="$("$ip_bin" -o -4 addr show dev "$name" 2>/dev/null | awk "{print \$4}" | head -n1)"
    ip4="${ip4%%/*}"
    mac="$(read_first_line "/sys/class/net/${name}/address")"
    [[ "$mac" == "00:00:00:00:00:00" ]] && mac=""
    # append JSON line
    "$jq_bin" -n --arg name "$name" --arg ip "${ip4:-}" --arg mac "${mac:-}" \
      '{name:$name, ipAddress:($ip|select(length>0)), macAddress:($mac|select(length>0))}' \
      >> "$tmpfile"
  done <<< "$names"

  # pack en tableau
  "$jq_bin" -s '.' "$tmpfile"
  rm -f "$tmpfile"
}

get_open_ports_json() {
  # renvoie un tableau JSON d’objets {port, protocol}
  local lines
  if [[ -n "${ss_bin}" ]]; then
    lines="$("$ss_bin" -lntu 2>/dev/null)"
  elif [[ -n "${netstat_bin}" ]]; then
    lines="$("$netstat_bin" -lntu 2>/dev/null)"
  else
    echo "[]"; return
  fi
  # parse -> proto + port
  echo "$lines" \
  | awk '
      BEGIN{IGNORECASE=1}
      /^State|^Proto|^Netid/ {next}
      {
        proto=tolower($1)
        # trouve dernier champ type host:port ou [::]:port
        port=""
        for(i=NF;i>=1;i--){
          if(index($i,":")>0){
            split($i,a,":"); port=a[length(a)]
            if(port ~ /^[0-9]+$/) break
          }
        }
        if((proto=="TCP"||proto=="UDP") && port ~ /^[0-9]+$/){
          key=proto "-" port
          if(!seen[key]++){ print proto, port }
        }
      }
    ' \
  | "$jq_bin" -Rn '
      [ inputs
        | split(" ")
        | select(length==2)
        | {port:(.[1]|tonumber), protocol: .[0]}
      ] | sort_by(.protocol, .port)
    '
}

# --- collecte ---
hostname_val="$(get_hostname)"
ip_val="$(get_primary_ipv4 || true)"
os_val="$(get_os_pretty || true)"
uptime_val="$(get_uptime || true)"

read -r ram_total ram_used <<< "$(get_mem_bytes || echo " ")"
cpu_cores_val="$(get_cpu_cores || echo "")"
cpu_usage_val="$(get_cpu_usage_percent || echo "")"
read -r disk_total disk_used <<< "$(get_disk_totals || echo " ")"

network_json="$(get_networks_json)"
open_ports_json="$(get_open_ports_json)"

# --- sortie JSON finale ---
"$jq_bin" -n \
  --arg hostname "${hostname_val}" \
  --arg ip "${ip_val:-}" \
  --arg os "${os_val:-}" \
  --arg uptime "${uptime_val:-}" \
  --argjson ramTotal "${ram_total:-null}" \
  --argjson ramUsed "${ram_used:-null}" \
  --argjson cpuCores "${cpu_cores_val:-null}" \
  --argjson cpuUsage "${cpu_usage_val:-null}" \
  --argjson diskTotal "${disk_total:-null}" \
  --argjson diskUsed "${disk_used:-null}" \
  --argjson nics "${network_json}" \
  --argjson ports "${open_ports_json}" \
  '{
     hostname: $hostname,
     ipAddress: ($ip | select(length>0)),
     osType: ($os | select(length>0)),
     uptime: ($uptime | select(length>0)),
     ramTotal: $ramTotal,
     ramUsed: $ramUsed,
     cpuCores: $cpuCores,
     cpuUsage: $cpuUsage,
     diskTotal: $diskTotal,
     diskUsed: $diskUsed,
     networkInterfaces: $nics,
     openPorts: $ports
   }'