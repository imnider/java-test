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
        System.out.println("=== CLIENTE ===");
        super.mostrarInfo();
        System.out.println("Cédula: " + cedula);
        System.out.println("Dirección: " + direccion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CLIENTE ===\n");
        sb.append("ID: ").append(idCliente).append("\n");
        sb.append("Cédula: ").append(cedula).append("\n");
        sb.append("Nombres: ").append(getNombres()).append("\n");
        sb.append("Apellidos: ").append(getApellidos()).append("\n");
        sb.append("Teléfono: ").append(getTelefono()).append("\n");
        sb.append("Email: ").append(getEmail()).append("\n");
        sb.append("Dirección: ").append(direccion).append("\n");
        sb.append("Fecha de Registro: ").append(fechaReg);
        return sb.toString();
    }
}