package dao;

import config.ConexionBD;
import interfaces.dao.IBuscable;
import model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements IBuscable<Categoria> {

    public List<Categoria> listarTodos() {

        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre, descripcion FROM categoria";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error listando categorías: " + e.getMessage());
        }

        return lista;
    }

    public Categoria buscarPorId(int id) {

        String sql = "SELECT id_categoria, nombre, descripcion FROM categoria WHERE id_categoria = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH ID categoría: " + e.getMessage());
        }

        return null;
    }

    public Categoria buscarPorNombre(String nombre) {

        String sql = "SELECT id_categoria, nombre, descripcion FROM categoria WHERE nombre LIKE ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH nombre categoría: " + e.getMessage());
        }

        return null;
    }
}