package com.joyeria.controller;

import com.joyeria.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ReporteController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping("/reportes")
    public String verReporte(Model model) {
        List<Object[]> datos = ventaRepository.ventasPorMes();

        List<String> mesesNombres = new ArrayList<>();
        List<Double> totales = new ArrayList<>();

        if (datos != null && !datos.isEmpty()) {
            String[] nombresMeses = new DateFormatSymbols(new Locale("es", "ES")).getMonths();

            for (Object[] fila : datos) {
                if (fila != null && fila[0] != null && fila[1] != null) {
                    try {
                        int mesIndice = ((Number) fila[0]).intValue() - 1; 
                        
                        if (mesIndice >= 0 && mesIndice < 12) {
                            mesesNombres.add(nombresMeses[mesIndice]);
                            totales.add(((Number) fila[1]).doubleValue());
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }

        model.addAttribute("meses", mesesNombres);
        model.addAttribute("totales", totales);

        return "reportes"; 
    }
}