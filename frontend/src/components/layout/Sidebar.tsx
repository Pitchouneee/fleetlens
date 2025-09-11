import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import {
  LayoutDashboard,
  Server,
  Key,
  Users,
  User,
  Package,
  Shield,
  LogOut,
} from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import { logout } from "@/lib/auth";

const navigation = [
  { name: "Dashboard", href: "/dashboard", icon: LayoutDashboard },
  { name: "Machines Virtuelles", href: "/vms", icon: Server },
  // { name: "Comptes", href: "/accounts", icon: User },
  // { name: "Logiciels", href: "/software", icon: Package },
  { name: "Clés API", href: "/api-keys", icon: Key },
  { name: "Utilisateurs", href: "/users", icon: Users },
];

export const Sidebar = () => {
  const location = useLocation();

  return (
    <div className="w-64 bg-card border-r border-border flex flex-col shadow-card">
      <div className="p-6">
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8 rounded-lg bg-gradient-primary flex items-center justify-center">
            <Shield className="w-4 h-4 text-primary-foreground" />
          </div>
          <span className="text-xl font-bold">Fleetlens</span>
        </div>
      </div>

      <nav className="flex-1 px-4 space-y-1">
        {navigation.map((item) => {
          const isActive = location.pathname === item.href;
          return (
            <Link key={item.name} to={item.href}>
              <Button
                variant={isActive ? "secondary" : "ghost"}
                className={cn(
                  "w-full justify-start transition-smooth",
                  isActive && "bg-primary/10 text-primary hover:bg-primary/15"
                )}
              >
                <item.icon className="mr-3 h-4 w-4" />
                {item.name}
              </Button>
            </Link>
          );
        })}
      </nav>

      <div className="p-4 border-t border-border">
        <Button
          variant="ghost"
          className="w-full justify-start text-muted-foreground hover:text-destructive transition-smooth"
          onClick={() => logout("/login")}
        >
          <LogOut className="mr-3 h-4 w-4" />
          Déconnexion
        </Button>
      </div>
    </div>
  );
};

