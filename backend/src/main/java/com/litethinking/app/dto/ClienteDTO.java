package com.litethinking.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteDTO {
  private Integer id;

  @NotBlank @Size(max = 100)
  private String nombre;

  @NotBlank @Email @Size(max = 100)
  private String correo;
}
