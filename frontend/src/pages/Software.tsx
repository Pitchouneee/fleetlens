// import { useState } from "react";
// import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
// import { Button } from "@/components/ui/button";
// import { Input } from "@/components/ui/input";
// import { Badge } from "@/components/ui/badge";
// import { 
//   Table,
//   TableBody,
//   TableCell,
//   TableHead,
//   TableHeader,
//   TableRow,
// } from "@/components/ui/table";
// import { 
//   Package, 
//   Search, 
//   Filter, 
//   Server,
//   ChevronLeft,
//   ChevronRight
// } from "lucide-react";
// import { Link } from "react-router-dom";

// const Software = () => {
//   const [currentPage, setCurrentPage] = useState(1);
//   const [searchFilter, setSearchFilter] = useState("");
//   const itemsPerPage = 10;

//   // Mock data - in real app would fetch from API
//   const software = [
//     {
//       id: 1,
//       name: "Apache",
//       category: "Web Server",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "2.4.54", status: "running" }
//       ]
//     },
//     {
//       id: 2,
//       name: "MySQL",
//       category: "Database",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "8.0.33", status: "running" },
//         { id: "vm-002", name: "db-primary", version: "8.0.35", status: "running" }
//       ]
//     },
//     {
//       id: 3,
//       name: "PHP",
//       category: "Runtime",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "8.1.2", status: "installed" },
//         { id: "vm-005", name: "dev-environment", version: "8.2.0", status: "installed" }
//       ]
//     },
//     {
//       id: 4,
//       name: "Node.js",
//       category: "Runtime",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "18.17.0", status: "installed" },
//         { id: "vm-005", name: "dev-environment", version: "20.5.0", status: "installed" }
//       ]
//     },
//     {
//       id: 5,
//       name: "Docker",
//       category: "Container",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "24.0.5", status: "running" },
//         { id: "vm-002", name: "db-primary", version: "24.0.5", status: "running" },
//         { id: "vm-005", name: "dev-environment", version: "24.0.7", status: "running" }
//       ]
//     },
//     {
//       id: 6,
//       name: "Git",
//       category: "VCS",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "2.34.1", status: "installed" },
//         { id: "vm-005", name: "dev-environment", version: "2.40.1", status: "installed" }
//       ]
//     },
//     {
//       id: 7,
//       name: "Nginx",
//       category: "Web Server",
//       vms: [
//         { id: "vm-001", name: "web-server-01", version: "1.18.0", status: "stopped" },
//         { id: "vm-002", name: "db-primary", version: "1.18.0", status: "running" }
//       ]
//     },
//     {
//       id: 8,
//       name: "Redis",
//       category: "Cache",
//       vms: [
//         { id: "vm-003", name: "cache-redis", version: "7.0.12", status: "stopped" }
//       ]
//     },
//     {
//       id: 9,
//       name: "PostgreSQL",
//       category: "Database",
//       vms: [
//         { id: "vm-005", name: "dev-environment", version: "15.3", status: "running" }
//       ]
//     },
//     {
//       id: 10,
//       name: "Python",
//       category: "Runtime",
//       vms: [
//         { id: "vm-004", name: "backup-server", version: "3.9.2", status: "installed" },
//         { id: "vm-005", name: "dev-environment", version: "3.11.4", status: "installed" }
//       ]
//     }
//   ];

//   const filteredSoftware = software.filter(soft => 
//     soft.name.toLowerCase().includes(searchFilter.toLowerCase()) ||
//     soft.category.toLowerCase().includes(searchFilter.toLowerCase())
//   );
//   const totalPages = Math.ceil(filteredSoftware.length / itemsPerPage);
//   const startIndex = (currentPage - 1) * itemsPerPage;
//   const paginatedSoftware = filteredSoftware.slice(startIndex, startIndex + itemsPerPage);

//   const getStatusBadge = (status: string) => {
//     if (status === "running") {
//       return <Badge className="bg-success text-success-foreground text-xs">En cours</Badge>;
//     }
//     if (status === "installed") {
//       return <Badge variant="secondary" className="text-xs">Installé</Badge>;
//     }
//     return <Badge variant="outline" className="text-xs">Arrêté</Badge>;
//   };

//   const getCategoryBadge = (category: string) => {
//     const colors: Record<string, string> = {
//       "Web Server": "bg-blue-100 text-blue-800",
//       "Database": "bg-green-100 text-green-800",
//       "Runtime": "bg-purple-100 text-purple-800",
//       "Container": "bg-orange-100 text-orange-800",
//       "VCS": "bg-gray-100 text-gray-800",
//       "Cache": "bg-red-100 text-red-800"
//     };
    
