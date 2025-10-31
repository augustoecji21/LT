package com.litethinking.app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="cliente")
@Data @NoArgsConstructor @AllArgsConstructor
public class Cliente {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false, length=100)
  private String nombre;

  @Column(nullable=false, unique=true, length=100)
  private String correo;
}
