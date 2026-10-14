package Strategy;

public class DescuentoMayorista implements DescuentoStrategy {
    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.20;
    }
}

