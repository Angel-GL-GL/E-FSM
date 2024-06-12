package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

class MutationOperations {

    public FSM mutateFSM(FSM fsmOriginal, int type, Random random, int maxState, String i[], String o[]) {
        FSM fsm = fsmOriginal.copyFSM(fsmOriginal);
        switch (type) { //Tipo de mutación
            case 0 -> {
                return fsm;
            }
            case 1 -> {
                if (fsmOriginal.getOutputHM().size() >= 1) {
                    fsm = changeOutput(fsm, random);
                } else {
                    fsm = mutateFSM(fsm, random.nextInt(4), random, maxState, i, o);
                }

            }
            case 2 -> {
                if (fsmOriginal.getNextStateHM().size() >= 1) {
                    fsm = changeTransition(fsm, random);
                } else {
                    fsm = mutateFSM(fsm, random.nextInt(4), random, maxState, i, o);
                }
            }
            case 3 -> {
                fsm = changeInitialState(fsm, random);
            }
        }

        //HashMap de las salidas
        HashMap<InputCoords, String> outputHM = fsm.getOutputHM();
        //Las combinaciones de entradas y estados
        ArrayList<InputCoords> ics = fsm.getIc();
        HashMap<InputCoords, States> nextStateHM = fsm.getNextStateHM();

        //Control del array
        int control = 0; //para array de entradas y salidas
        //va a ser true cuando exista un "match"
        boolean flag;
        States state = fsm.getInitialState();
        //Obtenemos el indice donde se encuentra el estado
        int index = new ArrayList<>(Arrays.asList(fsm.getStates())).indexOf(state);
        //Limites inferiores y superiores para disminuir los elementos explorados en el for
        int lowerLimit = index * fsm.getInputs().length;
        int upperLimit = (index + 1) * fsm.getInputs().length;

        flag = false; //Asi arranca el for

        //For reducido a solo los elementos donde tienen ese estado
        for (int j = lowerLimit; !flag && j < ics.size() && j < upperLimit && j >= 0; j++) {
            InputCoords ic = ics.get(j);
            if (outputHM.get(ic).equals(o[control]) && ic.getInput().equals(i[control])) { //La entrada y el estado tienen la salida buscada
                flag = true; // rompemos el for
            }
        }
        
        if (!flag) fsm = mutateFSM(fsm, random.nextInt(4), random, maxState, i, o);
                
        return fsm;
    }

    //Cambiamos salida
    public FSM changeOutput(FSM fsmOriginal, Random random) {
        FSM fsm = fsmOriginal.copyFSM(fsmOriginal);
        int index = random.nextInt(fsm.getOutputHM().size());
        int newIndex = random.nextInt(fsm.getOutputs().length);
        InputCoords key = fsm.getIc().get(index);
        String newOutput = fsm.getOutputs()[newIndex];
        fsm.getOutputHM().replace(key, newOutput);
        fsm.setMutation("Cambiar salida");
        return fsm;
    }

    //Cambiamos transición
    public FSM changeTransition(FSM fsmOriginal, Random random) {
        FSM fsm = fsmOriginal.copyFSM(fsmOriginal);
        int index = random.nextInt(fsm.getIc().size());
        int newIndex = random.nextInt(fsm.getStates().length);
        InputCoords key = fsm.getIc().get(index);
        States newState = fsm.getStates()[newIndex];
        HashMap<InputCoords, States> hm = fsm.getNextStateHM();
        hm.replace(key, newState);
        fsm.setNextStateHM(hm);
        fsm.setMutation("Cambiar transición");
        return fsm;
    }

    //Cambiamos estado inicial
    public FSM changeInitialState(FSM fsmOriginal, Random random) {
        FSM fsm = fsmOriginal.copyFSM(fsmOriginal);
        int index = random.nextInt(fsm.getStates().length);
        fsm.setInitialState(fsm.getStates()[index]);
        fsm.setMutation("Cambiar estado inicial");
        return fsm;
    }
}
