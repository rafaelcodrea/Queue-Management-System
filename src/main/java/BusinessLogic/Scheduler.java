package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    public static int maxTasksPerServer=10;
    private ArrayList<Server> servers;
    private int maxNoServers;
    private Strategy strategy;
    public ArrayList<Thread> threads;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = 10;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>();
        threads = new ArrayList<>();
        strategy = new ConcreteStrategyTime();
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(0);
            this.servers.add(server);

            Thread serverThread = new Thread(server);
            this.threads.add(serverThread);
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }
    public Integer getCrtClients(){
        Integer res=0;
        for(Server s : servers){
            res += s.tasks.size();
        }
        return res;
    }

    public boolean areAllQueuesEmpty() {
        for (Server s : servers) {
            if (s.tasks.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    public Double getAvgTime(){
        Double res =0.0;
        for(Server s: servers) {
            System.out.println(s.getTotalWaitingTime()+ "    " + s.getTotalClientsServed());
            float resaux = (float)s.getTotalWaitingTime() / (float) s.getTotalClientsServed();
            res += resaux;
        }

        //res = res / this.servers.size();
        return  res;
    }
    public int getMaxWaitingTime() {
        int maxWaitingTime = 0;

        for (Server server : servers) {
            int waitingTime = server.getWaitingPeriod();
            if (waitingTime > maxWaitingTime) {
                maxWaitingTime = waitingTime;
            }
        }

        return maxWaitingTime;
    }
    public void dispatchTask(Task t) {
        try {
            this.strategy.addTask(this.servers, t);
        } catch (Exception exception) {
            System.out.println("An exception occurred: " + exception.getMessage());
        }
    }
    public Integer getSchedulerTotalClientsServed(){
        Integer res = 0;
        for(Server s: servers){
            res= res +s.getTotalClientsServed();
        }
        return res;
    }
    public List<Server> getServers() {
        return servers;
    }
}
