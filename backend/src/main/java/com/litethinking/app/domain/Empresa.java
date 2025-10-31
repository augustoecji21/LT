package com.litethinking.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

  @Id
  @Column(length = 15)
  private String nit;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(length = 200)           
  private String direccion;

  @Column(length = 30)            
  private String telefono;
}
