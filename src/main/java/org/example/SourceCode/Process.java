package org.example.SourceCode;


import java.awt.*;

public class Process {

    public String name;
    // for GUI
    public  Color color;
    int arrivalTime;
    int burstTime;
    int priority;
    int waitingTime = 0;
    int turnaroundTime = 0;
    public int remainingTime;
    int completionTime = 0;
    int quantum = 0;
    int  fcaiFactor = 0;
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

}
