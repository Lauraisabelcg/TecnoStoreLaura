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
public double calcularSubtotalBruto() {
        if (detalles == null || detalles.isEmpty()) {
            return 0.0;
        }
        return detalles.stream()
                       .mapToDouble(DetalleVenta::getSubtotal)
                       .sum();
}
    @Override
    public String toString() {
        StringBuilder listaProductos = new StringBuilder();
        if (detalles != null && !detalles.isEmpty()) {
            for (DetalleVenta d : detalles) {
                listaProductos.append("   ").append(d.toString()).append("\n");
            }
        } else {
            listaProductos.append("   (Sin productos registrados)\n");
        }

        return """
               =============================================
               FACTURA DE VENTA #%d
               Fecha: %s
               Cliente: %s (Doc: %s)
               ---------------------------------------------
               DETALLE DE COMPRA:
               %s---------------------------------------------
               Subtotal:  $%,.2f
               Descuento: $%,.2f
               IVA (19%%): $%,.2f
               TOTAL:     $%,.2f
               =============================================
               """.formatted(
                id, 
                fecha, 
                cliente.getNombre(), 
                cliente.getDocumento(), 
                listaProductos.toString(), 
                calcularSubtotalBruto(), 
                descuento, 
                iva, 
                total
        );
    }
}

