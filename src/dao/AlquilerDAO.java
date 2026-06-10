package dao;

import config.ConexionBD;
import interfaces.dao.IRegistrable;
import model.Alquiler;
import model.Cliente;
import model.DetalleAlquiler;
import model.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlquilerDAO implements IRegistrable<Alquiler> {

    @Override
    public boolean registrar(Alquiler a) {

        String sqlAlquiler = "INSERT INTO alquiler (id_cliente, fecha_devolucion, total, estado) " +
                "VALUES (?, ?, ?, ?)";

        String sqlDetalle = "INSERT INTO detalle_alquiler (id_alquiler, id_pelicula, cantidad, precio_unitario) " +
                "VALUES (?, ?, ?, ?)";

        String sqlStock = "UPDATE pelicula SET stock = stock - ? WHERE id_pelicula = ?";

        Connection con = null;

        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false); // BEGIN TRAN

            // 1. Insertar cabecera del alquiler
            int idAlquiler;
            try (PreparedStatement ps = con.prepareStatement(sqlAlquiler, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, a.getCliente().getIdCliente());
                ps.setDate(2, new java.sql.Date(a.getFechaDevolucion().getTime()));
                ps.setDouble(3, a.calcularTotal());
                ps.setString(4, a.getEstado());
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                if (!keys.next()) throw new Exception("No se obtuvo el ID del alquiler.");
                idAlquiler = keys.getInt(1);
            }
            for (DetalleAlquiler d : a.getDetalles()) {
                try (PreparedStatement psD = con.prepareStatement(sqlDetalle)) {
                    psD.setInt(1, idAlquiler);
                    psD.setInt(2, d.getPelicula().getIdPelicula());
                    psD.setInt(3, d.getCantidad());
                    psD.setDouble(4, d.getPrecioUnitario());
                    psD.executeUpdate();
                }

                try (PreparedStatement psS = con.prepareStatement(sqlStock)) {
                    psS.setInt(1, d.getCantidad());
                    psS.setInt(2, d.getPelicula().getIdPelicula());
                    psS.executeUpdate();
                }
            }

            con.commit(); // COMMIT
            return true;

        } catch (Exception e) {
            System.out.println("Error en transacción de alquiler: " + e.getMessage());
            try {
                if (con != null) con.rollback(); // ROLLBACK
            } catch (SQLException ex) {
                System.out.println("Error en rollback: " + ex.getMessage());
            }
            return false;

        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Alquiler> listarTodos() {
        return new ArrayList<>();
    }

    public List<Alquiler> listarPorCliente(int idCliente) {

        List<Alquiler> lista = new ArrayList<>();

        String sql = "SELECT a.id_alquiler, a.fecha_alquiler, a.fecha_devolucion, a.total, a.estado, " +
                "c.id_cliente, c.cedula, c.nombres, c.apellidos, " +
                "d.id_detalle, d.cantidad, d.precio_unitario, d.subtotal, " +
                "p.id_pelicula, p.titulo, p.precio_alquiler, p.stock " +
                "FROM alquiler a " +
                "JOIN cliente c ON a.id_cliente = c.id_cliente " +
                "LEFT JOIN detalle_alquiler d ON a.id_alquiler = d.id_alquiler " +
                "LEFT JOIN pelicula p ON d.id_pelicula = p.id_pelicula " +
                "WHERE a.id_cliente = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            java.util.Map<Integer, Alquiler> mapa = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                int idAlquiler = rs.getInt("id_alquiler");

                if (!mapa.containsKey(idAlquiler)) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("id_cliente"));
                    c.setCedula(rs.getString("cedula"));
                    c.setNombres(rs.getString("nombres"));
                    c.setApellidos(rs.getString("apellidos"));

                    Alquiler a = new Alquiler();
                    a.setIdAlquiler(idAlquiler);
                    a.setCliente(c);
                    a.setFechaAlquiler(rs.getDate("fecha_alquiler"));
                    a.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                    a.setTotal(rs.getDouble("total"));
                    a.setEstado(rs.getString("estado"));

                    mapa.put(idAlquiler, a);
                }

                if (rs.getInt("id_detalle") != 0) {
                    Pelicula p = new Pelicula();
                    p.setIdPelicula(rs.getInt("id_pelicula"));
                    p.setNombre(rs.getString("titulo"));
                    p.setPrecio(rs.getDouble("precio_alquiler"));
                    p.setStock(rs.getInt("stock"));

                    DetalleAlquiler d = new DetalleAlquiler();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setPelicula(p);
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    d.setSubtotal(rs.getDouble("subtotal"));

                    mapa.get(idAlquiler).getDetalles().add(d);
                }
            }

            lista.addAll(mapa.values());

        } catch (Exception e) {
            System.out.println("Error listando alquileres: " + e.getMessage());
        }

        return lista;
    }
}