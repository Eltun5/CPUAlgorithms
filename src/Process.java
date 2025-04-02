public class Process {
    private final int processNumber;

    private final int arrivalTime;

    private final int burstTime;

    private int priority;

    private int timeNeededToFinish;

    public Process(int processNumber, int arrivalTime, int burstTime) {
        this.processNumber = processNumber;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.timeNeededToFinish = burstTime;
    }

    public int processNumber() {
        return processNumber;
    }

    public int arrivalTime() {
        return arrivalTime;
    }

    public int burstTime() {
        return burstTime;
    }

    public int priority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = Math.max(priority, 1);
    }

    public int timeNeededToFinish() {
        return timeNeededToFinish;
    }

    public void decreaseTimeNeededToFinish(int decreaseNumber) {
        this.timeNeededToFinish -=decreaseNumber;
        this.timeNeededToFinish = Math.max(timeNeededToFinish,0);
    }
}
