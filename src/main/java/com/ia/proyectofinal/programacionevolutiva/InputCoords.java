package com.ia.proyectofinal.programacionevolutiva;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Clase modelo de la combinación de estados y entradas
//para obtener la salida y el siguiente estado
public class InputCoords {
    private States state;
    private String input;
    
    public void printIC(){
        System.out.print(state.getName() + "x" + input);
    }
}
