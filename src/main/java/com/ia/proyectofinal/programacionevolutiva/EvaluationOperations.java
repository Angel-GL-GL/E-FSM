package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluationOperations {

    //Se evalua la fsm, se ingresa la fsm, los estados que tiene, la entrada y salida ingresadas,  la bandera 
    //que indica si tendran puntos extra las fsm con pocos estados y el max previamente obtenido
    public int evaluateFSM(FSM fsm, ArrayList<States> states, String i[], String o[]) {
        int eval = 0;
        //para guardar los indices y obtener el siguiente estado
        int save = 0;
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
                    save = j; //guardamos el indice
                }
            }

            if (!flag) { //la nueva flag dice que no hay match
                if(control == 0){ 
                    eval = 100;
                }else{ 
                    eval++;
                } //sumamos uno
            } else {
                //restamos uno al valor
                eval--;
                //Recibimos el siguiente estado
                state = nextStateHM.get(ics.get(save));
                //Si esta habilitada la opción vamos a agregar más puntos
            }
            control++; //aumentamos el indice para la entrada y salida
        } while (flag && control < o.length && control < i.length);
        //la primer bandera se debe de conservar como true
        // control no puede superar los límites del arreglo de entrada y salida
        return eval;
    }
}
