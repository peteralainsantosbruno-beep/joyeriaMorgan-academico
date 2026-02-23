package com.joyeria.service.impl;

import com.joyeria.model.Venta;
import com.joyeria.service.VentaService;
import com.joyeria.repository.VentaRepository; // Asumiendo que existe
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepo;
    
    @Autowired
    private com.joyeria.repository.ProductoRepository productoRepo;

    @Override
    public List<Venta> listarTodas() {
        return ventaRepo.findAll();
    }

    @Override
    @jakarta.transaction.Transactional 
    public Venta guardar(Venta venta) {
        com.joyeria.model.Producto producto = productoRepo.findById(venta.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        venta.setTotal(producto.getPrecio());

        venta.setFecha(java.time.LocalDate.now());

        producto.setEstado(com.joyeria.model.EstadoProducto.VENDIDO); 
        productoRepo.save(producto);

        return ventaRepo.save(venta);
    }

    @Override
    public void eliminar(Long id) {
        ventaRepo.deleteById(id);
    }

    @Override
    public Venta buscarPorId(Long id) {
        return ventaRepo.findById(id).orElse(null);
    }

    @Override
    public byte[] generarReportePdf() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try (Document documento = new Document(PageSize.A4)) {
            PdfWriter.getInstance(documento, salida);
            documento.open();
            
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("REPORTE DE VENTAS - JOYERÍA MORGAN", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(4); 
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            
            tabla.setWidths(new float[]{1.5f, 3.5f, 5f, 3f});

            String[] headers = {"ID", "Fecha", "Cliente", "Total"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, java.awt.Color.WHITE)));
                cell.setBackgroundColor(new java.awt.Color(175, 145, 100));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                tabla.addCell(cell);
            }

            List<Venta> ventas = ventaRepo.findAll();
            for (Venta v : ventas) {
                tabla.addCell(v.getId().toString());
                tabla.addCell(v.getFecha().toString());
                
                String nombreCompleto = (v.getCliente() != null) 
                    ? v.getCliente().getNombres() + " " + v.getCliente().getApellidos() 
                    : "Público General";
                
                tabla.addCell(nombreCompleto);
                tabla.addCell(String.format("S/.%.2f", v.getTotal()));
            }

            documento.add(tabla);
            documento.close(); 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return salida.toByteArray();
    }
}