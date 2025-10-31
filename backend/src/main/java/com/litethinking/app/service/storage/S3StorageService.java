package com.litethinking.app.service.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3StorageService {

  @Value("${app.aws.region:us-east-1}")
  private String region;

  @Value("${app.aws.s3.bucket:lt-inventarios-demo}")
  private String bucket;

  @Value("${app.aws.s3.presign-exp-seconds:900}")
  private long expSeconds;

  public Map<String, Object> uploadPdfAndPresign(byte[] pdf, String nit) {
    String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String key = "inventarios/" + nit + "/inventario_" + nit + "_" + ts + ".pdf";

    Region rg = Region.of(region);

    try (S3Client s3 = S3Client.builder()
             .region(rg)
             .credentialsProvider(DefaultCredentialsProvider.create())
             .build();
         S3Presigner presigner = S3Presigner.builder()
             .region(rg)
             .credentialsProvider(DefaultCredentialsProvider.create())
             .build()) {

      // Subir como privado (por defecto)
      PutObjectRequest put = PutObjectRequest.builder()
          .bucket(bucket)
          .key(key)
          .contentType("application/pdf")
          .build();

      s3.putObject(put, RequestBody.fromBytes(pdf));

      // Presignar GET
      GetObjectPresignRequest presign = GetObjectPresignRequest.builder()
          .signatureDuration(Duration.ofSeconds(expSeconds))
          .getObjectRequest(b -> b.bucket(bucket).key(key))
          .build();

      PresignedGetObjectRequest req = presigner.presignGetObject(presign);
      URL url = req.url();

      return Map.of(
          "bucket", bucket,
          "key", key,
          "url", url.toString(),
          "expiresInSeconds", expSeconds
      );
    } catch (S3Exception e) {
      throw new RuntimeException("Error S3: " + e.awsErrorDetails().errorMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Fallo subiendo/presignando PDF en S3: " + e.getMessage(), e);
    }
  }
}
