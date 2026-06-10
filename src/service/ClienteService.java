package service;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {

    private final ClienteDAO dao;

    public ClienteService(ClienteDAO dao) {
        this.dao = dao;
    }

    public boolean registrarCliente(Cliente c) {

        if (c.getCedula() == null || c.getCedula().trim().isEmpty()) {
            System.out.println("La cédula es obligatoria.");
            return false;
        }

        if (c.getCedula().length() < 10) {
            System.out.println("Cédula inválida.");
            return false;
        }

        if (c.getNombres() == null || c.getNombres().trim().isEmpty()) {
            System.out.println("Los nombres son obligatorios.");
            return false;
        }

        if (c.getApellidos() == null || c.getApellidos().trim().isEmpty()) {
            System.out.println("Los apellidos son obligatorios.");
            return false;
        }

        if (dao.buscarPorCedula(c.getCedula()) != null) {
            System.out.println("Ya existe un cliente con esa cédula.");
            return false;
        }

        return dao.registrar(c);
    }

    public Cliente buscarPorCedula(String cedula) {

        if (cedula == null || cedula.trim().isEmpty()) {
            System.out.println("Ingrese una cédula válida.");
            return null;
        }

        return dao.buscarPorCedula(cedula);
    }

    public List<Cliente> listarTodos() {
        return dao.listarTodos();
    }
}