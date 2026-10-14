package Model;

public enum  TipoCliente {
    REGULAR (1, 0.00) ,
    JUNIOR_MAYORISTA(2, 0.10),
    MAYORISTA (3, 0.20);
    
    private final int id;
    private final double porcentajeDescuento;

    private TipoCliente(int id, double porcentajeDescuento) {
        this.id = id;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public int getId() {
        return id;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
    
    public static TipoCliente obtenerPorId(int id){
        for (TipoCliente tipo : values ()){
            if (tipo.getId()== id){
                return tipo;}
            }return REGULAR;
            }
        }
    
