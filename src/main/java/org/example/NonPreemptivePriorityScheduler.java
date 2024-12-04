package org.example;
import java.util.*;

public class NonPreemptivePriorityScheduler implements Scheduler {
    private List<Process> processes;
    private List<String> executionOrder;
    // Keep track of completed processes
    private List<Process> completedProcesses;
    // Preserve the original list for calculation
    private List<Process> originalProcesses;

    public NonPreemptivePriorityScheduler(List<Process> processes) {
        // Make a copy of the list for scheduling
        this.processes = new ArrayList<>(processes);
        // Store original for stats
        this.originalProcesses = new ArrayList<>(processes);
        this.executionOrder = new ArrayList<>();
        this.completedProcesses = new ArrayList<>();
    }

    // Method to schedule processes using Non-Preemptive Priority Scheduling
    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        while (!processes.isEmpty()) {
            // Aging: Reduce priority (increase priority value numerically) for all waiting processes
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    p.priority = Math.max(1, p.priority - 1);
                }
            }

            // Find the next process to execute (with the highest priority among arrived processes)
            int finalCurrentTime = currentTime;
            Process nextProcess = processes.stream()
                    .filter(p -> p.arrivalTime <= finalCurrentTime)
                    .min(Comparator.comparingInt(p -> p.priority))
                    .orElse(null);

            if (nextProcess != null) {
                currentTime = Math.max(currentTime, nextProcess.arrivalTime);
                nextProcess.waitingTime = currentTime - nextProcess.arrivalTime;
                currentTime += nextProcess.burstTime;
                nextProcess.turnaroundTime = nextProcess.waitingTime + nextProcess.burstTime;

                // Add the process name just once to track its execution
                for (int i = 0; i < nextProcess.burstTime; i++) {
                    executionOrder.add(nextProcess.name);
                }

                // Add to completed processes
                completedProcesses.add(nextProcess);
                // Remove the process from the list
                processes.remove(nextProcess);
            } else {
                // If no process is ready, increment time
                currentTime++;
                executionOrder.add("Idle");
            }
        }
    }

    @Override
    public void Display() {
        // Print execution order
        System.out.println("Execution Order: " + String.join(" -> ", executionOrder));

        // Calculate total waiting and turnaround times
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        // Use original list to calculate averages
        for (Process p : originalProcesses) {
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
            System.out.println(p.name + ": Waiting Time = " + p.waitingTime + ", Turnaround Time = " + p.turnaroundTime);
        }

        // Print the average waiting time and turnaround time
        System.out.println("Average Waiting Time: " + (totalWaitingTime / originalProcesses.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / originalProcesses.size()));

    }
}

