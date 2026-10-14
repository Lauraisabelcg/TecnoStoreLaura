package Dao;

import Model.Cliente;
import Model.TipoCliente;
import Model.TipoDocumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private Connection conexion;

    public ClienteDao(Connection conexion) {
        this.conexion = conexion;
    }
    
    public boolean crear (int idPersona, Cliente cliente)
    {
    String sql= "Insert INTO cliente (id, tipo_cliente_fk , fecha_registro)"
            + "VALUES (?,?,NOW())";
    try ( PreparedStatement ps = conexion.prepareStatement(sql)){
    ps.setInt(1, idPersona);
    ps.setInt(2, cliente.getTipoCliente().getId());
    
    return ps.executeUpdate() > 0;
   
    
    } catch(SQLException e)
    {System.err.println("Error en BuscarporDocumento :" + e.getMessage());
    return false;
    } }
    
    public Cliente buscarPorDocumento(String documento) {
    String sql = "SELECT p.id, p.nombre, p.tipo_documento, p.documento, p.correo, "
            + "p.telefono, c.tipo_cliente_fk FROM persona p "
            + "INNER JOIN cliente c ON p.id = c.id "
            + "WHERE p.documento = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)){
        ps.setString(1, documento);
        try (ResultSet rs = ps.executeQuery()){
        if (rs.next()){
        TipoDocumento tipoDoc= TipoDocumento.valueOf(rs.getString("tipo_documento"));
        TipoCliente tipoCli = TipoCliente.obtenerPorId(rs.getInt("Tipo_cliente_fk"));
         
        return new Cliente (
        rs.getInt("id"),
                rs.getString("nombre"),
                tipoDoc,
                rs.getString("documento"),
                rs.getString("correo"),
                rs.getString("telefono"),
                tipoCli
        );
        }
        }
    }catch (SQLException e)
                {
                    System.err.println("Error clientedao buscar por documento: "+ e.getMessage());
        }return null;
    }
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.tipo_documento, p.documento, p.correo, p.telefono, "
                   + "       c.tipo_cliente_fk "
                   + "FROM persona p "
                   + "INNER JOIN cliente c ON p.id = c.id";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoDocumento tipoDoc = TipoDocumento.valueOf(rs.getString("tipo_documento"));
                TipoCliente tipoCli = TipoCliente.obtenerPorId(rs.getInt("tipo_cliente_fk"));

                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    tipoDoc,
                    rs.getString("documento"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    tipoCli
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en ClienteDao listar: " + e.getMessage());
        }
        return clientes;
    }
    public Cliente obtenerPorId(int id) {
    List<Cliente> listaClientes = listar(); 
    
    for (Cliente cliente : listaClientes) {
        if (cliente.getId() == id) {
            return cliente;
        }
    }
    return null;
}
    
    public boolean actualizar(Cliente cliente) {
    String sql = "Update cliente SET tipo_cliente_fk = ? WHERE id = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, cliente.getTipoCliente().getId());
        ps.setInt(2, cliente.getId());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error sql en ClienteDao actualizar: " + e.getMessage());
        return false;
    }
}
    }

