package com.litethinking.app.service;

import com.litethinking.app.domain.Empresa;
import com.litethinking.app.dto.EmpresaDTO;

import java.util.List;

public interface EmpresaService {

    List<Empresa> findAll();

    Empresa findByNit(String nit);

    Empresa create(EmpresaDTO dto);

    Empresa update(String nit, EmpresaDTO dto);

    void delete(String nit);
}
