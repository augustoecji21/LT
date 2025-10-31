package com.litethinking.app.web;

import java.time.OffsetDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
  private OffsetDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Map<String, String> fieldErrors;
}
