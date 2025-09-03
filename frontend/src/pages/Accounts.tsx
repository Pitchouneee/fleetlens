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
  User, 
  Search, 
  Filter, 
  Server,
  ChevronLeft,
  ChevronRight
} from "lucide-react";
import { Link } from "react-router-dom";

const Accounts = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchFilter, setSearchFilter] = useState("");
  const itemsPerPage = 10;

  // Mock data - in real app would fetch from API
  const accounts = [
    {
      id: 1,
      username: "admin",
      role: "Administrateur",
      lastLogin: "2024-01-25 14:30",
      status: "active",
      vms: [
        { id: "vm-001", name: "web-server-01", status: "running" },
        { id: "vm-002", name: "db-primary", status: "running" },
        { id: "vm-004", name: "backup-server", status: "running" }
      ]
    },
    {
      id: 2,
      username: "webdev",
      role: "Développeur",
      lastLogin: "2024-01-25 11:45",
      status: "active",
      vms: [
        { id: "vm-001", name: "web-server-01", status: "running" },
        { id: "vm-005", name: "dev-environment", status: "running" }
      ]
    },
    {
      id: 3,
      username: "backup",
      role: "Service",
      lastLogin: "2024-01-24 02:00",
      status: "inactive",
      vms: [
        { id: "vm-004", name: "backup-server", status: "running" }
      ]
    },
    {
      id: 4,
      username: "dbadmin",
      role: "DBA",
      lastLogin: "2024-01-25 09:15",
      status: "active",
      vms: [
        { id: "vm-002", name: "db-primary", status: "running" }
      ]
    },
    {
      id: 5,
      username: "devops",
      role: "DevOps",
      lastLogin: "2024-01-25 16:20",
      status: "active",
      vms: [
        { id: "vm-001", name: "web-server-01", status: "running" },
        { id: "vm-002", name: "db-primary", status: "running" },
        { id: "vm-003", name: "cache-redis", status: "stopped" },
        { id: "vm-004", name: "backup-server", status: "running" },
        { id: "vm-005", name: "dev-environment", status: "running" }
      ]
    }
  ];

  const filteredAccounts = accounts.filter(account => 
    account.username.toLowerCase().includes(searchFilter.toLowerCase()) ||
    account.role.toLowerCase().includes(searchFilter.toLowerCase())
  );
  const totalPages = Math.ceil(filteredAccounts.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedAccounts = filteredAccounts.slice(startIndex, startIndex + itemsPerPage);

  const getStatusBadge = (status: string) => {
    if (status === "active") {
      return <Badge className="bg-success text-success-foreground">Actif</Badge>;
    }
    return <Badge variant="secondary">Inactif</Badge>;
  };

  const getVmStatusBadge = (status: string) => {
    if (status === "running") {
      return <Badge variant="outline" className="text-xs">En ligne</Badge>;
    }
    return <Badge variant="secondary" className="text-xs">Arrêtée</Badge>;
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Comptes Utilisateurs</h1>
          <p className="text-muted-foreground">Gérez tous les comptes utilisateurs et leurs accès aux VMs</p>
        </div>
        <Button className="bg-gradient-primary hover:opacity-90">
          <User className="mr-2 h-4 w-4" />
          Nouveau Compte
        </Button>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle>Liste des Comptes</CardTitle>
              <CardDescription>
                {filteredAccounts.length} comptes utilisateurs {searchFilter ? 'trouvés' : 'au total'}
              </CardDescription>
            </div>
            <div className="flex items-center space-x-2">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
                <Input 
                  placeholder="Rechercher par nom ou rôle..." 
                  className="pl-10 w-64"
                  value={searchFilter}
                  onChange={(e) => setSearchFilter(e.target.value)}
                />
              </div>
              <Button variant="outline" size="icon">
                <Filter className="h-4 w-4" />
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Utilisateur</TableHead>
                <TableHead>Rôle</TableHead>
                <TableHead>Dernière Connexion</TableHead>
                <TableHead>VMs Rattachées</TableHead>
                <TableHead>Statut</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {paginatedAccounts.map((account) => (
                <TableRow key={account.id} className="hover:bg-accent/50 transition-smooth">
                  <TableCell className="font-medium">
                    <div className="flex items-center space-x-2">
                      <User className="h-4 w-4 text-muted-foreground" />
                      <span>{account.username}</span>
                    </div>
                  </TableCell>
                  <TableCell>{account.role}</TableCell>
                  <TableCell className="font-mono text-sm">{account.lastLogin}</TableCell>
                  <TableCell>
                    <div className="space-y-1">
                      <div className="text-sm font-medium">{account.vms.length} VM(s)</div>
                      <div className="flex flex-wrap gap-1">
                        {account.vms.slice(0, 3).map((vm) => (
                          <Link key={vm.id} to={`/vms/${vm.id}`}>
                            <div className="flex items-center space-x-1 px-2 py-1 rounded bg-accent/30 hover:bg-accent/50 transition-smooth">
                              <Server className="h-3 w-3" />
                              <span className="text-xs">{vm.name}</span>
                              {getVmStatusBadge(vm.status)}
                            </div>
                          </Link>
                        ))}
                        {account.vms.length > 3 && (
                          <span className="text-xs text-muted-foreground px-2 py-1">
                            +{account.vms.length - 3} autres
                          </span>
                        )}
                      </div>
                    </div>
                  </TableCell>
                  <TableCell>{getStatusBadge(account.status)}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <div className="flex items-center justify-between mt-6">
            <p className="text-sm text-muted-foreground">
              Affichage de {startIndex + 1} à {Math.min(startIndex + itemsPerPage, filteredAccounts.length)} sur {filteredAccounts.length} résultats
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

export default Accounts;