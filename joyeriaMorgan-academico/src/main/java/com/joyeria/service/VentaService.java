package com.joyeria.service;

import com.joyeria.model.Venta;
import java.util.List;

public interface VentaService {
    List<Venta> listarTodas();
    Venta guardar(Venta venta);
    void eliminar(Long id);
    Venta buscarPorId(Long id);
    
    byte[] generarReportePdf(); 
}