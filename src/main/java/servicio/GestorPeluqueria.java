package servicio;

import modelo.ClienteServicio;

import java.util.*;
import java.util.stream.*;

/**
 * Gestor del sistema de peluqueria.
 *
 * Colecciones usadas:
 *   List<ClienteServicio>           → registro general de todos los clientes
 *   Queue<ClienteServicio>          → pendientes FIFO (LinkedList)
 *   Deque<ClienteServicio>          → historial LIFO tipo pila (ArrayDeque)
 *
 * @author Gabriela Orozco Garcia
 */
public class GestorPeluqueria {

    // List: registro general — guarda TODOS los clientes sin importar estado
    private final List<ClienteServicio> elementos = new ArrayList<>();

    // Queue: cola de pendientes FIFO — offer() al final, poll() desde el frente
    private final Queue<ClienteServicio> pendientes = new LinkedList<>();

    // Deque: historial LIFO — push() apila, pop() desapila desde la cima
    private final Deque<ClienteServicio> historial = new ArrayDeque<>();


    // ─────────────────────────────────────────────────────────────────────────
    // 1. REGISTRAR
    // Regla: se agrega a List, Queue y Map. Estado inicial PENDIENTE.
    // Se valida que el turno no exista con containsKey().
    // ─────────────────────────────────────────────────────────────────────────
    public void registrar(String turno, String nombre, String servicio, String categoria)
            throws Exception {

        if (indicePorTurno.containsKey(turno.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con el turno " + turno);
        }

        ClienteServicio cliente = new ClienteServicio(
                turno.toUpperCase(), nombre, servicio, categoria);

        elementos.add(cliente);                               // List
        pendientes.offer(cliente);                            // Queue
        indicePorTurno.put(cliente.getNumeroTurno(), cliente); // Map

        System.out.println("\n  OK Cliente registrado:");
        System.out.println("  " + cliente);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. VER TODOS — List con forEach
    // ─────────────────────────────────────────────────────────────────────────
    public void verTodos() {
        System.out.println("\n  === LISTA GENERAL — List (" + elementos.size() + ") ===");
        if (elementos.isEmpty()) {
            System.out.println("  [Vacia]");
            return;
        }
        elementos.forEach(c -> System.out.println("  " + c));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. VER PENDIENTES — Queue con peek(), size(), forEach()
    // ─────────────────────────────────────────────────────────────────────────
    public void verPendientes() {
        System.out.println("\n  === PENDIENTES — Queue (" + pendientes.size() + ") ===");
        if (pendientes.isEmpty()) {
            System.out.println("  [Cola vacia]");
            return;
        }
        System.out.println("  Siguiente (peek): " + pendientes.peek());
        System.out.println("  ---");
        pendientes.forEach(c -> System.out.println("  " + c));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. PROCESAR SIGUIENTE
    // Queue.poll() — FIFO: saca del frente
    // Deque.push() — LIFO: apila en la cima del historial
    // ─────────────────────────────────────────────────────────────────────────
    public void procesarSiguiente() throws Exception {
        ClienteServicio procesado = pendientes.poll();
        if (procesado == null) {
            throw new IllegalStateException("No hay clientes pendientes para procesar.");
        }
        procesado.setEstado("PROCESADO");
        historial.push(procesado);
        System.out.println("\n  OK Cliente atendido: " + procesado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. VER HISTORIAL — Deque con peek(), size(), forEach()
    // ─────────────────────────────────────────────────────────────────────────
    public void verHistorial() {
        System.out.println("\n  === HISTORIAL — Deque (" + historial.size() + ") ===");
        if (historial.isEmpty()) { System.out.println("  [Historial vacio]"); return; }
        System.out.println("  Ultimo atendido (peek): " + historial.peek());
        System.out.println("  ---");
        historial.forEach(c -> System.out.println("  " + c));
    }
}
