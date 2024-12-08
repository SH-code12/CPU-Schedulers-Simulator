package org.example.SourceCode;

import java.util.*;

public class SJFScheduler implements Scheduler {

    private List<Process> processes;
    private List<String> executionOrder;
    private int contextSwitchTime;



    public SJFScheduler(List<Process> processes, int  contextSwitchTime) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
        this.contextSwitchTime = contextSwitchTime;
    }

    // Method to schedule processes using SJF with aging
    @Override
    public void schedule() {
        int currentTime = 0;
        int completedProcesses = 0;
        while (completedProcesses < processes.size()) {
            Process nextProcess = null;

            for (Process process : processes) {
                if (!process.isCompleted && process.arrivalTime <= currentTime) {
                    int effectivePriority = process.burstTime - process.agingFactor; // Adjust based on aging
                    if (nextProcess == null || effectivePriority < (nextProcess.burstTime - nextProcess.agingFactor) ||
                            (effectivePriority == (nextProcess.burstTime - nextProcess.agingFactor) && process.arrivalTime < nextProcess.arrivalTime)) {
                        nextProcess = process;
                    }
                }
            }

            if (nextProcess != null) {
                // Simulate context switching
                if (!executionOrder.isEmpty()){
                    currentTime += contextSwitchTime;
                    executionOrder.add("context Switch ");
                }

                // Execute the next process
                executionOrder.add(nextProcess.name);
                currentTime += nextProcess.burstTime;
                nextProcess.completionTime = currentTime;
                nextProcess.turnaroundTime = nextProcess.completionTime - nextProcess.arrivalTime;
                nextProcess.waitingTime = nextProcess.turnaroundTime - nextProcess.burstTime;
                nextProcess.isCompleted = true;
                completedProcesses++;

                // Reset aging factor for all other processes
                for (Process process : processes) {
                    if (!process.isCompleted && process.arrivalTime <= currentTime) {
                        // Increase aging factor for waiting processes
                        process.agingFactor += 1;
                    }
                }
            } else {
                // If no process is ready, increment time
                currentTime++;
                executionOrder.add("Idle");
            }
        }
    }

    // Method to print results for each process
    @Override
    public void Display() {
        System.out.println("Execution Order: " + String.join(" -> ", executionOrder));
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process p : processes) {
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
            System.out.println(p.name + ": Waiting Time = " + p.waitingTime
                    + ", Turnaround Time = " + p.turnaroundTime);
        }
        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();

        System.out.println("Average Waiting Time: " + avgWaitingTime
                + ", Average Turnaround Time: " + avgTurnaroundTime);
    }
}
