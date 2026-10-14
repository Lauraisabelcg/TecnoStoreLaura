package Dao;

import Model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonaDao {
    private Connection conexion;

    public PersonaDao(Connection conexion) {
        this.conexion = conexion;
    }
    public int crear(Persona persona){
    String sql = "INSERT INTO persona"
            + " (nombre, tipo_documento, documento, correo, telefono)"
            + " VALUES (?,?,?,?,?)";
        try (PreparedStatement ps= conexion.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS))
        {   ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getTipoDocumento().name());
            ps.setString(3, persona.getDocumento());
            ps.setString(4, persona.getCorreo());
            ps.setString(5, persona.getTelefono());
            
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas >0 )
                try(ResultSet rs= ps.getGeneratedKeys()){
                if (rs.next()){
                return rs.getInt(1);}
                }
        } catch (SQLException e) {
            System.err.println("Error sql en PersonaDao crear :" + e.getMessage());
        }return -1;
    }
    
    public int bucarIdPorDocumento(String documento){
        
        String sql = "Select id FROM persona WHERE documento = ?";
        
        try( PreparedStatement ps = conexion.prepareStatement(sql)){
        ps.setString(1, documento);
        try (ResultSet rs =ps.executeQuery()){
        if (rs.next()){
        return rs.getInt("id");
        }
        }
        
        }catch (SQLException e)
        {
            System.err.println("Error personaDao (buscarIdPorDocumento): "+ e.getMessage());
        }return -1;
    
    
    
    
    
    }
}
