package com.joyeria.service.impl;

import com.joyeria.model.*;
import com.joyeria.repository.*;
import com.joyeria.service.ReporteService;
import com.joyeria.dto.ComprobanteDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.*;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private VentaRepository ventaRepo;

    @Override
    public byte[] generarReporteInventarioPdf() throws Exception {
        List<Producto> lista = productoRepo.findAll();
        
        InputStream stream = getClass().getResourceAsStream("/reportes/inventario.jrxml");

        Map<String, Object> params = new HashMap<>();
        params.put("p_titulo", "INVENTARIO GENERAL - JOYERÍA MORGAN");
        params.put("p_fechaActual", java.time.LocalDate.now().toString());

        JasperPrint print = JasperFillManager.fillReport(stream, params, new JRBeanCollectionDataSource(lista));
        return JasperExportManager.exportReportToPdf(print);
    }

    @Override
    public byte[] generarBoletaVentaPdf(Long idProducto) throws Exception {
        Venta v = ventaRepo.findAll().stream()
                .filter(venta -> venta.getProducto().getId().equals(idProducto))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe una venta para este producto"));

        InputStream stream = getClass().getResourceAsStream("/reportes/boleta.jrxml");

        Map<String, Object> params = new HashMap<>();
        params.put("p_idVenta", v.getId());
        params.put("p_nombreCliente", v.getCliente().getNombres() + " " + v.getCliente().getApellidos());
        params.put("p_dniCliente", v.getCliente().getDni());
        params.put("p_fecha", v.getFecha().toString());
        params.put("p_total", v.getTotal());

        String descCompleta = v.getProducto().getTipo() + " " + v.getProducto().getMaterial();
        List<ComprobanteDTO> detalle = Collections.singletonList(
            new ComprobanteDTO(1, descCompleta, v.getProducto().getPrecio(), v.getTotal())
        );

        JasperPrint print = JasperFillManager.fillReport(stream, params, new JRBeanCollectionDataSource(detalle));
        return JasperExportManager.exportReportToPdf(print);
    }
}