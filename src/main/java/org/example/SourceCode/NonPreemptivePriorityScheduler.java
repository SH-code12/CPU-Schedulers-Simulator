package org.example.SourceCode;
import java.util.*;

public class NonPreemptivePriorityScheduler implements Scheduler {
    private List<Process> processes;
    public List<String> executionOrder;
    // Keep track of completed processes
    private List<Process> completedProcesses;
    // Preserve the original list for calculation
    private List<Process> originalProcesses;
    private int contextSwitchTime ;


    public NonPreemptivePriorityScheduler(List<Process> processes , int contextSwitchTime) {
        // Make a copy of the list for scheduling
        this.processes = new ArrayList<>(processes);
        // Store original for stats
        this.originalProcesses = new ArrayList<>(processes);
        this.executionOrder = new ArrayList<>();
        this.completedProcesses = new ArrayList<>();
        this.contextSwitchTime = contextSwitchTime;
    }

    // Method to schedule processes using Non-Preemptive Priority Scheduling
    @Override
    public void schedule() {
        // Sort by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        while (!processes.isEmpty()) {
            // Aging: Reduce priority (increase priority value numerically) for all waiting processes
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    // Lower priority value = higher priority
                    p.priority = Math.max(1, p.priority - 1);
                }
            }

            // Find the next process to execute (highest priority among arrived processes)
            int finalCurrentTime = currentTime;
            Process nextProcess = processes.stream()
                    .filter(p -> p.arrivalTime <= finalCurrentTime)
                    .min(Comparator.comparingInt(p -> p.priority))
                    .orElse(null);

            if (nextProcess != null) {
                // Wait if no process has arrived
                currentTime = Math.max(currentTime, nextProcess.arrivalTime);

                // Context Switching
                if (!executionOrder.isEmpty()) {
                    currentTime += contextSwitchTime;
                    executionOrder.add("Context Switch");
                }

                // Execute the process
                for (int i = 0; i < nextProcess.burstTime; i++) {
                    executionOrder.add(nextProcess.name);
                }

                // Update process times
                currentTime += nextProcess.burstTime;
                nextProcess.completionTime = currentTime;
                nextProcess.turnaroundTime = nextProcess.completionTime - nextProcess.arrivalTime;
                nextProcess.waitingTime = nextProcess.turnaroundTime - nextProcess.burstTime;

                // Add to completed processes and remove from list
                completedProcesses.add(nextProcess);
                processes.remove(nextProcess);
            } else {
                // If no process is ready, increment time
                executionOrder.add("Idle");
                currentTime++;

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
            System.out.println(p.name + ": Waiting Time = " + p.waitingTime
                    + ", Turnaround Time = " + p.turnaroundTime);
        }
        double avgWaitingTime = totalWaitingTime / originalProcesses.size();
        double avgTurnaroundTime = totalTurnaroundTime / originalProcesses.size();

        System.out.println("Average Waiting Time: " + avgWaitingTime
                + ", Average Turnaround Time: " + avgTurnaroundTime);

    }
}

