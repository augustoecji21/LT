package com.litethinking.app.service;

import java.util.List;
import com.litethinking.app.dto.CreateOrdenRequest;
import com.litethinking.app.dto.OrdenResponse;

public interface OrdenService {
  OrdenResponse crear(CreateOrdenRequest req);
  OrdenResponse obtener(Integer id);
  List<OrdenResponse> listar();
}
