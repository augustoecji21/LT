package com.litethinking.app.web;

import com.litethinking.app.dto.ProductoDTO;
import com.litethinking.app.service.InventarioService;
import com.litethinking.app.service.PdfService;
import com.litethinking.app.service.SesEmailService;  // <- IMPORTA LA INTERFAZ
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class InventarioController {

  private final InventarioService inventarioService;
  private final PdfService pdfService;
  private final SesEmailService sesEmailService;  // <- INYECTA LA INTERFAZ

  @GetMapping("/{nit}")
  public ResponseEntity<List<ProductoDTO>> porEmpresa(@PathVariable String nit) {
    return ResponseEntity.ok(inventarioService.obtenerInventarioPorEmpresa(nit));
  }

  @GetMapping(value = "/{nit}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> pdfPorEmpresa(@PathVariable String nit) {
    byte[] pdf = pdfService.exportProductos("Inventario Empresa " + nit,
        inventarioService.obtenerInventarioPorEmpresa(nit));
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=inventario_" + nit + ".pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }

  @PostMapping("/{nit}/email")
  public ResponseEntity<Void> enviarPorEmail(@PathVariable String nit, @RequestBody Map<String, String> body) {
    String to = body.getOrDefault("to", "");
    if (to.isBlank()) return ResponseEntity.badRequest().build();
    sesEmailService.enviarInventarioPdfPorEmpresa(nit, to, "Inventario " + nit, "augustoecheverriaj@gmail.com");
    return ResponseEntity.accepted().build();
  }
}
