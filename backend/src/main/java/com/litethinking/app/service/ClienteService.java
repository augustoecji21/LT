package com.litethinking.app.service;

import java.util.List;
import com.litethinking.app.domain.Cliente;

public interface ClienteService {
  Cliente save(Cliente c);
  List<Cliente> findAll();
  Cliente findById(Integer id);
}
