package view;

import model.Alquiler;
import model.Cliente;
import model.Pelicula;
import service.AlquilerService;
import service.PeliculaService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AlquilerView {

    private final Scanner sc;
    private final AlquilerService alquilerService;
    private final PeliculaService peliculaService;

    public AlquilerView(Scanner sc, AlquilerService alquilerService, PeliculaService peliculaService) {
        this.sc = sc;
        this.alquilerService = alquilerService;
        this.peliculaService = peliculaService;
    }

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

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1 -> nuevoAlquiler();
                case 2 -> consultarPorCliente();
                case 3 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 3);
    }

    private void nuevoAlquiler() {

        System.out.print("\nCédula del cliente: ");
        String cedula = sc.nextLine().trim();

        Cliente cliente = alquilerService.validarCliente(cedula);
        if (cliente == null) return;

        System.out.println("Cliente: " + cliente.getNombres() + " " + cliente.getApellidos());

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(cliente);

        while (true) {
            System.out.println("\n-- Películas disponibles --");

            List<Pelicula> conStock = peliculaService.listarConStock();
            if (conStock.isEmpty()) {
                System.out.println("No hay películas con stock disponible.");
                break;
            }

            for (Pelicula p : conStock) {
                System.out.printf("[%d] %s | Precio: $%.2f | Stock: %d%n",
                        p.getIdPelicula(), p.getNombre(), p.getPrecio(), p.getStock());
            }

            System.out.print("ID de película (0 para terminar): ");
            int idPelicula;
            try {
                idPelicula = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
                continue;
            }

            if (idPelicula == 0) break;

            System.out.print("Cantidad: ");
            int cantidad;
            try {
                cantidad = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            Pelicula pelicula = alquilerService.validarPelicula(idPelicula, cantidad);
            if (pelicula == null) continue;

            alquiler.agregarDetalle(pelicula, cantidad);
            System.out.printf("Agregado: %s x%d — Subtotal: $%.2f%n",
                    pelicula.getNombre(), cantidad, (double) cantidad * pelicula.getPrecio());
        }

        if (alquiler.getDetalles().isEmpty()) {
            System.out.println("No se agregaron películas. Alquiler cancelado.");
            return;
        }

        Date fechaDevolucion = null;
        while (fechaDevolucion == null) {
            System.out.print("Fecha de devolución (dd/MM/yyyy): ");
            String fechaStr = sc.nextLine().trim();
            try {
                fechaDevolucion = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                if (!alquilerService.validarFechaDevolucion(fechaDevolucion)) {
                    fechaDevolucion = null;
                }
            } catch (Exception e) {
                System.out.println("Formato inválido. Use dd/MM/yyyy.");
            }
        }

        alquiler.setFechaDevolucion(fechaDevolucion);

        System.out.println("\n" + alquiler);
        System.out.print("¿Confirmar alquiler? (s/n): ");
        String confirm = sc.nextLine().trim();

        if (!confirm.equalsIgnoreCase("s")) {
            System.out.println("Alquiler cancelado.");
            return;
        }

        if (alquilerService.confirmarAlquiler(alquiler)) {
            System.out.println("Alquiler registrado exitosamente.");
        } else {
            System.out.println("No se pudo registrar el alquiler.");
        }
    }

    private void consultarPorCliente() {

        System.out.print("\nCédula del cliente: ");
        String cedula = sc.nextLine().trim();

        List<Alquiler> alquileres = alquilerService.consultarPorCliente(cedula);

        if (alquileres.isEmpty()) {
            System.out.println("No se encontraron alquileres.");
            return;
        }

        for (Alquiler a : alquileres) {
            System.out.println(a);
            System.out.println("--------------------");
        }
    }
}