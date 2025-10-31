// InventarioServiceImpl.java
package com.litethinking.app.service.impl;

import com.litethinking.app.dto.ProductoDTO;
import com.litethinking.app.mapper.AppMapper;
import com.litethinking.app.repository.ProductoRepository;
import com.litethinking.app.service.InventarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService {

  private final ProductoRepository productoRepo;

  @Override
  @Transactional(readOnly = true)
  public List<ProductoDTO> obtenerInventario() {
    return productoRepo.findAll().stream()
        .map(AppMapper::productoToDTO)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductoDTO> obtenerInventarioPorEmpresa(String nit) {
    return productoRepo.findByEmpresaNit(nit).stream()
        .map(AppMapper::productoToDTO)
        .toList();
  }

  @Override
  @Transactional
  public void limpiarInventario() {
    productoRepo.deleteAll();
  }
}