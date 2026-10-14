package Controller;

import Model.Venta;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteController {


    public double calcularIngresosTotales(List<Venta> ventas) {
        return ventas.stream()
                     .mapToDouble(Venta::getTotal)
                     .sum();
    }

  
    public List<Venta> filtrarVentasPorMontoMinimo(List<Venta> ventas, double montoMinimo) {
        return ventas.stream()
                     .filter(v -> v.getTotal() >= montoMinimo)
                     .collect(Collectors.toList());
    }


    public Map<String, Double> calcularTotalFacturadoPorCliente(List<Venta> ventas) {
        return ventas.stream()
                     .collect(Collectors.groupingBy(
                         v -> v.getCliente().getNombre(),
                         Collectors.summingDouble(Venta::getTotal)
                     ));
    }


    public List<String> obtenerModelosVendidosUnicos(List<Venta> ventas) {
        return ventas.stream()
                     .flatMap(v -> v.getDetalles().stream())
                     .map(d -> d.getCelular().getModelo())
                     .distinct()
                     .sorted()
                     .collect(Collectors.toList());
    }
}
