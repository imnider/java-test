package view;

import model.Categoria;
import model.Pelicula;
import service.CategoriaService;
import service.PeliculaService;

import java.util.List;
import java.util.Scanner;

public class PeliculaView {

    private final Scanner sc;
    private final PeliculaService service;
    private final CategoriaService categoriaService;

    public PeliculaView(Scanner sc, PeliculaService service, CategoriaService categoriaService) {
        this.sc = sc;
        this.service = service;
        this.categoriaService = categoriaService;
    }

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

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1 -> registrarPelicula();
                case 2 -> buscarPorTitulo();
                case 3 -> listarTodas();
                case 4 -> listarConStock();
                case 5 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }

    private void registrarPelicula() {

        System.out.println("\n-- Categorías disponibles --");
        for (Categoria cat : categoriaService.listarTodas()) {
            System.out.println(cat);
        }

        Categoria categoriaSeleccionada = null;
        while (categoriaSeleccionada == null) {
            System.out.print("Seleccione ID de categoría: ");
            try {
                int idCat = Integer.parseInt(sc.nextLine().trim());
                categoriaSeleccionada = categoriaService.buscarPorId(idCat);
                if (categoriaSeleccionada == null) {
                    System.out.println("Categoría no encontrada. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
            }
        }

        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();

        System.out.print("Director: ");
        String director = sc.nextLine().trim();

        System.out.print("Año: ");
        int anio;
        try {
            anio = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
            return;
        }

        System.out.print("Precio: ");
        double precio;
        try {
            precio = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Precio inválido.");
            return;
        }

        System.out.print("Stock: ");
        int stock;
        try {
            stock = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Stock inválido.");
            return;
        }

        Pelicula p = new Pelicula(categoriaSeleccionada.getIdCategoria(), director, anio, titulo, precio, stock);
        if (service.registrarPelicula(p)) {
            System.out.println("Película registrada correctamente.");
        }
    }

    private void buscarPorTitulo() {

        System.out.print("Título: ");
        String titulo = sc.nextLine().trim();

        Pelicula encontrada = service.buscarPorTitulo(titulo);

        if (encontrada != null) {
            System.out.println(encontrada);
        } else {
            System.out.println("Película no encontrada.");
        }
    }

    private void listarTodas() {

        List<Pelicula> lista = service.listarTodas();

        if (lista.isEmpty()) {
            System.out.println("No hay películas registradas.");
            return;
        }

        for (Pelicula p : lista) {
            System.out.println(p);
            System.out.println("--------------------");
        }
    }

    private void listarConStock() {

        List<Pelicula> lista = service.listarConStock();

        if (lista.isEmpty()) {
            System.out.println("No hay películas con stock disponible.");
            return;
        }

        for (Pelicula p : lista) {
            System.out.println(p.getNombre() + " | Stock: " + p.getStock());
        }
    }
}