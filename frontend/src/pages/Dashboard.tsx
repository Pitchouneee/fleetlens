import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Server, Cpu, HardDrive, Activity } from "lucide-react";
import { PieChart, Pie, Cell, ResponsiveContainer, BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip } from "recharts";

const Dashboard = () => {
  const stats = [
    {
      title: "Machines Virtuelles",
      value: "24",
      description: "Total des VMs actives",
      icon: Server,
      color: "text-primary"
    },
    {
      title: "CPU Total",
      value: "96",
      description: "Cœurs alloués",
      icon: Cpu,
      color: "text-success"
    },
    {
      title: "Stockage",
      value: "2.4TB",
      description: "Espace utilisé",
      icon: HardDrive,
      color: "text-warning"
    },
    {
      title: "Uptime Moyen",
      value: "99.8%",
      description: "Derniers 30 jours",
      icon: Activity,
      color: "text-success"
    }
  ];

  const osData = [
    { name: "Ubuntu", value: 12, color: "#E95420" },
    { name: "CentOS", value: 6, color: "#932279" },
    { name: "Windows", value: 4, color: "#0078D4" },
    { name: "Debian", value: 2, color: "#A81D33" }
  ];

  const monthlyData = [
    { month: "Jan", vms: 18 },
    { month: "Fév", vms: 20 },
    { month: "Mar", vms: 22 },
    { month: "Avr", vms: 24 },
    { month: "Mai", vms: 24 },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
        <p className="text-muted-foreground">Vue d'ensemble de votre infrastructure</p>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <Card key={stat.title} className="shadow-card hover:shadow-lg transition-smooth">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">{stat.title}</CardTitle>
              <stat.icon className={`h-4 w-4 ${stat.color}`} />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stat.value}</div>
              <p className="text-xs text-muted-foreground">{stat.description}</p>
            </CardContent>
          </Card>
        ))}
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="shadow-card">
          <CardHeader>
            <CardTitle>Répartition des OS</CardTitle>
            <CardDescription>Distribution des systèmes d'exploitation</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={osData}
                    cx="50%"
                    cy="50%"
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                    label={({ name, value }) => `${name}: ${value}`}
                  >
                    {osData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </div>
            <div className="flex flex-wrap gap-2 mt-4">
              {osData.map((os) => (
                <Badge key={os.name} variant="secondary" className="flex items-center gap-1">
                  <div 
                    className="w-2 h-2 rounded-full" 
                    style={{ backgroundColor: os.color }}
                  />
                  {os.name} ({os.value})
                </Badge>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader>
            <CardTitle>Évolution des VMs</CardTitle>
            <CardDescription>Nombre de machines virtuelles par mois</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={monthlyData}>
                  <CartesianGrid strokeDasharray="3 3" className="opacity-20" />
                  <XAxis dataKey="month" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="vms" fill="hsl(var(--primary))" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>VMs Récentes</CardTitle>
          <CardDescription>Les 5 dernières machines virtuelles créées</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            {[
              { name: "web-server-01", ip: "192.168.1.10", os: "Ubuntu 22.04", status: "running" },
              { name: "db-primary", ip: "192.168.1.15", os: "CentOS 8", status: "running" },
              { name: "cache-redis", ip: "192.168.1.20", os: "Ubuntu 20.04", status: "stopped" },
              { name: "backup-server", ip: "192.168.1.25", os: "Debian 11", status: "running" },
              { name: "dev-environment", ip: "192.168.1.30", os: "Windows Server", status: "running" }
            ].map((vm, index) => (
              <div key={index} className="flex items-center justify-between p-3 rounded-lg bg-accent/50 transition-smooth hover:bg-accent">
                <div className="flex items-center space-x-4">
                  <Server className="h-4 w-4 text-muted-foreground" />
                  <div>
                    <p className="font-medium">{vm.name}</p>
                    <p className="text-sm text-muted-foreground">{vm.ip} • {vm.os}</p>
                  </div>
                </div>
                <Badge 
                  variant={vm.status === "running" ? "default" : "secondary"}
                  className={vm.status === "running" ? "bg-success text-success-foreground" : ""}
                >
                  {vm.status === "running" ? "En ligne" : "Arrêtée"}
                </Badge>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Dashboard;