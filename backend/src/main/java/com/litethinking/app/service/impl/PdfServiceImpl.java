package com.litethinking.app.service.impl;

import com.litethinking.app.dto.ProductoDTO;
import com.litethinking.app.service.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Service  
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] exportProductos(String titulo, List<ProductoDTO> rows) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph(titulo != null ? titulo : "Listado de Productos", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(12f);
            doc.add(title);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{18f, 38f, 24f, 20f});

            addHeader(table, "Código");
            addHeader(table, "Nombre");
            addHeader(table, "Características");
            addHeader(table, "Precio");

            if (rows != null) {
                for (ProductoDTO p : rows) {
                    table.addCell(safe(p.getCodigo()));
                    table.addCell(safe(p.getNombre()));
                    table.addCell(safe(p.getCaracteristicas()));
                    table.addCell(formatMoney(p.getPrecio()));
                }
            } else {
                PdfPCell cell = new PdfPCell(new Phrase("Sin datos"));
                cell.setColspan(4);
                table.addCell(cell);
            }

            doc.add(table);
            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }

    private static void addHeader(PdfPTable table, String text) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
        cell.setPadding(6f);
        table.addCell(cell);
    }
    private static String safe(String s) { return s == null ? "" : s; }
    private static String formatMoney(BigDecimal value) { return value == null ? "-" : "$ " + value.toPlainString(); }
}
