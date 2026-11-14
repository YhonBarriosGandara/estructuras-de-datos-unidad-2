package com.udc.busqueda;

/**
 * Encapsula los resultados y métricas de una operación de búsqueda sobre arreglos.
 */
public class ResultadoBusqueda {
    private final int indice;
    private final int comparaciones;
    private final long tiempoNanos;

    public ResultadoBusqueda(int indice, int comparaciones, long tiempoNanos) {
        this.indice = indice;
        this.comparaciones = comparaciones;
        this.tiempoNanos = tiempoNanos;
    }

    public int getIndice() {
        return indice;
    }

    public int getComparaciones() {
        return comparaciones;
    }

    public long getTiempoNanos() {
        return tiempoNanos;
    }

    public boolean isEncontrado() {
        return indice != -1;
    }

    @Override
    public String toString() {
        return String.format("Indice: %d | Comparaciones: %d | Tiempo: %d ns",
                indice, comparaciones, tiempoNanos);
    }
}
