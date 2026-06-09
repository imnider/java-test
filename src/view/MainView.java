package view;

import java.util.Scanner;

public class MainView {

    private Scanner sc = new Scanner(System.in);

    public void mostrar() {
        int opcion;

        do {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║  SISTEMA DE ALQUILER DE PELÍCULAS    ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Películas             ║");
            System.out.println("║  2. Gestión de Clientes              ║");
            System.out.println("║  3. Alquiler de Películas            ║");
            System.out.println("║  4. Salir                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    new PeliculaView().mostrarMenu();
                    break;
                case 2:
                    new ClienteView().mostrarMenu();
                    break;
                case 3:
                    new AlquilerView().mostrarMenu();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }
}