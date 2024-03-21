import BusinessLogic.Controller;
import GUI.SimulationFrame;


public class Main {

    public static void main(String[] args) {


        SimulationFrame simulationFrame = new SimulationFrame();

        for(int i = 0 ; i <10 ; i++)
        {
            System.out.println(i);
        }


        Controller controllerNou = new Controller(simulationFrame);
        simulationFrame.setVisible(false);
        int i =0;
    }

}