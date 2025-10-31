import React, { useEffect, useState } from "react";
import http from "../api/http";
import { useAuth } from "../auth/AuthContext";

type EmpresaDTO = {
  nit: string; nombre: string; direccion?: string; telefono?: string;
};

const Empresas: React.FC = () => {
  const { isAdmin, logout } = useAuth();
  const [list, setList] = useState<EmpresaDTO[]>([]);
  const [form, setForm] = useState<EmpresaDTO>({ nit:"", nombre:"" });
  const [msg, setMsg] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const load = async () => {
    const { data } = await http.get<EmpresaDTO[]>("/api/empresas");
    setList(data);
  };

  useEffect(() => { load(); }, []);

  const create = async (e: React.FormEvent) => {
    e.preventDefault();
    setMsg(null); setError(null);
    try {
      await http.post("/api/empresas", form);
      setForm({ nit:"", nombre:"" });
      setMsg("Empresa creada");
      await load();
    } catch (err: any) {
      setError(err?.response?.data?.message || "Error creando empresa");
      if (err?.response?.status === 401) logout();
    }
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Empresas</h2>

      <div style={{ marginTop:16, marginBottom:24 }}>
        <table border={1} cellPadding={6}>
          <thead><tr><th>NIT</th><th>Nombre</th><th>Dirección</th><th>Teléfono</th></tr></thead>
          <tbody>
            {list.map(e => (
              <tr key={e.nit}>
                <td>{e.nit}</td>
                <td>{e.nombre}</td>
                <td>{e.direccion || "-"}</td>
                <td>{e.telefono || "-"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {isAdmin ? (
        <form onSubmit={create} style={{ display:"grid", gap:8, maxWidth:420 }}>
          <h3>Nueva empresa</h3>
          <input placeholder="NIT" value={form.nit} onChange={e=>setForm({...form, nit:e.target.value})} required />
          <input placeholder="Nombre" value={form.nombre} onChange={e=>setForm({...form, nombre:e.target.value})} required />
          <input placeholder="Dirección" value={form.direccion || ""} onChange={e=>setForm({...form, direccion:e.target.value})} />
          <input placeholder="Teléfono" value={form.telefono || ""} onChange={e=>setForm({...form, telefono:e.target.value})} />
          <button>Crear</button>
          {msg && <div style={{ color:"green" }}>{msg}</div>}
          {error && <div style={{ color:"crimson" }}>{error}</div>}
        </form>
      ) : (
        <p><i>Inicia sesión como ADMIN para crear/editar.</i></p>
      )}
    </div>
  );
};

export default Empresas;
