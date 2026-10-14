package View;

import Model.Cliente;
import java.util.List;

public class ClienteView {

    private final Validaciones v;

    public ClienteView(Validaciones v) {
        this.v = v;
    }

    public int mostrarMenu() {
        return v.validarEntero("""
            
            --- GESTION DE CLIENTES ---
            1. Registrar nuevo cliente
            2. Listar clientes registrados
            3. Buscar cliente por documento
            4.Actualizar cliente
            5. Volver al menu principal
            Seleccione una opcion""");
    }

    public int mostrarMenuActualizacion() {
    return v.validarEntero("""
        
        --- QUE DATO DESEA ACTUALIZAR? ---
        1. Nombre
        2. Correo electronico
        3. Telefono
        4. Tipo de cliente (Categoria / Descuento)
        5. Terminar actualizacion
        Seleccione una opcion""");
}
    
    
    public String pedirNombre() {
        return v.validarTexto("Ingrese el nombre completo");
    }

    public String pedirTipoDocumento() {
        while (true) {
            String tipo = v.validarTexto("Ingrese tipo de documento (CC, PASAPORTE, VISA)").toUpperCase();
            if (tipo.equals("CC") || tipo.equals("PASAPORTE") || tipo.equals("VISA")) {
                return tipo;
            }
            System.out.println(">> Error: Solo se permite CC, PASAPORTE o VISA.");
        }
    }

    public String pedirDocumento() {
        return v.validarTexto("Ingrese el numero de documento");
    }

    public String pedirCorreo() {
        return v.validarEmail("Ingrese el correo electronico");
    }

    public String pedirTelefono() {
        return v.validarTexto("Ingrese el telefono de contacto");
    }

    public int pedirTipoClienteId() {
        while (true) {
            int opcion = v.validarEntero("""
                Seleccione el tipo de cliente:
                1. REGULAR (0% descuento)
                2. JUNIOR_MAYORISTA (10% descuento)
                3. MAYORISTA (20% descuento)
                Opcion""");
            if (opcion >= 1 && opcion <= 3) {
                return opcion;
            }
            System.out.println(">> Error: Seleccione una opcion valida (1, 2 o 3).");
        }
    }

    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println(">> No hay clientes registrados.");
            return;
        }
        System.out.println("\n--- LISTA DE CLIENTES REGISTRADOS ---");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    public void mostrarCliente(Cliente c) {
        if (c != null) {
            System.out.println("\nCliente encontrado:\n" + c);
        } else {
            System.out.println(">> No se encontro ningun cliente con ese documento.");
        }
    }
}