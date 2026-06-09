package dao;

import config.ConexionBD;
import interfaces.dao.IBuscable;
import interfaces.dao.IRegistrable;
import model.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO implements IRegistrable<Pelicula>, IBuscable<Pelicula> {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Pelicula p) {

        String sql = "INSERT INTO pelicula " +
                "(id_categoria, titulo, director, anio_estreno, precio_alquiler, stock) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setInt(1, p.getIdCategoria());
            ps.setString(2, p.getNombre()); // nombre = titulo
            ps.setString(3, p.getDirector());
            ps.setInt(4, p.getAnioEstreno());
            ps.setDouble(5, p.getPrecio());
            ps.setInt(6, p.getStock());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error INSERT película: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Pelicula buscarPorId(int id) {

        String sql = "SELECT * FROM pelicula WHERE id_pelicula = ?";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH ID película: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Pelicula buscarPorNombre(String nombre) {

        String sql = "SELECT * FROM pelicula WHERE titulo LIKE ?";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, "%" + nombre + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH nombre película: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Pelicula> listarTodos() {

        List<Pelicula> lista = new ArrayList<>();

        String sql = "SELECT * FROM pelicula";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (Exception e) {
            System.out.println("Error LIST películas: " + e.getMessage());
        }

        return lista;
    }

    public List<Pelicula> listarConStock() {

        List<Pelicula> lista = new ArrayList<>();

        String sql = "SELECT * FROM pelicula WHERE stock > 0";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (Exception e) {
            System.out.println("Error STOCK películas: " + e.getMessage());
        }

        return lista;
    }

    private Pelicula mapear(ResultSet rs) throws Exception {

        return new Pelicula(
                rs.getInt("id_pelicula"),
                rs.getInt("id_categoria"),
                rs.getString("director"),
                rs.getInt("anio_estreno"),
                rs.getString("titulo"),
                rs.getDouble("precio_alquiler"),
                rs.getInt("stock")
        );
    }
}