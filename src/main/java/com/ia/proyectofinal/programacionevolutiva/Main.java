package com.ia.proyectofinal.programacionevolutiva;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        ViewSystem viewSystem = new ViewSystem(); //Inicializamos la vista
        viewSystem.startView();
        //Iniciamos con el funcionamiento
        MainSystem mainSystem = new MainSystem(viewSystem);
    }
}
