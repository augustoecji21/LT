package com.litethinking.app.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProductoId implements Serializable {
  private Integer ordenId;
  private String productoCodigo;
}
