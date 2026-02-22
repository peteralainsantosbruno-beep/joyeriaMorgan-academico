package com.joyeria.controller;

import com.joyeria.model.Usuario;
import com.joyeria.service.UsuarioService; // Usamos el Service para el encriptado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model){
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("usuario", new Usuario());
        String[] roles = {"ADMIN", "VENDEDOR"};
        model.addAttribute("rolesDisponibles", roles);
        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes ra){
        try {
            usuarioService.guardar(usuario);
            ra.addFlashAttribute("success", "Usuario gestionado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar usuario: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        String[] roles = {"ADMIN", "VENDEDOR"};
        model.addAttribute("rolesDisponibles", roles);
        return "usuarios/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra){
        usuarioService.eliminar(id);
        ra.addFlashAttribute("success", "Usuario eliminado");
        return "redirect:/usuarios";
    }
}