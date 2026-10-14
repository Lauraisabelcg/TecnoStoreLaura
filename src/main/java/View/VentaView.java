package View;

import java.util.List;


public class VentaView {

    private final Validaciones v;

    public VentaView(Validaciones v) {
        this.v = v;
    }

    public int mostrarMenu() {
        return v.validarEntero("""
            
            --- GESTION DE VENTAS ---
            1. Registrar una nueva venta
            2. Consultar factura por ID
            3. Listar historial completo de ventas
            4. Volver al menu principal
            Seleccione una opcion""");
    }
public void mostrarCelularesDisponibles(List<Model.Celular> celulares) {
    System.out.println("\n--- CELULARES DISPONIBLES ---");
    boolean hayDisponibles = false;

    for (Model.Celular c : celulares) {
        if (c.getStock() > 0) {
            hayDisponibles = true;
            System.out.println(c); // Utiliza el toString() formateado de Celular
        }
    }

    if (!hayDisponibles) {
        System.out.println("No hay celulares con stock disponible en este momento.");
    }
}
    public int pedirIdVenta() {
        return v.validarEnteroPositivo("Ingrese el ID de la factura");
    }

    public String pedirDocumentoCliente() {
    return v.validarTexto("Ingrese el numero de documento/cedula del cliente");
}

    public int pedirIdCelular() {
        return v.validarEnteroPositivo("Ingrese el ID del celular a comprar");
    }

    public int pedirCantidad() {
        return v.validarEnteroPositivo("Ingrese la cantidad a comprar");
    }

    public boolean deseaAgregarOtroProducto() {
        String resp = v.validarTexto("Desea agregar otro producto a esta venta? (S/N)");
        return resp.equalsIgnoreCase("S");
    }
}