package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
   

    public Connection conexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore_Laura", "root", "1243");
            System.out.println("Conexion exitosa con base de datos tecnostore_Laura!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}

