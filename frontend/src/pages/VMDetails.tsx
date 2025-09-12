import { useParams, Link } from "react-router-dom";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { ArrowLeft, Server, Cpu, HardDrive, MemoryStick, Network, Shield, Users, Clock } from "lucide-react";
import { useQuery } from "@tanstack/react-query";
import api from "@/lib/api";

type NetworkInterface = {
  name: string;
  ipAddress: string;
  macAddress: string;
};

type OpenPort = {
  port: number;
  protocol: string;
};

type VmDetails = {
  id: string;
  hostname: string;
  ipAddress: string;
  osType: string;
  uptime: string;
  cpuCores: number;
  cpuUsage: number; // 0..1
  ramTotal: number;
  ramUsed: number;
  ramUsagePercentage: number;
  diskTotal: number;
  diskUsed: number;
  diskUsagePercentage: number;
  networkInterfaces: NetworkInterface[];
  openPorts: OpenPort[];
};

const formatBytes = (bytes?: number) => {
  if (bytes === undefined || bytes === null) return "—";
  if (bytes === 0) return "0 B";
  const k = 1024;
  const sizes = ["B", "KB", "MB", "GB", "TB", "PB"];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  const value = bytes / Math.pow(k, i);
  return `${value.toFixed(i < 2 ? 0 : 1)} ${sizes[i]}`;
};

const VMDetails = () => {
  const { id } = useParams();

  const { data: vm, isLoading, isError, refetch } = useQuery<VmDetails>({
    queryKey: ["vm", id],
    queryFn: async () => {
      const res = await api.get(`/vms/${id}`);
      return res.data as VmDetails;
    },
    enabled: !!id,
    staleTime: 10000,
  });

  return (
    <div className="space-y-6">
      <div className="flex items-center space-x-4">
        <Button variant="outline" size="icon" asChild>
          <Link to="/vms">
            <ArrowLeft className="h-4 w-4" />
          </Link>
        </Button>
        <div className="flex-1">
          <h1 className="text-3xl font-bold tracking-tight">{vm?.hostname || "VM"}</h1>
          <p className="text-muted-foreground">Détails de la machine virtuelle</p>
        </div>
        <div className="flex items-center space-x-2">
          {isLoading && <Badge variant="secondary">Chargement...</Badge>}
          {isError && (
            <Button variant="destructive" onClick={() => refetch()}>Réessayer</Button>
          )}
        </div>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Système</CardTitle>
            <Server className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm?.osType || "—"}</div>
            <div className="text-sm text-muted-foreground">IP principale: {vm?.ipAddress || "—"}</div>
            <div className="text-sm text-muted-foreground">Uptime: {vm?.uptime || "—"}</div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">CPU</CardTitle>
            <Cpu className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm?.cpuCores ?? "—"} vCPU</div>
            <div className="text-sm text-muted-foreground">Utilisation: {vm ? Math.round((vm.cpuUsage || 0) * 100) : 0}%</div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Mémoire</CardTitle>
            <MemoryStick className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">
              {formatBytes(vm?.ramTotal)} <span className="text-sm text-muted-foreground">({formatBytes(vm?.ramUsed)} utilisée)</span>
            </div>
            <div className="text-sm text-muted-foreground">Utilisation: {vm?.ramUsagePercentage != null ? Math.round(vm.ramUsagePercentage) : 0}%</div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Disque</CardTitle>
            <HardDrive className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">
              {formatBytes(vm?.diskTotal)} <span className="text-sm text-muted-foreground">({formatBytes(vm?.diskUsed)} utilisée)</span>
            </div>
            <div className="text-sm text-muted-foreground">Utilisation: {vm?.diskUsagePercentage != null ? Math.round(vm.diskUsagePercentage) : 0}%</div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Comptes</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-sm text-muted-foreground">Données indisponibles</div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Disponibilité</CardTitle>
            <Clock className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm?.uptime || "—"}</div>
            <div className="text-sm text-muted-foreground">Temps de fonctionnement</div>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="shadow-card">
          <CardHeader>
            <CardTitle className="flex items-center">
              <Network className="mr-2 h-4 w-4" />
              Interfaces Réseau
            </CardTitle>
            <CardDescription>Configuration réseau de la VM</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {(vm?.networkInterfaces ?? []).map((iface, index) => (
                <div key={index}>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="font-medium">{iface.name}</p>
                      <p className="text-sm text-muted-foreground">IP: {iface.ipAddress}</p>
                      <p className="text-sm text-muted-foreground">MAC: {iface.macAddress}</p>
                    </div>
                    <Badge variant="outline">Actif</Badge>
                  </div>
                  {index < (vm?.networkInterfaces?.length ?? 0) - 1 && <Separator className="mt-4" />}
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader>
            <CardTitle className="flex items-center">
              <Shield className="mr-2 h-4 w-4" />
              Ports Ouverts
            </CardTitle>
            <CardDescription>Services accessibles sur la VM</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-3">
              {(vm?.openPorts ?? []).map((port, index) => (
                <div key={index} className="flex items-center justify-between p-3 rounded-lg bg-accent/50">
                  <div>
                    <p className="font-medium">Port {port.port}/{port.protocol}</p>
                  </div>
                  <Badge variant="secondary">Ouvert</Badge>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default VMDetails;

