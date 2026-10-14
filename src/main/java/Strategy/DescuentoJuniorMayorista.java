package Strategy;

public class DescuentoJuniorMayorista implements DescuentoStrategy {
    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10;
    }
}
