package model;

public abstract class Persona {

    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;

    public Persona() {
    }

    public Persona(String nombres, String apellidos, String telefono, String email) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void mostrarInfo() {
        System.out.println("Nombres: " + getNombres() + " " + getApellidos());
        System.out.println("Teléfono: " + getTelefono());
        System.out.println("Email: " + getEmail());
    }

    @Override
    public String toString() {
        return "Persona {" +
                "nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}