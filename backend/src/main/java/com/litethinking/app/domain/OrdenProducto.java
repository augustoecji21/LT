package com.litethinking.app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orden_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProducto {

  @EmbeddedId
  private OrdenProductoId id;

  @ManyToOne
  @MapsId("ordenId")
  @JoinColumn(name = "orden_id")
  private Orden orden;

  @ManyToOne
  @MapsId("productoCodigo")
  @JoinColumn(name = "producto_codigo")
  private Producto producto;

  private Integer cantidad = 1;

  /** Igualdad basada en el ID embebido para que el Set funcione correctamente */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrdenProducto other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
