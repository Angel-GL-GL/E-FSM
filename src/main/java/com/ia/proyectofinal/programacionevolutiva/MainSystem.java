package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MainSystem {

    public MainSystem(ViewSystem viewSystem) {
        viewSystem.runButton.addActionListener((e) -> { //Botón de iniciar
            EvolutionAlgorithm geneticAlgorithm = new EvolutionAlgorithm(); //Generador
            FSMGenerator fSMGenerator = new FSMGenerator(); //Generador de FSM

            //Obtenemos los datos
            String input[] = viewSystem.getInput().split(",");
            String output[] = viewSystem.getOutput().split(",");
            int seed = viewSystem.getSeed();
            int elite = viewSystem.getElite();
            int numMaxStates = viewSystem.getMaxStates();
            int population = viewSystem.getPopulation();
            int numberInputs = viewSystem.getNumberInputs();
            int numberOutputs = viewSystem.getNumberOutputs();
            int max = 0;
            int maxGen = viewSystem.getMaxGeneration();
            int minGen = viewSystem.getMinGeneration();
            int genNoImprovement = viewSystem.getNoImprovement();
            int maxEval = viewSystem.getMaxEvaluation();
            int fitness = viewSystem.getFitness();
            boolean counterEvaluationCheck = viewSystem.counterEvaluationCheck.isSelected();
            boolean sortCheck = viewSystem.sortCheck.isSelected();
            boolean timeCheck = viewSystem.timeCheck.isSelected();

            int generation = 1;
            int noImprovement = 0;
            int lastValue = 0;
            int evals = 0;

            long initialTime = 0;
            long totalTime = 0;

            //Generador de aleatorios
            Random random = fSMGenerator.createGenerator(seed);
            //Generamos a la población
            ArrayList<FSM> populationElements = geneticAlgorithm.generarionPhase(random, population, numMaxStates, numberInputs, numberOutputs, input, output);
            do {
                System.out.println("Generación " + generation);
                //Activar opción de mostrar tiempo en cada generación
                initialTime = System.currentTimeMillis();

                //Obtenemos maxmin
                max = fSMGenerator.getMaxMin(populationElements, max);

                //Mutación
                geneticAlgorithm.mutationPhase(populationElements, random, input, output, numMaxStates);

                //Obtenemos su valor segun la función de coste
                if (generation == 1) {
                    geneticAlgorithm.evaluationPhase(populationElements, input, output);
                    evals += populationElements.size(); //Cuantas evaluaciones ha realizado
                } else {
                    //Partimos para solo evaluar la parte faltante
                    ArrayList<FSM> helper = new ArrayList<>(populationElements.subList(populationElements.size() / 2, populationElements.size()));
                    geneticAlgorithm.evaluationPhase(helper, input, output);
                    evals += populationElements.subList(populationElements.size() / 2, populationElements.size()).size(); //Cuantas evaluaciones ha realizado
                }
                
                Collections.sort(populationElements); //Ordenamos

                if (counterEvaluationCheck) {
                    System.out.println("Evaluaciones: " + populationElements.size());
                }

                if (sortCheck) {
                    System.out.println("Población: ");
                    int i = 1;
                    for (FSM fsm : populationElements) {
                        System.out.println(i + "°");
                        fsm.printFSM();
                        System.out.println("");
                        i++;
                    }
                }

                //Seleccionamos los mejores
                populationElements = geneticAlgorithm.selectionPhase(populationElements, elite);

                long finalTime = System.currentTimeMillis() - initialTime;
                totalTime+=finalTime;
                //Imprimir tiempo
                if (timeCheck) {
                    System.out.println("Tiempo transcurrido: " + finalTime + " milisegundos");
                }

                if (generation == 1) { //Primer generación
                    noImprovement = 0;
                    lastValue = populationElements.get(0).getValue();
                } else {
                    if (lastValue == populationElements.get(0).getValue()) {
                        noImprovement++;
                    } else {
                        lastValue = populationElements.get(0).getValue();
                        noImprovement = 0;
                    }
                }

                System.out.println("Tamaño de la población: " + populationElements.size());
                System.out.println("Evaluaciones totales: " + evals);
                System.out.println("Mejor evaluación hasta el momento: " + lastValue);
                System.out.println(noImprovement + " generaciones con " + lastValue + " como valor para la mejor FSM");
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<==============================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n\n");

                generation++;
//                
            } while (populationElements.get(0).getValue() > fitness && (generation <= minGen || (generation <= maxGen && noImprovement <= genNoImprovement && evals <= maxEval)));

            if(populationElements.get(0).getValue() <= fitness) System.out.println("Se alcanzó el objetivo.");
            else if(generation > maxGen) System.out.println("No se alcanzó el objetivo, se superó el límite de generaciones.");
            else if(noImprovement > genNoImprovement) System.out.println("No se alcanzó el objetivo, se superó el límite de generaciones sin mejora.");
            else if(evals > maxEval) System.out.println("No se alcanzó el objetivo, se superó el límite de evaluaciones.");
            
            System.out.println("Tiempo transcurrido: " + totalTime + " milisegundos");
            System.out.println("FSM con mejor puntaje: ");
            System.out.println("Valor: " + lastValue);
            System.out.println("Estado Inicial: " + populationElements.get(0).getInitialState().getName());

            printResult(populationElements.get(0), input, output);

        });
    }

    public void printResult(FSM fsm, String i[], String o[]) {
        DrawFSM drawFSM = new DrawFSM();
        //va a ser true cuando exista un "match"
        boolean flag = false;
        //HashMap de las salidas
        HashMap<InputCoords, String> outputHM = fsm.getOutputHM();
        //HashMap de los estados
        HashMap<InputCoords, States> nextStateHM = fsm.getNextStateHM();

        //Las combinaciones de entradas y estados
        ArrayList<InputCoords> ics = fsm.getIc();
        //Estados
        ArrayList<States> states = new ArrayList<>(Arrays.asList(fsm.getStates()));

        //Control del array
        int control = 0; //para array de entradas y salidas

        States state = fsm.getInitialState();
        do {
            //Obtenemos el indice donde se encuentra el estado
            int index = states.indexOf(state);
            //Limites inferiores y superiores para disminuir los elementos explorados en el for
            int lowerLimit = index * fsm.getInputs().length;
            int upperLimit = (index + 1) * fsm.getInputs().length;

            flag = false; //Asi arranca el for

            //For reducido a solo los elementos donde tienen ese estado
            for (int j = lowerLimit; !flag && j < ics.size() && j < upperLimit && j >= 0; j++) {
                InputCoords ic = ics.get(j);
                if (outputHM.get(ic).equals(o[control]) && ic.getInput().equals(i[control])) { //La entrada y el estado tienen la salida buscada
                    flag = true; // rompemos el for
                    state = nextStateHM.get(ics.get(j));
                }
            }

            control++; //aumentamos el indice para la entrada y salida
        } while (flag && control < o.length && control < i.length);

        if (flag) {
            System.out.println("O");

        } else {
            System.out.println("X");
        }

        fsm.printTable();
        drawFSM.Draw(fsm);

    }
}
