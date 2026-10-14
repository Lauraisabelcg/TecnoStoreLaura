package Dao;

import Model.Celular;
import Model.Gama;
import Model.Marca;
import Model.SistemaOperativo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CelularDao {

    private Connection conexion;

    public CelularDao(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean crear(Celular celular) {
        String sql = "Insert INTO celular (marca_fk, sistema_operativo_fk, modelo, gama, precio, stock) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, celular.getMarca().getId());
            ps.setInt(2, celular.getSistemaOperativo().getId());
            ps.setString(3, celular.getModelo());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        celular.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en CelularDao crear: " + e.getMessage());
        }
        return false;
    }

    public Celular buscarPorId(int id) {
        String sql = "Select c.id, c.modelo, c.gama, c.precio, c.stock, "
                   + "m.id AS id_marca, m.nombre AS nombre_marca, "
                   + "so.id AS id_so, so.nombre AS nombre_so "
                   + "FROM celular c "
                   + "inner join marca m ON c.marca_fk = m.id "
                   + "inner join sistema_operativo so ON c.sistema_operativo_fk = so.id "
                   + "where c.id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Marca marca = new Marca(rs.getInt("id_marca"), rs.getString("nombre_marca"));
                    SistemaOperativo so = new SistemaOperativo(rs.getInt("id_so"), rs.getString("nombre_so"));
                    Gama gama = Gama.valueOf(rs.getString("gama"));

                    return new Celular(
                        rs.getInt("id"),
                        marca,
                        so,
                        rs.getString("modelo"),
                        gama,
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en CelularDao buscarPorId: " + e.getMessage());
        }
        return null;
    }

    public List<Celular> listar() {
        List<Celular> celulares = new ArrayList<>();
        String sql = "Select c.id, c.modelo, c.gama, c.precio, c.stock, "
                   + "m.id AS id_marca, m.nombre AS nombre_marca, "
                   + "so.id AS id_so, so.nombre AS nombre_so "
                   + "FROM celular c "
                   + "inner join marca m ON c.marca_fk = m.id "
                   + "inner join sistema_operativo so ON c.sistema_operativo_fk = so.id "
                   + "order by c.id ASC";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca marca = new Marca(rs.getInt("id_marca"), rs.getString("nombre_marca"));
                SistemaOperativo so = new SistemaOperativo(rs.getInt("id_so"), rs.getString("nombre_so"));
                Gama gama = Gama.valueOf(rs.getString("gama"));

                Celular celular = new Celular(
                    rs.getInt("id"),
                    marca,
                    so,
                    rs.getString("modelo"),
                    gama,
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                celulares.add(celular);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en CelularDao listar: " + e.getMessage());
        }
        return celulares;
    }
    public Celular obtenerPorId(int id) {
    List<Celular> listaCelulares = listar(); 
    
    for (Celular celular : listaCelulares) {
        if (celular.getId() == id) {
            return celular;
        }
    }
    return null;
}

    public boolean actualizar(Celular celular) {
        String sql = "Update celular SET marca_fk = ?, sistema_operativo_fk = ?, "
                   + "modelo = ?, gama = ?, precio = ?, stock = ? "
                   + "where id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, celular.getMarca().getId());
            ps.setInt(2, celular.getSistemaOperativo().getId());
            ps.setString(3, celular.getModelo());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());
            ps.setInt(7, celular.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL en CelularDao actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarStock(int idCelular, int nuevoStock) {
        String sql = "update celular set stock = ? "
                   + "where id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idCelular);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL en CelularDao actualizarStock: " + e.getMessage());
            return false;
        }
    }
    
    public boolean tieneVentasAsociadas(int idCelular) {
        String sql = "Select 1 FROM detalle_venta where celular_fk = ? LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCelular);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            System.err.println("Error SQL validando ventas asociadas: " + e.getMessage());
            return true;
        }
    }
    public boolean eliminar(int id) {
        if (tieneVentasAsociadas(id)) {
            System.err.println("No se puede eliminar: el celular ya tiene ventas registradas"
                    + "");
            return false;
        }

        String sql = "Delete from celular where id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL en CelularDao eliminar: " + e.getMessage());
            return false;
        }
    }
    
}

