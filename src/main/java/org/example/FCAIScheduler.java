package org.example;

import java.util.ArrayList;
import java.util.List;

public class FCAIScheduler implements Scheduler{

    private List<Process> processes;
    private List<String> executionOrder;
    private double V1 ,V2;

    public FCAIScheduler(List<Process> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();

    }

    private double calculateFCAIFactor(Process p) {
        return (10 - p.priority) + (p.arrivalTime / V1) + (p.burstTime / V2);
    }

    private double calculateV1(Process p) {
         System.out.println("Not added yet");
        return 0.0;
    }

    private double calculateV2(Process p) {
        System.out.println("Not added yet");
        return 0.0;
    }

    @Override
    public void schedule() {

    }

    @Override
    public void Display() {

    }
}
