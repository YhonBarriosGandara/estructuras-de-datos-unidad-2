package com.udc.ordenamiento;

/**
 * Encapsula las métricas de rendimiento y el arreglo resultante
 * tras la ejecución de un algoritmo de ordenamiento deportivo.
 */
public final class ResultadoOrdenamiento {

    private final double[] tiemposOrdenados;
    private final long comparaciones;
    private final long intercambios;
    private final long tiempoNanos;

    public ResultadoOrdenamiento(double[] tiemposOrdenados, long comparaciones, long intercambios, long tiempoNanos) {
        this.tiemposOrdenados = tiemposOrdenados;
        this.comparaciones = comparaciones;
        this.intercambios = intercambios;
        this.tiempoNanos = tiempoNanos;
    }

    public double[] getTiemposOrdenados() {
        return tiemposOrdenados;
    }

    public long getComparaciones() {
        return comparaciones;
    }

    public long getIntercambios() {
        return intercambios;
    }

    public long getTiempoNanos() {
        return tiempoNanos;
    }

    public double getTiempoMilisegundos() {
        return tiempoNanos / 1_000_000.0;
    }
}
