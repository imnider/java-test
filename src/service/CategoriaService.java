package service;

import dao.CategoriaDAO;
import model.Categoria;

import java.util.List;

public class CategoriaService {

    private final CategoriaDAO dao;

    public CategoriaService(CategoriaDAO dao) {
        this.dao = dao;
    }

    public List<Categoria> listarTodas() {
        return dao.listarTodos();
    }

    public Categoria buscarPorId(int id) {
        if (id <= 0) {
            System.out.println("ID de categoría inválido.");
            return null;
        }
        return dao.buscarPorId(id);
    }

    public Categoria buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Ingrese un nombre válido.");
            return null;
        }
        return dao.buscarPorNombre(nombre);
    }
}