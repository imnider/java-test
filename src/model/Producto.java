package model;

public abstract class Producto {

    private String nombre;
    private double precio;
    private int stock;

    public Producto() {
    }

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public void mostrarDetalle() {
        System.out.println("Nombre: " + getNombre());
        System.out.println("Precio: $" + String.format("%.2f", getPrecio()));
        System.out.println("Stock: " + getStock());
    }

    public boolean validarStock(int cantidad) {
        return cantidad > 0 && cantidad <= stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}