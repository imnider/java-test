package model;

public class DetalleAlquiler {

    private int idDetalle;
    private Pelicula pelicula;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleAlquiler() {
    }

    public DetalleAlquiler(int idDetalle, Pelicula pelicula, int cantidad) {
        this.idDetalle = idDetalle;
        this.pelicula = pelicula;
        this.cantidad = cantidad;
        this.precioUnitario = pelicula.getPrecio();
        this.subtotal = calcularSubtotal();
    }

    // Método calcular subtotal
    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    // toString
    @Override
    public String toString() {
        return "DetalleAlquiler{" +
                "idDetalle=" + idDetalle +
                ", pelicula=" + pelicula.getNombre() +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}