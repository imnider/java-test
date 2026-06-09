package service;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO dao = new ClienteDAO();

    public boolean registrarCliente(Cliente c) {

        // VALIDACIONES
        if (c.getCedula() == null || c.getCedula().trim().isEmpty()) {
            System.out.println("La cédula es obligatoria.");
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

        if (c.getCedula().length() < 10) {
            System.out.println("Cédula inválida.");
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