package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) throws Exception {
        Server candidate = new Server(Integer.MAX_VALUE);
        for (Server s : servers) {
            if (s.getTasks().size() < candidate.getTasks().size()) {
                candidate = s;
            }
        }

        if (candidate == null) {
            throw new Exception("No servers available to add the task.");
        }

        candidate.addTask(t);}
}
