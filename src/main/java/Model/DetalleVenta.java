package Model;

import java.io.Serializable;

public class DetalleVenta implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int ventaId;
    private Celular celular;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleVenta(int id, int ventaId, Celular celular, int cantidad, double precioUnitario, double subtotal) {
        this.id = id;
        this.ventaId = ventaId;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return """
               -------------------------
               Id Detalle:      %s
               Celular:         %s
               Cantidad:        %s
               Precio Unitario: $%.2f
               Subtotal:        $%.2f
               """.formatted(id, celular.getModelo(), cantidad, precioUnitario, subtotal);
    }
}