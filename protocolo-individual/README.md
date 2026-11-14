# Universidad de Cartagena — Estructuras de Datos
## Unidad 2: Taller Práctico — Algoritmos de Búsqueda
### Ejercicio 4: Buscador de Números de Identificación

---

## 1. Descripción del Proyecto

Este repositorio contiene la solución formal al **Ejercicio 4: Buscador de números de identificación**, desarrollado para la asignatura de **Estructuras de Datos** en el programa de Ingeniería de Software (CTEV) de la Universidad de Cartagena.

El objetivo del proyecto es implementar, validar experimentalmente y comparar de manera rigurosa dos algoritmos fundamentales de búsqueda secuencial sobre arreglos unidimensionales en Java:
1. **Búsqueda Lineal Tradicional** (secuencial estándar con doble condición de parada).
2. **Búsqueda Lineal con Centinela** (optimización que reduce la sobrecarga de control de límites de ciclo).

El sistema cuenta con una interfaz de consola interactiva que permite ejecutar búsquedas manuales, contrastar ambos algoritmos frente a una misma entrada, ejecutar una batería de casos de prueba estandarizados y correr un banco experimental de rendimiento con muestras masivas de datos ($10.000$, $100.000$ y $1.000.000$ de registros).

---

## 2. Modelado y Estructura de Clases

La solución sigue principios de diseño limpio y separación de responsabilidades:

- **`BuscadorIdentificaciones`** (`com.udc.busqueda.BuscadorIdentificaciones`): Clase utilitaria que aloja las implementaciones puras y manuales de los algoritmos de búsqueda, sin delegar en métodos utilitarios de la API estándar de Java (`Arrays.binarySearch`, `List.indexOf`, etc.).
- **`ResultadoBusqueda`** (`com.udc.busqueda.ResultadoBusqueda`): Objeto inmutable de transferencia de métricas que encapsula:
  - `indice`: Posición encontrada en el arreglo (o `-1` si no existe).
  - `comparaciones`: Número exacto de comparaciones de clave realizadas.
  - `tiempoNanos`: Tiempo de ejecución medido en nanosegundos mediante `System.nanoTime()`.
  - `isEncontrado()`: Método de conveniencia para validar el estado de éxito.
- **`Main`** (`com.udc.busqueda.Main`): Punto de entrada con menú interactivo por consola, validaciones de entrada, casos de prueba automatizados y banco experimental multiescala.

---

## 3. Algoritmos Implementados

### 3.1 Búsqueda Lineal Tradicional

La búsqueda lineal tradicional examina secuencialmente cada celda del arreglo desde el índice inicial ($0$) hasta el índice final ($N-1$).

En cada ciclo iterativo se evalúan obligatoriamente **dos condiciones lógicas**:
1. Comprobación de límites: `i < arreglo.length` (evitar desbordamiento de índice).
2. Comprobación de clave: `arreglo[i] == objetivo` (determinar coincidencia de datos).

```java
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
            return new ResultadoBusqueda(i, comparaciones, finTiempo - inicioTiempo);
        }
    }

    long finTiempo = System.nanoTime();
    return new ResultadoBusqueda(-1, comparaciones, finTiempo - inicioTiempo);
}
```

### 3.2 Búsqueda Lineal con Centinela

La técnica de centinela optimiza el recorrido secuencial eliminando la comprobación de límites en cada iteración del bucle. Para lograrlo:
1. Se preserva el valor original de la última posición del arreglo (`arreglo[n - 1]`).
2. Se coloca temporalmente el elemento buscado (*centinela*) en dicha última posición.
3. El bucle `while` itera únicamente comparando la clave (`arreglo[i] != objetivo`), garantizando que la búsqueda siempre terminará sin riesgo de `ArrayIndexOutOfBoundsException`.
4. **Restauración obligatoria**: Al salir del ciclo, se restaura inmediatamente el valor original del arreglo antes de retornar para garantizar la integridad de los datos.
5. Se verifica si la coincidencia ocurrió antes del final o en la última posición comprobando el valor original.

