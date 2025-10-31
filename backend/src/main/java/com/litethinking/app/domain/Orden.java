package com.litethinking.app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private LocalDate fecha = LocalDate.now();

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  
  @OneToMany(
      mappedBy = "orden",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<OrdenProducto> items = new LinkedHashSet<>();

  /** Helper para mantener la relación bidireccional y que cascade persista los items */
  public void addItem(Producto p, int cantidad) {
    OrdenProducto op = new OrdenProducto();
    // El id embebido se recalcula tras persistir (orden.id ya estará asignado)
    op.setId(new OrdenProductoId(this.id, p.getCodigo()));
    op.setOrden(this);
    op.setProducto(p);
    op.setCantidad(cantidad <= 0 ? 1 : cantidad);
    this.items.add(op);
  }

  public void clearItems() {
    this.items.clear();
  }
}
