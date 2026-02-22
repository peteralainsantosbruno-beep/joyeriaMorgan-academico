package com.joyeria.service.impl;

import com.joyeria.model.Cliente;
import com.joyeria.repository.ClienteRepository;
import com.joyeria.service.ReporteService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Override
    public byte[] generarReporteClientesPdf() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document documento = new Document(PageSize.A4);
        
        try {
            PdfWriter.getInstance(documento, salida);
            documento.open();

            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("LISTADO DE CLIENTES - JOYERÍA MORGAN", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            
            tabla.addCell("DNI");
            tabla.addCell("Nombres");
            tabla.addCell("Apellidos");
            tabla.addCell("Teléfono");

            List<Cliente> clientes = clienteRepo.findAll();
            for (Cliente c : clientes) {
                tabla.addCell(c.getDni());
                tabla.addCell(c.getNombres());
                tabla.addCell(c.getApellidos());
                tabla.addCell(c.getTelefono());
            }

            documento.add(tabla);
            documento.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return salida.toByteArray();
    }
}