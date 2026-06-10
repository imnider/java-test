package dao;

import config.ConexionBD;
import interfaces.dao.IRegistrable;
import model.Alquiler;
import model.DetalleAlquiler;

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

        String sql = "SELECT a.id_alquiler, a.fecha_alquiler, a.fecha_devolucion, a.total, a.estado " +
                "FROM alquiler a WHERE a.id_cliente = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alquiler a = new Alquiler();
                a.setIdAlquiler(rs.getInt("id_alquiler"));
                a.setFechaAlquiler(rs.getDate("fecha_alquiler"));
                a.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                a.setTotal(rs.getDouble("total"));
                a.setEstado(rs.getString("estado"));
                lista.add(a);
            }

        } catch (Exception e) {
            System.out.println("Error listando alquileres: " + e.getMessage());
        }

        return lista;
    }
}