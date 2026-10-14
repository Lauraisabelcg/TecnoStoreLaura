package Model;

import java.io.Serializable;

public class Celular implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Marca marca;
    private SistemaOperativo sistemaOperativo;
    private String modelo;
    private Gama gama;
    private double precio;
    private int stock;

    public Celular(int id, Marca marca, SistemaOperativo sistemaOperativo, String modelo, Gama gama, double precio, int stock) {
        this.id = id;
        this.marca = marca;
        this.sistemaOperativo = sistemaOperativo;
        this.modelo = modelo;
        this.gama = gama;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public SistemaOperativo getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(SistemaOperativo sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public String toString() {
        return """
               *************************
               Id:                %s
               Modelo:            %s
               Marca:             %s
               Sistema Operativo: %s
               Gama:              %s
               Precio:            %s
               Stock:             %s
               """.formatted(id, modelo, marca.getNombre(), sistemaOperativo.getNombre(), gama, precio, stock);
    }
    
}
