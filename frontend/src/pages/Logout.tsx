import { useEffect } from "react";
import { logout } from "@/lib/auth";

export default function Logout() {
  useEffect(() => {
    logout("/login");
  }, []);

  return null;
}

