package com.litethinking.app.service;

import com.litethinking.app.domain.Producto;
import com.litethinking.app.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {

  /** Lista todos los productos (sin paginación) */
  List<Producto> findAll();

  /** Busca un producto por código o lanza IllegalArgumentException si no existe */
  Producto findByCodigo(String codigo);

  /** Crea un producto a partir del DTO */
  Producto create(ProductoDTO dto);

  /** Actualiza un producto existente identificado por código */
  Producto update(String codigo, ProductoDTO dto);

  /** Elimina un producto por su código */
  void delete(String codigo);
}

