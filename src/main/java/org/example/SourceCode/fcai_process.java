package org.example.SourceCode;

import java.awt.*;
class fcai_process {
    String name;
    int arrivalTime;
    int burstTime;
    int priority;
    int remainingTime;
    int fcai;
    int q;
    int quantum;

    fcai_process(String name, int arrivalTime, int burstTime, int priority, int quantum, int fcai) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.quantum = quantum;
        this.fcai = fcai;
        this.q = 0;
    }

    fcai_process() {
        this.name = "";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.remainingTime = 0;
        this.quantum = 0;
        this.fcai = 0;
        this.q = 0;
    }
}
