package com.joyeria.controller;

import com.joyeria.model.Venta;
import com.joyeria.service.VentaService;
import com.joyeria.service.ProductoService;
import com.joyeria.service.ClienteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;

    // LISTAR TODAS LAS VENTAS
    @GetMapping
    public String listar(Model model){
        model.addAttribute("ventas", ventaService.listarTodas());
        return "ventas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model){
        model.addAttribute("venta", new Venta());
        model.addAttribute("productos", productoService.listarDisponibles());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("venta") Venta venta, RedirectAttributes ra){
        try {
            ventaService.guardar(venta);
            ra.addFlashAttribute("success", "Venta realizada con éxito");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al procesar la venta: " + e.getMessage());
            return "redirect:/ventas/nueva";
        }
        
        return "redirect:/ventas";
    }
    @GetMapping("/reportes")
    public String mostrarReportes(Model model) {
        List<Venta> listaVentas = ventaService.listarTodas();
        Double[] totalesMensuales = new Double[12];
        java.util.Arrays.fill(totalesMensuales, 0.0);
        String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                                 "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        for (Venta v : listaVentas) {
            if (v.getFecha() != null) {
                int mesIndice = v.getFecha().getMonthValue() - 1;
                totalesMensuales[mesIndice] += v.getTotal();
            }
        }
        
        model.addAttribute("ventas", listaVentas);
        model.addAttribute("meses", nombresMeses);
        model.addAttribute("totales", totalesMensuales);
        
        return "reportes";
    }
    
    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> descargarPdf() {
        byte[] pdf = ventaService.generarReportePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Ventas_Morgan.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}