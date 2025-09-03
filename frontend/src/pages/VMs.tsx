import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import { 
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { 
  Server, 
  Search, 
  Filter, 
  Eye, 
  Power,
  MoreHorizontal,
  ChevronLeft,
  ChevronRight,
  ArrowUpDown,
  ArrowUp,
  ArrowDown
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";
import { Link } from "react-router-dom";

const VMs = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchFilter, setSearchFilter] = useState("");
  const [sortColumn, setSortColumn] = useState<string | null>(null);
  const [sortDirection, setSortDirection] = useState<"asc" | "desc">("asc");
  const [statusFilters, setStatusFilters] = useState<string[]>(["running", "stopped"]);
  const [osFilters, setOSFilters] = useState<string[]>([]);
  const itemsPerPage = 10;

  const vms = [
    {
      id: "vm-001",
      name: "web-server-01",
      ip: "192.168.1.10",
      os: "Ubuntu 22.04",
      architecture: "x86_64",
      disk: "40GB",
      ram: "4GB",
      cpu: "2 vCPU",
      accounts: 3,
      uptime: "15d 4h 32m",
      status: "running"
    },
    {
      id: "vm-002", 
      name: "db-primary",
      ip: "192.168.1.15",
      os: "CentOS 8",
      architecture: "x86_64",
      disk: "100GB",
      ram: "8GB",
      cpu: "4 vCPU",
      accounts: 2,
      uptime: "8d 12h 15m",
      status: "running"
    },
    {
      id: "vm-003",
      name: "cache-redis",
      ip: "192.168.1.20", 
      os: "Ubuntu 20.04",
      architecture: "x86_64",
      disk: "20GB",
      ram: "2GB",
      cpu: "1 vCPU",
      accounts: 1,
      uptime: "0d 0h 0m",
      status: "stopped"
    },
    {
      id: "vm-004",
      name: "backup-server",
      ip: "192.168.1.25",
      os: "Debian 11",
      architecture: "x86_64",
      disk: "500GB",
      ram: "4GB", 
      cpu: "2 vCPU",
      accounts: 2,
      uptime: "22d 8h 45m",
      status: "running"
    },
    {
      id: "vm-005",
      name: "dev-environment",
      ip: "192.168.1.30",
      os: "Windows Server 2019",
      architecture: "x86_64",
      disk: "80GB",
      ram: "8GB",
      cpu: "4 vCPU", 
      accounts: 5,
      uptime: "3d 2h 12m",
      status: "running"
    }
  ];

  const availableOS = [...new Set(vms.map(vm => vm.os))];

  const handleSort = (column: string) => {
    if (sortColumn === column) {
      setSortDirection(sortDirection === "asc" ? "desc" : "asc");
    } else {
      setSortColumn(column);
      setSortDirection("asc");
    }
  };

  const getSortIcon = (column: string) => {
    if (sortColumn !== column) {
      return <ArrowUpDown className="h-4 w-4 text-muted-foreground" />;
    }
    return sortDirection === "asc" 
      ? <ArrowUp className="h-4 w-4 text-primary" /> 
      : <ArrowDown className="h-4 w-4 text-primary" />;
  };

  const sortVMs = (vmList: Array<{
    id: string;
    name: string;
    ip: string;
    os: string;
    architecture: string;
    disk: string;
    ram: string;
    cpu: string;
    accounts: number;
    uptime: string;
    status: string;
  }>) => {
    if (!sortColumn) return vmList;

    return [...vmList].sort((a, b) => {
      let aValue: any = a[sortColumn as keyof typeof a];
      let bValue: any = b[sortColumn as keyof typeof b];

      // Gestion spéciale pour l'uptime
      if (sortColumn === "uptime") {
        const parseUptime = (uptime: string) => {
          const parts = uptime.split(" ");
          let totalMinutes = 0;
          for (let i = 0; i < parts.length; i += 2) {
            const value = parseInt(parts[i]);
            const unit = parts[i + 1];
            if (unit && unit.startsWith("d")) totalMinutes += value * 24 * 60;
            if (unit && unit.startsWith("h")) totalMinutes += value * 60;
            if (unit && unit.startsWith("m")) totalMinutes += value;
          }
          return totalMinutes;
        };
        aValue = parseUptime(aValue);
        bValue = parseUptime(bValue);
      }

      // Gestion spéciale pour les ressources (RAM, CPU)
      if (sortColumn === "ram") {
        aValue = parseInt(aValue);
        bValue = parseInt(bValue);
      }

      if (sortColumn === "cpu") {
        aValue = parseInt(aValue);
        bValue = parseInt(bValue);
      }

      if (typeof aValue === "string") {
        aValue = aValue.toLowerCase();
        bValue = bValue.toLowerCase();
      }

      if (sortDirection === "asc") {
        return aValue < bValue ? -1 : aValue > bValue ? 1 : 0;
      } else {
        return aValue > bValue ? -1 : aValue < bValue ? 1 : 0;
      }
    });
  };

  const handleStatusFilterChange = (status: string, checked: boolean) => {
    if (checked) {
      setStatusFilters([...statusFilters, status]);
    } else {
      setStatusFilters(statusFilters.filter(s => s !== status));
    }
  };

  const handleOSFilterChange = (os: string, checked: boolean) => {
    if (checked) {
      setOSFilters([...osFilters, os]);
    } else {
      setOSFilters(osFilters.filter(o => o !== os));
    }
  };

  const filteredAndSortedVms = sortVMs(
    vms.filter(vm => {
      const matchesSearch = vm.name.toLowerCase().includes(searchFilter.toLowerCase());
      const matchesStatus = statusFilters.length === 0 || statusFilters.includes(vm.status);
      const matchesOS = osFilters.length === 0 || osFilters.includes(vm.os);
      return matchesSearch && matchesStatus && matchesOS;
    })
  );

  const totalPages = Math.ceil(filteredAndSortedVms.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedVms = filteredAndSortedVms.slice(startIndex, startIndex + itemsPerPage);

  const getStatusBadge = (status: string) => {
    if (status === "running") {
      return <Badge className="bg-success text-success-foreground">En ligne</Badge>;
    }
    return <Badge variant="secondary">Arrêtée</Badge>;
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Machines Virtuelles</h1>
          <p className="text-muted-foreground">Gérez vos machines virtuelles</p>
        </div>
        <Button className="bg-gradient-primary hover:opacity-90">
          <Server className="mr-2 h-4 w-4" />
          Nouvelle VM
        </Button>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle>Liste des VMs</CardTitle>
              <CardDescription>
                {filteredAndSortedVms.length} machines virtuelles {searchFilter ? 'trouvées' : 'au total'}
              </CardDescription>
            </div>
            <div className="flex items-center space-x-2">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
                <Input 
                  placeholder="Rechercher par nom..." 
                  className="pl-10 w-64"
                  value={searchFilter}
                  onChange={(e) => setSearchFilter(e.target.value)}
                />
              </div>
              <Popover>
                <PopoverTrigger asChild>
                  <Button variant="outline" size="icon">
                    <Filter className="h-4 w-4" />
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-80">
                  <div className="space-y-4">
                    <h4 className="font-medium leading-none">Filtres</h4>
                    
                    <div className="space-y-3">
                      <Label className="text-sm font-medium">Statut</Label>
                      <div className="space-y-2">
                        <div className="flex items-center space-x-2">
                          <Checkbox 
                            id="status-running"
                            checked={statusFilters.includes("running")}
                            onCheckedChange={(checked) => handleStatusFilterChange("running", checked as boolean)}
                          />
                          <Label htmlFor="status-running" className="text-sm">En ligne</Label>
                        </div>
                        <div className="flex items-center space-x-2">
                          <Checkbox 
                            id="status-stopped"
                            checked={statusFilters.includes("stopped")}
                            onCheckedChange={(checked) => handleStatusFilterChange("stopped", checked as boolean)}
                          />
                          <Label htmlFor="status-stopped" className="text-sm">Arrêtée</Label>
                        </div>
                      </div>
                    </div>

                    <Separator />

                    <div className="space-y-3">
                      <Label className="text-sm font-medium">Système d'exploitation</Label>
                      <div className="space-y-2">
                        {availableOS.map((os) => (
                          <div key={os} className="flex items-center space-x-2">
                            <Checkbox 
                              id={`os-${os}`}
                              checked={osFilters.includes(os)}
                              onCheckedChange={(checked) => handleOSFilterChange(os, checked as boolean)}
                            />
                            <Label htmlFor={`os-${os}`} className="text-sm">{os}</Label>
                          </div>
                        ))}
                      </div>
                    </div>

                    <Separator />

                    <div className="flex justify-end space-x-2">
                      <Button 
                        variant="outline" 
                        size="sm"
                        onClick={() => {
                          setStatusFilters(["running", "stopped"]);
                          setOSFilters([]);
                        }}
                      >
                        Réinitialiser
                      </Button>
                    </div>
                  </div>
                </PopoverContent>
              </Popover>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead 
                  className="cursor-pointer hover:bg-accent/50 select-none"
                  onClick={() => handleSort("name")}
                >
                  <div className="flex items-center space-x-1">
                    <span>Nom</span>
                    {getSortIcon("name")}
                  </div>
                </TableHead>
                <TableHead 
                  className="cursor-pointer hover:bg-accent/50 select-none"
                  onClick={() => handleSort("ip")}
                >
                  <div className="flex items-center space-x-1">
                    <span>IP</span>
                    {getSortIcon("ip")}
                  </div>
                </TableHead>
                <TableHead 
                  className="cursor-pointer hover:bg-accent/50 select-none"
                  onClick={() => handleSort("os")}
                >
                  <div className="flex items-center space-x-1">
                    <span>OS</span>
                    {getSortIcon("os")}
                  </div>
                </TableHead>
                <TableHead>Ressources</TableHead>
                <TableHead 
                  className="cursor-pointer hover:bg-accent/50 select-none"
                  onClick={() => handleSort("uptime")}
                >
                  <div className="flex items-center space-x-1">
                    <span>Uptime</span>
                    {getSortIcon("uptime")}
                  </div>
                </TableHead>
                <TableHead 
                  className="cursor-pointer hover:bg-accent/50 select-none"
                  onClick={() => handleSort("status")}
                >
                  <div className="flex items-center space-x-1">
                    <span>Statut</span>
                    {getSortIcon("status")}
                  </div>
                </TableHead>
                <TableHead className="w-[50px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {paginatedVms.map((vm) => (
                <TableRow key={vm.id} className="hover:bg-accent/50 transition-smooth cursor-pointer" onClick={() => window.location.href = `/vms/${vm.id}`}>
                  <TableCell className="font-medium">
                    <div className="flex items-center space-x-2">
                      <Server className="h-4 w-4 text-muted-foreground" />
                      <span>{vm.name}</span>
                    </div>
                  </TableCell>
                  <TableCell className="font-mono text-sm">{vm.ip}</TableCell>
                  <TableCell>{vm.os}</TableCell>
                  <TableCell>
                    <div className="text-sm space-y-1">
                      <div>{vm.cpu} • {vm.ram}</div>
                      <div className="text-muted-foreground">{vm.disk}</div>
                    </div>
                  </TableCell>
                  <TableCell className="font-mono text-sm">{vm.uptime}</TableCell>
                  <TableCell>{getStatusBadge(vm.status)}</TableCell>
                  <TableCell>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" size="icon">
                          <MoreHorizontal className="h-4 w-4" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent align="end">
                        <DropdownMenuItem asChild>
                          <Link to={`/vms/${vm.id}`} className="flex items-center">
                            <Eye className="mr-2 h-4 w-4" />
                            Voir détails
                          </Link>
                        </DropdownMenuItem>
                        <DropdownMenuItem>
                          <Power className="mr-2 h-4 w-4" />
                          {vm.status === "running" ? "Arrêter" : "Démarrer"}
                        </DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <div className="flex items-center justify-between mt-6">
            <p className="text-sm text-muted-foreground">
              Affichage de {startIndex + 1} à {Math.min(startIndex + itemsPerPage, filteredAndSortedVms.length)} sur {filteredAndSortedVms.length} résultats
            </p>
            <div className="flex items-center space-x-2">
              <Button
                variant="outline"
                size="sm"
                onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
                disabled={currentPage === 1}
              >
                <ChevronLeft className="h-4 w-4" />
                Précédent
              </Button>
              <span className="text-sm">
                Page {currentPage} sur {totalPages}
              </span>
              <Button
                variant="outline"
                size="sm"
                onClick={() => setCurrentPage(Math.min(totalPages, currentPage + 1))}
                disabled={currentPage === totalPages}
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