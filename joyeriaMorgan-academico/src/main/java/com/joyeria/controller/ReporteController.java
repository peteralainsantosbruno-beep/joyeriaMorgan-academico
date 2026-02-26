package com.joyeria.controller;

import com.joyeria.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/inventario")
    public ResponseEntity<byte[]> descargarInventario() throws Exception {
        byte[] pdf = reporteService.generarReporteInventarioPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Inventario_Morgan.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdf);
    }

    @GetMapping("/boleta/{id}")
    public ResponseEntity<byte[]> descargarBoleta(@PathVariable Long id) throws Exception {
        byte[] pdf = reporteService.generarBoletaVentaPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Boleta_Producto_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdf);
    }
}