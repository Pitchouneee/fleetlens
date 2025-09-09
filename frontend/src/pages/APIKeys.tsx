import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
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
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { 
  Key, 
  Plus, 
  Copy,
  Trash2,
  Eye,
  EyeOff,
  MoreHorizontal
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useToast } from "@/hooks/use-toast";

const APIKeys = () => {
  const [showKey, setShowKey] = useState<{ [key: string]: boolean }>({});
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false);
  const { toast } = useToast();

  const apiKeys = [
    {
      id: "key-001",
      name: "Production API",
      key: "sk_live_51H7Q2BFZzV8Z7Q2B...",
      created: "2024-01-15",
      lastUsed: "2024-01-20",
      status: "active"
    },
    {
      id: "key-002", 
      name: "Development",
      key: "sk_test_51H7Q2BFZzV8Z7Q2B...",
      created: "2024-01-10",
      lastUsed: "2024-01-19",
      status: "active"
    },
    {
      id: "key-003",
      name: "Monitoring",
      key: "sk_live_51H7Q2BFZzV8Z7Q2B...",
      created: "2023-12-20",
      lastUsed: "2024-01-18",
      status: "inactive"
    }
  ];

  const toggleKeyVisibility = (keyId: string) => {
    setShowKey(prev => ({
      ...prev,
      [keyId]: !prev[keyId]
    }));
  };

  const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(text);
    toast({
      title: "Copié !",
      description: "La clé API a été copiée dans le presse-papiers",
    });
  };

  const toggleKeyStatus = (keyId: string) => {
    // Ici on mettrait la logique pour changer le statut via l'API
    toast({
      title: "Statut modifié",
      description: "Le statut de la clé API a été mis à jour",
    });
  };

  const getStatusBadge = (status: string, keyId: string) => {
    if (status === "active") {
      return (
        <Badge 
          className="bg-success text-success-foreground cursor-pointer hover:opacity-80" 
          onClick={() => toggleKeyStatus(keyId)}
        >
          Active
        </Badge>
      );
    }
    return (
      <Badge 
        variant="secondary" 
        className="cursor-pointer hover:bg-muted"
        onClick={() => toggleKeyStatus(keyId)}
      >
        Inactive
      </Badge>
    );
  };

  const formatKey = (key: string, keyId: string) => {
    if (showKey[keyId]) {
      return key;
    }
    return key.substring(0, 12) + "..." + key.substring(key.length - 8);
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Clés API</h1>
          <p className="text-muted-foreground">Gérez vos clés d'accès à l'API</p>
        </div>
        <Dialog open={isCreateDialogOpen} onOpenChange={setIsCreateDialogOpen}>
          <DialogTrigger asChild>
            <Button className="bg-gradient-primary hover:opacity-90">
              <Plus className="mr-2 h-4 w-4" />
              Nouvelle clé
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Créer une nouvelle clé API</DialogTitle>
              <DialogDescription>
                Donnez un nom à votre clé API pour l'identifier facilement.
              </DialogDescription>
            </DialogHeader>
            <div className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="keyName">Nom de la clé</Label>
                <Input
                  id="keyName"
                  placeholder="Ex: Production API, Development..."
                />
              </div>
            </div>
            <DialogFooter>
              <Button variant="outline" onClick={() => setIsCreateDialogOpen(false)}>
                Annuler
              </Button>
              <Button onClick={() => setIsCreateDialogOpen(false)}>
                Créer la clé
              </Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Clés API actives</CardTitle>
          <CardDescription>
            Utilisez ces clés pour accéder à l'API Fleetlens
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Nom</TableHead>
                <TableHead>Clé</TableHead>
                <TableHead>Créée le</TableHead>
                <TableHead>Dernière utilisation</TableHead>
                <TableHead>Statut</TableHead>
                <TableHead className="w-[50px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {apiKeys.map((apiKey) => (
                <TableRow key={apiKey.id} className="hover:bg-accent/50 transition-smooth">
                  <TableCell className="font-medium">
                    <div className="flex items-center space-x-2">
                      <Key className="h-4 w-4 text-muted-foreground" />
                      <span>{apiKey.name}</span>
                    </div>
                  </TableCell>
                  <TableCell>
                    <div className="flex items-center space-x-2">
                      <code className="font-mono text-sm bg-accent px-2 py-1 rounded">
                        {formatKey(apiKey.key, apiKey.id)}
                      </code>
                      <Button
                        variant="ghost"
                        size="icon"
                        onClick={() => toggleKeyVisibility(apiKey.id)}
                      >
                        {showKey[apiKey.id] ? (
                          <EyeOff className="h-4 w-4" />
                        ) : (
                          <Eye className="h-4 w-4" />
                        )}
                      </Button>
                      <Button
                        variant="ghost"
                        size="icon"
                        onClick={() => copyToClipboard(apiKey.key)}
                      >
                        <Copy className="h-4 w-4" />
                      </Button>
                    </div>
                  </TableCell>
                  <TableCell>{apiKey.created}</TableCell>
                  <TableCell>{apiKey.lastUsed}</TableCell>
                  <TableCell>{getStatusBadge(apiKey.status, apiKey.id)}</TableCell>
                  <TableCell>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" size="icon">
                          <MoreHorizontal className="h-4 w-4" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent align="end">
                        <DropdownMenuItem onClick={() => copyToClipboard(apiKey.key)}>
                          <Copy className="mr-2 h-4 w-4" />
                          Copier
                        </DropdownMenuItem>
                        <DropdownMenuItem onClick={() => toggleKeyStatus(apiKey.id)}>
                          {apiKey.status === "active" ? "Désactiver" : "Activer"}
                        </DropdownMenuItem>
                        <DropdownMenuItem className="text-destructive">
                          <Trash2 className="mr-2 h-4 w-4" />
                          Supprimer
                        </DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Documentation API</CardTitle>
          <CardDescription>
            Comment utiliser vos clés API avec Fleetlens
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div>
            <h4 className="font-medium mb-2">Authentification</h4>
            <code className="text-sm bg-accent p-3 rounded block">
              curl -H "Authorization: Bearer YOUR_API_KEY" https://api.Fleetlens.com/v1/vms
            </code>
          </div>
          <div>
            <h4 className="font-medium mb-2">Exemple de requête</h4>
            <code className="text-sm bg-accent p-3 rounded block">
              {`{
  "method": "GET",
  "url": "https://api.Fleetlens.com/v1/vms",
  "headers": {
    "Authorization": "Bearer YOUR_API_KEY",
    "Content-Type": "application/json"
  }
}`}
            </code>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default APIKeys;