package com.udc.busqueda;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] identificaciones = {
            1045829101, 1045829102, 1045829103, 1045829104,
            1045829105, 1045829106, 1045829107
        };

        int opcion;

        do {
            System.out.println("\n==========================================");
            System.out.println("  SISTEMA DE BÚSQUEDA DE IDENTIFICACIONES ");
            System.out.println("==========================================");
            System.out.println("1. Búsqueda lineal tradicional");
            System.out.println("2. Búsqueda lineal con centinela");
            System.out.println("3. Contrastar ambos métodos con un número");
            System.out.println("4. Casos de prueba predefinidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> busquedaTradicional(scanner, identificaciones);
                case 2 -> busquedaCentinela(scanner, identificaciones);
                case 3 -> contrastar(scanner, identificaciones);
                case 4 -> probarCasos(identificaciones);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 0);
    }

    private static void busquedaTradicional(Scanner scanner, int[] identificaciones) {
        System.out.print("Ingrese número de identificación a buscar: ");
        int objetivo = scanner.nextInt();

        ResultadoBusqueda resultado = BuscadorIdentificaciones.busquedaLinealTradicional(identificaciones, objetivo);

        System.out.println("\n[Resultado Búsqueda Tradicional]");
        mostrarResultado(resultado);
    }

    private static void busquedaCentinela(Scanner scanner, int[] identificaciones) {
        System.out.print("Ingrese número de identificación a buscar: ");
        int objetivo = scanner.nextInt();

        ResultadoBusqueda resultado = BuscadorIdentificaciones.busquedaLinealCentinela(identificaciones, objetivo);

        System.out.println("\n[Resultado Búsqueda con Centinela]");
        mostrarResultado(resultado);
    }

    private static void contrastar(Scanner scanner, int[] identificaciones) {
        System.out.print("Ingrese número de identificación para contrastar: ");
        int objetivo = scanner.nextInt();

        ResultadoBusqueda resTradicional = BuscadorIdentificaciones.busquedaLinealTradicional(identificaciones, objetivo);
        ResultadoBusqueda resCentinela = BuscadorIdentificaciones.busquedaLinealCentinela(identificaciones, objetivo);

        System.out.println("\n--- Contraste para el valor: " + objetivo + " ---");
        System.out.println("Tradicional -> Encontrado: " + (resTradicional.isEncontrado() ? "SÍ (índice " + resTradicional.getIndice() + ")" : "NO") +
                " | Comparaciones: " + resTradicional.getComparaciones() +
                " | Tiempo: " + resTradicional.getTiempoNanos() + " ns");

        System.out.println("Centinela   -> Encontrado: " + (resCentinela.isEncontrado() ? "SÍ (índice " + resCentinela.getIndice() + ")" : "NO") +
                " | Comparaciones: " + resCentinela.getComparaciones() +
                " | Tiempo: " + resCentinela.getTiempoNanos() + " ns");
    }

    private static void probarCasos(int[] identificaciones) {
        System.out.println("\n--- 1. Caso: Elemento al inicio ---");
        probar(identificaciones, identificaciones[0]);

        System.out.println("\n--- 2. Caso: Elemento en el centro ---");
        int centro = identificaciones.length / 2;
        probar(identificaciones, identificaciones[centro]);

        System.out.println("\n--- 3. Caso: Elemento al final ---");
        int fin = identificaciones.length - 1;
        probar(identificaciones, identificaciones[fin]);

        System.out.println("\n--- 4. Caso: Elemento inexistente ---");
        probar(identificaciones, 999999999);

        System.out.println("\n--- 5. Caso: Elemento repetido (duplicado en el arreglo) ---");
        int[] identificacionesConDuplicados = {
            1045829101, 1045829102, 1045829103, 1045829104,
            1045829103, 1045829106, 1045829103
        };
        System.out.println("Colección con duplicados: " + Arrays.toString(identificacionesConDuplicados));
        System.out.println("Buscando número 1045829103 (duplicado en índices 2, 4 y 6):");
        probar(identificacionesConDuplicados, 1045829103);
        System.out.println("Comportamiento: Ambos algoritmos retornan la primera aparición e interrumpen el recorrido.");
    }

    private static void probar(int[] identificaciones, int objetivo) {
        ResultadoBusqueda resTradicional = BuscadorIdentificaciones.busquedaLinealTradicional(identificaciones, objetivo);
        ResultadoBusqueda resCentinela = BuscadorIdentificaciones.busquedaLinealCentinela(identificaciones, objetivo);

        System.out.println("Objetivo: " + objetivo);
        System.out.println("  Tradicional: " + (resTradicional.isEncontrado() ? "Índice " + resTradicional.getIndice() : "No hallado") +
                " | " + resTradicional.getComparaciones() + " comparaciones | " + resTradicional.getTiempoNanos() + " ns");
        System.out.println("  Centinela:   " + (resCentinela.isEncontrado() ? "Índice " + resCentinela.getIndice() : "No hallado") +
                " | " + resCentinela.getComparaciones() + " comparaciones | " + resCentinela.getTiempoNanos() + " ns");
    }

    private static void mostrarResultado(ResultadoBusqueda resultado) {
        if (resultado.isEncontrado()) {
            System.out.println("Estado: Identificación localizada.");
            System.out.println("Posición en arreglo: " + resultado.getIndice());
        } else {
            System.out.println("Estado: Identificación no encontrada.");
        }
        System.out.println("Comparaciones realizadas: " + resultado.getComparaciones());
        System.out.println("Tiempo de ejecución: " + resultado.getTiempoNanos() + " ns");
    }
}
