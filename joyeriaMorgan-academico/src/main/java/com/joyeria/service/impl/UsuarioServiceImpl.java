package com.joyeria.service.impl;

import com.joyeria.model.Usuario;
import com.joyeria.repository.UsuarioRepository;
import com.joyeria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null || !usuario.getPassword().startsWith("$2a$")) { 
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        
        if (!usuario.getRol().startsWith("ROLE_")) {
            usuario.setRol("ROLE_" + usuario.getRol().toUpperCase());
        }
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}