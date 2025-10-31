package com.litethinking.app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "role")
@Data @NoArgsConstructor @AllArgsConstructor
public class Role {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false, length = 50)
  private String name; // "ROLE_ADMIN", "ROLE_EXTERNO"
}
