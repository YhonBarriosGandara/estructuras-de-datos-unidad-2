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
            System.out.println("3. Comparar Bubble Sort vs Selection Sort (Métricas y veredicto)");
            System.out.println("4. Ejecutar escenarios de prueba (Casi ordenado, Inverso, Empates)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> clasificarBurbuja(tiempos);
                case 2 -> clasificarSeleccion(tiempos);
                case 3 -> contrastarMetodos(tiempos);
                case 4 -> ejecutarEscenariosDePrueba();
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

    private static void contrastarMetodos(double[] tiempos) {
        System.out.println("\n[Comparativa Directa de Algoritmos]");
        System.out.println("Tiempos evaluados: " + Arrays.toString(tiempos));

        ResultadoOrdenamiento resBurbuja = ClasificadorTiempos.burbuja(tiempos);
        ResultadoOrdenamiento resSeleccion = ClasificadorTiempos.seleccion(tiempos);

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-26s %-16s %-16s%n", "Métrica", "Bubble Sort", "Selection Sort");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-26s %-16d %-16d%n", "Comparaciones", resBurbuja.getComparaciones(), resSeleccion.getComparaciones());
        System.out.printf("%-26s %-16d %-16d%n", "Intercambios (movimientos)", resBurbuja.getIntercambios(), resSeleccion.getIntercambios());
        System.out.printf("%-26s %-16s %-16s%n", "Tiempo de ejecución", resBurbuja.getTiempoNanos() + " ns", resSeleccion.getTiempoNanos() + " ns");
        System.out.println("------------------------------------------------------------");

        System.out.println("Veredicto de eficiencia:");
        long diferenciaMovimientos = resBurbuja.getIntercambios() - resSeleccion.getIntercambios();
        if (diferenciaMovimientos > 0) {
            System.out.println("Selection Sort realizó " + diferenciaMovimientos + " movimientos menos que Bubble Sort.");
            System.out.println("Fundamento: Selection Sort realiza a lo sumo N-1 intercambios, optimizando la reubicación de elementos.");
        } else if (diferenciaMovimientos < 0) {
            System.out.println("Bubble Sort realizó " + Math.abs(diferenciaMovimientos) + " movimientos menos debido a la bandera de corte temprano.");
        } else {
            System.out.println("Ambos algoritmos realizaron la misma cantidad de movimientos.");
        }
    }

    private static void ejecutarEscenariosDePrueba() {
        System.out.println("\n============================================================");
        System.out.println("         EJECUCIÓN DE ESCENARIOS DE PRUEBA AVANZADOS        ");
        System.out.println("============================================================");

        probarEscenario("1. Arreglo Casi Ordenado (Mejor caso para Bubble Sort)",
                new double[] { 9.80, 9.85, 9.90, 10.00, 9.95, 10.10, 10.20 });

        probarEscenario("2. Arreglo Totalmente Invertido (Peor caso para Bubble Sort)",
                new double[] { 10.50, 10.35, 10.20, 10.10, 9.98, 9.90, 9.80 });

        probarEscenario("3. Arreglo con Empates / Tiempos Duplicados (Estabilidad)",
                new double[] { 10.15, 9.98, 10.05, 9.98, 9.85, 10.20, 9.85 });
    }

    private static void probarEscenario(String titulo, double[] datos) {
        System.out.println("\n>>> " + titulo);
        System.out.println("Entrada: " + Arrays.toString(datos));

        ResultadoOrdenamiento resBurbuja = ClasificadorTiempos.burbuja(datos);
        ResultadoOrdenamiento resSeleccion = ClasificadorTiempos.seleccion(datos);

        System.out.printf("  - Bubble Sort    -> Comp: %2d | Swaps: %2d | Ordenado: %s%n",
                resBurbuja.getComparaciones(), resBurbuja.getIntercambios(),
                Arrays.toString(resBurbuja.getTiemposOrdenados()));
        System.out.printf("  - Selection Sort -> Comp: %2d | Swaps: %2d | Ordenado: %s%n",
                resSeleccion.getComparaciones(), resSeleccion.getIntercambios(),
                Arrays.toString(resSeleccion.getTiemposOrdenados()));
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
