package com.litethinking.app.web;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.litethinking.app.domain.Cliente;
import com.litethinking.app.dto.ClienteDTO;
import com.litethinking.app.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class ClienteController {
  private final ClienteService service;

  @GetMapping public List<Cliente> listar(){ return service.findAll(); }

  @PostMapping public Cliente crear(@Valid @RequestBody ClienteDTO dto){
    Cliente c = new Cliente();
    c.setNombre(dto.getNombre());
    c.setCorreo(dto.getCorreo());
    return service.save(c);
  }

  @GetMapping("/{id}") public Cliente obtener(@PathVariable Integer id){ return service.findById(id); }
}
