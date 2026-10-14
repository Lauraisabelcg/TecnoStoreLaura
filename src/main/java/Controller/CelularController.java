package Controller;


import Dao.CelularDao;
import Dao.MarcaDao;
import Model.Celular;
import Model.Gama;
import Model.Marca;
import Model.SistemaOperativo;
import View.CelularView;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CelularController {

    private final CelularDao celularDao;
    private final MarcaDao marcaDao;
    private final CelularView vista;

    public CelularController(Connection conexion, CelularView vista) {
        this.celularDao = new CelularDao(conexion);
        this.marcaDao = new MarcaDao(conexion);
        this.vista = vista;
    }

    public void iniciar() {
        boolean volver = false;
        while (!volver) {
            int opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1:
                    registrarCelular();
                    break;
                case 2:
                    listarCatalogo();
                    break;
                case 3:
                    consultarPorId();
                    break;
                case 4:
                    actualizarStock();
                    break;
                case 5:
                    eliminarCelular();
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }
    }

    private void registrarCelular() {
        System.out.println("\n--- REGISTRO DE NUEVO CELULAR ---");

        List<Marca> marcas = marcaDao.listar();
        vista.mostrarMarcas(marcas);
        int idMarca = vista.pedirIdMarca();
        Marca marca = marcaDao.buscarPorId(idMarca);

        if (marca == null) {
            System.out.println("Error: La marca seleccionada no existe.");
            return;
        }

        List<SistemaOperativo> listaSO = obtenerSistemasDisponibles();
        vista.mostrarSistemasOperativos(listaSO);
        int idSo = vista.pedirIdSistemaOperativo();
        SistemaOperativo so = buscarSistemaOperativoPorId(idSo, listaSO);

        if (so == null) {
            System.out.println("Error: El sistema operativo seleccionado no existe.");
            return;
        }

        String modelo = vista.pedirModelo();
        String gamaStr = vista.pedirGama();
            Gama gama;
            if (gamaStr.equalsIgnoreCase("Alta")) {
            gama = Gama.Alta;
            } else if (gamaStr.equalsIgnoreCase("Media")) {
            gama = Gama.Media;
            } else {
            gama = Gama.Baja;
}
        double precio = vista.pedirPrecio();
        int stock = vista.pedirStock();

        Celular celular = new Celular(0, marca, so, modelo, gama, precio, stock);
        boolean registrado = celularDao.crear(celular);

        if (registrado) {
            System.out.println("Celular registrado exitosamente con ID #" + celular.getId() + ".");
        } else {
            System.err.println("Error al guardar el celular en la base de datos.");
        }
    }

    private void listarCatalogo() {
        List<Celular> celulares = celularDao.listar();
        vista.mostrarCatalogo(celulares);
    }

    private void consultarPorId() {
        int id = vista.pedirIdCelular();
        Celular c = celularDao.buscarPorId(id);
        vista.mostrarCelular(c);
    }

    private void actualizarStock() {
        int id = vista.pedirIdCelular();
        Celular c = celularDao.buscarPorId(id);

        if (c == null) {
            System.out.println("No existe ningun celular con el ID: " + id);
            return;
        }

        System.out.println("\nCelular seleccionado:\n" + c);
        int nuevoStock = vista.pedirStock();

        boolean actualizado = celularDao.actualizarStock(id, nuevoStock);
        if (actualizado) {
            System.out.println("Stock actualizado correctamente a " + nuevoStock + " unidades.");
        } else {
            System.err.println("Error al actualizar el stock en la base de datos.");
        }
    }

    private void eliminarCelular() {
    int id = vista.pedirIdCelular();

    
    Celular c = celularDao.buscarPorId(id);
    if (c == null) {
        System.out.println("No existe ningun celular con el ID: " + id);
        return;
    }

    boolean eliminado = celularDao.eliminar(id);
    if (eliminado) {
        System.out.println("Celular con ID #" + id + " eliminado exitosamente.");
    }
}

    private List<SistemaOperativo> obtenerSistemasDisponibles() {
        List<SistemaOperativo> lista = new ArrayList<>();
        lista.add(new SistemaOperativo(1, "Android"));
        lista.add(new SistemaOperativo(2, "iOS"));
        return lista;
    }

    private SistemaOperativo buscarSistemaOperativoPorId(int id, List<SistemaOperativo> lista) {
        for (SistemaOperativo so : lista) {
            if (so.getId() == id) {
                return so;
            }
        }
        return null;
    }
}