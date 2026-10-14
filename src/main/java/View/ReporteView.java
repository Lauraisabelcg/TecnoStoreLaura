package View;

import Model.Venta;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReporteView {

    private final Scanner scanner = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("""
            
            ================ MENU DE REPORTES Y PERSISTENCIA ================
            1. Calcular ingresos totales
            2. Filtrar ventas por monto minimo
            3. Total facturado por cliente
            4. Top 3 celulares mas vendidos
            5. Generar copia de seguridad binaria (.dat)
            6. Cargar y mostrar copia de seguridad (.dat)
            7. Volver al menu principal
            =================================================================
            Seleccione una opcion:""");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double pedirMontoMinimo() {
        System.out.print("Ingrese el monto minimo a filtrar: ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void mostrarIngresosTotales(double total) {
        System.out.printf("%n>> Ingresos Totales Generados: $%,.2f%n", total);
    }

    public void mostrarVentasFiltradas(List<Venta> ventas, double montoMinimo) {
        System.out.println("\n--- VENTAS CON MONTO SUPERIOR A $" + montoMinimo + " ---");
        if (ventas.isEmpty()) {
            System.out.println("No se encontraron ventas que superen ese valor.");
        } else {
            ventas.forEach(System.out::println);
        }
    }

    public void mostrarTotalPorCliente(Map<String, Double> porCliente) {
        System.out.println("\n--- FACTURACION TOTAL POR CLIENTE ---");
        porCliente.forEach((nombre, monto) ->
                System.out.printf("Cliente: %-20s | Total Facturado: $%,.2f%n", nombre, monto));
    }

    public void mostrarTop3(List<String> top3) {
    if (top3.isEmpty()) {
        System.out.println("No hay ventas registradas para generar el top.");
        return;
    }
    System.out.println("\n--- TOP 3 CELULARES MAS VENDIDOS ---");
    for (int i = 0; i < top3.size(); i++) {
        System.out.println((i + 1) + ". " + top3.get(i));
    }
}

    public void mostrarVentasRecuperadas(List<Venta> ventas) {
        System.out.println("\n--- FACTURAS RECUPERADAS DESDE ARCHIVO BINARIO ---");
        if (ventas.isEmpty()) {
            System.out.println("No se encontraron registros en el archivo.");
        } else {
            ventas.forEach(System.out::println);
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}