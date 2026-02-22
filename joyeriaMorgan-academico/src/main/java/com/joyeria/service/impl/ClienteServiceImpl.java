package com.joyeria.service.impl;

import com.joyeria.model.Cliente;
import com.joyeria.repository.ClienteRepository;
import com.joyeria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        if (cliente.getNombres() != null) {
            cliente.setNombres(cliente.getNombres().toUpperCase());
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}