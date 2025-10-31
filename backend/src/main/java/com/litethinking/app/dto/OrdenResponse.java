package com.litethinking.app.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrdenResponse {
  private Integer id;
  private LocalDate fecha;
  private Integer clienteId;
  private String clienteNombre;
  private List<Item> items;

  @Data @NoArgsConstructor @AllArgsConstructor
  public static class Item {
    private String codigo;
    private String nombre;
    private Integer cantidad;
  }
}
