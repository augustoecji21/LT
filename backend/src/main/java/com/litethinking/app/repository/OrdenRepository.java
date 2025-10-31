package com.litethinking.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.litethinking.app.domain.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {}
