import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { User, Mail, Building, Shield, Save } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

// Profile component

const Profile = () => {
  const { toast } = useToast();
  const [isEditing, setIsEditing] = useState(false);
  
  // Données utilisateur simulées
  const [userProfile, setUserProfile] = useState({
    firstName: "Jean",
    lastName: "Dupont",
    email: "jean.dupont@entreprise.com",
    company: "TechCorp",
    role: "admin"
  });

  const [editedProfile, setEditedProfile] = useState(userProfile);

  const handleSave = () => {
    setUserProfile(editedProfile);
    setIsEditing(false);
    toast({
      title: "Profil mis à jour",
      description: "Vos informations ont été sauvegardées avec succès",
    });
  };

  const handleCancel = () => {
    setEditedProfile(userProfile);
    setIsEditing(false);
  };

  const getRoleBadge = (role: string) => {
    if (role === "admin") {
      return (
        <Badge className="bg-primary text-primary-foreground">
          <Shield className="mr-1 h-3 w-3" />
          Administrateur
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

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Mon Profil</h1>
        <p className="text-muted-foreground">Gérez vos informations personnelles</p>
      </div>

      <div className="grid gap-6 md:grid-cols-3">
        <Card className="shadow-card">
          <CardHeader className="text-center">
            <div className="flex justify-center mb-4">
              <Avatar className="h-20 w-20">
                <AvatarFallback className="bg-primary/10 text-primary text-xl">
                  {getInitials(userProfile.firstName, userProfile.lastName)}
                </AvatarFallback>
              </Avatar>
            </div>
            <CardTitle className="text-xl">
              {userProfile.firstName} {userProfile.lastName}
            </CardTitle>
            <CardDescription>{userProfile.email}</CardDescription>
            <div className="flex justify-center mt-3">
              {getRoleBadge(userProfile.role)}
            </div>
          </CardHeader>
        </Card>

        <Card className="md:col-span-2 shadow-card">
          <CardHeader>
            <div className="flex items-center justify-between">
              <div>
                <CardTitle>Informations personnelles</CardTitle>
                <CardDescription>
                  Modifiez vos informations de profil
                </CardDescription>
              </div>
              {!isEditing ? (
                <Button onClick={() => setIsEditing(true)}>
                  Modifier
                </Button>
              ) : (
                <div className="flex space-x-2">
                  <Button variant="outline" onClick={handleCancel}>
                    Annuler
                  </Button>
                  <Button onClick={handleSave}>
                    <Save className="mr-2 h-4 w-4" />
                    Enregistrer
                  </Button>
                </div>
              )}
            </div>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="firstName">
                  <User className="mr-2 h-4 w-4 inline" />
                  Prénom
                </Label>
                <Input
                  id="firstName"
                  value={isEditing ? editedProfile.firstName : userProfile.firstName}
                  onChange={(e) => setEditedProfile({...editedProfile, firstName: e.target.value})}
                  disabled={!isEditing}
                  className={!isEditing ? "bg-muted" : ""}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="lastName">Nom</Label>
                <Input
                  id="lastName"
                  value={isEditing ? editedProfile.lastName : userProfile.lastName}
                  onChange={(e) => setEditedProfile({...editedProfile, lastName: e.target.value})}
                  disabled={!isEditing}
                  className={!isEditing ? "bg-muted" : ""}
                />
              </div>
            </div>
            
            <div className="space-y-2">
              <Label htmlFor="email">
                <Mail className="mr-2 h-4 w-4 inline" />
                Email
              </Label>
              <Input
                id="email"
                type="email"
                value={isEditing ? editedProfile.email : userProfile.email}
                onChange={(e) => setEditedProfile({...editedProfile, email: e.target.value})}
                disabled={!isEditing}
                className={!isEditing ? "bg-muted" : ""}
              />
            </div>
            
            <div className="space-y-2">
              <Label htmlFor="company">
                <Building className="mr-2 h-4 w-4 inline" />
                Société
              </Label>
              <Input
                id="company"
                value={isEditing ? editedProfile.company : userProfile.company}
                onChange={(e) => setEditedProfile({...editedProfile, company: e.target.value})}
                disabled={!isEditing}
                className={!isEditing ? "bg-muted" : ""}
              />
            </div>

            <div className="space-y-2">
              <Label>
                <Shield className="mr-2 h-4 w-4 inline" />
                Rôle
              </Label>
              <div className="pt-2">
                {getRoleBadge(userProfile.role)}
                <p className="text-sm text-muted-foreground mt-1">
                  Le rôle ne peut pas être modifié depuis votre profil
                </p>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Sécurité</CardTitle>
          <CardDescription>Gérez les paramètres de sécurité de votre compte</CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="flex items-center justify-between p-4 border rounded-lg">
            <div>
              <h4 className="font-medium">Mot de passe</h4>
              <p className="text-sm text-muted-foreground">
                Dernière modification il y a 30 jours
              </p>
            </div>
            <Button variant="outline">
              Changer le mot de passe
            </Button>
          </div>
          
          <div className="flex items-center justify-between p-4 border rounded-lg">
            <div>
              <h4 className="font-medium">Authentification à deux facteurs</h4>
              <p className="text-sm text-muted-foreground">
                Ajoutez une couche de sécurité supplémentaire
              </p>
            </div>
            <Button variant="outline">
              Configurer
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Profile;