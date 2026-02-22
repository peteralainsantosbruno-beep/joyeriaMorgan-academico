package com.joyeria.controller;

import com.joyeria.model.Producto;
import com.joyeria.model.EstadoProducto;
import com.joyeria.service.ProductoService; // CAMBIO: Usamos el Service

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model model){
        model.addAttribute("productos", productoService.listarTodos());
        
        long total = productoService.listarTodos().size();
        long disponibles = productoService.listarDisponibles().size();
        double valorTotal = productoService.listarTodos().stream()
                                           .mapToDouble(Producto::getPrecio)
                                           .sum();

        model.addAttribute("total", total);
        model.addAttribute("disponibles", disponibles);
        model.addAttribute("valor", valorTotal);
        
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("estados", EstadoProducto.values());
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
                          BindingResult result,
                          RedirectAttributes ra,
                          Model model){
        if(result.hasErrors()){
            model.addAttribute("estados", EstadoProducto.values());
            return "productos/form";
        }
        
        productoService.guardar(producto);
        ra.addFlashAttribute("success", "Producto procesado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra){
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            ra.addFlashAttribute("error", "El producto no existe");
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("estados", EstadoProducto.values());
        return "productos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra){
        Producto p = productoService.buscarPorId(id);
        if (p != null) {
            p.setEstado(EstadoProducto.ANULADO);
            productoService.guardar(p);
            ra.addFlashAttribute("success", "Producto anulado correctamente");
        }
        return "redirect:/productos";
    }
}