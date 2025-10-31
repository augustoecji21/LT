import React, { useEffect, useMemo, useState } from "react";
import http from "../api/http";

type Empresa = {
  nit: string;
  nombre: string;
  direccion?: string | null;
  telefono?: string | null;
};

type ProductoDTO = {
  codigo: string;
  nombre: string;
  caracteristicas?: string | null;
  precio: number;
  empresaNit?: string;
};

type Query = {
  empresaNit?: string;
  nombre?: string;
  min?: number;
  max?: number;
  sort: string; 
};

const initialQuery: Query = { sort: "nombre,asc" };

export default function Productos() {
  const [rows, setRows] = useState<ProductoDTO[]>([]);
  const [q, setQ] = useState<Query>(initialQuery);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [empresas, setEmpresas] = useState<Empresa[]>([]);
  const [, setEmpresasLoading] = useState(false);
  const [openForm, setOpenForm] = useState(false);
  const [editing, setEditing] = useState<ProductoDTO | null>(null);
  const isEdit = Boolean(editing);

  // ------ Query params memorizados
  const params = useMemo(() => {
    const p: any = { sort: q.sort };
    if (q.empresaNit) p.empresaNit = q.empresaNit;
    if (q.nombre) p.nombre = q.nombre;
    if (q.min !== undefined) p.min = q.min;
    if (q.max !== undefined) p.max = q.max;
    return p;
  }, [q]);

  // ------ Cargar empresas para select
  useEffect(() => {
    (async () => {
      try {
        const { data } = await http.get<Empresa[]>("/api/empresa");
        setEmpresas(data);
      } catch {
        setEmpresas([]);
      }
    })();
  }, []);

  useEffect(() => { loadEmpresas(); }, []);

  useEffect(() => {
    if (openForm && empresas.length === 0) loadEmpresas();
  }, [openForm]);

  async function loadEmpresas() {
    try {
      setEmpresasLoading(true);
      const { data } = await http.get<Empresa[]>("/api/empresas"); 
      setEmpresas(data);
    } catch (e) {
      setEmpresas([]);
    } finally {
      setEmpresasLoading(false);
    }
  }
  // ------ Cargar productos sin paginar
  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const { data } = await http.get<ProductoDTO[]>("/api/productos", { params });
      setRows(data);
    } catch (e: any) {
      setRows([]);
      setError(e?.response?.data?.message || "Error cargando productos");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); /* eslint-disable-next-line */ }, [q.sort]); // al cambiar sort recarga

  const buscar = (e: React.FormEvent) => {
    e.preventDefault();
    load();
  };

  const limpiar = () => {
    setQ(initialQuery);
    setTimeout(load, 0);
  };

  const eliminar = async (codigo: string) => {
    if (!confirm(`¿Eliminar producto ${codigo}?`)) return;
    await http.delete(`/api/productos/${codigo}`);
    load();
  };

  // ------ Formulario modal
  const [model, setModel] = useState<ProductoDTO>({
    codigo: "",
    nombre: "",
    caracteristicas: "",
    precio: 0,
    empresaNit: "",
  });
  const [saving, setSaving] = useState(false);

  const openNew = () => {
    setEditing(null);
    setModel({ codigo: "", nombre: "", caracteristicas: "", precio: 0, empresaNit: "" });
    setOpenForm(true);
  };

  const openEdit = (p: ProductoDTO) => {
    setEditing(p);
    setModel({ ...p });
    setOpenForm(true);
  };

  const closeForm = () => {
    setOpenForm(false);
    setSaving(false);
  };

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (isEdit) {
        await http.put(`/api/productos/${model.codigo}`, model);
      } else {
        await http.post(`/api/productos`, model);
      }
      closeForm();
      load();
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="max-w-6xl mx-auto p-4 space-y-4">
      <h1 className="text-2xl font-semibold">Productos</h1>

      <form onSubmit={buscar} className="bg-white p-3 rounded-lg border space-y-3">
        <div className="grid grid-cols-1 md:grid-cols-5 gap-3">
          <select
            className="input"
            value={q.empresaNit ?? ""}
            onChange={(e) => setQ({ ...q, empresaNit: e.target.value || undefined })}
          >
            <option value="">Empresa (todas)</option>
            {empresas.map((e) => (
              <option key={e.nit} value={e.nit}>
                {e.nit} — {e.nombre}
              </option>
            ))}
          </select>

          <input
            className="input"
            placeholder="Nombre contiene…"
            value={q.nombre ?? ""}
            onChange={(e) => setQ({ ...q, nombre: e.target.value || undefined })}
          />

          <input
            className="input"
            type="number"
            min={0}
            step="0.01"
            placeholder="Precio mín"
            value={q.min ?? ""}
            onChange={(e) =>
              setQ({ ...q, min: e.target.value === "" ? undefined : Number(e.target.value) })
            }
          />

          <input
            className="input"
            type="number"
            min={0}
            step="0.01"
            placeholder="Precio máx"
            value={q.max ?? ""}
            onChange={(e) =>
              setQ({ ...q, max: e.target.value === "" ? undefined : Number(e.target.value) })
            }
          />

          <select
            className="input"
            value={q.sort}
            onChange={(e) => setQ({ ...q, sort: e.target.value })}
          >
            <option value="nombre,asc">Nombre ↑</option>
            <option value="nombre,desc">Nombre ↓</option>
            <option value="precio,asc">Precio ↑</option>
            <option value="precio,desc">Precio ↓</option>
          </select>
        </div>

        <div className="flex gap-2">
          <button className="btn-primary">Buscar</button>
          <button type="button" className="btn" onClick={limpiar}>Limpiar</button>
          <div className="grow" />
          <button type="button" className="btn-primary" onClick={openNew}>+ Nuevo</button>
        </div>
      </form>

      <div className="bg-white rounded-lg border overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead className="bg-gray-50">
            <tr className="[&>th]:p-2 [&>th]:text-left">
              <th>Código</th>
              <th>Nombre</th>
              <th>Características</th>
              <th className="text-right">Precio</th>
              <th>Empresa</th>
              <th className="text-right">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {loading && (
              <tr><td colSpan={6} className="p-4 text-center">Cargando…</td></tr>
            )}
            {!loading && rows.length === 0 && (
              <tr><td colSpan={6} className="p-4 text-center">Sin resultados</td></tr>
            )}
            {!loading && rows.map((p) => (
              <tr key={p.codigo} className="[&>td]:p-2 border-t">
                <td>{p.codigo}</td>
                <td>{p.nombre}</td>
                <td className="max-w-[420px] truncate" title={p.caracteristicas || ""}>{p.caracteristicas}</td>
                <td className="text-right">$ {Number(p.precio).toLocaleString()}</td>
                <td>{p.empresaNit || "-"}</td>
                <td className="text-right">
                  <div className="inline-flex gap-2">
                    <button className="btn" onClick={() => openEdit(p)}>Editar</button>
                    <button className="btn" onClick={() => eliminar(p.codigo)}>Eliminar</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Modal */}
      {openForm && (
        <div className="fixed inset-0 z-50 bg-black/40 flex items-center justify-center p-4">
          <div className="bg-white rounded-xl shadow-lg w-full max-w-2xl">
            <div className="p-4 border-b flex items-center justify-between">
              <h3 className="text-lg font-semibold">
                {isEdit ? "Editar producto" : "Nuevo producto"}
              </h3>
              <button onClick={closeForm} className="text-gray-500 hover:text-black">✕</button>
            </div>
            <form onSubmit={submit} className="p-4 space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <label className="flex flex-col gap-1">
                  <span className="text-sm">Código *</span>
                  <input
                    className="input"
                    value={model.codigo}
                    onChange={(e) => setModel({ ...model, codigo: e.target.value })}
                    required
                    disabled={isEdit}
                  />
                </label>
                <label className="flex flex-col gap-1">
                  <span className="text-sm">Nombre *</span>
                  <input
                    className="input"
                    value={model.nombre}
                    onChange={(e) => setModel({ ...model, nombre: e.target.value })}
                    required
                  />
                </label>
                <label className="flex flex-col gap-1 md:col-span-2">
                  <span className="text-sm">Características</span>
                  <textarea
                    className="input"
                    rows={3}
                    value={model.caracteristicas ?? ""}
                    onChange={(e) => setModel({ ...model, caracteristicas: e.target.value })}
                  />
                </label>
                <label className="flex flex-col gap-1">
                  <span className="text-sm">Precio *</span>
                  <input
                    type="number"
                    min={0}
                    step="0.01"
                    className="input"
                    value={model.precio}
                    onChange={(e) => setModel({ ...model, precio: Number(e.target.value) })}
                    required
                  />
                </label>
                <label className="flex flex-col gap-1">
                  <span className="text-sm">Empresa (NIT) *</span>
                  <select
                    className="input"
                    value={model.empresaNit ?? ""}
                    onChange={(e) => setModel({ ...model, empresaNit: e.target.value })}
                    required
                  >
                    <option value="">Seleccione…</option>
                    {empresas.map((e) => (
                      <option key={e.nit} value={e.nit}>
                        {e.nit} — {e.nombre}
                      </option>
                    ))}
                  </select>
                </label>
              </div>
              <div className="flex items-center justify-end gap-2 pt-2">
                <button type="button" onClick={closeForm} className="btn">Cancelar</button>
                <button disabled={saving} className="btn-primary">
                  {saving ? "Guardando…" : "Guardar"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {error && <p className="text-red-600">{error}</p>}
    </div>
  );
}
