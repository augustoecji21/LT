package com.litethinking.app.repository;

import java.util.List;

import com.litethinking.app.domain.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductoRepository extends JpaRepository<Producto, String> {
  List<Producto> findByEmpresaNit(String empresaNit);
}