package com.litethinking.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsConfig {

  @Bean
  public SesClient sesClient() {
    return SesClient.builder()
        .region(Region.US_EAST_1) // Ajustar a región SES
        .build();
  }
}
