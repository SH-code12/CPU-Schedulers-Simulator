package org.example.SourceCode;


import java.util.*;

public class SRTFScheduler implements Scheduler {

    private List<Process> processes;
    public List<String> executionOrder;
    private int contextSwitchTime;


    public SRTFScheduler(List<Process> processes ,  int contextSwitchTime) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
        this.contextSwitchTime = contextSwitchTime;
    }

    @Override
    public void schedule() {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int completed = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));

        Process lastExcuted = null;

        while (completed < processes.size()) {
            // Add processes that have arrived by currentTime to the readyQueue
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !readyQueue.contains(p) && p.remainingTime > 0) {
                    readyQueue.add(p);
                }
            }

            // If no process is ready, increment time
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Pick the process with the shortest remaining time
            Process currentProcess = readyQueue.poll();

            // context switching
            if (lastExcuted != null && !lastExcuted.equals(currentProcess)) {
                currentTime += contextSwitchTime;
            }

            // Execute the current process for 1 time unit
            executionOrder.add(currentProcess.name);
            currentProcess.remainingTime--;
            currentTime++;

            // If the process is completed
            if (currentProcess.remainingTime == 0) {
                completed++;
                currentProcess.completionTime = currentTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
            } else {
                // Re-add the process to the ready queue if it still has remaining time
                readyQueue.add(currentProcess);
            }
            lastExcuted = currentProcess;

        }
    }

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
