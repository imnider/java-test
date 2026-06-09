package model;

public class Pelicula extends Producto {

    private int idPelicula;
    private int idCategoria;
    private String director;
    private int anioEstreno;

    public Pelicula() {
    }

    public Pelicula(int idCategoria, String director, int anioEstreno,
                    String nombre, double precio, int stock) {
        super(nombre, precio, stock);
        this.idCategoria = idCategoria;
        this.director = director;
        this.anioEstreno = anioEstreno;
    }

    public Pelicula(int idPelicula, int idCategoria, String director, int anioEstreno,
                    String nombre, double precio, int stock) {
        super(nombre, precio, stock);
        this.idPelicula = idPelicula;
        this.idCategoria = idCategoria;
        this.director = director;
        this.anioEstreno = anioEstreno;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(int anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    @Override
    public void mostrarDetalle() {
        System.out.println(this);
    }

    // toString
    @Override
    public String toString() {
        return "=== PELÍCULA ===" +
                "\nID Película: " + idPelicula +
                "\nCategoría: " + idCategoria +
                "\nTitulo: " + getNombre() +
                "\nDirector: " + director +
                "\nAño Estreno: " + anioEstreno +
                "\nPrecio: " + getPrecio() +
                "\nStock: " + getStock();
    }
}