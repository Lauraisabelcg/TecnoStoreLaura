package com.mycompany.tecnostorelaura;

import Dao.ConexionBD;

public class TecnoStoreLaura {

    public static void main(String[] args) {
        ConexionBD con = new ConexionBD();
        con.conexion();
    }
}
