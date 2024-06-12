package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EvolutionAlgorithm {

    //Fase de generación, se ingresa el objeto random, tamaño de población, la cantidad máxima de estados, número de entradas y salidas, entradas y salidas
    //La salida es la lista de todas las fsm generadas
    public ArrayList<FSM> generarionPhase(Random random, int population, int maxStates, int nI, int nO, String i[], String o[]) {
        ArrayList<FSM> fsmList = new ArrayList<>();
        for (int j = 0; j < population; j++) {
            FSMGenerator fsmg = new FSMGenerator();
            FSM fsm = fsmg.createFSM(random, population, maxStates, nI, nO, i, o);
            fsmList.add(fsm);
        }
        return fsmList;
    }

    //Funcion de coste, se ingresa la población, la entrada, la salida, el generador de aleatorios, la bandera 
    //que indica si tendran puntos extra las fsm con pocos estados y el max previamente obtenido
    //Se obtiene la lista con los nuevos valores de coste
    public void evaluationPhase(ArrayList<FSM> fsms, String i[], String o[]) {
        fsms.forEach(fsm -> {//Uno por uno de los elementos
            fsm.setValue(0);////////////////////////////////////////////////////////////////////////////////////////////////////////7
            //pasamos los estados de un array a un arrayList
            ArrayList<States> states = new ArrayList<>(Arrays.asList(fsm.getStates()));
            if (states.contains(fsm.getInitialState())) {//El estado inicial pertenece al conjunto de estados
                EvaluationOperations evOp = new EvaluationOperations();
                fsm.setValue(evOp.evaluateFSM(fsm, states, i, o));
            } else {
                fsm.setValue(fsm.getValue() + 1); //el estado inicial no pertenecia al conjunto de estados
            }
        });
    }
    //Mutación, necesita el arraylist de fsm, el random, las entradas/salidas y el máximo de estados
    public void mutationPhase(ArrayList<FSM> fsms, Random random, String i[], String o[], int maxStates){
        ArrayList<FSM> fsmsMutated = new ArrayList<>();
        fsms.stream().map(fsm -> {
            MutationOperations mutationOperations = new MutationOperations();
            int type = random.nextInt(4); //Tipo de mutación
            FSM fsmMutated = mutationOperations.mutateFSM(fsm, type, random, maxStates, i, o);
            return fsmMutated; //Mutada
        }).map(fsmMutated -> {
            fsmMutated.setValue(0); // valor de 0
            return fsmMutated;
        }).forEachOrdered(fsmMutated -> {
            fsmsMutated.add(fsmMutated); //Lista de mutaciones
        });
        fsms.addAll(fsmsMutated);//comibinamos
    }
    
    //Selección, necesitamos el arraylist y el número de selecciones
    public ArrayList<FSM> selectionPhase(ArrayList<FSM> fsms, int range){
        ArrayList<FSM> bestFSMS = new ArrayList<>();
        for(int i = 0; i<range && i<fsms.size(); i++){
            bestFSMS.add(fsms.get(i));
        }
        return bestFSMS;
    }
}