//     return (
//       <Badge variant="outline" className={`text-xs ${colors[category] || "bg-gray-100 text-gray-800"}`}>
//         {category}
//       </Badge>
//     );
//   };

//   return (
//     <div className="space-y-6">
//       <div className="flex items-center justify-between">
//         <div>
//           <h1 className="text-3xl font-bold tracking-tight">Logiciels</h1>
//           <p className="text-muted-foreground">Gérez tous les logiciels installés sur vos VMs</p>
//         </div>
//         <Button className="bg-gradient-primary hover:opacity-90">
//           <Package className="mr-2 h-4 w-4" />
//           Installer Logiciel
//         </Button>
//       </div>

//       <Card className="shadow-card">
//         <CardHeader>
//           <div className="flex items-center justify-between">
//             <div>
//               <CardTitle>Liste des Logiciels</CardTitle>
//               <CardDescription>
//                 {filteredSoftware.length} logiciels {searchFilter ? 'trouvés' : 'au total'}
//               </CardDescription>
//             </div>
//             <div className="flex items-center space-x-2">
//               <div className="relative">
//                 <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
//                 <Input 
//                   placeholder="Rechercher par nom ou catégorie..." 
//                   className="pl-10 w-64"
//                   value={searchFilter}
//                   onChange={(e) => setSearchFilter(e.target.value)}
//                 />
//               </div>
//               <Button variant="outline" size="icon">
//                 <Filter className="h-4 w-4" />
//               </Button>
//             </div>
//           </div>
//         </CardHeader>
//         <CardContent>
//           <Table>
//             <TableHeader>
//               <TableRow>
//                 <TableHead>Logiciel</TableHead>
//                 <TableHead>Catégorie</TableHead>
//                 <TableHead>VMs Installées</TableHead>
//                 <TableHead>Versions & Statuts</TableHead>
//               </TableRow>
//             </TableHeader>
//             <TableBody>
//               {paginatedSoftware.map((soft) => (
//                 <TableRow key={soft.id} className="hover:bg-accent/50 transition-smooth">
//                   <TableCell className="font-medium">
//                     <div className="flex items-center space-x-2">
//                       <Package className="h-4 w-4 text-muted-foreground" />
//                       <span>{soft.name}</span>
//                     </div>
//                   </TableCell>
//                   <TableCell>{getCategoryBadge(soft.category)}</TableCell>
//                   <TableCell>
//                     <div className="space-y-1">
//                       <div className="text-sm font-medium">{soft.vms.length} VM(s)</div>
//                       <div className="flex flex-wrap gap-1">
//                         {soft.vms.slice(0, 2).map((vm) => (
//                           <Link key={vm.id} to={`/vms/${vm.id}`}>
//                             <div className="flex items-center space-x-1 px-2 py-1 rounded bg-accent/30 hover:bg-accent/50 transition-smooth">
//                               <Server className="h-3 w-3" />
//                               <span className="text-xs">{vm.name}</span>
//                             </div>
//                           </Link>
//                         ))}
//                         {soft.vms.length > 2 && (
//                           <span className="text-xs text-muted-foreground px-2 py-1">
//                             +{soft.vms.length - 2} autres
//                           </span>
//                         )}
//                       </div>
//                     </div>
//                   </TableCell>
//                   <TableCell>
//                     <div className="space-y-1">
//                       {soft.vms.map((vm, index) => (
//                         <div key={index} className="flex items-center space-x-2 text-xs">
//                           <span className="font-mono">{vm.version}</span>
//                           {getStatusBadge(vm.status)}
//                         </div>
//                       ))}
//                     </div>
//                   </TableCell>
//                 </TableRow>
//               ))}
//             </TableBody>
//           </Table>

//           <div className="flex items-center justify-between mt-6">
//             <p className="text-sm text-muted-foreground">
//               Affichage de {startIndex + 1} à {Math.min(startIndex + itemsPerPage, filteredSoftware.length)} sur {filteredSoftware.length} résultats
//             </p>
//             <div className="flex items-center space-x-2">
//               <Button
//                 variant="outline"
//                 size="sm"
//                 onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
//                 disabled={currentPage === 1}
//               >
//                 <ChevronLeft className="h-4 w-4" />
//                 Précédent
//               </Button>
//               <span className="text-sm">
//                 Page {currentPage} sur {totalPages}
//               </span>
//               <Button
//                 variant="outline"
//                 size="sm"
//                 onClick={() => setCurrentPage(Math.min(totalPages, currentPage + 1))}
//                 disabled={currentPage === totalPages}
//               >
//                 Suivant
//                 <ChevronRight className="h-4 w-4" />
//               </Button>
//             </div>
//           </div>
//         </CardContent>
//       </Card>
//     </div>
//   );
// };

// export default Software;