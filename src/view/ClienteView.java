package view;

import model.Cliente;
import service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class ClienteView {

    private final Scanner sc;
    private final ClienteService service;

    public ClienteView(Scanner sc, ClienteService service) {
        this.sc = sc;
        this.service = service;
    }

    public void mostrarMenu() {

        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║          GESTIÓN DE CLIENTES         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Registrar cliente                ║");
            System.out.println("║  2. Buscar cliente por cédula        ║");
            System.out.println("║  3. Listar todos los clientes        ║");
            System.out.println("║  4. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> buscarPorCedula();
                case 3 -> listarTodos();
                case 4 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private void registrarCliente() {

        System.out.print("Cédula: ");
        String cedula = sc.nextLine().trim();

        System.out.print("Nombres: ");
        String nombres = sc.nextLine().trim();

        System.out.print("Apellidos: ");
        String apellidos = sc.nextLine().trim();

        System.out.print("Teléfono: ");
        String telefono = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Dirección: ");
        String direccion = sc.nextLine().trim();

        Cliente c = new Cliente();
        c.setCedula(cedula);
        c.setNombres(nombres);
        c.setApellidos(apellidos);
        c.setTelefono(telefono);
        c.setEmail(email);
        c.setDireccion(direccion);

        if (service.registrarCliente(c)) {
            System.out.println("Cliente registrado correctamente.");
        }
    }

    private void buscarPorCedula() {

        System.out.print("Cédula: ");
        String cedula = sc.nextLine().trim();

        Cliente c = service.buscarPorCedula(cedula);

        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void listarTodos() {

        List<Cliente> lista = service.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente c : lista) {
            System.out.println(c);
            System.out.println("-------------------");
        }
    }
}