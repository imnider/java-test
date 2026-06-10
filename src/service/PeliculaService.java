package service;

import dao.PeliculaDAO;
import model.Pelicula;

import java.util.List;

public class PeliculaService {

    private final PeliculaDAO dao;

    public PeliculaService(PeliculaDAO dao) {
        this.dao = dao;
    }

    public boolean registrarPelicula(Pelicula p) {

        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return false;
        }

        if (p.getIdCategoria() <= 0) {
            System.out.println("Debe seleccionar una categoría válida.");
            return false;
        }

        if (p.getPrecio() <= 0) {
            System.out.println("El precio debe ser mayor a 0.");
            return false;
        }

        if (p.getStock() < 0) {
            System.out.println("El stock no puede ser negativo.");
            return false;
        }

        return dao.registrar(p);
    }

    public Pelicula buscarPorTitulo(String titulo) {

        if (titulo == null || titulo.trim().isEmpty()) {
            System.out.println("Ingrese un título válido.");
            return null;
        }

        return dao.buscarPorNombre(titulo);
    }

    public List<Pelicula> listarTodas() {
        return dao.listarTodos();
    }

    public List<Pelicula> listarConStock() {
        return dao.listarConStock();
    }
}