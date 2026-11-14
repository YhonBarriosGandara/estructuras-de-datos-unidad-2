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
                return new ResultadoBusqueda(i, comparaciones, (finTiempo - inicioTiempo));
            }
        }

        long finTiempo = System.nanoTime();
        return new ResultadoBusqueda(-1, comparaciones, (finTiempo - inicioTiempo));
    }
}
