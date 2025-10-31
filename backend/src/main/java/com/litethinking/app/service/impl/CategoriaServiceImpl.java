package com.litethinking.app.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.litethinking.app.domain.Categoria;
import com.litethinking.app.repository.CategoriaRepository;
import com.litethinking.app.service.CategoriaService;

@Service @RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
  private final CategoriaRepository repo;
  @Override public Categoria save(Categoria c){ return repo.save(c); }
  @Override public List<Categoria> findAll(){ return repo.findAll(); }
}
