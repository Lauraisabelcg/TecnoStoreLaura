package Strategy;

public class DescuentoRegular implements DescuentoStrategy {
    
    @Override
    public double calcularDescuento(double subtotal) {
        return 0.0;
    }
    
}
