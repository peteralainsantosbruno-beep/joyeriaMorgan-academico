package com.joyeria.controller;

import com.joyeria.model.Cliente;
import com.joyeria.service.ClienteService; // Usamos el Service

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model){
        List<Cliente> lista = clienteService.listarTodos();
        model.addAttribute("clientes", lista != null ? lista : new ArrayList<Cliente>());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cliente") Cliente cliente, RedirectAttributes ra){
        try {
            clienteService.guardar(cliente);
            ra.addFlashAttribute("success", "Cliente guardado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar el cliente");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra){
        try {
            clienteService.eliminar(id);
            ra.addFlashAttribute("success", "Cliente eliminado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "No se puede eliminar este cliente porque tiene ventas asociadas");
        }
        return "redirect:/clientes";
    }
}