package org.example.SourceCode;

import java.awt.*;

public class Process {

    String name;
    // for GUI
    Color color;
    int arrivalTime;
    int burstTime;
    int priority;
    int waitingTime = 0;
    int turnaroundTime = 0;
    int remainingTime;
    int quantum = 0;
    double fcaiFactor = 0.0;

    public Process(String name, int arrivalTime, int burstTime, int priority, Color color) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.color = Color.decode(colorToHex(color));
        this.remainingTime = burstTime;
    }
    public void calculateFCAIFactor(double V1, double V2) {
            fcaiFactor = (10 - priority) + (arrivalTime / V1) + (remainingTime / V2);
    }
    private String colorToHex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }

    // Getter Methods
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public double getFcaiFactor() {
        return fcaiFactor;
    }

}


