package com.litethinking.app.service.impl;

import com.litethinking.app.dto.ProductoDTO;
import com.litethinking.app.service.InventarioService;
import com.litethinking.app.service.PdfService;
import com.litethinking.app.service.SesEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsSesEmailService implements SesEmailService {   // <- IMPLEMENTA LA INTERFAZ

  private final SesClient sesClient;
  private final InventarioService inventarioService;
  private final PdfService pdfService;

  @Override
  public void enviarInventarioPdfPorEmpresa(String nit, String toEmail, String subject, String fromEmail) {
    List<ProductoDTO> productos = inventarioService.obtenerInventarioPorEmpresa(nit);
    byte[] pdfBytes = pdfService.exportProductos("Inventario Empresa " + nit, productos);
    String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

    String mime = buildRawMime(
        fromEmail, toEmail, subject,
        "Adjunto inventario de la empresa " + nit,
        "inventario_" + nit + ".pdf", "application/pdf", base64Pdf
    );

    SdkBytes data = SdkBytes.fromString(mime, StandardCharsets.UTF_8);
    SendRawEmailRequest request = SendRawEmailRequest.builder()
        .rawMessage(RawMessage.builder().data(data).build())
        .build();

    sesClient.sendRawEmail(request);
  }

  private static String buildRawMime(String from, String to, String subject,
                                     String bodyText, String filename,
                                     String contentType, String base64Data) {
    String boundary = "NextPart" + System.currentTimeMillis();
    String CRLF = "\r\n";

    StringBuilder sb = new StringBuilder();
    sb.append("From: ").append(from).append(CRLF);
    sb.append("To: ").append(to).append(CRLF);
    sb.append("Subject: ").append(subject).append(CRLF);
    sb.append("MIME-Version: 1.0").append(CRLF);
    sb.append("Content-Type: multipart/mixed; boundary=\"").append(boundary).append("\"").append(CRLF).append(CRLF);

    sb.append("--").append(boundary).append(CRLF);
    sb.append("Content-Type: text/plain; charset=UTF-8").append(CRLF);
    sb.append("Content-Transfer-Encoding: 7bit").append(CRLF).append(CRLF);
    sb.append(bodyText).append(CRLF).append(CRLF);

    sb.append("--").append(boundary).append(CRLF);
    sb.append("Content-Type: ").append(contentType).append("; name=\"").append(filename).append("\"").append(CRLF);
    sb.append("Content-Transfer-Encoding: base64").append(CRLF);
    sb.append("Content-Disposition: attachment; filename=\"").append(filename).append("\"").append(CRLF).append(CRLF);
    sb.append(base64Data).append(CRLF);
    sb.append("--").append(boundary).append("--");

    return sb.toString();
  }
}
