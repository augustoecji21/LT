package com.litethinking.app.web;

import com.litethinking.app.domain.Producto;
import com.litethinking.app.dto.ProductoDTO;
import com.litethinking.app.mapper.AppMapper;
import com.litethinking.app.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        List<Producto> productos = productoService.findAll();
        List<ProductoDTO> response = productos.stream()
                .map(AppMapper::productoToDTO) 
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable String codigo) {
        Producto p = productoService.findByCodigo(codigo);
        return ResponseEntity.ok(AppMapper.productoToDTO(p)); 
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        Producto p = productoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppMapper.productoToDTO(p)); 
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable String codigo, @Valid @RequestBody ProductoDTO dto) {
        Producto p = productoService.update(codigo, dto);
        return ResponseEntity.ok(AppMapper.productoToDTO(p)); 
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        productoService.delete(codigo);
        return ResponseEntity.noContent().build();
    }
}
