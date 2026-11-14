package com.udc.busqueda;

/**
 * Algoritmos manuales de búsqueda sobre arreglos de números de identificación.
 */
public final class BuscadorIdentificaciones {

    private BuscadorIdentificaciones() {
        // Constructor privado para clase utilitaria
    }

    /**
     * Búsqueda lineal tradicional que recorre secuencialmente el arreglo.
     * Contabiliza las comparaciones de clave realizadas y el tiempo total.
     *
     * @param arreglo arreglo con números de identificación
     * @param objetivo número de identificación a localizar
     * @return resultado con el índice (o -1), comparaciones y tiempo en nanosegundos
     */
    public static ResultadoBusqueda busquedaLinealTradicional(int[] arreglo, int objetivo) {
        if (arreglo == null || arreglo.length == 0) {
            return new ResultadoBusqueda(-1, 0, 0);
        }

        int comparaciones = 0;
        long inicioTiempo = System.nanoTime();

        for (int i = 0; i < arreglo.length; i++) {
            comparaciones++;
            if (arreglo[i] == objetivo) {
                long finTiempo = System.nanoTime();
                long duracionNanos = finTiempo - inicioTiempo;
                return new ResultadoBusqueda(i, comparaciones, duracionNanos);
            }
        }

        long finTiempo = System.nanoTime();
        long duracionNanos = finTiempo - inicioTiempo;
        return new ResultadoBusqueda(-1, comparaciones, duracionNanos);
    }

    /**
     * Búsqueda lineal optimizada mediante técnica de centinela.
     * Reduce las comprobaciones de desbordamiento en cada iteración del bucle.
     *
     * @param arreglo arreglo con números de identificación
     * @param objetivo número de identificación a localizar
     * @return resultado con el índice (o -1), comparaciones y tiempo en nanosegundos
     */
    public static ResultadoBusqueda busquedaLinealCentinela(int[] arreglo, int objetivo) {
        if (arreglo == null || arreglo.length == 0) {
            return new ResultadoBusqueda(-1, 0, 0);
        }

        int totalElementos = arreglo.length;
        int comparaciones = 0;
        long inicioTiempo = System.nanoTime();

        // 1. Guardar el último elemento y colocar el centinela
        int ultimoValor = arreglo[totalElementos - 1];
        arreglo[totalElementos - 1] = objetivo;

        // 2. Bucle optimizado sin chequeo de límite de índice
        int i = 0;
        while (arreglo[i] != objetivo) {
            comparaciones++;
            i++;
        }
        comparaciones++; // Comparación que finaliza el bucle

        // 3. Restaurar inmediatamente el valor original del arreglo
        arreglo[totalElementos - 1] = ultimoValor;

        long finTiempo = System.nanoTime();
        long duracionNanos = finTiempo - inicioTiempo;

        // 4. Evaluar si se encontró antes del centinela o si el último elemento era el objetivo
        if (i < totalElementos - 1 || ultimoValor == objetivo) {
            return new ResultadoBusqueda(i, comparaciones, duracionNanos);
        }

        return new ResultadoBusqueda(-1, comparaciones, duracionNanos);
    }
}
