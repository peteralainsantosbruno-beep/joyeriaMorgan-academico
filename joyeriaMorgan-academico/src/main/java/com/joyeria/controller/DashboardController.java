package com.joyeria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.DateFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joyeria.model.EstadoProducto;
import com.joyeria.repository.ProductoRepository;
import com.joyeria.repository.VentaRepository;

@Controller
public class DashboardController {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private VentaRepository ventaRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalProductos = productoRepo.count();
        long disponibles = productoRepo.countByEstado(EstadoProducto.DISPONIBLE);
        
        Double inventarioRaw = productoRepo.valorInventario();
        double inventario = (inventarioRaw != null) ? inventarioRaw : 0.0;

        Long ventasRaw = ventaRepo.totalVentas();
        long ventas = (ventasRaw != null) ? ventasRaw : 0L;

        Double dineroRaw = ventaRepo.dineroTotal();
        double dinero = (dineroRaw != null) ? dineroRaw : 0.0;

        List<Object[]> datos = ventaRepo.ventasPorMes();
        List<String> meses = new ArrayList<>();
        List<Double> totales = new ArrayList<>();
        
        if (datos != null && !datos.isEmpty()) {
            String[] nombresMeses = new DateFormatSymbols(new Locale("es", "ES")).getMonths();
            for(Object[] fila : datos){
                if (fila[0] != null && fila[1] != null) {
                    int nMes = ((Number) fila[0]).intValue();
                    meses.add(nombresMeses[nMes - 1]); 
                    totales.add(((Number) fila[1]).doubleValue());
                }
            }
        }

        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("disponibles", disponibles);
        model.addAttribute("inventario", inventario);
        model.addAttribute("ventas", ventas);
        model.addAttribute("dinero", dinero);
        model.addAttribute("meses", meses);
        model.addAttribute("totales", totales);

        return "dashboard"; 
    }
}