package service;

import dao.AlquilerDAO;
import dao.ClienteDAO;
import dao.PeliculaDAO;
import model.Alquiler;
import model.Cliente;
import model.Pelicula;

import java.util.Date;
import java.util.List;

public class AlquilerService {

    private final AlquilerDAO alquilerDAO;
    private final ClienteDAO clienteDAO;
    private final PeliculaDAO peliculaDAO;

    public AlquilerService(AlquilerDAO alquilerDAO, ClienteDAO clienteDAO, PeliculaDAO peliculaDAO) {
        this.alquilerDAO = alquilerDAO;
        this.clienteDAO = clienteDAO;
        this.peliculaDAO = peliculaDAO;
    }

    public Cliente validarCliente(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            System.out.println("Cédula inválida.");
            return null;
        }
        Cliente c = clienteDAO.buscarPorCedula(cedula);
        if (c == null) System.out.println("Cliente no encontrado.");
        return c;
    }

    public Pelicula validarPelicula(int idPelicula, int cantidad) {
        Pelicula p = peliculaDAO.buscarPorId(idPelicula);

        if (p == null) {
            System.out.println("Película no encontrada.");
            return null;
        }

        if (!p.validarStock(cantidad)) {
            System.out.println("Sin stock disponible para: " + p.getNombre());
            return null;
        }

        return p;
    }

    public boolean validarFechaDevolucion(Date fechaDevolucion) {
        if (fechaDevolucion == null) {
            System.out.println("La fecha de devolución es obligatoria.");
            return false;
        }

        if (!fechaDevolucion.after(new Date())) {
            System.out.println("La fecha de devolución debe ser posterior a hoy.");
            return false;
        }

        return true;
    }

    public boolean confirmarAlquiler(Alquiler alquiler) {
        if (alquiler.getDetalles().isEmpty()) {
            System.out.println("No hay películas en el alquiler.");
            return false;
        }

        return alquilerDAO.registrar(alquiler);
    }

    public List<Alquiler> consultarPorCliente(String cedula) {
        Cliente c = clienteDAO.buscarPorCedula(cedula);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return List.of();
        }
        return alquilerDAO.listarPorCliente(c.getIdCliente());
    }
}