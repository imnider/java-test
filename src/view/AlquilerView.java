package view;

import java.util.Scanner;

public class AlquilerView {

    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {

        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║        ALQUILER DE PELÍCULAS         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Nuevo alquiler                   ║");
            System.out.println("║  2. Consultar alquileres cliente     ║");
            System.out.println("║  3. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    // Nuevo alquiler
                    System.out.println(">> Iniciando registro de alquiler...");
                    break;

                case 2:
                    // Consultar alquileres por cliente
                    System.out.print("Ingrese cédula del cliente: ");
                    String cedula = sc.nextLine();

                    System.out.println(">> Consultando alquileres de: " + cedula);
                    break;

                case 3:
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 3);
    }
}