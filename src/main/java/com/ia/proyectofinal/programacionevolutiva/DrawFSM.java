package com.ia.proyectofinal.programacionevolutiva;

import java.util.ArrayList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class DrawFSM {

    public void Draw(FSM fsm) {

        System.setProperty("org.graphstream.ui", "swing");
        // Crea el grafo
        Graph graph = new SingleGraph("Autómata");

        Node states[] = new Node[fsm.getStates().length];
        Edge edges[] = new Edge[fsm.getIc().size()];

        int i = 0;
        for (States state : fsm.getStates()) {
            states[i] = graph.addNode(state.getName());
            states[i].setAttribute("ui.label", state.getName());
            
            // Marcar el estado inicial
            if (state.equals(fsm.getInitialState())) {
                states[i].setAttribute("ui.style", "shape:circle; fill-color: yellow; stroke-mode: plain; stroke-color: black; size:20px; stroke-width: 2px;");
            } else{
                states[i].setAttribute("ui.style", "shape:circle; fill-color: white; stroke-mode: plain; stroke-color: black; size:20px; stroke-width: 1px;");
            }
            
            i++;
        }

        ArrayList<NodeFSM> nfsms = new ArrayList<>();

        for (InputCoords ic : fsm.getIc()) {
            States state1 = ic.getState();
            States state2 = fsm.getNextStateHM().get(ic);
            boolean flag = true;
            for (NodeFSM nfsm : nfsms) {
                if (nfsm.getState1().equals(state1) && nfsm.getState2().equals(state2)/* && !(nfsm.getState1().equals(state2) && nfsm.getState2().equals(state1))*/) {
                    flag = false;
                    nfsm.setValue(nfsm.getValue() + "," + ic.getInput() + "/" + fsm.getOutputHM().get(ic));
                    break;
                }
            }
            if (flag) {
                NodeFSM newNode = new NodeFSM();
                newNode.setState1(state1);
                newNode.setState2(state2);
                newNode.setValue(ic.getInput() + "/" + fsm.getOutputHM().get(ic));
                nfsms.add(newNode);
            }
        }

        i = 0;
        for (NodeFSM nfsm : nfsms) {

            int initialIndex = findState(fsm.getStates(), nfsm.getState1());

            int finalIndex = findState(fsm.getStates(), nfsm.getState2());
            edges[i] = graph.addEdge(nfsm.getState1().getName() + "-" + nfsm.getState2().getName(),
                    states[initialIndex], states[finalIndex],true);
            edges[i].setAttribute("ui.label", nfsm.getValue());
            edges[i].setAttribute("ui.style", "text-size: 10;text-alignment: above; text-visibility-mode: normal; text-style: bold; text-background-mode: plain;");
            edges[i].isDirected();
            i++;
        }

        // Mostrar el grafo en una ventana
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }

    private int findState(States[] states, States state) {
        int save = -1;
        for (int i = 0; i < states.length; i++) {
            if (states[i].equals(state)) {
                save = i;
                break;
            }
        }
        return save;
    }
}
