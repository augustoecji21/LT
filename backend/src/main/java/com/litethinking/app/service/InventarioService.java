
package com.litethinking.app.service;

import com.litethinking.app.dto.ProductoDTO;
import java.util.List;

public interface InventarioService {
  List<ProductoDTO> obtenerInventario();
  List<ProductoDTO> obtenerInventarioPorEmpresa(String nit); // NUEVO
  void limpiarInventario();
}
