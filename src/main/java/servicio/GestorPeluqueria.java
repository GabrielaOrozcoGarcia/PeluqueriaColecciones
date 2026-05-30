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

    // Map: indice para busqueda O(1) por numero de turno
    private final Map<String, ClienteServicio> indicePorTurno = new HashMap<>();


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

    // ─────────────────────────────────────────────────────────────────────────
    // 6. BUSCAR POR TURNO — Map.get() O(1)
    // ─────────────────────────────────────────────────────────────────────────
    public void buscarPorTurno(String turno) throws Exception {
        if (!indicePorTurno.containsKey(turno.toUpperCase())) {
            throw new IllegalArgumentException(
                    "No existe cliente con turno " + turno);
        }
        ClienteServicio encontrado = indicePorTurno.get(turno.toUpperCase());
        System.out.println("\n  OK Encontrado con Map.get():");
        System.out.println("  " + encontrado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 7. BUSCAR POR NOMBRE — Stream filter() + findFirst()
    // ─────────────────────────────────────────────────────────────────────────
    public void buscarPorNombre(String nombre) {
        Optional<ClienteServicio> resultado = elementos.stream()
                .filter(c -> c.getNombreCliente().equalsIgnoreCase(nombre))
                .findFirst();

        if (resultado.isPresent()) {
            System.out.println("\n  OK Encontrado con Stream.filter().findFirst():");
            System.out.println("  " + resultado.get());
        } else {
            System.out.println("\n  No se encontro cliente con nombre: " + nombre);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 8. FILTRAR — Stream filter() + toList()
    // ─────────────────────────────────────────────────────────────────────────
    public void filtrar(String criterio, String valor) {
        List<ClienteServicio> filtrados;

        if (criterio.equalsIgnoreCase("estado")) {
            filtrados = elementos.stream()
                    .filter(c -> c.getEstado().equalsIgnoreCase(valor))
                    .toList();
        } else {
            filtrados = elementos.stream()
                    .filter(c -> c.getCategoria().equalsIgnoreCase(valor))
                    .toList();
        }

        System.out.println("\n  === FILTRO Stream.filter() — "
                + criterio + " = " + valor + " (" + filtrados.size() + ") ===");
        if (filtrados.isEmpty()) {
            System.out.println("  [Sin resultados]");
        } else {
            filtrados.forEach(c -> System.out.println("  " + c));
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 9. ORDENAR — Stream sorted() + Comparator
    // ─────────────────────────────────────────────────────────────────────────
    public void ordenar(int criterio) {
        List<ClienteServicio> ordenados;
        String etiqueta;

        switch (criterio) {
            case 1 -> {
                ordenados = elementos.stream()
                        .sorted(Comparator.comparing(ClienteServicio::getNombreCliente))
                        .toList();
                etiqueta = "NOMBRE (A-Z)";
            }
            case 2 -> {
                ordenados = elementos.stream()
                        .sorted(Comparator.comparing(ClienteServicio::getNumeroTurno).reversed())
                        .toList();
                etiqueta = "TURNO descendente";
            }
            case 3 -> {
                ordenados = elementos.stream()
                        .sorted(Comparator.comparing(ClienteServicio::getCategoria))
                        .toList();
                etiqueta = "CATEGORIA (A-Z)";
            }
            default -> {
                ordenados = elementos.stream()
                        .sorted(Comparator.comparing(ClienteServicio::getNumeroTurno))
                        .toList();
                etiqueta = "TURNO ascendente";
            }
        }

        System.out.println("\n  === ORDENADO Stream.sorted() — "
                + etiqueta + " (" + ordenados.size() + ") ===");
        ordenados.forEach(c -> System.out.println("  " + c));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 10. ESTADISTICAS — Stream + Collectors.groupingBy + counting + anyMatch
    // ─────────────────────────────────────────────────────────────────────────
    public void verEstadisticas() {
        System.out.println("\n  === ESTADISTICAS — Stream + Map ===");

        // groupingBy + counting: cantidad por estado
        Map<String, Long> porEstado = elementos.stream()
                .collect(Collectors.groupingBy(
                        ClienteServicio::getEstado, Collectors.counting()));
        System.out.println("  Cantidad por estado:");
        porEstado.forEach((e, t) -> System.out.println("    " + e + ": " + t));

        // groupingBy + counting: cantidad por categoria
        Map<String, Long> porCategoria = elementos.stream()
                .collect(Collectors.groupingBy(
                        ClienteServicio::getCategoria, Collectors.counting()));
        System.out.println("  Cantidad por categoria:");
        porCategoria.forEach((c, t) -> System.out.println("    " + c + ": " + t));

        // filter + count
        long pendientesCount = elementos.stream()
                .filter(c -> c.getEstado().equalsIgnoreCase("PENDIENTE")).count();
        long procesadosCount = elementos.stream()
                .filter(c -> c.getEstado().equalsIgnoreCase("PROCESADO")).count();
        long canceladosCount = elementos.stream()
                .filter(c -> c.getEstado().equalsIgnoreCase("CANCELADO")).count();

        System.out.println("  Pendientes : " + pendientesCount);
        System.out.println("  Procesados : " + procesadosCount);
        System.out.println("  Cancelados : " + canceladosCount);
        System.out.println("  Total      : " + elementos.size());

        // anyMatch / allMatch / noneMatch
        boolean hayPendientes = elementos.stream()
                .anyMatch(c -> c.getEstado().equalsIgnoreCase("PENDIENTE"));
        boolean todosTienenTurno = elementos.stream()
                .allMatch(c -> c.getNumeroTurno() != null
                        && !c.getNumeroTurno().isBlank());
        boolean sinCancelados = elementos.stream()
                .noneMatch(c -> c.getEstado().equalsIgnoreCase("CANCELADO"));

        System.out.println("  anyMatch  - Hay pendientes     : " + hayPendientes);
        System.out.println("  allMatch  - Todos tienen turno : " + todosTienenTurno);
        System.out.println("  noneMatch - Sin cancelados     : " + sinCancelados);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 11. AGRUPAMIENTOS — Collectors.groupingBy + Collectors.toMap
    // ─────────────────────────────────────────────────────────────────────────
    public void verAgrupamientos() {
        System.out.println("\n  === AGRUPAMIENTO POR CATEGORIA — Collectors.groupingBy ===");
        Map<String, List<ClienteServicio>> porCategoria = elementos.stream()
                .collect(Collectors.groupingBy(ClienteServicio::getCategoria));
        porCategoria.forEach((cat, lista) -> {
            System.out.println("  Categoria: " + cat + " (" + lista.size() + ")");
            lista.forEach(c -> System.out.println("    " + c));
        });

        System.out.println("\n  === AGRUPAMIENTO POR ESTADO — Collectors.groupingBy ===");
        Map<String, List<ClienteServicio>> porEstado = elementos.stream()
                .collect(Collectors.groupingBy(ClienteServicio::getEstado));
        porEstado.forEach((est, lista) -> {
            System.out.println("  Estado: " + est + " (" + lista.size() + ")");
            lista.forEach(c -> System.out.println("    " + c));
        });

        // Collectors.toMap: reconstruir indice desde la lista
        Map<String, ClienteServicio> mapaReconstruido = elementos.stream()
                .collect(Collectors.toMap(
                        ClienteServicio::getNumeroTurno,
                        c -> c,
                        (existente, repetido) -> existente));
        System.out.println("\n  Collectors.toMap — mapa reconstruido: "
                + mapaReconstruido.size() + " entradas");

        // Recorrer entradas del Map
        System.out.println("  Map.entrySet():");
        indicePorTurno.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("    " + e.getKey()
                        + " -> " + e.getValue().getNombreCliente()));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 12. CANCELAR — Map.get() + removeIf() en Queue
    // No se elimina de List ni de Map: queda como evidencia
    // ─────────────────────────────────────────────────────────────────────────
    public void cancelar(String turno) throws Exception {
        ClienteServicio cliente = indicePorTurno.get(turno.toUpperCase());
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "No existe cliente con turno " + turno);
        }
        if (!cliente.getEstado().equalsIgnoreCase("PENDIENTE")) {
            throw new IllegalStateException(
                    "Solo se pueden cancelar clientes PENDIENTES. Estado actual: "
                            + cliente.getEstado());
        }
        cliente.setEstado("CANCELADO");
        pendientes.removeIf(c -> c.getNumeroTurno().equalsIgnoreCase(turno));
        System.out.println("\n  OK Cancelado: " + cliente);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 13. DESHACER — Deque.pop() + Queue.offer()
    // El cliente vuelve a la cola con estado PENDIENTE
    // ─────────────────────────────────────────────────────────────────────────
    public void deshacer() throws Exception {
        if (historial.isEmpty()) {
            throw new IllegalStateException("No hay operaciones para deshacer.");
        }
        ClienteServicio ultimo = historial.pop();
        ultimo.setEstado("PENDIENTE");
        pendientes.offer(ultimo);
        System.out.println("\n  OK Deshecho. Cliente devuelto a la cola:");
        System.out.println("  " + ultimo);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 14. VER CANTIDADES — size() de cada coleccion + Stream map() para turnos
    // ─────────────────────────────────────────────────────────────────────────
    public void verCantidades() {
        System.out.println("\n  === CANTIDADES ===");
        System.out.println("  List  elementos.size()       : " + elementos.size());
        System.out.println("  Queue pendientes.size()      : " + pendientes.size());
        System.out.println("  Deque historial.size()       : " + historial.size());
        System.out.println("  Map   indicePorTurno.size()  : " + indicePorTurno.size());

        // Stream map() para extraer solo los turnos
        List<String> turnos = elementos.stream()
                .map(ClienteServicio::getNumeroTurno)
                .toList();
        System.out.println("  Turnos (Stream.map): " + turnos);

        // Stream groupingBy + counting por estado
        Map<String, Long> conteo = elementos.stream()
                .collect(Collectors.groupingBy(
                        ClienteServicio::getEstado, Collectors.counting()));
        conteo.forEach((e, t) ->
                System.out.println("  Stream conteo " + e + ": " + t));

        // Map.keySet() y Map.values()
        System.out.println("  Map.keySet()  : " + indicePorTurno.keySet());
        System.out.println("  Map.values()  : "
                + indicePorTurno.values().stream()
                .map(ClienteServicio::getNombreCliente).toList());
    }
}
