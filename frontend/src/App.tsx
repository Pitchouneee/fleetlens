import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
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
            <DashboardLayout>
              <Dashboard />
            </DashboardLayout>
          } />
          <Route path="/dashboard" element={
            <DashboardLayout>
              <Dashboard />
            </DashboardLayout>
          } />
          <Route path="/vms" element={
            <DashboardLayout>
              <VMs />
            </DashboardLayout>
          } />
          <Route path="/vms/:id" element={
            <DashboardLayout>
              <VMDetails />
            </DashboardLayout>
          } />
          <Route path="/accounts" element={
            <DashboardLayout>
              <Accounts />
            </DashboardLayout>
          } />
          <Route path="/software" element={
            <DashboardLayout>
              <Software />
            </DashboardLayout>
          } />
          <Route path="/api-keys" element={
            <DashboardLayout>
              <APIKeys />
            </DashboardLayout>
          } />
          <Route path="/users" element={
            <DashboardLayout>
              <Users />
            </DashboardLayout>
          } />
          <Route path="/users/:id/edit" element={
            <DashboardLayout>
              <EditUser />
            </DashboardLayout>
          } />
          <Route path="/profile" element={
            <DashboardLayout>
              <Profile />
            </DashboardLayout>
          } />
          {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
