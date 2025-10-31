import React, { useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";

const Login: React.FC = () => {
  const { login } = useAuth();
  const nav = useNavigate();
  const [email, setEmail] = useState("admin@demo.com");
  const [password, setPassword] = useState("Admin123*");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      await login(email, password);
      nav("/");
    } catch (err: any) {
      setError(err?.response?.data?.message || "Error al iniciar sesión");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ display:"grid", placeItems:"center", minHeight:"100vh" }}>
      <form onSubmit={submit} style={{ width:320, display:"grid", gap:12, padding:24, border:"1px solid #ddd", borderRadius:12 }}>
        <h2>Iniciar sesión</h2>
        <label>Email
          <input value={email} onChange={e=>setEmail(e.target.value)} type="email" required />
        </label>
        <label>Contraseña
          <input value={password} onChange={e=>setPassword(e.target.value)} type="password" required />
        </label>
        {error && <div style={{ color:"crimson" }}>{error}</div>}
        <button disabled={loading}>{loading ? "Entrando..." : "Entrar"}</button>
        <small>Tip: admin@demo.com / Admin123*</small>
      </form>
    </div>
  );
};

export default Login;
