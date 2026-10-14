package Dao;

import Model.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDao {
    
    private Connection conexion;

    public MarcaDao(Connection conexion) {
        this.conexion = conexion;
    }
    
    public boolean insertar(Marca marca){
    String sql = "INSERT INTO marca (nombre) VALUES (?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)){
    ps.setString(1, marca.getNombre());
    return ps.executeUpdate() > 0;
    }catch (SQLException e){
        System.err.println("Error al insertar :"+ e.getMessage()); //mio
      return false;
    }
       
   
    }
    public List<Marca> listar() {
        List<Marca> lista = new ArrayList<>();     
        String sql = ("select id, nombre from marca");
           try (PreparedStatement ps = conexion.prepareStatement(sql);
                   ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                lista.add(new Marca(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error en listar :"+ e.getMessage()); //mio
        }
        return lista;
}
    
    public Marca buscarPorId(int id){
    String sql = "Select id, nombre From marca where id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                return new Marca(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en Buscar por id :"+ e.getMessage()); //mio
        }
        return null;
    
    }
}
