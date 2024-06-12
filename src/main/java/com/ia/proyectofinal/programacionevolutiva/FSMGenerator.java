package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class FSMGenerator {

    //Los estados de la FSM (Los estados son de 1 hasta n)
    public States[] setFSMStates(int lenght) {
        States states[] = new States[lenght];
        for (int i = 0; i < lenght; i++) {
            String stateName = i + "";
            if (i < 10) {
                stateName = "0" + i + "";
            }
            states[i] = new States();
            states[i].setName(stateName);
        }
        return states;
    }

    //Las entradas/salidas de la FSM (Son las de la cadena ingresada)
    //Type 0 es salida, Type 1 es entrada
    public String[] setFSMIO(int lenght, String io[], int type) {
        ArrayList<String> cleanedIO = new ArrayList<>();
        HashSet<String> clonedElements = new HashSet<>();
        for (String readIO : io) {
            if (type == 0 && readIO.equals(" ")) {
                readIO = " ";
            }
            if (!clonedElements.contains(readIO)) {
                cleanedIO.add(readIO);
                clonedElements.add(readIO);
            }
        }
        return cleanedIO.toArray(new String[0]);
    }

    //Obtenemos las combinaciones entre entradas y estados
    public ArrayList<InputCoords> setALInputCoords(String x[], States y[]) {
        ArrayList<InputCoords> arrayList = new ArrayList<>();
        for (States readState : y) {
            for (String readInput : x) {
                InputCoords ic = new InputCoords();
                ic.setInput(readInput);
                ic.setState(readState);
                arrayList.add(ic);
            }
        }

        return arrayList;
    }

    //Obtenemos las funciones de estado siguiente o las funciones de salida de acuerdo con la entrada, se define con el tipo
    //0== estados, 1==salidas
    public <V> HashMap<InputCoords, V> setOSFunction(V v[], ArrayList<InputCoords> icAL, int lenght, Random random) {
        HashMap<InputCoords, V> hashMap = new HashMap<>();
            icAL.forEach(ic -> {
                hashMap.put(ic,v[random.nextInt(lenght)]);
            });

        return hashMap;
    }

    //Obtenemos el estado inicial
    public States selectInitialState(States states[], Random random, int lenght) {
        return states[random.nextInt(lenght)];
    }

    //Creamos el generador de números aleatorios
    public Random createGenerator(int seed) {
        return new Random(seed);
    }

    public int getMaxMin(ArrayList<FSM> fsms, int max) {
        for (FSM fsm : fsms) {
            if (fsm.getStates().length > max) {
                max = fsm.getStates().length;
            }
        }

        return max;
    }

    //Se ingresa el objeto random, tamaño de población, la cantidad máxima de estados, número de entradas y salidas, entradas y salidas
    //Se obtiene una FSM
    public FSM createFSM(Random random, int population, int maxStates, int nI, int nO, String i[], String o[]) {
        FSM fsm = new FSM();

        int numStates = random.nextInt(maxStates) + 1;
        String inputs[] = setFSMIO(nI, i, 1);
        String outputs[] = setFSMIO(nO, o, 0);
        States states[] = setFSMStates(numStates);
        ArrayList<InputCoords> icAL = setALInputCoords(inputs, states);
        HashMap<InputCoords, States> sFunctionHM = setOSFunction(states, icAL, states.length, random/*, 0*/);
        HashMap<InputCoords, String> oFunctionHM = setOSFunction(outputs, icAL, outputs.length, random/*, 1*/);
        States initialS = selectInitialState(states, random, states.length);

        fsm.setInputs(inputs);
        fsm.setOutputs(outputs);
        fsm.setStates(states);
        fsm.setNextStateHM(sFunctionHM);
        fsm.setOutputHM(oFunctionHM);
        fsm.setIc(icAL);
        fsm.setInitialState(initialS);
        fsm.setValue(0);
        fsm.setMutation("ninguna");
                
        return fsm;
    }

}
