package com.udc.busqueda;

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
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> busquedaTradicional(scanner, identificaciones);
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
