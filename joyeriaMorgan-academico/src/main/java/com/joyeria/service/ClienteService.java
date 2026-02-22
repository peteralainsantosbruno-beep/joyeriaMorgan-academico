package com.joyeria.service;

import com.joyeria.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listarTodos();
    Cliente guardar(Cliente cliente);
    Cliente buscarPorId(Long id);
    Optional<Cliente> buscarPorDni(String dni);
    void eliminar(Long id);
}