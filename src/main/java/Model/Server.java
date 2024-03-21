package Model;

import BusinessLogic.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    public BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int serverId;
    private int totalClientsServed;
    private int totalServiceTime;

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }
    public Integer getTotalClientsServed() {
        return totalClientsServed;
    }

    public void setTotalWaitingTime(int totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    private int totalWaitingTime;

    public Server(int waitingPeriod) {
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(waitingPeriod);

        this.totalClientsServed =0;
        this.totalWaitingTime = 0;
        this.totalServiceTime = 0;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        this.waitingPeriod = new AtomicInteger(waitingPeriod.intValue() + newTask.getProcessingTime());

    }

    @Override
    public void run(){
        try {
            if(!tasks.isEmpty()){
                for(Task t: tasks){
                    t.incWaitingTime();
                }
                tasks.element().setWaitingTime(tasks.element().getWaitingTime() - 1);
                tasks.element().setProcessingTime(tasks.element().getProcessingTime() - 1);
                if(tasks.element().getProcessingTime() == 0){
                   this.totalClientsServed++;
                   this.totalWaitingTime+=tasks.element().getWaitingTime();
                    tasks.take();
                }
            }
            Thread.sleep(1000);
            if(this.waitingPeriod.intValue() > 0)
                this.waitingPeriod.decrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Task> getTasks() {
        ArrayList<Task> rez = new ArrayList<>();
        for (Task t: this.tasks) {
            rez.add(t);
        }
        return rez;
    }

    public boolean isBusy() {
        return !tasks.isEmpty();
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public Task getTaskToExecute(List<Task> waitingTasks) {
        return tasks.peek();
    }

    public int getTaskQueueSize() {
        return tasks.size();
    }

    public List<Task> getWaitingTasks() {
        return new ArrayList<>(tasks);
    }

    public void startTask(Task task) {
        System.out.println("Task started on server " + serverId + ": " + task);
        try {
            Thread.sleep(task.getProcessingTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int processingTime = task.getProcessingTime();
        waitingPeriod.addAndGet(processingTime);

        System.out.println("Task completed on server " + serverId + ": " + task);
    }

    public void update() {
        int completedTasks = 0;

        for (Task task : tasks) {
            if (task.isCompleted()) {
                int processingTime = task.getProcessingTime();
                waitingPeriod.addAndGet(-processingTime);
                completedTasks++;
            }
        }

        tasks.removeIf(Task::isCompleted);

        System.out.println("Completed tasks on server " + serverId + ": " + completedTasks);
    }

    @Override
    public String toString() {
        String s = new String();

        for(Task t : tasks)
            s = s + ' ' + t.toString();

        return s;
    }
    public Double getServiceTimeRemaining(){
        Double res =0.0;
        for(Task t:tasks){
            res += t.getProcessingTime();
            return res;
        }
        return res;
    }
    public int getServerId() {
        return serverId;
    }
}
