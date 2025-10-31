package com.litethinking.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.litethinking.app.domain.*;
import com.litethinking.app.dto.CreateOrdenRequest;
import com.litethinking.app.dto.OrdenResponse;
import com.litethinking.app.repository.*;
import com.litethinking.app.service.OrdenService;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

  private final OrdenRepository ordenRepo;
  private final ClienteRepository clienteRepo;
  private final ProductoRepository productoRepo;

  /** Crear orden con items en una sola transacción; cascade persiste la tabla join */
  @Override
  @Transactional
  public OrdenResponse crear(CreateOrdenRequest req) {
    Cliente cliente = clienteRepo.findById(req.getClienteId())
        .orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));

    Orden orden = new Orden();
    orden.setCliente(cliente);

    if (req.getItems() != null) {
      for (CreateOrdenRequest.Item it : req.getItems()) {
        Producto p = productoRepo.findById(it.getProductoCodigo())
            .orElseThrow(() -> new IllegalArgumentException("Producto no existe: " + it.getProductoCodigo()));
        int cantidad = it.getCantidad() == null ? 1 : it.getCantidad();
        orden.addItem(p, cantidad);
      }
    }

    // Un único save; gracias al cascade se persisten también los OrdenProducto
    orden = ordenRepo.save(orden);
    // Forzamos carga de items si tu fetch por defecto es LAZY (opcional):
    orden.getItems().size();

    return map(orden);
  }

  @Override
  public OrdenResponse obtener(Integer id) {
    Orden o = ordenRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Orden no existe"));
    // Si items es LAZY y necesitas serializarlos, toca accederlos
    o.getItems().size();
    return map(o);
  }

  @Override
  public List<OrdenResponse> listar() {
    return ordenRepo.findAll().stream().map(this::map).collect(Collectors.toList());
  }

  private OrdenResponse map(Orden o) {
    return new OrdenResponse(
        o.getId(),
        o.getFecha(),
        o.getCliente() != null ? o.getCliente().getId() : null,
        o.getCliente() != null ? o.getCliente().getNombre() : null,
        o.getItems().stream().map(i -> new OrdenResponse.Item(
            i.getProducto().getCodigo(),
            i.getProducto().getNombre(),
            i.getCantidad()
        )).toList()
    );
  }
}
