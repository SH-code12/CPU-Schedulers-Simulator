package org.example.SourceCode;

import java.awt.*;
public class fcai_process {
    public String name;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int remainingTime;
    public int fcai;
    int q;
    int quantum;

    public fcai_process(String name, int arrivalTime, int burstTime, int priority, int quantum, int fcai) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.quantum = quantum;
        this.fcai = fcai;
        this.q = 0;
    }

    public fcai_process() {
        this.name = "";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.remainingTime = 0;
        this.quantum = 0;
        this.fcai = 0;
        this.q = 0;
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
    public void setQuantum(int pr){
        this.quantum= pr;
    }
}
