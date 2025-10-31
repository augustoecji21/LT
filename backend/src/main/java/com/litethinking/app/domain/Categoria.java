package com.litethinking.app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="categoria")
@Data @NoArgsConstructor @AllArgsConstructor
public class Categoria {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false, length=100)
  private String nombre;
}
