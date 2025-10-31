package com.litethinking.app.service;

import java.util.List;
import com.litethinking.app.domain.Categoria;

public interface CategoriaService {
  Categoria save(Categoria c);
  List<Categoria> findAll();
}
