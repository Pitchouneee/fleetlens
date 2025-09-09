import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import RequireAuth from "@/components/RequireAuth";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import VMs from "./pages/VMs";
import VMDetails from "./pages/VMDetails";
import Accounts from "./pages/Accounts";
import Software from "./pages/Software";
import APIKeys from "./pages/APIKeys";
import Users from "./pages/Users";
import EditUser from "./pages/EditUser";
import Profile from "./pages/Profile";
import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={
            <RequireAuth>
              <DashboardLayout>
                <Dashboard />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/dashboard" element={
            <RequireAuth>
              <DashboardLayout>
                <Dashboard />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/vms" element={
            <RequireAuth>
              <DashboardLayout>
                <VMs />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/vms/:id" element={
            <RequireAuth>
              <DashboardLayout>
                <VMDetails />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/accounts" element={
            <RequireAuth>
              <DashboardLayout>
                <Accounts />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/software" element={
            <RequireAuth>
              <DashboardLayout>
                <Software />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/api-keys" element={
            <RequireAuth>
              <DashboardLayout>
                <APIKeys />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/users" element={
            <RequireAuth>
              <DashboardLayout>
                <Users />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/users/:id/edit" element={
            <RequireAuth>
              <DashboardLayout>
                <EditUser />
              </DashboardLayout>
            </RequireAuth>
          } />
          <Route path="/profile" element={
            <RequireAuth>
              <DashboardLayout>
                <Profile />
              </DashboardLayout>
            </RequireAuth>
          } />
          {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
