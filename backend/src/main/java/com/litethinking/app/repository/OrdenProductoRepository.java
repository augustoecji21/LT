package com.litethinking.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.litethinking.app.domain.OrdenProducto;
import com.litethinking.app.domain.OrdenProductoId;

public interface OrdenProductoRepository extends JpaRepository<OrdenProducto, OrdenProductoId> {}
