import BusinessLogic.Controller;
import GUI.SimulationFrame;


public class Main {

    public static void main(String[] args) {


        SimulationFrame simulationFrame = new SimulationFrame();
        Controller controllerNou = new Controller(simulationFrame);
        simulationFrame.setVisible(false);
        int i =0;
    }

}