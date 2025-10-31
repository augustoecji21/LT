package com.litethinking.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI().info(new Info()
        .title("Lite Thinking API")
        .version("v1")
        .description("API de prueba técnica con validaciones, manejo de errores y PDF/SES"));
  }
}
