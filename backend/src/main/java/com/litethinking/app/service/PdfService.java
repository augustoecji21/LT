package com.litethinking.app.service;

import com.litethinking.app.dto.ProductoDTO;
import java.util.List;

public interface PdfService {
    byte[] exportProductos(String titulo, List<ProductoDTO> productos);
}
