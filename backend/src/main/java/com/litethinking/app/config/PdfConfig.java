package com.litethinking.app.config;

import com.litethinking.app.service.PdfService;
import com.litethinking.app.service.impl.PdfServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfConfig {
  @Bean
  public PdfService pdfService() {
    return new PdfServiceImpl();
  }
}
