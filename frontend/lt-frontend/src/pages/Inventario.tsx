import React, { useState } from "react";
import http from "../api/http";
import { useAuth } from "../auth/AuthContext";

type ProductoDTO = {
  codigo: string; nombre: string; caracteristicas?: string; precio?: number; empresaNit?: string;
};

const Inventario: React.FC = () => {
  const { isAdmin } = useAuth();
  const [nit, setNit] = useState("900123456");
  const [items, setItems] = useState<ProductoDTO[]>([]);
  const [email, setEmail] = useState("");
  const [msg, setMsg] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const fetchInventario = async () => {
    setMsg(null); setError(null);
    try {
      const { data } = await http.get(`/api/inventario/${nit}`);
      setItems(data);
    } catch (e: any) {
      setError("Error al consultar inventario");
    }
  };

  const descargarPdf = () => {
    const url = `${import.meta.env.VITE_API_URL}/api/inventario/${nit}/pdf`;
    window.open(url, "_blank");
  };

  const enviarEmail = async () => {
    setMsg(null); setError(null);
    try {
      await http.post(`/api/inventario/${nit}/email`,
        { to: email },                            
        { headers: { 'Content-Type': 'application/json' } }
      );
      setMsg("Correo enviado");
    } catch (e: any) {
      setError(e?.response?.data?.message || "Error enviando correo");
    }
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Inventario por empresa</h2>
      <div style={{ display: "flex", gap: 8 }}>
        <input placeholder="NIT" value={nit} onChange={e => setNit(e.target.value)} />
        <button onClick={fetchInventario}>Consultar</button>
        <button onClick={descargarPdf}>Descargar PDF</button>
      </div>

      <div style={{ marginTop: 16 }}>
        <table border={1} cellPadding={6}>
          <thead><tr><th>Código</th><th>Nombre</th><th>Características</th><th>Precio</th></tr></thead>
          <tbody>
            {items.map(p => (
              <tr key={p.codigo}>
                <td>{p.codigo}</td>
                <td>{p.nombre}</td>
                <td>{p.caracteristicas || "-"}</td>
                <td>{p.precio?.toLocaleString("es-CO", { minimumFractionDigits: 2 }) ?? "-"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div style={{ marginTop: 24 }}>
        <h3>Enviar por correo (SES)</h3>
        <div style={{ display: "flex", gap: 8 }}>
          <input placeholder="destino@correo.com" value={email} onChange={e => setEmail(e.target.value)} />
          <button onClick={enviarEmail} disabled={!isAdmin}>Enviar</button>
        </div>
        {!isAdmin && <small>Debes iniciar sesión como ADMIN para enviar.</small>}
      </div>

      {msg && <div style={{ color: "green", marginTop: 12 }}>{msg}</div>}
      {error && <div style={{ color: "crimson", marginTop: 12 }}>{error}</div>}
    </div>
  );
};

export default Inventario;
