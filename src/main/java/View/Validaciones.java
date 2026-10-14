package View;

import java.util.Scanner;

public class Validaciones {

    private final Scanner scanner;

    public Validaciones() {
        this.scanner = new Scanner(System.in);
    }

    public String validarTexto(String mensaje) {
        String texto = "";
        do {
            System.out.print(mensaje + ": ");
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println(" Error: El campo no puede estar vacio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public int validarEntero(String mensaje) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje + ": ");
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println(" Error: Debe ingresar un numero entero valido.");
            }
        }
        return valor;
    }

    public int validarEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = validarEntero(mensaje);
            if (valor < 0) {
                System.out.println(" Error: El valor no puede ser negativo.");
            }
        } while (valor < 0);
        return valor;
    }

    public double validarDecimalPositivo(String mensaje) {
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje + ": ");
            try {
                valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor <= 0) {
                    System.out.println(" Error: El valor debe ser mayor a 0.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Debe ingresar un numero decimal valido.");
            }
        }
        return valor;
    }

    public String validarEmail(String mensaje) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String email;
        do {
            email = validarTexto(mensaje);
            if (!email.matches(regex)) {
                System.out.println(" Error: Formato de correo electronico invalido (ejemplo: usuario@correo.com).");
            }
        } while (!email.matches(regex));
        return email;
    }

    public String validarGama(String mensaje) {
        String gama;
        boolean valida = false;
        do {
            gama = validarTexto(mensaje + " (ALTA, MEDIA, BAJA)").toUpperCase();
            if (gama.equals("ALTA") || gama.equals("MEDIA") || gama.equals("BAJA")) {
                valida = true;
            } else {
                System.out.println(" Error: Gama no valida. Opciones permitidas: ALTA, MEDIA, BAJA.");
            }
        } while (!valida);
        return gama;
    }
}
