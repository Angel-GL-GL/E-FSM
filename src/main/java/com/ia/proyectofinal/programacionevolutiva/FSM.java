package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Clase que define una FSM
public class FSM implements Comparable<FSM> {

    private String[] inputs;
    private String[] outputs;
    private States[] states;
    private States initialState;
    private ArrayList<InputCoords> ic; //Para recorrer los hashMap
    private HashMap<InputCoords, States> nextStateHM;
    private HashMap<InputCoords, String> outputHM;
    private int value;
    private String mutation;

    public void printTable() { //Imprimimos la FSM como tabla

        System.out.println("\nFSM:");
        System.out.print("__||");
        for (String input : inputs) {
            System.out.print("   " + input + "   |");
        }
        System.out.println(" ");

        int i = 0;
        for (States state : states) {
            System.out.print(state.getName() + "||");
            for (String input : inputs) {
                System.out.print("[" + nextStateHM.get(ic.get(i)).getName() + "," + outputHM.get(ic.get(i)) + "] |");
                i++;
            }
            System.out.println(" ");
        }
        System.out.println("\n");
    }

    public void printFSM() { //Imprimimos la tabla según su definición (sextupla)
        System.out.print("{[ ");
        for (States state : states) {
            System.out.print(state.getName() + " ");
        }
        System.out.print("];[ ");
        for (String input : inputs) {
            System.out.print(input + " ");
        }
        System.out.print("];[ ");
        for (String output : outputs) {
            System.out.print(output + " ");
        }
        System.out.print("];" + initialState.getName() + ";\n[ ");
        int i = 0;
        for (States state : states) {
            for (String input : inputs) {
                System.out.print("(");
                ic.get(i).printIC();
                System.out.print(")->" + nextStateHM.get(ic.get(i)).getName() + " ");
                i++;
            }
        }
        System.out.print("];;\n[ ");
        i = 0;
        for (States state : states) {
            for (String input : inputs) {
                System.out.print("(");
                ic.get(i).printIC();
                System.out.print(")->" + outputHM.get(ic.get(i)) + " ");
                i++;
            }
        }
        System.out.println("]}");
    }

    public FSM copyFSM(FSM fsm) {
        FSM fsm2 = new FSM();

        fsm2.setIc(fsm.getIc());
        fsm2.setInitialState(fsm.getInitialState());
        fsm2.setInputs(fsm.getInputs());
        fsm2.setNextStateHM(fsm.getNextStateHM());
        fsm2.setOutputHM(fsm.getOutputHM());
        fsm2.setOutputs(fsm.getOutputs());
        fsm2.setStates(fsm.getStates());
        fsm2.setValue(0);
        fsm2.setMutation(fsm.getMutation());

        return fsm2;
    }

    @Override
    public int compareTo(FSM fsm) {
        int valueComparison = Integer.compare(this.value, fsm.getValue());

        if (valueComparison == 0) {
            int statesLengthComparison = Integer.compare(this.states.length, fsm.getStates().length);
            return statesLengthComparison != 0 ? statesLengthComparison : valueComparison;
        }

        return valueComparison;
    }

}
