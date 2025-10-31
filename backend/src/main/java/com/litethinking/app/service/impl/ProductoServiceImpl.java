package com.litethinking.app.service.impl;

import com.litethinking.app.domain.Producto;
import com.litethinking.app.dto.ProductoDTO;
import com.litethinking.app.mapper.AppMapper;
import com.litethinking.app.repository.ProductoRepository;
import com.litethinking.app.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

  private final ProductoRepository productoRepo;

  @Override
  @Transactional(readOnly = true)
  public List<Producto> findAll() {
    return productoRepo.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Producto findByCodigo(String codigo) {
    return productoRepo.findById(codigo)
        .orElseThrow(() -> new IllegalArgumentException("Producto no existe: " + codigo));
  }

  @Override
  @Transactional
  public Producto create(ProductoDTO dto) {
    if (productoRepo.existsById(dto.getCodigo())) {
      throw new IllegalArgumentException("Ya existe un producto con código: " + dto.getCodigo());
    }
    Producto p = AppMapper.productoToEntity(dto);          
    return productoRepo.save(p);
  }

  @Override
  @Transactional
  public Producto update(String codigo, ProductoDTO dto) {
    Producto p = productoRepo.findById(codigo)
        .orElseThrow(() -> new IllegalArgumentException("Producto no existe: " + codigo));

    AppMapper.productoCopyToEntity(dto, p);                
    return productoRepo.save(p);
  }

  @Override
  @Transactional
  public void delete(String codigo) {
    if (!productoRepo.existsById(codigo)) {
      throw new IllegalArgumentException("Producto no existe: " + codigo);
    }
    productoRepo.deleteById(codigo);
  }
}
