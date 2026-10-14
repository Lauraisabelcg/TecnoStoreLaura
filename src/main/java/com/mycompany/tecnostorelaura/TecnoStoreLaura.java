package com.mycompany.tecnostorelaura;

import Controller.MenuPrincipalController;
import Dao.ConexionBD;
import View.MenuPrincipalView;
import java.sql.Connection;

public class TecnoStoreLaura {

    public static void main(String[] args) {
        Connection conexion = new ConexionBD().conexion();

        if (conexion == null) {
            System.err.println("No se pudo conectar a la base de datos.");
            return;
        }

        MenuPrincipalView menuVista = new MenuPrincipalView();
        MenuPrincipalController app = new MenuPrincipalController(conexion, menuVista);
        app.iniciar();
    }
}
