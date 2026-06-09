package interfaces.dao;

public interface IBuscable<T> {

    T buscarPorId(int id);

    T buscarPorNombre(String nombre);
}
