package Controller;

import Dao.CelularDao;
import Dao.ClienteDao;
import Dao.VentaDao;
import Model.Celular;
import Model.Cliente;
import Model.DetalleVenta;
import Model.Venta;
import View.VentaView;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class VentaController {

    private final VentaDao ventaDao;
    private final ClienteDao clienteDao;
    private final CelularDao celularDao;
    private final VentaView vista;

    public VentaController(Connection conexion, VentaView vista) {
        this.ventaDao = new VentaDao(conexion);
        this.clienteDao = new ClienteDao(conexion);
        this.celularDao = new CelularDao(conexion);
        this.vista = vista;
    }

    public void iniciar() {
        boolean volver = false;
        while (!volver) {
            int opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1:
                    registrarVenta();
                    break;
                case 2:
                    consultarFacturaPorId();
                    break;
                case 3:
                    listarHistorialVentas();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
    }

    private void registrarVenta() {
        System.out.println("\n--- REGISTRO DE NUEVA VENTA ---");

       
        String documento = vista.pedirDocumentoCliente();
        Cliente cliente = clienteDao.buscarPorDocumento(documento);

        if (cliente == null) {
            System.out.println("No se encontro ningun cliente con el documento: " + documento);
            return;
        }

        System.out.println("Cliente: " + cliente.getNombre() + " (" + cliente.getDocumento() + ")");

        Venta venta = new Venta(0, cliente, LocalDateTime.now(), 0.0, 0.0, 0.0);

        boolean continuarAgregando = true;
        while (continuarAgregando) {
            vista.mostrarCelularesDisponibles(celularDao.listar());

            int idCelular = vista.pedirIdCelular();
            Celular celular = celularDao.buscarPorId(idCelular);

            if (celular == null) {
                System.out.println("No existe ningun celular con el ID: " + idCelular);
            } else {
                System.out.println("Seleccionado: " + celular.getModelo() + " | Stock disponible: " + celular.getStock());
                int cantidad = vista.pedirCantidad();

                if (cantidad > celular.getStock()) {
                    System.out.println("Stock insuficiente. Cantidad disponible: " + celular.getStock());
                } else {
                    double precioUnitario = celular.getPrecio();
                    double subtotal = precioUnitario * cantidad;

                    DetalleVenta detalle = new DetalleVenta(0, 0, celular, cantidad, precioUnitario, subtotal);
                    venta.agregarDetalle(detalle);
                    System.out.println("Producto agregado al carrito de compra.");
                }
            }

            continuarAgregando = vista.deseaAgregarOtroProducto();
        }

        if (venta.getDetalles().isEmpty()) {
            System.out.println("No se agregaron productos. Venta cancelada.");
            return;
        }

        // 4. Calculos financieros
        double subtotalBruto = venta.calcularSubtotalBruto();

        double porcentajeDescuento = (cliente.getTipoCliente() != null)
                ? cliente.getTipoCliente().getPorcentajeDescuento()
                : 0.0;

        double descuento = subtotalBruto * porcentajeDescuento;
        double baseImponible = subtotalBruto - descuento;
        double iva = baseImponible * 0.19; // IVA 19%
        double total = baseImponible + iva;

        venta.setDescuento(descuento);
        venta.setIva(iva);
        venta.setTotal(total);

        boolean guardado = ventaDao.registrarVenta(venta);
        if (guardado) {
            System.out.println("\n¡Venta registrada exitosamente!");
            System.out.println(venta);
        } else {
            System.out.println("Error al procesar la venta en la base de datos.");
        }
    }

    private void consultarFacturaPorId() {
        int idVenta = vista.pedirIdVenta();
        Venta venta = ventaDao.obtenerVentaPorId(idVenta);
        if (venta != null) {
            System.out.println(venta);
        } else {
            System.out.println("No se encontro ninguna factura con el ID: " + idVenta);
        }
    }

    private void listarHistorialVentas() {
        List<Venta> ventas = ventaDao.listarTodas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas en el sistema.");
            return;
        }
        System.out.println("\n================ HISTORIAL DE VENTAS ================");
        for (Venta v : ventas) {
            System.out.println(v);
        }
    }
}