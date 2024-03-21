package Model;

public class Task {
    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int arrivalTime;

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    private int processingTime;

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    private boolean isCompleted;


    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    private int waitingTime;
    public Task(int id, int arrivalTime, int processingTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.waitingTime = 0;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }






    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public int compareTo(Task t){
        return this.arrivalTime - t.arrivalTime;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + arrivalTime + ", " + processingTime + ")\n";
    }

    public void incWaitingTime() {
        this.waitingTime ++;
    }
}
