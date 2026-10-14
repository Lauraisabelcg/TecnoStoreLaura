package View;

import Model.Celular;
import Model.Marca;
import Model.SistemaOperativo;
import java.util.List;

public class CelularView {

    private final Validaciones v;

    public CelularView(Validaciones v) {
        this.v = v;
    }

    public int mostrarMenu() {
        return v.validarEntero("""
            
            --- GESTION DE CELULARES ---
            1. Registrar nuevo celular
            2. Listar catalogo de celulares
            3. Consultar celular por ID
            4. Actualizar stock
            5. Eliminar celular
            6. Volver al menu principal
            Seleccione una opcion""");
    }

    public void mostrarMarcas(List<Marca> marcas) {
        System.out.println("\nMarcas disponibles:");
        for (Marca m : marcas) {
            System.out.println("  " + m.getId() + ". " + m.getNombre());
        }
    }

    public int pedirIdMarca() {
        return v.validarEnteroPositivo("Seleccione el ID de la marca");
    }

    public void mostrarSistemasOperativos(List<SistemaOperativo> sistemas) {
        System.out.println("\nSistemas Operativos disponibles:");
        for (SistemaOperativo so : sistemas) {
            System.out.println("  " + so.getId() + ". " + so.getNombre());
        }
    }

    public int pedirIdSistemaOperativo() {
        return v.validarEnteroPositivo("Seleccione el ID del sistema operativo");
    }

    public String pedirModelo() {
        return v.validarTexto("Ingrese el modelo del celular");
    }

    public String pedirGama() {
        return v.validarGama("Ingrese la gama (Alta, Media, Baja)");
    }

    public double pedirPrecio() {
        return v.validarDecimalPositivo("Ingrese el precio unitario");
    }

    public int pedirStock() {
        return v.validarEnteroPositivo("Ingrese la cantidad en stock");
    }

    public int pedirIdCelular() {
        return v.validarEnteroPositivo("Ingrese el ID del celular");
    }

    public void mostrarCatalogo(List<Celular> celulares) {
        if (celulares.isEmpty()) {
            System.out.println("No hay celulares registrados en el catalogo.");
            return;
        }
        System.out.println("\n--- CATALOGO DE CELULARES ---");
        for (Celular c : celulares) {
            System.out.println(c);
        }
    }

    public void mostrarCelular(Celular c) {
        if (c != null) {
            System.out.println("\nCelular encontrado:\n" + c);
        } else {
            System.out.println("No se encontro ningun celular con ese ID.");
        }
    }
}