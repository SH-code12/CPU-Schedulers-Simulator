package org.example.SourceCode;

public class Process {

    public String name;
    // for GUI
    String color;
    int arrivalTime;
    int burstTime;
    int priority;
    int waitingTime = 0;
    int turnaroundTime = 0;
    int remainingTime;
    int completionTime = 0;
    int quantum = 0;
    double fcaiFactor = 0.0;
    boolean isCompleted = false;
    int agingFactor = 0;

    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
    public Process() {
        this.name = "";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.remainingTime = 0;
    }

    public void setname(String n){
        this.name = n;
    }
    public void setArrivalTime(int ar){
        this.arrivalTime = ar;
    }
    public void setBurstTime(int br){
        this.burstTime = br;
    }
    public void setPriority(int pr){
        this.priority= pr;
    }
        public void calculateFCAIFactor(double V1, double V2) {
            fcaiFactor = (10 - priority) + (arrivalTime / V1) + (remainingTime / V2);
        }

}
