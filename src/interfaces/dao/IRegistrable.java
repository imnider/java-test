package interfaces.dao;

import java.util.List;

public interface IRegistrable<T> {

    boolean registrar(T obj);

    List<T> listarTodos();
}