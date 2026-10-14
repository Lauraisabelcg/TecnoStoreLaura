package Controller;


import View.CelularView;
import View.ClienteView;
import View.MenuPrincipalView;
import View.ReporteView;
import View.Validaciones;
import View.VentaView;
import java.sql.Connection;

public class MenuPrincipalController {

    private final Connection conexion;
    private final MenuPrincipalView vista;
    private final Validaciones validador;

    public MenuPrincipalController(Connection conexion, MenuPrincipalView vista) {
        this.conexion = conexion;
        this.vista = vista;
        this.validador = new Validaciones();
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            int opcion = vista.mostrarMenuPrincipal();
            switch (opcion) {
                case 1 -> {
                    CelularView celularView = new CelularView(validador);
                    CelularController celularCtrl = new CelularController(conexion, celularView);
                    celularCtrl.iniciar();
}
                
                case 2 -> {
                    ClienteView clienteView = new ClienteView(validador);
                    ClienteController clienteCtrl = new ClienteController(conexion, clienteView);
                    clienteCtrl.iniciar();
                }
                case 3 -> {
                    VentaView ventaView = new VentaView(validador);
                    VentaController ventaCtrl = new VentaController(conexion, ventaView);
                    ventaCtrl.iniciar();
                }
                case 4 -> {
                    ReporteView reporteView = new ReporteView();
                    ReporteController reporteCtrl = new ReporteController(conexion, reporteView);
                    reporteCtrl.iniciar();
                }
                case 5 -> {
                    vista.mostrarMensaje("¡Gracias por usar TecnoStore! Cerrando sistema...");
                    salir = true;
                }
                default -> vista.mostrarMensaje("Opcion no valida. Ingrese un numero del 1 al 5.");
            }
        }
    }
}