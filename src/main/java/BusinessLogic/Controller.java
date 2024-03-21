package BusinessLogic;

import BusinessLogic.*;
import GUI.SimulationFrame;
import Model.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {
    public Server model;
    public SimulationFrame simulationFrame;
    public static SimulationManager manager;
    public Controller(SimulationFrame simulationFrame) {
        this.model = model;
        this.simulationFrame = simulationFrame;

        //view.setVisible(true);

        this.manager = new SimulationManager(this.simulationFrame);

        simulationFrame.addSimBtnListener(new SimBtnListener());
    }


    class SimBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
                Controller.manager = new SimulationManager(simulationFrame.getSimInterval(), simulationFrame.getArrMin(), simulationFrame.getArrMax(), simulationFrame.getExMax(), simulationFrame.getExMin(), simulationFrame.getNoQ(), simulationFrame.getNoClients(), SelectionPolicy.SHORTEST_TIME, simulationFrame, Controller.this);
            Thread t = new Thread(manager);
            t.start();
        }
    }


}
