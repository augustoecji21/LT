package com.litethinking.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaDTO {
  @NotBlank @Size(max = 15)
  private String nit;

  @NotBlank @Size(max = 120)
  private String nombre;

  @Size(max = 200)    
  private String direccion;

  @Size(max = 30)     
  private String telefono;
}
