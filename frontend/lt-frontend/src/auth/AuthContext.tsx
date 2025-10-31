import React, { createContext, useContext, useMemo, useState } from "react";
import http from "../api/http";

type AuthContextType = {
  token: string | null;
  roles: string[];
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAdmin: boolean;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem("token"));
  const [roles, setRoles] = useState<string[]>(
    () => JSON.parse(localStorage.getItem("roles") || "[]")
  );

  const login = async (email: string, password: string) => {
    const { data } = await http.post("/auth/login", { email, password });
    localStorage.setItem("token", data.token);
    localStorage.setItem("roles", JSON.stringify(data.roles || []));
    setToken(data.token);
    setRoles(data.roles || []);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("roles");
    setToken(null);
    setRoles([]);
  };

  const value = useMemo(
    () => ({ token, roles, login, logout, isAdmin: roles.includes("ROLE_ADMIN") }),
    [token, roles]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
};
