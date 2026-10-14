package Controller;


import Dao.ClienteDao;
import Dao.PersonaDao;
import Model.Cliente;
import Model.Persona;
import Model.TipoCliente;
import Model.TipoDocumento;
import View.ClienteView;
import java.sql.Connection;
import java.util.List;

public class ClienteController {

    private final ClienteDao clienteDao;
    private final PersonaDao personaDao;
    private final ClienteView vista;

    public ClienteController(Connection conexion, ClienteView vista) {
        this.clienteDao = new ClienteDao(conexion);
        this.personaDao = new PersonaDao(conexion);
        this.vista = vista;
    }

    public void iniciar() {
        boolean volver = false;
        while (!volver) {
            int opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorDocumento();
                    break;
                case 4:
                    actualizarCliente();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println(">> Opcion invalida. Intente nuevamente.");
            }
        }
    }

    private void registrarCliente() {
        System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");
        String nombre = vista.pedirNombre();
        TipoDocumento tipoDoc = TipoDocumento.valueOf(vista.pedirTipoDocumento());
        String documento = vista.pedirDocumento();
        String correo = vista.pedirCorreo();
        String telefono = vista.pedirTelefono();
        int tipoClienteId = vista.pedirTipoClienteId();
        TipoCliente tipoCliente = TipoCliente.obtenerPorId(tipoClienteId);

        Persona persona = new Persona(0, nombre, tipoDoc, documento, correo, telefono);
        int idPersonaGenerada = personaDao.crear(persona);

        if (idPersonaGenerada > 0) {
            
            Cliente nuevoCliente = new Cliente(idPersonaGenerada, nombre, tipoDoc, documento, correo, telefono, tipoCliente);
            boolean clienteOk = clienteDao.crear(idPersonaGenerada, nuevoCliente);

            if (clienteOk) {
                System.out.println("¡Cliente registrado exitosamente con ID #" + idPersonaGenerada + "!");
            } else {
                System.err.println("Error al registrar en la tabla cliente.");
            }
        } else {
            System.err.println(" Error al registrar la persona en la base de datos.");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteDao.listar();
        vista.mostrarClientes(clientes);
    }

    private void buscarClientePorDocumento() {
        String doc = vista.pedirDocumento();
        Cliente c = clienteDao.buscarPorDocumento(doc);
        vista.mostrarCliente(c);
    }

    private void actualizarCliente() {
        System.out.println("\n--- ACTUALIZAR CLIENTE ---");
        String doc = vista.pedirDocumento();
        Cliente cliente = clienteDao.buscarPorDocumento(doc);

        if (cliente == null) {
            System.out.println(" No existe ningun cliente registrado con el documento: " + doc);
            return;
        }

        System.out.println("\nCliente seleccionado:\n" + cliente);

        boolean editando = true;
        while (editando) {
            int opcion = vista.mostrarMenuActualizacion();
            switch (opcion) {
                case 1:
                    String nuevoNombre = vista.pedirNombre();
                    cliente.setNombre(nuevoNombre);
                    actualizarPersonaEnBD(cliente);
                    System.out.println(" Nombre actualizado correctamente.");
                    break;

                case 2:
                    String nuevoCorreo = vista.pedirCorreo();
                    cliente.setCorreo(nuevoCorreo);
                    actualizarPersonaEnBD(cliente);
                    System.out.println(" Correo electronico actualizado correctamente.");
                    break;

                case 3:
                    String nuevoTelefono = vista.pedirTelefono();
                    cliente.setTelefono(nuevoTelefono);
                    actualizarPersonaEnBD(cliente);
                    System.out.println("Telefono de contacto actualizado correctamente.");
                    break;

                case 4:
                    int nuevoTipoId = vista.pedirTipoClienteId();
                    TipoCliente nuevoTipo = TipoCliente.obtenerPorId(nuevoTipoId);
                    cliente.setTipoCliente(nuevoTipo);
                    clienteDao.actualizar(cliente);
                    System.out.println(" Tipo de cliente actualizado correctamente.");
                    break;

                case 5:
                    System.out.println("Finalizando actualizacion del cliente.");
                    editando = false;
                    break;

                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }
    }

    private void actualizarPersonaEnBD(Cliente cliente) {
        Persona persona = new Persona(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getTipoDocumento(),
            cliente.getDocumento(),
            cliente.getCorreo(),
            cliente.getTelefono()
        );
        personaDao.actualizar(persona);
    }
}