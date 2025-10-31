package com.litethinking.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.litethinking.app.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {}
