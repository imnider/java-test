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
        System.out.println("=== PELÍCULA ===");
        super.mostrarDetalle();
        System.out.println("Director: " + director);
        System.out.println("Año Estreno: " + anioEstreno);
        System.out.println("Categoría ID: " + idCategoria);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PELÍCULA ===\n");
        sb.append("ID: ").append(idPelicula).append("\n");
        sb.append("Categoría: ").append(idCategoria).append("\n");
        sb.append("Título: ").append(getNombre()).append("\n");
        sb.append("Director: ").append(director).append("\n");
        sb.append("Año Estreno: ").append(anioEstreno).append("\n");
        sb.append("Precio: $").append(String.format("%.2f", getPrecio())).append("\n");
        sb.append("Stock: ").append(getStock());
        return sb.toString();
    }
}