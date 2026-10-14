package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Cliente cliente;
    private LocalDateTime fecha;
    private double descuento;
    private double iva;
    private double total;
    private List<DetalleVenta> detalles;

 
    public Venta(int id, Cliente cliente, LocalDateTime fecha, double descuento, double iva, double total) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.descuento = descuento;
        this.iva = iva;
        this.total = total;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        this.detalles.add(detalle);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return """
               =========================
               Id Venta:   %s
               Fecha:      %s
               Cliente:    %s (Doc: %s)
               Descuento:  $%s
               IVA (19%%):  $%s
               Total:      $%s
               =========================
               """.formatted(id, fecha, cliente.getNombre(), cliente.getDocumento(), descuento, iva, total);
    }
}

