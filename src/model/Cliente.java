package model;

public class Cliente extends Persona {

    private int idCliente;
    private String cedula;
    private String direccion;
    private String fechaReg;

    public Cliente() {
    }

    public Cliente(int idCliente, String cedula, String direccion, String fechaReg,
                   String nombres, String apellidos, String telefono, String email) {
        super(nombres, apellidos, telefono, email);
        this.idCliente = idCliente;
        this.cedula = cedula;
        this.direccion = direccion;
        this.fechaReg = fechaReg;
    }

    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(String fechaReg) {
        this.fechaReg = fechaReg;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL CLIENTE ===");
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "ID Cliente: " + idCliente +
                "\nCédula: " + cedula +
                "\nNombres: " + getNombres() +
                "\nApellidos: " + getApellidos() +
                "\nTeléfono: " + getTelefono() +
                "\nEmail: " + getEmail() +
                "\nDirección: " + direccion +
                "\nFecha de Registro: " + fechaReg;
    }
}