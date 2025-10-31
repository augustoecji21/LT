package com.litethinking.app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoDTO {

  @NotBlank
  @Size(max = 20)
  private String codigo;

  @NotBlank
  @Size(max = 100)
  private String nombre;

  private String caracteristicas;

  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
  private BigDecimal precio;

   
    private BigDecimal precioCop;

    /** Calculados al vuelo */
    private BigDecimal precioUsd;
    private BigDecimal precioEur;

  
  @Size(max = 15)
  private String empresaNit;
}
