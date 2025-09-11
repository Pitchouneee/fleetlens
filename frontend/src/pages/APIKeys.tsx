import { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
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
import { Key, Plus, MoreHorizontal, Trash2, Copy } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useToast } from "@/hooks/use-toast";
import { Badge } from "@/components/ui/badge";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import api from "@/lib/api";

type ApiKeyItem = {
  id: string;
  name: string;
  createdAt: string;
  expiresAt?: string | null;
  enabled: boolean;
  lastUsedAt?: string | null;
  preview: string;
};

type ApiKeyListResponse = {
  content: ApiKeyItem[];
  page: {
    size: number;
    number: number; // 0-based
    totalElements: number;
    totalPages: number;
  };
};

const formatDate = (iso?: string | null) => {
  if (!iso) return "—";
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return "—";
  return d.toLocaleString();
};

const APIKeys = () => {
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false);
  const [keyName, setKeyName] = useState("");
  const [expiresAtLocal, setExpiresAtLocal] = useState<string>("");
  const [createdPlainKey, setCreatedPlainKey] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [size] = useState(20);
  const { toast } = useToast();
  const queryClient = useQueryClient();

  const { data, isLoading, isError, refetch } = useQuery<ApiKeyListResponse>({
    queryKey: ["apiKeys", page, size],
    queryFn: async () => {
      const res = await api.get("/admin/api-keys", { params: { page, size } });
      return res.data as ApiKeyListResponse;
    },
    placeholderData: (previousData) => previousData,
    staleTime: 10000,
  });

  const createApiKey = useMutation({
    mutationFn: async (payload: { name: string; expiresAt?: string }) => {
      const res = await api.post("/admin/api-keys", payload);
      return res.data as { plainKey?: string; key?: string; apiKey?: string };
    },
    onSuccess: (data) => {
      const plain = (data as any)?.plainKey || (data as any)?.key || (data as any)?.apiKey;
      if (plain) {
        setCreatedPlainKey(plain);
        toast({
          title: "Clé créée",
          description: "Copiez la clé maintenant, elle ne sera plus affichée.",
        });
      } else {
        setIsCreateDialogOpen(false);
        toast({ title: "Création réussie", description: "La clé a été créée." });
      }
      queryClient.invalidateQueries({ queryKey: ["apiKeys"] });
    },
    onError: (err: any) => {
      const msg = err?.response?.data?.message || err?.message || "Échec de la création";
      toast({ title: "Erreur", description: msg });
    },
  });

  const revokeApiKey = useMutation({
    mutationFn: async (id: string) => {
      // Révocation: POST /admin/api-keys/{id}/revoke (correspond à /{id}/revoke)
      await api.post(`/admin/api-keys/${encodeURIComponent(id)}/revoke`);
    },
    onSuccess: () => {
      toast({ title: "Clé révoquée", description: "La clé a été révoquée." });
      queryClient.invalidateQueries({ queryKey: ["apiKeys"] });
    },
    onError: (err: any) => {
      const msg = err?.response?.data?.message || err?.message || "Échec de la révocation";
      toast({ title: "Erreur", description: msg });
    },
  });

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Clés API</h1>
          <p className="text-muted-foreground">Gérez vos clés d'accès à l'API</p>
        </div>
        <Dialog
          open={isCreateDialogOpen}
          onOpenChange={(open) => {
            setIsCreateDialogOpen(open);
            if (!open) {
              setKeyName("");
              setExpiresAtLocal("");
              setCreatedPlainKey(null);
            }
          }}
        >
          <DialogTrigger asChild>
            <Button className="bg-gradient-primary hover:opacity-90">
              <Plus className="mr-2 h-4 w-4" />
              Nouvelle clé
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Créer une nouvelle clé API</DialogTitle>
              {!createdPlainKey && (
                <DialogDescription>Donnez un nom à votre clé API pour l'identifier facilement.</DialogDescription>
              )}
            </DialogHeader>

            {!createdPlainKey ? (
              <>
                <div className="space-y-4">
                  <div className="space-y-2">
                    <Label htmlFor="keyName">Nom de la clé</Label>
                    <Input
                      id="keyName"
                      placeholder="Ex: Production, Développement..."
                      value={keyName}
                      onChange={(e) => setKeyName(e.target.value)}
                      disabled={createApiKey.isPending}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="expiresAt">Expiration (optionnel)</Label>
                    <Input
                      id="expiresAt"
                      type="datetime-local"
                      value={expiresAtLocal}
                      onChange={(e) => setExpiresAtLocal(e.target.value)}
                      disabled={createApiKey.isPending}
                    />
                    <p className="text-xs text-muted-foreground">Laissez vide pour ne pas définir d'expiration.</p>
                  </div>
                </div>
                <DialogFooter>
                  <Button variant="outline" onClick={() => setIsCreateDialogOpen(false)} disabled={createApiKey.isPending}>
                    Annuler
                  </Button>
                  <Button
                    onClick={() => {
                      const name = keyName.trim();
                      if (!name) {
                        toast({ title: "Nom requis", description: "Veuillez saisir un nom." });
                        return;
                      }
                      const iso = expiresAtLocal ? new Date(expiresAtLocal).toISOString() : undefined;
                      createApiKey.mutate({ name, expiresAt: iso });
                    }}
                    disabled={createApiKey.isPending}
                  >
                    {createApiKey.isPending ? "Création..." : "Créer la clé"}
                  </Button>
                </DialogFooter>
              </>
            ) : (
              <>
                <div className="space-y-4">
                  <Label>Clé API (affichée une seule fois)</Label>
                  <div className="flex items-center space-x-2">
                    <code className="font-mono text-sm bg-accent px-2 py-1 rounded select-all">{createdPlainKey}</code>
                    <Button variant="ghost" size="icon" onClick={() => navigator.clipboard.writeText(createdPlainKey)}>
                      <Copy className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
                <DialogFooter>
                  <Button
                    onClick={() => {
                      setIsCreateDialogOpen(false);
                      setKeyName("");
                      setCreatedPlainKey(null);
                    }}
                  >
                    Fermer
                  </Button>
                </DialogFooter>
              </>
            )}
          </DialogContent>
        </Dialog>
      </div>

      <Card className="shadow-card">
        <CardHeader>
          <CardTitle>Clés API existantes</CardTitle>
          <CardDescription>
            {data?.page?.totalElements ?? 0} clés {isLoading ? "(chargement)" : "au total"}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Nom</TableHead>
                <TableHead>Clé</TableHead>
                <TableHead>Créée le</TableHead>
                <TableHead>Expire le</TableHead>
                <TableHead>Statut</TableHead>
                <TableHead>Dernière utilisation</TableHead>
                <TableHead className="w-[50px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {(data?.content ?? []).map((k) => (
                <TableRow key={k.id} className="hover:bg-accent/50 transition-smooth">
                  <TableCell className="font-medium">
                    <div className="flex items-center space-x-2">
                      <Key className="h-4 w-4 text-muted-foreground" />
                      <span>{k.name}</span>
                    </div>
                  </TableCell>
                  <TableCell>
                    <code className="font-mono text-sm bg-accent px-2 py-1 rounded">{k.preview ?? "—"}</code>
                  </TableCell>
                  <TableCell>{formatDate(k.createdAt)}</TableCell>
                  <TableCell>{formatDate(k.expiresAt)}</TableCell>
                  <TableCell>
                    {k.enabled ? (
                      <Badge className="bg-success text-success-foreground">Activée</Badge>
                    ) : (
                      <Badge variant="secondary">Désactivée</Badge>
                    )}
                  </TableCell>
                  <TableCell>{formatDate(k.lastUsedAt)}</TableCell>
                  <TableCell>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" size="icon">
                          <MoreHorizontal className="h-4 w-4" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent align="end">
                        <DropdownMenuItem
                          className="text-destructive"
                          disabled={!k.enabled || revokeApiKey.isPending}
                          onClick={() => {
                            if (!k.enabled || revokeApiKey.isPending) return;
                            const ok = window.confirm(`Révoquer la clé "${k.name}" ? Cette action est définitive.`);
                            if (ok) revokeApiKey.mutate(k.id);
                          }}
                        >
                          <Trash2 className="mr-2 h-4 w-4" />
                          Révoquer
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
              Page {(data?.page?.number ?? page) + 1} sur {data?.page?.totalPages ?? 1} — {data?.page?.totalElements ?? 0} résultats
            </p>
            <div className="flex items-center space-x-2">
              <Button
                variant="outline"
                size="sm"
                onClick={() => setPage(Math.max(0, page - 1))}
                disabled={(data?.page?.number ?? page) === 0 || isLoading}
              >
                Précédent
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={() => setPage(Math.min((data?.page?.totalPages ?? 1) - 1, page + 1))}
                disabled={((data?.page?.number ?? page) + 1) === (data?.page?.totalPages ?? 1) || isLoading}
              >
                Suivant
              </Button>
              {isError && (
                <Button variant="destructive" size="sm" onClick={() => refetch()}>Réessayer</Button>
              )}
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default APIKeys;
