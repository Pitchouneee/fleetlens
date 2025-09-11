import { useMemo, useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Server, Search, Eye, ChevronLeft, ChevronRight } from "lucide-react";
import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import api from "@/lib/api";

type VmApiItem = {
  id: string;
  hostname: string;
  ipAddress: string;
  osType: string;
  ramTotal: number; // bytes
  cpuCores: number;
  diskTotal: number; // bytes
  uptime: string; // e.g. "12:21"
};

type VmApiResponse = {
  content: VmApiItem[];
  page: {
    size: number;
    number: number; // 0-based
    totalElements: number;
    totalPages: number;
  };
};

const formatBytes = (bytes: number): string => {
  if (!bytes && bytes !== 0) return "-";
  if (bytes === 0) return "0 B";
  const k = 1024;
  const sizes = ["B", "KB", "MB", "GB", "TB", "PB"];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  const value = bytes / Math.pow(k, i);
  return `${value.toFixed(i < 2 ? 0 : 1)} ${sizes[i]}`;
};

const VMs = () => {
  const [page, setPage] = useState(0); // API is 0-based
  const [size] = useState(20);
  const [search, setSearch] = useState("");

  const { data, isLoading, isError, refetch } = useQuery<VmApiResponse>({
    queryKey: ["vms", page, size],
    queryFn: async () => {
      const res = await api.get("/vms", { params: { page, size } });
      return res.data as VmApiResponse;
    },
    staleTime: 10_000,
    placeholderData: (previousData) => previousData,
  });

  const vms = data?.content ?? [];

  const filtered = useMemo(() => {
    const q = search.trim().toLowerCase();
    if (!q) return vms;
    return vms.filter((vm) =>
      vm.hostname.toLowerCase().includes(q) ||
      vm.ipAddress.toLowerCase().includes(q) ||
      vm.osType.toLowerCase().includes(q)
    );
  }, [vms, search]);

  const totalPages = data?.page?.totalPages ?? 1;
  const totalElements = data?.page?.totalElements ?? filtered.length;
  const currentPage = (data?.page?.number ?? page) + 1;

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Machines Virtuelles</h1>
          <p className="text-muted-foreground">Gérez vos machines virtuelles</p>
        </div>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle>Liste des VMs</CardTitle>
              <CardDescription>
                {totalElements} machines virtuelles {search ? "trouvées" : "au total"}
              </CardDescription>
            </div>
            <div className="flex items-center space-x-2">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
                <Input
                  placeholder="Rechercher par nom, IP, OS..."
                  className="pl-10 w-64"
                  value={search}
                  onChange={(e) => setSearch(e.target.value)}
                />
              </div>
              {isLoading && <span className="text-sm text-muted-foreground">Chargement...</span>}
              {isError && (
                <Button variant="destructive" size="sm" onClick={() => refetch()}>Réessayer</Button>
              )}
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="select-none">
                  <div className="flex items-center space-x-1">
                    <span>Nom</span>
                  </div>
                </TableHead>
                <TableHead className="select-none">
                  <div className="flex items-center space-x-1">
                    <span>IP</span>
                  </div>
                </TableHead>
                <TableHead className="select-none">
                  <div className="flex items-center space-x-1">
                    <span>OS</span>
                  </div>
                </TableHead>
                <TableHead>Ressources</TableHead>
                <TableHead className="select-none">
                  <div className="flex items-center space-x-1">
                    <span>Uptime</span>
                  </div>
                </TableHead>
                <TableHead className="w-[50px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {filtered.map((vm) => (
                <TableRow key={vm.hostname} className="hover:bg-accent/50 transition-smooth cursor-pointer" onClick={() => window.location.href = `/vms/${vm.id}`}>
                  <TableCell className="font-medium">
                    <div className="flex items-center space-x-2">
                      <Server className="h-4 w-4 text-muted-foreground" />
                      <span>{vm.hostname}</span>
                    </div>
                  </TableCell>
                  <TableCell className="font-mono text-sm">{vm.ipAddress}</TableCell>
                  <TableCell>{vm.osType}</TableCell>
                  <TableCell>
                    <div className="text-sm space-y-1">
                      <div>{vm.cpuCores} vCPU • {formatBytes(vm.ramTotal)}</div>
                      <div className="text-muted-foreground">{formatBytes(vm.diskTotal)}</div>
                    </div>
                  </TableCell>
                  <TableCell className="font-mono text-sm">{vm.uptime}</TableCell>
                  <TableCell>
                    <Button asChild variant="ghost" size="icon">
                      <Link to={`/vms/${vm.hostname}`} className="flex items-center">
                        <Eye className="h-4 w-4" />
                      </Link>
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <div className="flex items-center justify-between mt-6">
            <p className="text-sm text-muted-foreground">
              Page {currentPage} sur {totalPages} — {totalElements} résultats
            </p>
            <div className="flex items-center space-x-2">
              <Button
                variant="outline"
                size="sm"
                onClick={() => setPage(Math.max(0, page - 1))}
                disabled={page === 0 || isLoading}
              >
                <ChevronLeft className="h-4 w-4" />
                Précédent
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={() => setPage(Math.min((totalPages - 1), page + 1))}
                disabled={currentPage === totalPages || isLoading}
              >
                Suivant
                <ChevronRight className="h-4 w-4" />
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default VMs;

