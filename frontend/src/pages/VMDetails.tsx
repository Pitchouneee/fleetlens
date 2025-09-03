import { useParams, Link } from "react-router-dom";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { 
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { 
  ArrowLeft,
  Server, 
  Cpu,
  HardDrive,
  MemoryStick,
  Network,
  Shield,
  Users,
  Clock,
  Power,
  Settings,
  Package,
  User
} from "lucide-react";

const VMDetails = () => {
  const { id } = useParams();

  // Mock data - in real app would fetch from API
  const vm = {
    id: id,
    name: "web-server-01",
    ip: "192.168.1.10",
    os: "Ubuntu 22.04 LTS",
    architecture: "x86_64",
    disk: "40GB",
    diskUsed: "24GB",
    ram: "4GB",
    ramUsed: "2.8GB", 
    cpu: "2 vCPU",
    cpuUsage: "45%",
    accounts: 3,
    uptime: "15d 4h 32m",
    status: "running",
    interfaces: [
      { name: "eth0", ip: "192.168.1.10", mac: "00:16:3e:12:34:56" },
      { name: "eth1", ip: "10.0.0.5", mac: "00:16:3e:78:90:ab" }
    ],
    openPorts: [
      { port: 22, protocol: "TCP", service: "SSH" },
      { port: 80, protocol: "TCP", service: "HTTP" },
      { port: 443, protocol: "TCP", service: "HTTPS" },
      { port: 3306, protocol: "TCP", service: "MySQL" }
    ],
    attachedUsers: [
      { id: 1, username: "admin", role: "Administrateur", lastLogin: "2024-01-25 14:30", status: "active" },
      { id: 2, username: "webdev", role: "Développeur", lastLogin: "2024-01-25 11:45", status: "active" },
      { id: 3, username: "backup", role: "Service", lastLogin: "2024-01-24 02:00", status: "inactive" }
    ],
    installedSoftware: [
      { name: "Apache", version: "2.4.54", category: "Web Server", status: "running" },
      { name: "MySQL", version: "8.0.33", category: "Database", status: "running" },
      { name: "PHP", version: "8.1.2", category: "Runtime", status: "installed" },
      { name: "Node.js", version: "18.17.0", category: "Runtime", status: "installed" },
      { name: "Docker", version: "24.0.5", category: "Container", status: "running" },
      { name: "Git", version: "2.34.1", category: "VCS", status: "installed" },
      { name: "Nginx", version: "1.18.0", category: "Web Server", status: "stopped" }
    ]
  };

  const getStatusBadge = (status: string) => {
    if (status === "running") {
      return <Badge className="bg-success text-success-foreground">En ligne</Badge>;
    }
    return <Badge variant="secondary">Arrêtée</Badge>;
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center space-x-4">
        <Button variant="outline" size="icon" asChild>
          <Link to="/vms">
            <ArrowLeft className="h-4 w-4" />
          </Link>
        </Button>
        <div className="flex-1">
          <h1 className="text-3xl font-bold tracking-tight">{vm.name}</h1>
          <p className="text-muted-foreground">Détails de la machine virtuelle</p>
        </div>
        <div className="flex items-center space-x-2">
          {getStatusBadge(vm.status)}
          <Button variant="outline">
            <Settings className="mr-2 h-4 w-4" />
            Configurer
          </Button>
          <Button variant={vm.status === "running" ? "destructive" : "default"}>
            <Power className="mr-2 h-4 w-4" />
            {vm.status === "running" ? "Arrêter" : "Démarrer"}
          </Button>
        </div>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Système</CardTitle>
            <Server className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm.os}</div>
            <div className="text-sm text-muted-foreground">
              Architecture: {vm.architecture}
            </div>
            <div className="text-sm text-muted-foreground">
              IP principale: {vm.ip}
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">CPU</CardTitle>
            <Cpu className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm.cpu}</div>
            <div className="text-sm text-muted-foreground">
              Utilisation: {vm.cpuUsage}
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Mémoire</CardTitle>
            <MemoryStick className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm.ram}</div>
            <div className="text-sm text-muted-foreground">
              Utilisée: {vm.ramUsed}
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Stockage</CardTitle>
            <HardDrive className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm.disk}</div>
            <div className="text-sm text-muted-foreground">
              Utilisé: {vm.diskUsed}
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Comptes</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm.accounts}</div>
            <div className="text-sm text-muted-foreground">
              Utilisateurs actifs
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Uptime</CardTitle>
            <Clock className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="text-2xl font-bold">{vm.uptime}</div>
            <div className="text-sm text-muted-foreground">
              Temps de fonctionnement
            </div>
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
              {vm.interfaces.map((interface_, index) => (
                <div key={index}>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="font-medium">{interface_.name}</p>
                      <p className="text-sm text-muted-foreground">IP: {interface_.ip}</p>
                      <p className="text-sm text-muted-foreground">MAC: {interface_.mac}</p>
                    </div>
                    <Badge variant="outline">Actif</Badge>
                  </div>
                  {index < vm.interfaces.length - 1 && <Separator className="mt-4" />}
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
              {vm.openPorts.map((port, index) => (
                <div key={index} className="flex items-center justify-between p-3 rounded-lg bg-accent/50">
                  <div>
                    <p className="font-medium">Port {port.port}/{port.protocol}</p>
                    <p className="text-sm text-muted-foreground">{port.service}</p>
                  </div>
                  <Badge variant="secondary">Ouvert</Badge>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Détails avancés</CardTitle>
          <CardDescription>Informations détaillées sur les utilisateurs et logiciels</CardDescription>
        </CardHeader>
        <CardContent>
          <Accordion type="single" collapsible className="w-full">
            <AccordionItem value="users">
              <AccordionTrigger className="flex items-center">
                <div className="flex items-center">
                  <User className="mr-2 h-4 w-4" />
                  Utilisateurs Rattachés ({vm.attachedUsers.length})
                </div>
              </AccordionTrigger>
              <AccordionContent>
                <div className="space-y-4 pt-4">
                  {vm.attachedUsers.map((user, index) => (
                    <div key={index}>
                      <div className="flex items-center justify-between p-4 rounded-lg bg-accent/30">
                        <div>
                          <p className="font-medium">{user.username}</p>
                          <p className="text-sm text-muted-foreground">Rôle: {user.role}</p>
                          <p className="text-sm text-muted-foreground">Dernière connexion: {user.lastLogin}</p>
                        </div>
                        <Badge variant={user.status === "active" ? "default" : "secondary"}>
                          {user.status === "active" ? "Actif" : "Inactif"}
                        </Badge>
                      </div>
                      {index < vm.attachedUsers.length - 1 && <Separator className="my-2" />}
                    </div>
                  ))}
                </div>
              </AccordionContent>
            </AccordionItem>

            <AccordionItem value="software">
              <AccordionTrigger className="flex items-center">
                <div className="flex items-center">
                  <Package className="mr-2 h-4 w-4" />
                  Logiciels Installés ({vm.installedSoftware.length})
                </div>
              </AccordionTrigger>
              <AccordionContent>
                <div className="space-y-3 pt-4">
                  {vm.installedSoftware.map((software, index) => (
                    <div key={index} className="flex items-center justify-between p-4 rounded-lg bg-accent/30">
                      <div>
                        <p className="font-medium">{software.name}</p>
                        <p className="text-sm text-muted-foreground">Version: {software.version}</p>
                        <p className="text-xs text-muted-foreground">{software.category}</p>
                      </div>
                      <Badge variant={
                        software.status === "running" ? "default" :
                        software.status === "installed" ? "secondary" : "outline"
                      }>
                        {software.status === "running" ? "En cours" :
                         software.status === "installed" ? "Installé" : "Arrêté"}
                      </Badge>
                    </div>
                  ))}
                </div>
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </CardContent>
      </Card>
    </div>
  );
};

export default VMDetails;