package com.joyeria.service.impl;

import com.joyeria.model.Producto;
import com.joyeria.model.EstadoProducto;
import com.joyeria.repository.ProductoRepository;
import com.joyeria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> listarDisponibles() {
        return productoRepository.findByEstado(EstadoProducto.DISPONIBLE);
    }

    @Override
    public Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            producto.setEstado(EstadoProducto.DISPONIBLE);
        }
        return productoRepository.save(producto);
    }

    @Override
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}