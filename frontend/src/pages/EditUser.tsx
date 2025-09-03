import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { ArrowLeft, Save, Shield, User } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

// EditUser component

const EditUser = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { toast } = useToast();

  // Données utilisateurs simulées (normalement récupérées depuis une API)
  const allUsers = [
    {
      id: "user-001",
      firstName: "Jean",
      lastName: "Dupont", 
      email: "jean.dupont@entreprise.com",
      company: "TechCorp",
      role: "admin",
      createdAt: "2024-01-15",
      lastLogin: "2024-01-20"
    },
    {
      id: "user-002",
      firstName: "Marie",
      lastName: "Martin",
      email: "marie.martin@startup.fr",
      company: "StartupInc",
      role: "user",
      createdAt: "2024-01-10",
      lastLogin: "2024-01-19"
    },
    {
      id: "user-003",
      firstName: "Pierre",
      lastName: "Durand",
      email: "pierre.durand@consulting.com",
      company: "ConsultCorp",
      role: "user",
      createdAt: "2023-12-20",
      lastLogin: "2024-01-18"
    },
    {
      id: "user-004",
      firstName: "Sophie",
      lastName: "Bernard",
      email: "sophie.bernard@tech.fr",
      company: "TechSolutions",
      role: "admin",
      createdAt: "2023-11-15",
      lastLogin: "2024-01-17"
    }
  ];

  const [user, setUser] = useState(allUsers.find(u => u.id === id));
  const [editedUser, setEditedUser] = useState(user);

  useEffect(() => {
    const foundUser = allUsers.find(u => u.id === id);
    if (foundUser) {
      setUser(foundUser);
      setEditedUser(foundUser);
    }
  }, [id]);

  const handleSave = () => {
    // Ici on sauvegarderait normalement via une API
    toast({
      title: "Utilisateur modifié",
      description: `Les informations de ${editedUser?.firstName} ${editedUser?.lastName} ont été mises à jour`,
    });
    navigate("/users");
  };

  const getRoleBadge = (role: string) => {
    if (role === "admin") {
      return (
        <Badge className="bg-primary text-primary-foreground">
          <Shield className="mr-1 h-3 w-3" />
          Admin
        </Badge>
      );
    }
    return (
      <Badge variant="secondary">
        <User className="mr-1 h-3 w-3" />
        Utilisateur
      </Badge>
    );
  };

  const getInitials = (firstName: string, lastName: string) => {
    return `${firstName.charAt(0)}${lastName.charAt(0)}`.toUpperCase();
  };

  if (!user || !editedUser) {
    return (
      <div className="space-y-6">
        <div className="flex items-center space-x-4">
          <Button variant="ghost" onClick={() => navigate("/users")}>
            <ArrowLeft className="mr-2 h-4 w-4" />
            Retour
          </Button>
        </div>
        <Card className="shadow-card">
          <CardContent className="p-6 text-center">
            <p className="text-muted-foreground">Utilisateur non trouvé</p>
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-4">
          <Button variant="ghost" onClick={() => navigate("/users")}>
            <ArrowLeft className="mr-2 h-4 w-4" />
            Retour
          </Button>
          <div>
            <h1 className="text-3xl font-bold tracking-tight">Modifier l'utilisateur</h1>
            <p className="text-muted-foreground">
              Modifiez les informations de {user.firstName} {user.lastName}
            </p>
          </div>
        </div>
        <Button onClick={handleSave} className="bg-gradient-primary hover:opacity-90">
          <Save className="mr-2 h-4 w-4" />
          Enregistrer
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-3">
        <Card className="shadow-card">
          <CardHeader className="text-center">
            <div className="flex justify-center mb-4">
              <Avatar className="h-20 w-20">
                <AvatarFallback className="bg-primary/10 text-primary text-xl">
                  {getInitials(editedUser.firstName, editedUser.lastName)}
                </AvatarFallback>
              </Avatar>
            </div>
            <CardTitle className="text-xl">
              {editedUser.firstName} {editedUser.lastName}
            </CardTitle>
            <CardDescription>{editedUser.email}</CardDescription>
            <div className="flex justify-center mt-3">
              {getRoleBadge(editedUser.role)}
            </div>
          </CardHeader>
        </Card>

        <Card className="md:col-span-2 shadow-card">
          <CardHeader>
            <CardTitle>Informations utilisateur</CardTitle>
            <CardDescription>
              Modifiez les informations de cet utilisateur
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="firstName">Prénom</Label>
                <Input
                  id="firstName"
                  value={editedUser.firstName}
                  onChange={(e) => setEditedUser({...editedUser, firstName: e.target.value})}
                  placeholder="Jean"
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="lastName">Nom</Label>
                <Input
                  id="lastName"
                  value={editedUser.lastName}
                  onChange={(e) => setEditedUser({...editedUser, lastName: e.target.value})}
                  placeholder="Dupont"
                />
              </div>
            </div>
            
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={editedUser.email}
                onChange={(e) => setEditedUser({...editedUser, email: e.target.value})}
                placeholder="jean.dupont@entreprise.com"
              />
            </div>
            
            <div className="space-y-2">
              <Label htmlFor="company">Société</Label>
              <Input
                id="company"
                value={editedUser.company}
                onChange={(e) => setEditedUser({...editedUser, company: e.target.value})}
                placeholder="Nom de l'entreprise"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="role">Rôle</Label>
              <Select value={editedUser.role} onValueChange={(value) => setEditedUser({...editedUser, role: value})}>
                <SelectTrigger>
                  <SelectValue placeholder="Sélectionner un rôle" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="user">Utilisateur</SelectItem>
                  <SelectItem value="admin">Administrateur</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </CardContent>
        </Card>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Informations de compte</CardTitle>
          <CardDescription>Détails sur l'activité du compte</CardDescription>
        </CardHeader>
        <CardContent className="grid gap-4 md:grid-cols-2">
          <div className="space-y-2">
            <Label>Date de création</Label>
            <p className="text-sm bg-accent px-3 py-2 rounded">{user.createdAt}</p>
          </div>
          <div className="space-y-2">
            <Label>Dernière connexion</Label>
            <p className="text-sm bg-accent px-3 py-2 rounded">{user.lastLogin}</p>
          </div>
        </CardContent>
      </Card>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Actions sur le compte</CardTitle>
          <CardDescription>Actions avancées pour cet utilisateur</CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="flex items-center justify-between p-4 border rounded-lg">
            <div>
              <h4 className="font-medium">Réinitialiser le mot de passe</h4>
              <p className="text-sm text-muted-foreground">
                Envoyer un email de réinitialisation à l'utilisateur
              </p>
            </div>
            <Button variant="outline">
              Réinitialiser
            </Button>
          </div>
          
          <div className="flex items-center justify-between p-4 border border-destructive/20 rounded-lg">
            <div>
              <h4 className="font-medium text-destructive">Supprimer le compte</h4>
              <p className="text-sm text-muted-foreground">
                Cette action est irréversible
              </p>
            </div>
            <Button variant="destructive">
              Supprimer
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default EditUser;