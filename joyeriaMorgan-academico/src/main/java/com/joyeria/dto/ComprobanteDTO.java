package com.joyeria.dto;

public class ComprobanteDTO {
    private Integer cantidad;
    private String descripcion;
    private Double precioUnitario;
    private Double importe;

    public ComprobanteDTO(Integer cantidad, String descripcion, Double precioUnitario, Double importe) {
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.importe = importe;
    }

    public Integer getCantidad() { return cantidad; }
    public String getDescripcion() { return descripcion; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public Double getImporte() { return importe; }
}