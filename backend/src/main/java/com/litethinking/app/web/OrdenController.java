package com.litethinking.app.web;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.litethinking.app.dto.CreateOrdenRequest;
import com.litethinking.app.dto.OrdenResponse;
import com.litethinking.app.service.OrdenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class OrdenController {

  private final OrdenService service;

  @PostMapping
  public OrdenResponse crear(@Valid @RequestBody CreateOrdenRequest req){
    return service.crear(req);
  }

  @GetMapping("/{id}")
  public OrdenResponse obtener(@PathVariable Integer id){
    return service.obtener(id);
  }

  @GetMapping
  public List<OrdenResponse> listar(){
    return service.listar();
  }
}
