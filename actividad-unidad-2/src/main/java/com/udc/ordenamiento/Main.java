package com.udc.ordenamiento;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[] tiempos = { 9.85, 10.12, 9.98, 10.45, 10.05, 9.92, 10.20, 10.15 };
        int opcion;

        do {
            System.out.println("\n======================================================");
            System.out.println("  CLASIFICACIÓN DE TIEMPOS DEPORTIVOS (COMPETENCIA)   ");
            System.out.println("======================================================");
            System.out.println("1. Clasificar tiempos con Bubble Sort");
            System.out.println("2. Clasificar tiempos con Selection Sort");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> clasificarBurbuja(tiempos);
                case 2 -> clasificarSeleccion(tiempos);
                case 0 -> System.out.println("Saliendo del sistema deportivo...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 0);
    }

    private static void clasificarBurbuja(double[] tiempos) {
        System.out.println("\n[Clasificación con Bubble Sort]");
        System.out.println("Tiempos iniciales: " + Arrays.toString(tiempos));

        ResultadoOrdenamiento resultado = ClasificadorTiempos.burbuja(tiempos);

        mostrarDetalles(resultado);
    }

    private static void clasificarSeleccion(double[] tiempos) {
        System.out.println("\n[Clasificación con Selection Sort]");
        System.out.println("Tiempos iniciales: " + Arrays.toString(tiempos));

        ResultadoOrdenamiento resultado = ClasificadorTiempos.seleccion(tiempos);

        mostrarDetalles(resultado);
    }

    private static void mostrarDetalles(ResultadoOrdenamiento resultado) {
        System.out.println("Tiempos ordenados: " + Arrays.toString(resultado.getTiemposOrdenados()));
        System.out.println("Comparaciones realizadas: " + resultado.getComparaciones());
        System.out.println("Intercambios realizados:  " + resultado.getIntercambios());
        System.out.println("Tiempo de ejecución:      " + resultado.getTiempoNanos() + " ns");

        mostrarPodio(resultado.getTiemposOrdenados());
    }

    private static void mostrarPodio(double[] clasificados) {
        System.out.println("\n--- Podio Oficial de la Carrera ---");
        if (clasificados.length >= 1) System.out.println("  1° Lugar (Oro):    " + clasificados[0] + " s");
        if (clasificados.length >= 2) System.out.println("  2° Lugar (Plata):  " + clasificados[1] + " s");
        if (clasificados.length >= 3) System.out.println("  3° Lugar (Bronce): " + clasificados[2] + " s");
    }
}
