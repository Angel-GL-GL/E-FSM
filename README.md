# E-FSM
 Computational system developed in Java using evolutionary programming for the reduction of finite state machines

<p align="center">
  <img src="adn.png" alt="Imagen"><br>
  Img source: https://www.flaticon.es/iconos-gratis/gene <br>
  <b>Gene iconos creados por Freepik - Flaticon</b>
</p>

## Stack and libraries 
- Java
  - `java`
  - `version 16`
- Maven
  - `mvn`
  - `version 4.0`
- JTattoo
  - `com.jtattoo`
  - `version 1.6.13`
- Lombok
  - `org.projectlombok`
  - `version 1.18.26`
- GraphStream
  - `org.graphstream`
  - `version 2.0`

## How to Start
Verify that you have the required java version installed.
```shell
java -version
```

Verify that you have the required maven version installed.
```shell
mvn -version
```

In case you do not have any of the above mentioned, please make the process of indicated in their respective official pages.

Access from the command line to the root of the project and compile the project.
```shell
mvn clean compile
```

## Inputs
1. General
   - **Semilla aleatoria:** An unsigned integer used to initialize the pseudo-random number generator using a Random object.
   - **Tamaño de la población:** An unsigned integer used to define the initial number of FSM to be generated at system startup.
   - **Cantidad k a seleccionar:** An unsigned integer used to define the number of FSM to be selected according to the selection method, e.g., Elitism.
2. Configuración de salida
   - **Muestra el número de evaluaciones:** The number of evaluations performed in each generation will be printed.
   - **Muestra el tiempo:** It will print how much time was used for each of the generations.
   - **Muestra la población ordenada de mejor a peor:** The definitions (sextuples) of each of the FSM of each generation will be printed, where they will be ordered according to the value obtained in the evaluation phase.
3. Criterio de parada.
   - **Número máximo de generaciones:** This is the maximum number of generations; if this number is exceeded, the system will stop.
   - **Número de generaciones sin mejora:** This is the maximum number of generations without improvement, in case this number is exceeded the system will stop.
   - **Número mínimo de generaciones:** This is the minimum number of generations, this is the second generation with the highest priority, since the system will continue to operate, even if the two criteria mentioned above or the following one are met.
   - **Número máximo de evaluaciones:** It is the maximum number of evaluations that are performed during the execution of the system, this encompasses each of the generations.
   - **Objetivo a alcanzar:** We must add a negative score, which must be reached by at least one of the FSM for the system to finish the execution.
4. Identificación de sistemas.
   - **Número de símbolos de entrada:** It is the cardinality of the set of input symbols.
   - **Número de símbolos de salida:** It is the cardinality of the set of output symbols.
   - **Número máximo de estados:** This is the maximum number of states that one of the generated FSMs has.
   - **Cadena de entrada:** It is the string formed by elements of the set of input symbols separated by commas, which will be used as one of the references to evaluate each FSM.
   - **Cadena de salida:** It is the string formed by elements of the output symbol set separated by commas, which will be used to evaluate each of the FSM. In case you want to add the null character (λ) you must leave a blank space.


Once a generation is found that reaches the target to be reached, a warning will be displayed giving us data such as the elapsed time, the cost value and its initial state, as well as the transitions table of the resulting FMS. A screen will also be displayed showing the resulting FMS graphically highlighting the final state.
