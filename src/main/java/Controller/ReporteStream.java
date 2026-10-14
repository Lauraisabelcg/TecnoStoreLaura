package Controller;

import Model.Venta;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteStream {


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


    public List<String> obtenerTop3(List<Venta> ventas) {
        return ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        d -> d.getCelular().getModelo(),
                        Collectors.summingInt(d -> d.getCantidad())
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(entry -> entry.getKey() + " (" + entry.getValue() + " unidades vendidas)")
                .collect(Collectors.toList());
    }
            
}