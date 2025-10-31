package com.litethinking.app.web;

import com.litethinking.app.domain.Empresa;
import com.litethinking.app.dto.EmpresaDTO;
import com.litethinking.app.mapper.AppMapper;
import com.litethinking.app.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listar() {
        List<Empresa> empresas = empresaService.findAll();
        List<EmpresaDTO> response = empresas.stream()
                .map(AppMapper::empresaToDTO) 
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{nit}")
    public ResponseEntity<EmpresaDTO> obtener(@PathVariable String nit) {
        Empresa e = empresaService.findByNit(nit);
        return ResponseEntity.ok(AppMapper.empresaToDTO(e));
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> crear(@Valid @RequestBody EmpresaDTO dto) {
        Empresa e = empresaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppMapper.empresaToDTO(e));
    }

    @PutMapping("/{nit}")
    public ResponseEntity<EmpresaDTO> actualizar(@PathVariable String nit, @Valid @RequestBody EmpresaDTO dto) {
        Empresa e = empresaService.update(nit, dto);
        return ResponseEntity.ok(AppMapper.empresaToDTO(e));
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<Void> eliminar(@PathVariable String nit) {
        empresaService.delete(nit);
        return ResponseEntity.noContent().build();
    }
}
