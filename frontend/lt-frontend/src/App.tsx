import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import { useAuth } from "./auth/AuthContext";
import Login from "./pages/Login";
import Empresas from "./pages/Empresas";
import Inventario from "./pages/Inventario";
import ProtectedRoute from "./auth/ProtectedRoute";
import Productos from "./pages/Productos";

const App: React.FC = () => {
  const { token, logout, isAdmin } = useAuth();

  return (
    <>
      <nav style={{ display:"flex", gap:12, padding:12, borderBottom:"1px solid #ddd" }}>
        <Link to="/">Empresas</Link>
        <Link to="/inventario">Inventario</Link>
        <Link to="/productos">Productos</Link>
        <span style={{ flex:1 }} />
        {token ? (
          <>
            <span>{isAdmin ? "ADMIN" : "EXTERNO"}</span>
            <button onClick={logout}>Salir</button>
          </>
        ) : (
          <Link to="/login">Login</Link>
        )}
      </nav>

      <Routes>
        <Route path="/" element={<Empresas />} />
        <Route path="/inventario" element={<Inventario />} />
        <Route path="/login" element={<Login />} />
        <Route path="/productos" element={<Productos />} />
        {/* ejemplo: ruta solo para admin */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute requireAdmin>
              <div style={{ padding:24 }}>Vista solo ADMIN</div>
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
};

export default App;
