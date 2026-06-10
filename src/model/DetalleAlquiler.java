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

    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

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

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  - ").append(pelicula.getNombre()).append("\n");
        sb.append("    Cantidad: ").append(cantidad).append("\n");
        sb.append("    Precio unitario: $").append(String.format("%.2f", precioUnitario)).append("\n");
        sb.append("    Subtotal: $").append(String.format("%.2f", subtotal));
        return sb.toString();
    }
}