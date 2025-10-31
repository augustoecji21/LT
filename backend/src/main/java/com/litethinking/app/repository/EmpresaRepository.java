package com.litethinking.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.litethinking.app.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
