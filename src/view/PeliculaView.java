package view;

import model.Pelicula;
import service.PeliculaService;

import java.util.Scanner;

public class PeliculaView {

    private final Scanner sc = new Scanner(System.in);
    private PeliculaService service = new PeliculaService();

    public void mostrarMenu() {

        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE PELÍCULAS         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Registrar película               ║");
            System.out.println("║  2. Buscar película por título       ║");
            System.out.println("║  3. Listar todas las películas       ║");
            System.out.println("║  4. Ver stock disponible             ║");
            System.out.println("║  5. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:

                    System.out.print("Categoría: ");
                    int cat = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Director: ");
                    String director = sc.nextLine();

                    System.out.print("Año: ");
                    int anio = sc.nextInt();

                    System.out.print("Precio: ");
                    double precio = sc.nextDouble();

                    System.out.print("Stock: ");
                    int stock = sc.nextInt();

                    Pelicula p = new Pelicula(cat, director, anio, nombre, precio, stock);

                    if (service.registrarPelicula(p)) {
                        System.out.println("Película registrada correctamente.");
                    }

                    break;

                case 2:

                    sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();

                    Pelicula encontrada = service.buscarPorTitulo(titulo);

                    if (encontrada != null) {
                        System.out.println(encontrada);
                    } else {
                        System.out.println("No encontrada.");
                    }

                    break;

                case 3:

                    for (Pelicula p1 : service.listarTodas()) {
                        System.out.println(p1);
                        System.out.println("--------------------");
                    }

                    break;

                case 4:

                    for (Pelicula p2 : service.listarConStock()) {
                        System.out.println(p2.getNombre() + " | Stock: " + p2.getStock());
                    }

                    break;

                case 5:
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }
}