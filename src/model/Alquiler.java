package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alquiler {

    private int idAlquiler;
    private Cliente cliente;
    private Date fechaAlquiler;
    private Date fechaDevolucion;
    private double total;
    private String estado;

    private List<DetalleAlquiler> detalles = new ArrayList<>();

    public Alquiler() {
        this.detalles = new ArrayList<>();
        this.fechaAlquiler = new Date();
        this.estado = "ACTIVO";
    }

    public Alquiler(int idAlquiler, Cliente cliente) {
        this();
        this.idAlquiler = idAlquiler;
        this.cliente = cliente;
    }

    public void agregarDetalle(Pelicula pelicula, int cantidad) {
        if (pelicula.validarStock(cantidad)) {

            DetalleAlquiler detalle = new DetalleAlquiler(
                    detalles.size() + 1,
                    pelicula,
                    cantidad
            );

            detalles.add(detalle);
            calcularTotal();

        } else {
            System.out.println("Stock insuficiente para: " + pelicula.getNombre());
        }
    }

    public double calcularTotal() {
        total = 0;

        for (DetalleAlquiler d : detalles) {
            total += d.getSubtotal();
        }

        return total;
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(Date fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {this.total = total;}

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleAlquiler> getDetalles() {
        return detalles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALQUILER ===\n");
        sb.append("ID: ").append(idAlquiler).append("\n");
        sb.append("Cliente: ").append(cliente.getNombres()).append(" ").append(cliente.getApellidos()).append("\n");
        sb.append("Fecha Alquiler: ").append(fechaAlquiler).append("\n");
        sb.append("Fecha Devolución: ").append(fechaDevolucion).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Detalles:\n");
        for (DetalleAlquiler d : detalles) {
            sb.append(d.toString()).append("\n");
        }
        sb.append("TOTAL: $").append(String.format("%.2f", calcularTotal()));
        return sb.toString();
    }
}