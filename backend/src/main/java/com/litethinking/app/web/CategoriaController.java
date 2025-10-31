package com.litethinking.app.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.litethinking.app.domain.Categoria;
import com.litethinking.app.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class CategoriaController {

  private final CategoriaService service;

  @GetMapping public List<Categoria> listar(){ return service.findAll(); }
  @PostMapping public Categoria crear(@RequestBody Categoria c){ return service.save(c); }
}
