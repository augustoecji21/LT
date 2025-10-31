package com.litethinking.app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "producto")
public class Producto {

  @Id
  @Column(length = 20)
  private String codigo;

  @Column(nullable = false, length = 100)
  private String nombre;

  @Column(length = 500)
  private String caracteristicas;

  @Column(nullable = false, precision = 18, scale = 2)
  private BigDecimal precio;

  
  @Column(name = "empresa_nit", length = 15)
  private String empresaNit;   
}
