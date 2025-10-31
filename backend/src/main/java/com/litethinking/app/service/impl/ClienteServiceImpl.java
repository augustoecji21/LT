package com.litethinking.app.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.litethinking.app.domain.Cliente;
import com.litethinking.app.repository.ClienteRepository;
import com.litethinking.app.service.ClienteService;

@Service @RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
  private final ClienteRepository repo;
  @Override public Cliente save(Cliente c){ return repo.save(c); }
  @Override public List<Cliente> findAll(){ return repo.findAll(); }
  @Override public Cliente findById(Integer id){ return repo.findById(id).orElse(null); }
}