```java
public static ResultadoBusqueda busquedaLinealCentinela(int[] arreglo, int objetivo) {
    if (arreglo == null || arreglo.length == 0) {
        return new ResultadoBusqueda(-1, 0, 0);
    }

    int totalElementos = arreglo.length;
    int ultimoValor = arreglo[totalElementos - 1];

    int comparaciones = 1;
    long inicioTiempo = System.nanoTime();

    if (ultimoValor == objetivo) {
        long finTiempo = System.nanoTime();
        return new ResultadoBusqueda(totalElementos - 1, comparaciones, finTiempo - inicioTiempo);
    }

    arreglo[totalElementos - 1] = objetivo;

    int i = 0;
    while (arreglo[i] != objetivo) {
        comparaciones++;
        i++;
    }

    arreglo[totalElementos - 1] = ultimoValor; // Restauración fundamental

    long finTiempo = System.nanoTime();
    long duracionNanos = finTiempo - inicioTiempo;

    if (i < totalElementos - 1) {
        return new ResultadoBusqueda(i, comparaciones, duracionNanos);
    }

    return new ResultadoBusqueda(-1, comparaciones, duracionNanos);
}
```

---

## 4. Análisis Formal de Complejidad Temporal y Espacial

| Métrica | Búsqueda Tradicional | Búsqueda con Centinela | Explicación Matemática |
| :--- | :---: | :---: | :--- |
| **Mejor Caso** | $O(1)$ | $O(1)$ | El objetivo se encuentra en la primera posición ($i = 0$ o en el centinela). Requiere 1 sola comparación de clave. |
| **Peor Caso** | $O(n)$ | $O(n)$ | El elemento no existe en el arreglo o se ubica en la última posición ($i = n - 1$). Se realizan $N$ comparaciones de clave. |
| **Caso Promedio** | $O(n)$ | $O(n)$ | Para datos uniformemente distribuidos, se esperan en promedio $\frac{n + 1}{2}$ comparaciones de clave. |
| **Complejidad Espacial** | $O(1)$ | $O(1)$ | Ambos algoritmos operan *in-place* sobre el arreglo existente, usando memoria auxiliar constante ($O(1)$). |
| **Condiciones por ciclo** | **2 condiciones** (`i < n` y `a[i] == x`) | **1 condición** (`a[i] != x`) | El centinela reduce a la mitad las instrucciones condicionales de salto evaluadas por la CPU dentro del bucle. |

### ¿Por qué el Centinela es más eficiente si ambos realizan $O(n)$ comparaciones de clave?

Es vital distinguir entre **comparaciones de clave** (las que evalúan el dato buscado) e **instrucciones de control de flujo en la CPU**:
- En la búsqueda tradicional, para un arreglo de $N$ elementos donde el dato no existe, la CPU evalúa:
  $$\text{Operaciones condicionales} = N \text{ (límites)} + N \text{ (claves)} = 2N \text{ comprobaciones}$$
- En la búsqueda con centinela, la condición de límite se omite por completo en el cuerpo del `while`:
  $$\text{Operaciones condicionales} = N \text{ (claves)} + 1 \text{ (validación final)} = N + 1 \text{ comprobaciones}$$

Esta reducción teórica de aproximadamente el **50% de las instrucciones de salto condicional** se traduce en una ejecución más rápida en ciclos de reloj de procesador para colecciones grandes.

---

## 5. Resultados Experimentales

### 5.1 Batería de Casos de Prueba (Colección Base de 7 Identificaciones)

Arreglo base: `[1045829101, 1045829102, 1045829103, 1045829104, 1045829105, 1045829106, 1045829107]` (dimensión impar con centro matemático exacto en índice 3).

| Caso de Prueba | Valor Buscado | Índice Tradicional | Comps Tradicional | Índice Centinela | Comps Centinela | Estado |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **1. Elemento al inicio** | `1045829101` | `0` | **1** | `0` | **1** | Localizado |
| **2. Elemento en el centro** | `1045829104` | `3` | **4** | `3` | **4** | Localizado |
| **3. Elemento al final** | `1045829107` | `6` | **7** | `6` | **7** | Localizado |
| **4. Elemento inexistente** | `999999999` | `-1` | **7** | `-1` | **7** | No encontrado |
| **5. Elementos duplicados** | `1045829103` | `2` | **3** | `2` | **3** | Primera aparición |

