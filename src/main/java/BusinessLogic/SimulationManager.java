package BusinessLogic;

import GUI.SimulationFrame;
import Model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxProcessingTime ;
    public int minProcessingTime ;
    public int numberOfServers ;
    public int numberOfClients;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int nrServers;
    public int nrClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;

    private Scheduler scheduler;
    private SimulationFrame frame;
    private ArrayList<Task> generatedTasks;

    private SimulationFrame simulationFrame;
    private Controller controller;
    private int crtTime;

    private int peakHourClients;
    private int peakHour;


    //    private List<Task> generateNRandomTasks() {
//        List<Task> tasks = new ArrayList<>();
//        for (int i = 0; i < numberOfClients; i++) {
//            int processingTime = (int) (Math.random() * (maxProcessingTime - minProcessingTime + 1)) + minProcessingTime;
//            int arrivalTime = (int) (Math.random() * timeLimit);
//            Task task = new Task(i + 1, arrivalTime, processingTime);
//            tasks.add(task);
//        }
//        Collections.sort(tasks, (t1, t2) -> t1.getArrivalTime() - t2.getArrivalTime());
//        return tasks;
//    }
public void generateNRandomTasks()
{
    this.generatedTasks = new ArrayList<>();
    for(int i =0; i< this.nrClients; i++) {
        Task newTask = new Task(i, 1 + (int)(Math.random() * ((this.maxArrivalTime - minArrivalTime) + 1)), minProcessingTime + (int)(Math.random() * ((maxProcessingTime - minProcessingTime) + 1)));
        this.generatedTasks.add(newTask);
        this.generatedTasks.sort(Task::compareTo);
    }
}


    public Double getTotalSerTime(){
        Double res = 0.0;
        for(Task t: this.generatedTasks)
            res += t.getProcessingTime();
        return res;
    }

    public Double getQSerTimeRemaining(){
        Double res =0.0;
        for(Server s: this.scheduler.getServers()){
            res = res + s.getServiceTimeRemaining();
        }
        return res;
    }

    public void updatePeakHour(){
        if(this.scheduler.getCrtClients()>this.peakHourClients) {
            this.peakHourClients= this.scheduler.getCrtClients();
            this.peakHour = this.crtTime;
        }
    }
    public void createFile() {
        try {
            File newFile = new File("logs.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                newFile.delete();
                newFile.createNewFile();
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void appendFile(String s){
        try {
            FileWriter myWriter = new FileWriter("logs.txt");
            myWriter.append(s);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getQueuesInfo() {
        String s = new String();
        System.out.println(generatedTasks.size());
        for (Task t : this.generatedTasks){
            s = s + t.toString() + " ";
        }
        s= s + '\n';
        System.out.println();
        for (int i = 0; i <nrServers; i++){
            //System.out.println(this.scheduler.servers.get(i).toString());
            s = s + "Queue " + i + ": " + scheduler.getServers().get(i).toString() + '\n';
        }
        s=s+("Current time: " + this.crtTime + '\n');
        s=s+("\n\n\n");
        return s;
    }

//    public String getQueuesInfo(){
//        String s = new String();
//        System.out.println(generatedTasks.size());
//        for (Task t : this.generatedTasks){
//            s = s + t.toString() + " ";
//        }
//        s= s + '\n';
//        System.out.println();
//        for (int i = 0; i <nrServers; i++){
//            //System.out.println(this.scheduler.servers.get(i).toString());
//            s = s + "Queue " + i + ": " + scheduler.getServers().get(i).toString() + '\n';
//        }
//        s=s+("Current time: " + this.crtTime + '\n');
//        s=s+("\n\n\n");
//        return s;
//    }

    public SimulationManager(SimulationFrame simulationFrame) {
        this.timeLimit = 0;
        this.minArrivalTime = 0;
        this.maxArrivalTime = 0;
        this.maxProcessingTime = 0;
        this.minProcessingTime = 0;
        this.nrServers = 0;
        this.nrClients = 0;
        this.selectionPolicy = SelectionPolicy.SHORTEST_TIME;

        Scheduler scheduler = new Scheduler(0, 0);
        this.scheduler = scheduler;
        this.generatedTasks = new ArrayList<>();
        this.simulationFrame = simulationFrame;
        this.peakHour = 0;
        this.peakHourClients = 0;

    }

    public SimulationManager(int timeLimit, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, int nrClients, int nrServers, SelectionPolicy selectionPolicy, SimulationFrame simulationFrame, Controller controller) {
        scheduler = new Scheduler(numberOfServers, 5);
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.nrClients = nrClients;
        this.nrServers = nrServers;
        this.selectionPolicy = selectionPolicy;
        this.simulationFrame = simulationFrame;
        this.controller = controller;
//        for (int i = 0; i < numberOfServers; i++) {
//            Server server = new Server(i + 1);
//            scheduler.getServers().add(server);
//            Thread serverThread = new Thread(server);
//            serverThread.start();
//        }
//        scheduler.changeStrategy(selectionPolicy);
//        frame = new SimulationFrame(numberOfServers);
//        generatedTasks = generateNRandomTasks(this.numberOfClients);
        for(Thread t : scheduler.threads)
            t.start();
        generateNRandomTasks();

    }
    public void run(){
        this.crtTime = 0;
        createFile();
        Double avgSer = this.getTotalSerTime();
        String finalstring = new String();
        while(crtTime <= this.timeLimit){
            if(((this.generatedTasks.isEmpty()) && (this.scheduler.areAllQueuesEmpty())))
                break;
            ArrayList<Task> newList = new ArrayList<>();
            for (Task t : this.generatedTasks) {
                if (t.getArrivalTime() == crtTime) {
                    scheduler.dispatchTask(t);
                } else newList.add(t);
            }
            String s = this.getQueuesInfo();
            simulationFrame.queuesPane.setText(s);
            finalstring += s;
            updatePeakHour();
            this.crtTime++;
            this.generatedTasks = newList;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        avgSer = (avgSer - this.getTotalSerTime() - this.getQSerTimeRemaining())/this.scheduler.getSchedulerTotalClientsServed();
        simulationFrame.queuesPane.append("\nAverage Waiting time: " + this.scheduler.getAvgTime() +'\n');
        simulationFrame.queuesPane.append("\nAverage Service time: " + avgSer +'\n');
        simulationFrame.queuesPane.append("\nPeak hour: " + this.peakHour + '\n');
        String s = simulationFrame.queuesPane.getText();
        finalstring += s;
        s = s + ("\nAverage Waiting time: " + this.scheduler.getAvgTime() +'\n');
        appendFile(finalstring);
    }
//    @Override
//    public void run() {
//        int currentTime = 0;
//        while (currentTime < timeLimit) {
//            List<Task> tasksToDispatch = new ArrayList<>();
//            for (Task task : generatedTasks) {
//                if (task.getArrivalTime() == currentTime) {
//                    tasksToDispatch.add(task);
//                }
//            }
//
//            for (Task task : tasksToDispatch) {
//                try {
//                    scheduler.dispatchTask(task);
//                    generatedTasks.remove(task);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            frame.update(scheduler.getServers());
//
//            currentTime++;
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
