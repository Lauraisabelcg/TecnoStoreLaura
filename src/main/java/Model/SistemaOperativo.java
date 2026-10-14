package Model;

import java.io.Serializable;

public class SistemaOperativo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;

    public SistemaOperativo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String toString() {
        return """
               *************************
               Id:                  %s
               Sistema Operativo:   %s
               """.formatted(id, nombre);
    }
    
}
