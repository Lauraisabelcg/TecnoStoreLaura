package Util;

import Model.Venta;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ArchivoUtils {

    private static final String ARCHIVO = "reporte_ventas.dat";


    public static void guardarReporteVentasBinario(List<Venta> ventas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(ventas);
            System.out.println(">> Reporte binario generado exitosamente: " + ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar archivo .dat: " + e.getMessage());
        }
    }

    public static List<Venta> leerReporteVentasBinario() {
        List<Venta> ventas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            ventas = (List<Venta>) ois.readObject();
            System.out.println(">> Reporte binario cargado exitosamente (" + ventas.size() + " ventas).");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer archivo .dat: " + e.getMessage());
        }
        return ventas;
    }
}