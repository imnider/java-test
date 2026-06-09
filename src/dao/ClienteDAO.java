package dao;

import config.ConexionBD;
import interfaces.dao.IBuscable;
import interfaces.dao.IRegistrable;
import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IRegistrable<Cliente>, IBuscable<Cliente> {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean registrar(Cliente c) {

        String sql = "INSERT INTO cliente " +
                "(cedula, nombres, apellidos, telefono, email, direccion) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getApellidos());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getDireccion());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error INSERT cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH ID cliente: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Cliente buscarPorNombre(String nombre) {

        String sql = "SELECT * FROM cliente WHERE nombres LIKE ?";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, "%" + nombre + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH nombre cliente: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Cliente> listarTodos() {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM cliente";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (Exception e) {
            System.out.println("Error LIST clientes: " + e.getMessage());
        }

        return lista;
    }

    public Cliente buscarPorCedula(String cedula) {

        String sql = "SELECT * FROM cliente WHERE cedula = ?";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, cedula);

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }

        } catch (Exception e) {
            System.out.println("Error SEARCH por cédula cliente: " + e.getMessage());
        }

        return null;
    }

    private Cliente mapear(ResultSet rs) throws Exception {

        Cliente c = new Cliente();

        c.setIdCliente(rs.getInt("id_cliente"));
        c.setCedula(rs.getString("cedula"));
        c.setNombres(rs.getString("nombres"));
        c.setApellidos(rs.getString("apellidos"));
        c.setTelefono(rs.getString("telefono"));
        c.setEmail(rs.getString("email"));
        c.setDireccion(rs.getString("direccion"));
        c.setFechaReg(rs.getString("fecha_reg"));

        return c;
    }
}