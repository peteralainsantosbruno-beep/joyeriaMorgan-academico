package com.joyeria.service;

import com.joyeria.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarTodos();
    List<Producto> listarDisponibles();
    Producto guardar(Producto producto);
    Producto buscarPorId(Long id);
    void eliminar(Long id);
}
