package Model;

import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private TipoCliente tipoCliente;
    
    public Cliente(int id, String nombre, TipoDocumento tipoDocumento, String documento, String correo, String telefono,TipoCliente tipoCliente) {
        super(id, nombre, tipoDocumento, documento, correo, telefono);
        this.tipoCliente = tipoCliente;
    }
    
    public TipoCliente getTipoCliente(){
    return tipoCliente;
    }
    
    public void setTipoCliente(TipoCliente tipoCliente){
    this.tipoCliente = tipoCliente;
    }
    
    
}
