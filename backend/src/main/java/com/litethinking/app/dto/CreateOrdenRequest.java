package com.litethinking.app.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateOrdenRequest {
  @NotNull
  private Integer clienteId;

  @NotEmpty
  @Valid
  private List<Item> items;

  @Data @NoArgsConstructor @AllArgsConstructor
  public static class Item {
    @NotNull
    private String productoCodigo;

    @NotNull @Min(1)
    private Integer cantidad;
  }
}
