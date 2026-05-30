import modelo.ClienteServicio;
import servicio.GestorPeluqueria;

import java.util.Scanner;

/**
 * Clase principal con menu de consola — 15 opciones obligatorias.
 * Caso 21 - Peluqueria | Actividad 3: Colecciones SDK Java
 * Asignatura: Estructuras de Datos
 *
 * @author Gabriela Orozco Garcia
 * Codigo: 7502420036
 * Universidad de Cartagena
 * Docente: John Carlos Arrieta Arrieta
 */
public class Main {

    private static final GestorPeluqueria gestor = new GestorPeluqueria();
    private static final Scanner scanner = new Scanner(System.in);
    private static int contadorTurno = 1;

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   SISTEMA DE GESTION - PELUQUERIA");
        System.out.println("   Colecciones SDK Java | Actividad 3");
        System.out.println("   Gabriela Orozco Garcia - 7502420036");
        System.out.println("   Universidad de Cartagena");
        System.out.println("==============================================");

        int opcion = 0;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                ejecutar(opcion);
            } catch (NumberFormatException e) {
                System.out.println("\n  Opcion invalida. Ingrese un numero del 1 al 15.");
            } catch (Exception e) {
                System.out.println("\n  Error: " + e.getMessage());
            }
        } while (opcion != 15);
    }

    private static void mostrarMenu() {
        System.out.println("\n----------------------------------------------");
        System.out.println("   1.  Registrar cliente");
        System.out.println("   2.  Ver todos los clientes          [List]");
        System.out.println("   3.  Ver clientes pendientes         [Queue]");
        System.out.println("   4.  Procesar siguiente cliente");
        System.out.println("   5.  Ver historial de atendidos      [Deque]");
        System.out.println("   6.  Buscar por turno                [Map]");
        System.out.println("   7.  Buscar por nombre               [Stream]");
        System.out.println("   8.  Filtrar elementos               [Stream]");
        System.out.println("   9.  Ordenar elementos               [Stream]");
        System.out.println("  10.  Estadisticas                    [Stream + Map]");
        System.out.println("  11.  Agrupamientos                   [Stream + Map]");
        System.out.println("  12.  Cancelar cliente pendiente");
        System.out.println("  13.  Deshacer ultimo procesamiento");
        System.out.println("  14.  Ver cantidades");
        System.out.println("  15.  Salir");
        System.out.println("----------------------------------------------");
        System.out.print("  Seleccione una opcion: ");
    }

    private static void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1  -> registrarCliente();
            case 2  -> gestor.verTodos();
            case 3  -> gestor.verPendientes();
            case 4  -> gestor.procesarSiguiente();
            case 5  -> gestor.verHistorial();
            case 6  -> buscarPorTurno();
            case 7  -> buscarPorNombre();
            case 8  -> filtrar();
            case 9  -> ordenar();
            case 10 -> gestor.verEstadisticas();
            case 11 -> gestor.verAgrupamientos();
            case 12 -> cancelar();
            case 13 -> gestor.deshacer();
            case 14 -> gestor.verCantidades();
            case 15 -> System.out.println("\n  Sistema cerrado. Hasta luego.");
            default -> System.out.println("\n  Opcion no valida. Elija entre 1 y 15.");
        }
    }

    // ── Helpers de entrada ───────────────────────────────────────────────────

    private static void registrarCliente() throws Exception {
        System.out.println("\n  --- REGISTRAR CLIENTE ---");

        System.out.print("  Nombre del cliente: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacio");

        System.out.println("  Servicios disponibles:");
        System.out.println("    1. Corte de cabello    [CABELLO]");
        System.out.println("    2. Tintura             [CABELLO]");
        System.out.println("    3. Peinado             [CABELLO]");
        System.out.println("    4. Barba               [BARBA]");
        System.out.println("    5. Arreglo de cejas    [BARBA]");
        System.out.println("    6. Tratamiento capilar [TRATAMIENTO]");
        System.out.print("  Seleccione (1-6): ");
        String op = scanner.nextLine().trim();

        String servicio;
        String categoria;
        switch (op) {
            case "1" -> { servicio = "Corte de cabello";    categoria = "CABELLO"; }
            case "2" -> { servicio = "Tintura";             categoria = "CABELLO"; }
            case "3" -> { servicio = "Peinado";             categoria = "CABELLO"; }
            case "4" -> { servicio = "Barba";               categoria = "BARBA"; }
            case "5" -> { servicio = "Arreglo de cejas";    categoria = "BARBA"; }
            case "6" -> { servicio = "Tratamiento capilar"; categoria = "TRATAMIENTO"; }
            default  -> { servicio = "Corte de cabello";    categoria = "CABELLO"; }
        }

        String turno = "T-" + String.format("%03d", contadorTurno++);
        gestor.registrar(turno, nombre, servicio, categoria);
    }

    private static void buscarPorTurno() throws Exception {
        System.out.print("\n  Numero de turno (ej: T-001): ");
        String turno = scanner.nextLine().trim();
        if (turno.isEmpty())
            throw new IllegalArgumentException("El turno no puede estar vacio");
        gestor.buscarPorTurno(turno);
    }

    private static void buscarPorNombre() {
        System.out.print("\n  Nombre del cliente: ");
        String nombre = scanner.nextLine().trim();
        gestor.buscarPorNombre(nombre);
    }

    private static void filtrar() {
        System.out.println("\n  Filtrar por:");
        System.out.println("    1. Estado  (PENDIENTE / PROCESADO / CANCELADO)");
        System.out.println("    2. Categoria (CABELLO / BARBA / TRATAMIENTO)");
        System.out.print("  Elija (1-2): ");
        String op = scanner.nextLine().trim();

        if (op.equals("1")) {
            System.out.print("  Estado: ");
            gestor.filtrar("estado", scanner.nextLine().trim());
        } else {
            System.out.print("  Categoria: ");
            gestor.filtrar("categoria", scanner.nextLine().trim());
        }
    }

    private static void ordenar() {
        System.out.println("\n  Ordenar por:");
        System.out.println("    1. Nombre (A-Z)");
        System.out.println("    2. Turno descendente");
        System.out.println("    3. Categoria (A-Z)");
        System.out.println("    4. Turno ascendente");
        System.out.print("  Elija (1-4): ");
        try {
            gestor.ordenar(Integer.parseInt(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            gestor.ordenar(4);
        }
    }

    private static void cancelar() throws Exception {
        System.out.print("\n  Turno a cancelar (ej: T-002): ");
        String turno = scanner.nextLine().trim();
        if (turno.isEmpty())
            throw new IllegalArgumentException("El turno no puede estar vacio");
        gestor.cancelar(turno);
    }
}