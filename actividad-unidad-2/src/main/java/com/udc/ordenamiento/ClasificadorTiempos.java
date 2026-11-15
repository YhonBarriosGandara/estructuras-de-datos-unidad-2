package com.udc.ordenamiento;

/**
 * Algoritmos manuales de ordenamiento aplicados a registros de tiempos deportivos.
 */
public final class ClasificadorTiempos {

    private ClasificadorTiempos() {
        // Constructor privado para clase utilitaria
    }

    /**
     * Ordena registros de tiempos de menor a mayor usando Bubble Sort con bandera.
     * Contabiliza las comparaciones e intercambios realizados.
     *
     * @param tiempos arreglo con los tiempos registrados en la competencia
     * @return resultado inmutable con los tiempos ordenados y las métricas
     */
    public static ResultadoOrdenamiento burbuja(double[] tiempos) {
        if (tiempos == null || tiempos.length == 0) {
            return new ResultadoOrdenamiento(new double[0], 0, 0, 0);
        }

        double[] copiaTiempos = tiempos.clone();
        int totalElementos = copiaTiempos.length;
        long comparaciones = 0;
        long intercambios = 0;
        long inicioTiempo = System.nanoTime();

        boolean huboIntercambio;

        for (int i = 0; i < totalElementos - 1; i++) {
            huboIntercambio = false;

            for (int j = 0; j < totalElementos - 1 - i; j++) {
                comparaciones++;
                if (copiaTiempos[j] > copiaTiempos[j + 1]) {
                    double tiempoAuxiliar = copiaTiempos[j];
                    copiaTiempos[j] = copiaTiempos[j + 1];
                    copiaTiempos[j + 1] = tiempoAuxiliar;
                    intercambios++;
                    huboIntercambio = true;
                }
            }

            if (!huboIntercambio) {
                break;
            }
        }

        long finTiempo = System.nanoTime();
        long duracionNanos = finTiempo - inicioTiempo;

        return new ResultadoOrdenamiento(copiaTiempos, comparaciones, intercambios, duracionNanos);
    }
}
