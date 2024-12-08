package org.example.SourceCode;

import java.util.*;

public class SJFScheduler implements Scheduler {

    private List<Process> processes;
    private List<String> executionOrder;

    public SJFScheduler(List<Process> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
    }

    // Method to schedule processes using SJF with aging
    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        // To track processes that have arrived
        int index = 0;

        while (!readyQueue.isEmpty() || index < processes.size()) {
            // Add all processes that have arrived by currentTime to the readyQueue
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            // If the ready queue is empty, move the current time to the next process's arrival time
            if (readyQueue.isEmpty()) {
                currentTime = processes.get(index).arrivalTime;
                continue;
            }

            // Pick the process with the shortest burst time
            Process currentProcess = readyQueue.poll();

            // Execute the selected process
            int startTime = Math.max(currentTime, currentProcess.arrivalTime);
            currentProcess.waitingTime = startTime - currentProcess.arrivalTime;
            currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;
            currentTime = startTime + currentProcess.burstTime;

            // Record the execution order
            for (int i = 0; i < currentProcess.burstTime; i++) {
                executionOrder.add(currentProcess.name);
            }

            // Apply aging to remaining processes in the readyQueue
            List<Process> agedProcesses = new ArrayList<>();
            while (!readyQueue.isEmpty()) {
                Process p = readyQueue.poll();
                // Increase the priority of the process that has been in the ready queue
                // Aging: Reduce burst time by 1, minimum burst time is 1
                p.burstTime = Math.max(1, p.burstTime - 1);
                agedProcesses.add(p);
            }
            // Re-add aged processes to the ready queue
            readyQueue.addAll(agedProcesses);
        }
    }

    // Method to print results for each process
    @Override
    public String Display() {
        System.out.println("Execution Order: " + String.join(" -> ", executionOrder));
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process p : processes) {
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
            System.out.println(p.name + ": Waiting Time = " + p.waitingTime + "\nTurnaround Time = " + p.turnaroundTime);
        }

        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processes.size()));
        return "";
    }
}
