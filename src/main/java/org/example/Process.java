package org.example;

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
}
