package View;

import java.util.Scanner;

public class MenuPrincipalView {

    private final Scanner scanner = new Scanner(System.in);

    public int mostrarMenuPrincipal() {
        System.out.println("""
            
            =============================================
                      TECNOSTORE - MENU PRINCIPAL
            =============================================
            1. Gestion de Celulares (Inventario)
            2. Gestion de Clientes / Personas
            3. Gestion de Ventas
            4. Modulo de Reportes y Respaldo Binario
            5. Salir
            =============================================
            Seleccione una opcion:""");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}