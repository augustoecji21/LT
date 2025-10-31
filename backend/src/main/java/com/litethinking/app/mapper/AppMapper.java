package com.litethinking.app.mapper;

import com.litethinking.app.domain.Producto;
import com.litethinking.app.dto.ProductoDTO;

import com.litethinking.app.domain.Empresa;
import com.litethinking.app.dto.EmpresaDTO;

public class AppMapper {

    // ====== PRODUCTO ======
    public static ProductoDTO productoToDTO(Producto p) {
        if (p == null) {
            return null;
        }
        ProductoDTO dto = new ProductoDTO();
        dto.setCodigo(p.getCodigo());
        dto.setNombre(p.getNombre());
        dto.setCaracteristicas(p.getCaracteristicas());
        dto.setPrecio(p.getPrecio());
        dto.setEmpresaNit(p.getEmpresaNit()); 
        return dto;
    }

    public static Producto productoToEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }
        Producto p = new Producto();
        p.setCodigo(dto.getCodigo());
        p.setNombre(dto.getNombre());
        p.setCaracteristicas(dto.getCaracteristicas());
        p.setPrecio(dto.getPrecioCop() != null ? dto.getPrecioCop() : dto.getPrecio());
        p.setEmpresaNit(dto.getEmpresaNit());
        return p;
    }

    public static void productoCopyToEntity(ProductoDTO dto, Producto p) {
        if (dto == null || p == null) {
            return;
        }
        p.setNombre(dto.getNombre());
        p.setCaracteristicas(dto.getCaracteristicas());
        p.setPrecio(dto.getPrecio());
        p.setEmpresaNit(dto.getEmpresaNit()); 
    }

    // ====== EMPRESA ======
    public static EmpresaDTO empresaToDTO(Empresa e) {
    if (e == null) return null;
    EmpresaDTO dto = new EmpresaDTO();
    dto.setNit(e.getNit());
    dto.setNombre(e.getNombre());
    dto.setDireccion(e.getDireccion());   
    dto.setTelefono(e.getTelefono());     
    return dto;
  }

  public static Empresa empresaToEntity(EmpresaDTO dto) {
    if (dto == null) return null;
    Empresa e = new Empresa();
    e.setNit(dto.getNit());
    e.setNombre(dto.getNombre());
    e.setDireccion(dto.getDireccion());   
    e.setTelefono(dto.getTelefono());     
    return e;
  }

  public static void empresaCopyToEntity(EmpresaDTO dto, Empresa e) {
    if (dto == null || e == null) return;
    e.setNombre(dto.getNombre());
    e.setDireccion(dto.getDireccion());   
    e.setTelefono(dto.getTelefono());     
  }

}
