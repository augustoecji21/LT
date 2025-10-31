package com.litethinking.app.service.impl;

import com.litethinking.app.domain.Empresa;
import com.litethinking.app.dto.EmpresaDTO;
import com.litethinking.app.mapper.AppMapper;
import com.litethinking.app.repository.EmpresaRepository;
import com.litethinking.app.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Empresa findByNit(String nit) {
        return repo.findById(nit)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no existe: " + nit));
    }

    @Override
    @Transactional
    public Empresa create(EmpresaDTO dto) {
        if (repo.existsById(dto.getNit())) {
            throw new IllegalArgumentException("Ya existe una empresa con NIT: " + dto.getNit());
        }
        Empresa e = AppMapper.empresaToEntity(dto);
        return repo.save(e);
    }

    @Override
    @Transactional
    public Empresa update(String nit, EmpresaDTO dto) {
        Empresa e = repo.findById(nit)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no existe: " + nit));
        AppMapper.empresaCopyToEntity(dto, e);
        return repo.save(e);
    }

    @Override
    @Transactional
    public void delete(String nit) {
        if (!repo.existsById(nit)) {
            throw new IllegalArgumentException("Empresa no existe: " + nit);
        }
        repo.deleteById(nit);
    }
}
