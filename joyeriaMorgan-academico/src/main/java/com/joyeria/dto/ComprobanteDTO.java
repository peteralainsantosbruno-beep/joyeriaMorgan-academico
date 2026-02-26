package com.joyeria.dto;

public class ComprobanteDTO {
    private Integer cantidad;
    private String descripcion;
    private Double precioUnitario;
    private Double importe;

    // Constructor vacío (necesario para algunas librerías)
    public ComprobanteDTO() {
    }

    // Constructor con campos
    public ComprobanteDTO(Integer cantidad, String descripcion, Double precioUnitario, Double importe) {
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.importe = importe;
    }

    // Getters y Setters (JasperReports los usa para leer los datos)
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }
}
