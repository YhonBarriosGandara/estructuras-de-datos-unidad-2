# Actividad de Aprendizaje — Unidad 2
## Ejercicio 4: Clasificación de Tiempos Deportivos (Algoritmos de Ordenamiento)

Este módulo implementa y evalúa de manera comparativa dos algoritmos clásicos de ordenamiento por comparación: **Bubble Sort (con bandera de optimización)** y **Selection Sort (Ordenamiento por Selección)**, aplicados a la clasificación ascendente de registros de tiempos en competencias de velocidad (ej. 100 metros planos) para la determinación oficial de clasificaciones y asignación del podio atlético (Oro, Plata y Bronce).

---

### 1. Descripción del Problema y Justificación

En las disciplinas atléticas de alta competencia, las diferencias entre posiciones finales se miden en centésimas o milésimas de segundo. Al registrar los tiempos de los participantes en un arreglo unidimensional de números reales (`double[]`), es imperativo transformar dicha secuencia no ordenada en un orden ascendente estricto:

$$\text{tiempo}_1 \le \text{tiempo}_2 \le \dots \le \text{tiempo}_n$$

El análisis algorítmico no solo evalúa el tiempo de reloj consumido, sino dos operaciones elementales críticas:
1. **Comparaciones:** Cuántas veces se evalúa la relación de orden entre pares de marcas deportivas.
2. **Intercambios / Movimientos de memoria (*Swaps*):** Cuántas escrituras y reubicaciones físicas de datos ocurren en la estructura. Esta métrica es vital cuando el costo de escritura es elevado o la memoria es restringida.

---

### 2. Algoritmos Implementados

Ambas técnicas fueron implementadas manualmente en la clase `ClasificadorTiempos`, garantizando la inmutabilidad de los datos de origen mediante la copia profunda previa de los arreglos.

#### A. Bubble Sort con Bandera de Corte Temprano (`ClasificadorTiempos.burbuja`)
- **Mecánica:** Recorre iterativamente el arreglo comparando pares adyacentes $(A[j], A[j+1])$. Si se encuentran en orden inverso ($A[j] > A[j+1]$), se intercambian.
- **Optimización implementada:** Incorpora una bandera booleana (`huboIntercambio`). Si durante una pasada completa no se produce ningún intercambio, el arreglo ya se encuentra ordenado y el ciclo termina anticipadamente.
- **Comportamiento Asintótico:**
  - **Mejor caso (arreglo ya ordenado):** $\Omega(n)$ comparaciones, $0$ intercambios.
  - **Caso promedio y Peor caso:** $O(n^2)$ comparaciones y $O(n^2)$ intercambios.

#### B. Selection Sort con Búsqueda Lineal de Mínimos (`ClasificadorTiempos.seleccion`)
- **Mecánica:** Divide conceptualmente el arreglo en una zona ordenada a la izquierda y una desordenada a la derecha. En cada iteración $i$, escanea el segmento desordenado para localizar la posición del menor valor y realiza **a lo sumo un único intercambio** para ubicarlo en la posición $i$.
- **Comportamiento Asintótico:**
  - **Comparaciones:** Siempre realiza exactamente $\frac{n(n - 1)}{2} = \Theta(n^2)$ comparaciones, independientemente del orden inicial.
  - **Movimientos (Intercambios):** A lo sumo $n - 1$ intercambios en el peor escenario, es decir, complejidad lineal de escritura $O(n)$.

---

### 3. Matriz Comparativa Teórica

| Criterio | Bubble Sort (Optimizado) | Selection Sort |
| :--- | :---: | :---: |
| **Complejidad Temporal (Mejor caso)** | $\Omega(n)$ | $\Theta(n^2)$ |
| **Complejidad Temporal (Promedio)** | $O(n^2)$ | $\Theta(n^2)$ |
| **Complejidad Temporal (Peor caso)** | $O(n^2)$ | $\Theta(n^2)$ |
| **Complejidad de Movimientos (Swaps)** | $O(n^2)$ | **$O(n)$ (a lo sumo $n-1$)** |
| **Memoria Auxiliar Espacial** | $O(1)$ *(In-place)* | $O(1)$ *(In-place)* |
| **Estabilidad** | **Estable** (preserva orden relativo de empates) | No estable por defecto |
| **Adaptabilidad** | Sí (detecta orden previo) | No (ejecuta comparaciones completas) |

---

### 4. Estructura de la Solución

```text
actividad-unidad-2/
├── pom.xml                                     # Configuración de compilación Maven (Java 17)
├── README.md                                   # Documentación técnica y análisis comparativo
└── src/
    └── main/
        └── java/
            └── com/
                └── udc/
                    └── ordenamiento/
                        ├── ClasificadorTiempos.java    # Lógica algorítmica manual de ordenamiento
                        ├── Main.java                   # Menú interactivo y suite de pruebas
                        └── ResultadoOrdenamiento.java  # DTO inmutable con métricas de ejecución
```

---

### 5. Batería Experimental y Resultados Observados

Al someter ambos algoritmos a los cuatro escenarios evaluados por el sistema de consola interactivo:

```text
1. Arreglo Estándar de Carrera (8 atletas)
   - Bubble Sort:    25 comparaciones | 10 intercambios
   - Selection Sort: 28 comparaciones |  4 intercambios
   * Veredicto: Selection Sort realizó 6 movimientos menos (reducción del 60%).

2. Arreglo Casi Ordenado (Mejor caso)
   - Bubble Sort:    11 comparaciones |  1 intercambio  (corte temprano activado)
   - Selection Sort: 21 comparaciones |  1 intercambio

3. Arreglo Invertido (Peor caso)
   - Bubble Sort:    21 comparaciones | 21 intercambios (saturación máxima de swaps)
   - Selection Sort: 21 comparaciones |  3 intercambios (eficiencia de escritura $O(n)$)

4. Arreglo con Empates / Tiempos Duplicados
   - Entrada:        [10.15, 9.98, 10.05, 9.98, 9.85, 10.20, 9.85]
   - Ambos ordenaron consistentemente a: [9.85, 9.85, 9.98, 9.98, 10.05, 10.15, 10.20]
```

---

### 6. Instrucciones de Compilación y Ejecución

#### Requisitos
- **JDK 17** o superior instalado.
- **Apache Maven 3.8+**.

#### Compilación limpia
```bash
mvn clean compile
```

#### Ejecución interactiva
```bash
java -cp target/classes com.udc.ordenamiento.Main
```
O mediante el plugin de Maven:
```bash
mvn exec:java -Dexec.mainClass="com.udc.ordenamiento.Main"
```
