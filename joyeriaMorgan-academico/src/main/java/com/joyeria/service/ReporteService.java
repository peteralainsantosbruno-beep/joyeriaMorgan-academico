package com.joyeria.service;

public interface ReporteService {
    byte[] generarReporteInventarioPdf() throws Exception;
    byte[] generarBoletaVentaPdf(Long idVenta) throws Exception;
}