package view;

import dao.*;
import service.*;

import java.util.Scanner;

public class MainView {

    private final Scanner sc;
    private final PeliculaView peliculaView;
    private final ClienteView clienteView;
    private final AlquilerView alquilerView;

    public MainView() {
        this.sc = new Scanner(System.in);

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        PeliculaDAO peliculaDAO = new PeliculaDAO();
        AlquilerDAO alquilerDAO = new AlquilerDAO();

        CategoriaService categoriaService = new CategoriaService(categoriaDAO);
        ClienteService clienteService = new ClienteService(clienteDAO);
        PeliculaService peliculaService = new PeliculaService(peliculaDAO);
        AlquilerService alquilerService = new AlquilerService(alquilerDAO, clienteDAO, peliculaDAO);

        this.clienteView = new ClienteView(sc, clienteService);
        this.peliculaView = new PeliculaView(sc, peliculaService, categoriaService);
        this.alquilerView = new AlquilerView(sc, alquilerService, peliculaService);
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║  SISTEMA DE ALQUILER DE PELÍCULAS    ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Películas             ║");
            System.out.println("║  2. Gestión de Clientes              ║");
            System.out.println("║  3. Alquiler de Películas            ║");
            System.out.println("║  4. Salir                            ║");
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
                case 1 -> peliculaView.mostrarMenu();
                case 2 -> clienteView.mostrarMenu();
                case 3 -> alquilerView.mostrarMenu();
                case 4 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }
}