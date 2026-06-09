package view;

import model.Cliente;
import service.ClienteService;

import java.util.Scanner;

public class ClienteView {

    private final Scanner sc = new Scanner(System.in);
    private ClienteService service = new ClienteService();

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

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:

                    sc.nextLine();

                    System.out.print("Cédula: ");
                    String cedula = sc.nextLine();

                    System.out.print("Nombres: ");
                    String nombres = sc.nextLine();

                    System.out.print("Apellidos: ");
                    String apellidos = sc.nextLine();

                    System.out.print("Teléfono: ");
                    String telefono = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Dirección: ");
                    String direccion = sc.nextLine();

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

                    break;

                case 2:

                    sc.nextLine();
                    System.out.print("Cédula: ");
                    String cedulaBuscada = sc.nextLine();

                    Cliente c2 = service.buscarPorCedula(cedulaBuscada);

                    if (c2 != null) {
                        System.out.println(c2);
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }

                    break;

                case 3:

                    for (Cliente c3 : service.listarTodos()) {
                        System.out.println(c3);
                        System.out.println("-------------------");
                    }

                    break;

                case 4:
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }
}