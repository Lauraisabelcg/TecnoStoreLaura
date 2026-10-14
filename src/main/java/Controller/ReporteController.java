package Controller;

import Dao.VentaDao;
import Model.Venta;
import Util.ArchivoUtils;
import View.ReporteView;
import java.sql.Connection;
import java.util.List;

public class ReporteController {

    private final VentaDao ventaDao;
    private final ReporteStream reporteStream; // Usa directamente tu clase ReporteStream
    private final ReporteView vista;

    public ReporteController(Connection conexion, ReporteView vista) {
        this.ventaDao = new VentaDao(conexion);
        this.reporteStream = new ReporteStream();
        this.vista = vista;
    }

    public void iniciar() {
        boolean volver = false;
        while (!volver) {
            int opcion = vista.mostrarMenu();
            List<Venta> ventasActuales = ventaDao.listarTodas();

            switch (opcion) {
                case 1 -> {
                    double total = reporteStream.calcularIngresosTotales(ventasActuales);
                    vista.mostrarIngresosTotales(total);
                }
                case 2 -> {
                    double monto = vista.pedirMontoMinimo();
                    List<Venta> filtradas = reporteStream.filtrarVentasPorMontoMinimo(ventasActuales, monto);
                    vista.mostrarVentasFiltradas(filtradas, monto);
                }
                case 3 -> {
                    var porCliente = reporteStream.calcularTotalFacturadoPorCliente(ventasActuales);
                    vista.mostrarTotalPorCliente(porCliente);
                }
                    case 4 -> {
                        List<String> top3 = reporteStream.obtenerTop3(ventasActuales);
                        vista.mostrarTop3(top3);
                    
                }
                case 5 -> exportarBackup(ventasActuales);
                case 6 -> importarBackup();
                case 7 -> volver = true;
                default -> vista.mostrarMensaje("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void exportarBackup(List<Venta> ventas) {
        if (ventas.isEmpty()) {
            vista.mostrarMensaje("No hay ventas en la base de datos para respaldar.");
            return;
        }
        ArchivoUtils.guardarReporteVentasBinario(ventas);
    }

    private void importarBackup() {
        List<Venta> ventasRecuperadas = ArchivoUtils.leerReporteVentasBinario();
        vista.mostrarVentasRecuperadas(ventasRecuperadas);
    }
}