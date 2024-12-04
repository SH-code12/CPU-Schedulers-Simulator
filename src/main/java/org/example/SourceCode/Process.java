package org.example.SourceCode;

public class Process {

    String name;
    // for GUI
    String color;
    int arrivalTime;
    int burstTime;
    int priority;
    int waitingTime = 0;
    int turnaroundTime = 0;
    int remainingTime;
    int quantum = 0;
    double fcaiFactor = 0.0;

    Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
        public void calculateFCAIFactor(double V1, double V2) {
            fcaiFactor = (10 - priority) + (arrivalTime / V1) + (remainingTime / V2);
        }

}