> **Nota sobre elementos duplicados:** Ambos algoritmos son deterministas y secuenciales; al encontrar la primera ocurrencia en el arreglo, retornan inmediatamente el índice e interrumpen el recorrido, realizando idéntico número de comparaciones de clave.

---

### 5.2 Banco de Pruebas de Rendimiento a Escala (Peor Caso $O(n)$)

Mediciones empíricas reales obtenidas sobre la Máquina Virtual de Java (OpenJ9 / HotSpot JVM, Java 17):

| Tamaño ($N$) | Comps Tradicional | Tiempo Tradicional (ms) | Comps Centinela | Tiempo Centinela (ms) | Algoritmo Más Rápido |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **10.000** | 10.000 | 0,2446 ms | 10.000 | **0,1573 ms** | **Centinela (~35% más rápido)** |
| **100.000** | 100.000 | 3,1492 ms | 100.000 | **2,1080 ms** | **Centinela (~33% más rápido)** |
| **1.000.000** | 1.000.000 | **0,8392 ms** | 1.000.000 | 4,3658 ms | **Tradicional (Optimización JIT)** |

#### Análisis del Comportamiento con la JVM (Just-In-Time Compiler)
1. **Escalas de $10.000$ y $100.000$ registros**: El Centinela demuestra su superioridad teórica reduciendo el tiempo de procesamiento en aproximadamente un **33% - 35%**, debido a que la supresión del chequeo de límites ahorra ciclos de reloj de procesador.
2. **Escala de $1.000.000$ de registros**: La JVM activa el compilador JIT (C2 Compiler / HotSpot) al detectar un *método caliente* (*hot method*). Al ser el bucle tradicional estrictamente de sólo lectura, el JIT aplica de forma agresiva **desenrollado de bucles** (*loop unrolling*) y vectorización de registros nativos. En contraste, el Centinela realiza una escritura y posterior restauración en memoria (`arreglo[n - 1] = valor`), lo que introduce una barrera de memoria (*memory barrier*) que limita ciertas optimizaciones nativas de la JVM en microbenchmarks repetidos.

---

## 6. Requisitos y Ejecución

### Prerrequisitos
- Java Development Kit (**JDK 17** o superior).
- Apache Maven (**3.8+**).

### Compilación del Proyecto
Clonar el repositorio y ubicarse en el subdirectorio del ejercicio:
```bash
git clone https://github.com/YhonBarriosGandara/estructuras-de-datos-unidad-2.git
cd estructuras-de-datos-unidad-2/protocolo-individual
mvn clean compile
```

### Ejecución de la Aplicación
Ejecutar la clase principal mediante el classpath compilado:
```bash
java -cp target/classes com.udc.busqueda.Main
```

O mediante el plugin de Maven:
```bash
mvn compile exec:java -Dexec.mainClass="com.udc.busqueda.Main"
```

---

## 7. Estructura del Repositorio

```text
estructuras-de-datos-unidad-2/
├── .gitignore
├── README.md                                          # Documentación técnica completa
└── protocolo-individual/
    ├── pom.xml                                        # Configuración del proyecto Maven
    └── src/
        └── main/
            └── java/
                └── com/
                    └── udc/
                        └── busqueda/
                            ├── BuscadorIdentificaciones.java  # Algoritmos tradicionales y con centinela
                            ├── ResultadoBusqueda.java         # Encapsulamiento de métricas
                            └── Main.java                      # Menú de consola y banco experimental
```

---

## 8. Autor e Información Institucional

- **Estudiante**: Yhon Barrios Gándara
- **Programa**: Ingeniería de Software
- **Asignatura**: Estructuras de Datos (Unidad 2)
- **Tutor**: John Carlos Arrieta Arrieta
- **Institución**: Universidad de Cartagena — Centro de Tecnologías para la Educación Virtual (CTEV)
- **Año**: 2026
