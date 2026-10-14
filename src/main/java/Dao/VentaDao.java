package Dao;

import Model.Celular;
import Model.Cliente;
import Model.DetalleVenta;
import Model.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VentaDao {
    private final Connection conexion;

    public VentaDao(Connection conexion) {
        this.conexion = conexion;
    }
    
    public boolean registrarVenta(Venta venta) {
    String sqlVenta = "INSERT INTO venta (cliente_fk, fecha, descuento, iva, total) VALUES (?, ?, ?, ?, ?)";
    String sqlDetalle = "INSERT INTO detalle_venta (venta_fk, celular_fk, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
    String sqlStock = "UPDATE celular SET stock = stock - ? WHERE id = ? AND stock >= ?";

    try {
        conexion.setAutoCommit(false); // Iniciamos transacción

        // 1. Guardar la venta principal y obtener su ID
        int ventaId;
        try (PreparedStatement psVenta = conexion.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            psVenta.setInt(1, venta.getCliente().getId());
            psVenta.setTimestamp(2, Timestamp.valueOf(venta.getFecha()));
            psVenta.setDouble(3, venta.getDescuento());
            psVenta.setDouble(4, venta.getIva());
            psVenta.setDouble(5, venta.getTotal());
            psVenta.executeUpdate();

            try (ResultSet rs = psVenta.getGeneratedKeys()) {
                if (!rs.next()) {
                    conexion.rollback();
                    return false;
                }
                ventaId = rs.getInt(1);
                venta.setId(ventaId);
            }
        }

        // 2. Guardar detalles y descontar stock en un solo paso
            try (PreparedStatement psDetalle = conexion.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psStock = conexion.prepareStatement(sqlStock)) {

                for (DetalleVenta detalle : venta.getDetalles()) {
                    // Restar stock si alcanza
                    psStock.setInt(1, detalle.getCantidad());
                    psStock.setInt(2, detalle.getCelular().getId());
                    psStock.setInt(3, detalle.getCantidad());

                    int filasAfectadas = psStock.executeUpdate();
                    if (filasAfectadas == 0) {
                        System.err.println("Sin stock para: " + detalle.getCelular().getModelo());
                        conexion.rollback();
                        return false;
                    }

                    // Guardar la línea en detalle_venta
                    psDetalle.setInt(1, ventaId);
                    psDetalle.setInt(2, detalle.getCelular().getId());
                    psDetalle.setInt(3, detalle.getCantidad());
                    psDetalle.setDouble(4, detalle.getPrecioUnitario());
                    psDetalle.setDouble(5, detalle.getSubtotal());
                    psDetalle.executeUpdate();

                    // Capturar el ID asignado por la BD y actualizar el objeto en memoria
                    try (ResultSet rsDetalle = psDetalle.getGeneratedKeys()) {
                        if (rsDetalle.next()) {
                            detalle.setId(rsDetalle.getInt(1));
                        }
                    }
                }
            }

        conexion.commit(); // Todo salió bien
        return true;

    } catch (SQLException e) {
        System.err.println("Error en venta: " + e.getMessage());
        try { conexion.rollback(); } catch (SQLException ex) { /* ignorar */ }
        return false;
    } finally {
        try { conexion.setAutoCommit(true); } catch (SQLException e) { /* ignorar */ }
    }
}
    public List<DetalleVenta> listarPorVenta(int ventaId){
    List<DetalleVenta> detalles = new ArrayList<>();
    String sql= "Select * from detalle_venta where venta_fk = ?";
    CelularDao celularDao = new CelularDao(conexion);
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)){
    ps.setInt(1, ventaId);
    try(ResultSet rs = ps.executeQuery()){
    while (rs.next()){
    Celular celularCargado = celularDao.obtenerPorId(rs.getInt("celular_fk"));
    
    DetalleVenta d = new DetalleVenta(
    rs.getInt("id"),
    rs.getInt("venta_fk"),
    celularCargado,
    rs.getInt("cantidad"),
    rs.getDouble("precio_unitario"),
    rs.getDouble("subtotal")  
    
    
    );
    detalles.add(d);
    }
    }
    
    }catch (SQLException e){
            System.err.println("Error al listar detalles de la venta "+ e.getMessage());
            } 
    
    return detalles;
    
    }
    
    public Venta obtenerVentaPorId(int id)
    {
    String sql = "Select * from venta where id= ?";
    ClienteDao clienteDao = new ClienteDao(conexion);
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)){
    ps.setInt(1, id);
    try(ResultSet rs = ps.executeQuery()){
    while (rs.next()){
    Cliente cliente = clienteDao.obtenerPorId(rs.getInt("cliente_fk"));
    
    Venta venta = new Venta(
    rs.getInt("id"),
    cliente,
    rs.getTimestamp("fecha").toLocalDateTime(),
    rs.getDouble("descuento"),
    rs.getDouble("iva"),
    rs.getDouble("total")  
    
    
    );
    venta.setDetalles(listarPorVenta(id));
    return venta;
    }
    }
    
    }catch (SQLException e){
            System.err.println("Error al listar detalles de la venta "+ e.getMessage());
            } 
    
    return null;
    
    }
    public List<Venta> listarTodas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "Select id FROM venta Order by id desc";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("id");
                Venta venta = obtenerVentaPorId(idVenta);
                if (venta != null) {
                    ventas.add(venta);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar todas las ventas: " + e.getMessage());
        }

        return ventas;
    }
    
    
    
    
    
    }
    