import BusinessLogic.Controller;
import GUI.SimulationFrame;


public class Main {

    public static void main(String[] args) {


        SimulationFrame simulationFrame = new SimulationFrame();
        Controller controllerNew = new Controller(simulationFrame);
        simulationFrame.setVisible(true);
    }

}