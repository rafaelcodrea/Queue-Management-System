package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class ConcreteStrategyTime implements Strategy {


    @Override
    public void addTask(List<Server> servers, Task t) throws Exception {
        try {
            Server candidate = new Server(Integer.MAX_VALUE);
            for (Server s : servers) {
                if (s.getWaitingPeriod() < candidate.getWaitingPeriod()) {
                    candidate = s;
                }
            }
            candidate.addTask(t);
        } catch (Exception e) {
            System.out.println("An exception occurred while adding the task: " + e.getMessage());
        }
    }
}