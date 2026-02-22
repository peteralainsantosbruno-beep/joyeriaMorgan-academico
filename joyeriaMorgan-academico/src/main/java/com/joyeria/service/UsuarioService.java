package com.joyeria.service;

import com.joyeria.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> listarTodos();
    Usuario guardar(Usuario usuario);
    Usuario buscarPorId(Long id);
    Usuario buscarPorUsername(String username);
    void eliminar(Long id);
}
