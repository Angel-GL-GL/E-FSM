package com.ia.proyectofinal.programacionevolutiva;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewSystem extends JFrame {

    private File file;
    private String pathImage;
    private Image icon;
    private boolean bFinalGenerationCheck;
    private Container container;
    private final JTextField randomSeedField, populationSizeField, eliteSizeField;
    private final JTextField maxGenerationField, generationNoImprovementField, minGenerationField;
    private final JTextField maxEvaluationField, fitnessReachesField, numberInputField, numberOutputField;
    private final JTextField maxStatesField, inputField, outputField;
    private JLabel resultLabel;
    public JButton runButton, helpButton;
    public JCheckBox counterEvaluationCheck, timeCheck;
    public JCheckBox sortCheck, finalGenerationCheck, reducctionCheck;

    public ViewSystem() { //Todos los componentes de la vista
        //img source: https://www.flaticon.es/iconos-gratis/gene ====> Gene iconos creados por Freepik - Flaticon
        file = new File("");
        pathImage = file.getAbsolutePath() + "\\" + "adn.png";
        icon = Toolkit.getDefaultToolkit().getImage(pathImage);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().height / 10,
                Toolkit.getDefaultToolkit().getScreenSize().height / 10,
                java.awt.Image.SCALE_SMOOTH));

        container = getContentPane();
        container.setLayout(new BorderLayout());

        //JLabel
        resultLabel = new JLabel();

        //JPanel
        JPanel headerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel generalPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JPanel stoppingPanel = new JPanel();
        JPanel systemIdentificationPanel = new JPanel();
        JPanel statesFiniteMachinePanel = new JPanel();
        JPanel helperHeaderMainPanels[] = new JPanel[2];
        JPanel helperGeneralPanel[] = new JPanel[3];
        JPanel helperStoppingPanel[] = new JPanel[5];
        JPanel helperSystemIdentificationPanel[] = new JPanel[5];

        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        stoppingPanel.setLayout(new BoxLayout(stoppingPanel, BoxLayout.Y_AXIS));

        systemIdentificationPanel.setLayout(new BoxLayout(systemIdentificationPanel, BoxLayout.Y_AXIS));
        statesFiniteMachinePanel.setLayout(new BorderLayout());

        //Helper panels
        helperHeaderMainPanels = generatePanels(helperHeaderMainPanels, helperHeaderMainPanels.length);
        helperGeneralPanel = generatePanels(helperGeneralPanel, helperGeneralPanel.length);
        helperStoppingPanel = generatePanels(helperStoppingPanel, helperStoppingPanel.length);
        helperSystemIdentificationPanel = generatePanels(helperSystemIdentificationPanel, helperSystemIdentificationPanel.length);

        //JTextField
        randomSeedField = new JTextField();
        populationSizeField = new JTextField();
        eliteSizeField = new JTextField();
        maxGenerationField = new JTextField();
        generationNoImprovementField = new JTextField();
        minGenerationField = new JTextField();
        maxEvaluationField = new JTextField();
        fitnessReachesField = new JTextField();
        numberInputField = new JTextField();
        numberOutputField = new JTextField();
        maxStatesField = new JTextField();
        inputField = new JTextField();
        outputField = new JTextField();

        //JButton
        runButton = new JButton("Iniciar");
        helpButton = new JButton("Ayuda");

        helpButton.addActionListener((e) -> {
            showHelp();
        });

        //CheckButton
        counterEvaluationCheck = new JCheckBox("Muestra el número de evaluaciones");
        timeCheck = new JCheckBox("Muestra el tiempo");
        sortCheck = new JCheckBox("Muestra la población ordenada de mejor a peor");
        finalGenerationCheck = new JCheckBox("Terminar generación usando Ctrl + C");
        reducctionCheck = new JCheckBox("Realización de maquinas");

        //JLabel
        JLabel label1 = new JLabel("General");
        JLabel label2 = new JLabel("Configuración de salida");
        JLabel label3 = new JLabel("Criterio de parada");
        JLabel label4 = new JLabel("Identificación de Sistemas");

        Font font1 = label1.getFont();
        Font font2 = label2.getFont();
        Font font3 = label1.getFont();
        Font font4 = label2.getFont();

        label1.setFont(font1.deriveFont(font1.getStyle() | Font.BOLD));
        label2.setFont(font2.deriveFont(font2.getStyle() | Font.BOLD));
        label3.setFont(font3.deriveFont(font3.getStyle() | Font.BOLD));
        label4.setFont(font4.deriveFont(font4.getStyle() | Font.BOLD));

        //Set Component
        helperGeneralPanel[0].add(new JLabel("Semilla aleatoria: "));
        helperGeneralPanel[0].add(randomSeedField);
        helperGeneralPanel[1].add(new JLabel("Tamaño de la población: "));
        helperGeneralPanel[1].add(populationSizeField);
        helperGeneralPanel[2].add(new JLabel("Cantidad k a seleccionar: "));
        helperGeneralPanel[2].add(eliteSizeField);

        generalPanel.add(label1);
        generalPanel.add(helperGeneralPanel[0]);
        generalPanel.add(helperGeneralPanel[1]);
        generalPanel.add(helperGeneralPanel[2]);

        outputPanel.add(label2);
        outputPanel.add(counterEvaluationCheck);
        outputPanel.add(timeCheck);
        outputPanel.add(sortCheck);

        helperStoppingPanel[0].add(new JLabel("Número máximo de generaciones: "));
        helperStoppingPanel[0].add(maxGenerationField);
        helperStoppingPanel[1].add(new JLabel("Número de generaciones sin mejora: "));
        helperStoppingPanel[1].add(generationNoImprovementField);
        helperStoppingPanel[2].add(new JLabel("Número mínimo de generaciones: "));
        helperStoppingPanel[2].add(minGenerationField);
        helperStoppingPanel[3].add(new JLabel("Número máximo de evaluaciones: "));
        helperStoppingPanel[3].add(maxEvaluationField);
        helperStoppingPanel[4].add(new JLabel("Objetivo a alcanzar: "));
        helperStoppingPanel[4].add(fitnessReachesField);

        stoppingPanel.add(label3);
        stoppingPanel.add(helperStoppingPanel[0]);
        stoppingPanel.add(helperStoppingPanel[1]);
        stoppingPanel.add(helperStoppingPanel[2]);
        stoppingPanel.add(helperStoppingPanel[3]);
        stoppingPanel.add(helperStoppingPanel[4]);
        
        helperSystemIdentificationPanel[0].add(new JLabel("Número de símbolos de entrada: "));
        helperSystemIdentificationPanel[0].add(numberInputField);
        helperSystemIdentificationPanel[1].add(new JLabel("Número de símbolos de salida: "));
        helperSystemIdentificationPanel[1].add(numberOutputField);
        helperSystemIdentificationPanel[2].add(new JLabel("Número máximo posible de estados: "));
        helperSystemIdentificationPanel[2].add(maxStatesField);
        helperSystemIdentificationPanel[3].add(new JLabel("Cadena de entrada (separadas por comas): "));
        helperSystemIdentificationPanel[3].add(inputField);
        helperSystemIdentificationPanel[4].add(new JLabel("Cadena de salida (separada por comas, λ = , ,): "));
        helperSystemIdentificationPanel[4].add(outputField);

        systemIdentificationPanel.add(label4);
        systemIdentificationPanel.add(helperSystemIdentificationPanel[0]);
        systemIdentificationPanel.add(helperSystemIdentificationPanel[1]);
        systemIdentificationPanel.add(helperSystemIdentificationPanel[2]);
        systemIdentificationPanel.add(helperSystemIdentificationPanel[3]);
        systemIdentificationPanel.add(helperSystemIdentificationPanel[4]);

        statesFiniteMachinePanel.add(resultLabel);

        helperHeaderMainPanels[0].add(generalPanel);
        helperHeaderMainPanels[0].add(outputPanel);

        helperHeaderMainPanels[1].add(new JLabel(imageIcon));
        helperHeaderMainPanels[1].add(stoppingPanel);

        headerPanel.add(helperHeaderMainPanels[0]);
        headerPanel.add(helperHeaderMainPanels[1]);
        headerPanel.add(systemIdentificationPanel);

        buttonPanel.add(runButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(finalGenerationCheck);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void startView() { //Dimensiones y asignamos eventos
        setTitle("<<< E-FSM >>>");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2 * Toolkit.getDefaultToolkit().getScreenSize().width / 5, 6 * Toolkit.getDefaultToolkit().getScreenSize().height / 10);
        setLocation(0, 0);
        setVisible(true);
        setResizable(false);

        finalGenerationCheck.addActionListener((ActionEvent e) -> {
            bFinalGenerationCheck = finalGenerationCheck.isSelected();
            if (bFinalGenerationCheck) {
                finalGenerationCheck.requestFocusInWindow();
            }
        });

        finalGenerationCheck.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (bFinalGenerationCheck && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    System.exit(0);
                }
            }
        });
    }

    //Generate helper Panels
    private JPanel[] generatePanels(JPanel panel[], int lenght) {
        for (int i = 0; i < lenght; i++) {
            panel[i] = new JPanel();
            panel[i].setLayout(new BoxLayout(panel[i], BoxLayout.X_AXIS));
        }
        return panel;
    }

    //Obtenemos los valores ingresados en los JTextField
    public int getSeed() {
        return Integer.parseInt(getRandomSeedField().getText());
    }

    public int getPopulation() {
        return Integer.parseInt(getPopulationSizeField().getText());
    }

    public int getElite() {
        return Integer.parseInt(getEliteSizeField().getText());
    }

    public int getMaxGeneration() {
        return Integer.parseInt(getMaxGenerationField().getText());
    }

    public int getNoImprovement() {
        return Integer.parseInt(getGenerationNoImprovementField().getText());
    }

    public int getMinGeneration() {
        return Integer.parseInt(getMinGenerationField().getText());
    }

    public int getMaxEvaluation() {
        return Integer.parseInt(getMaxEvaluationField().getText());
    }

    public int getFitness() {
        return Integer.parseInt(getFitnessReachesField().getText());
    }

    public int getNumberInputs() {
        return Integer.parseInt(getNumberInputField().getText());
    }

    public int getNumberOutputs() {
        return Integer.parseInt(getNumberOutputField().getText());
    }

    public int getMaxStates() {
        return Integer.parseInt(getMaxStatesField().getText());
    }

    public String getInput() {
        return getInputField().getText();
    }

    public String getOutput() {
        return getOutputField().getText();
    }

    public void showHelp() {
        String information = """
                             Para utilizar GFSM, primero debes ingresar un número entero en el campo de 'Semilla aleatoria', además
                             de definir el tamaño de la población y la cantidad de k elementos que se van a seleccionar a través del
                             método de 'Elitismo'.
                             
                             De forma opcional, puedes seleccionar si quieres que se muestre el número de evaluaciones realizadas en
                             la generación actual, si quieres que se muestre el tiempo de cada generación y si quieres que se imprima
                             la población de la generación a través de su definición de cada FSM mediante su sextupla 
                             [Estados, Entradas, Salidas, Estado inicial, Funciones de Estado Siguiente, Funciones de Salida].
                             
                             En la sección de 'Criterio de parada', se indicará el número máximo de generaciones, el número máximo de
                             generaciones sin mejora, el número mínimo de generaciones, el número máximo de evaluaciones y el objetivo
                             que se quiere alcanzar a través de la función que evalúa cada una de las FSM. Es completamente necesario 
                             indicar que las mejores FSM tendrán un valor negativo, mientras que las menos valiosas (que no cumplen con
                             los datos ingresados en la siguiente sección) tendrán una puntuación positiva.
                             
                             En la sección final, 'Identificación de Sistemas', se debe indicar el número de símbolos de entrada, el 
                             número de símbolos de salida, el número máximo de estados, la cadena de entrada y la cadena final. Cada uno
                             de los elementos deben estar separados por comas en ambas cadenas. Si quieres agregar el carácter vacío (λ),
                             simplemente deja un espacio entre las comas.
                             
                             Finalmente, al hacer clic en el botón 'Iniciar', comenzará el algoritmo genético. Si necesitas ayuda, puedes
                             hacer clic en el botón 'Ayuda' para mostrar este cuadro de diálogo. Además, puedes cerrar el programa presio-
                             nando la combinación de teclas Ctrl + C.
                             """;

        JOptionPane.showInternalMessageDialog(null, information,
                "information", JOptionPane.INFORMATION_MESSAGE);
    }
}
