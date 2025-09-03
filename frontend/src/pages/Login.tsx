import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Shield } from "lucide-react";

const Login = () => {
  return (
    <div className="min-h-screen bg-gradient-subtle flex items-center justify-center p-4">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader className="text-center space-y-2">
          <div className="mx-auto w-12 h-12 rounded-xl bg-gradient-primary flex items-center justify-center mb-4">
            <Shield className="w-6 h-6 text-primary-foreground" />
          </div>
          <CardTitle className="text-2xl font-bold">CloudManager</CardTitle>
          <CardDescription>Connectez-vous à votre tableau de bord</CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input 
              id="email" 
              type="email" 
              placeholder="nom@entreprise.com"
              className="transition-smooth"
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="password">Mot de passe</Label>
            <Input 
              id="password" 
              type="password"
              className="transition-smooth"
            />
          </div>
          <Button className="w-full bg-gradient-primary hover:opacity-90 transition-smooth">
            Se connecter
          </Button>
          <div className="text-center">
            <a href="#" className="text-sm text-muted-foreground hover:text-primary transition-smooth">
              Mot de passe oublié ?
            </a>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Login;