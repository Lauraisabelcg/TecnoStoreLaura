package Strategy;

import Model.TipoCliente;

public interface DescuentoStrategy {
    double calcularDescuento(double subtotal);

    static DescuentoStrategy obtenerEstrategia(TipoCliente tipo) {
        if (tipo == null) {
            return new DescuentoRegular();
        }
        return switch (tipo) {
            case JUNIOR_MAYORISTA -> new DescuentoJuniorMayorista();
            case MAYORISTA        -> new DescuentoMayorista();
            default               -> new DescuentoRegular();
        };
    }
}

