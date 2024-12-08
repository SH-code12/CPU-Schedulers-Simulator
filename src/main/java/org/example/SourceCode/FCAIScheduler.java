package org.example.SourceCode;

import java.util.*;

public class FCAIScheduler implements Scheduler {
    private List<Process> processes;
    private List<String> executionOrder;
    private double V1, V2;
    private Queue<Process> readyQueue;
    private Map<String, List<Integer>> quantumHistory;

    public FCAIScheduler(List<Process> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
        this.readyQueue = new LinkedList<>();
        this.quantumHistory = new HashMap<>();
        calculateV1AndV2();
    }

    private void calculateV1AndV2() {
        int lastArrivalTime = processes.stream().mapToInt(p -> p.arrivalTime).max().orElse(0);
        int maxBurstTime = processes.stream().mapToInt(p -> p.burstTime).max().orElse(0);

        V1 = lastArrivalTime / 10.0;
        V2 = maxBurstTime / 10.0;
    }

    private double calculateFCAIFactor(Process p) {
        return (10 - p.priority) + (p.arrivalTime / V1) + (p.remainingTime / V2);
    }

    private void initializeQuantum(Process p) {
        // If the quantum is zero, calculate it and add to quantumHistory
        if (p.quantum == 0) {
            p.quantum = Math.max(2, (10 - p.priority) / 2); // Adjust quantum based on priority
            quantumHistory.putIfAbsent(p.name, new ArrayList<>()); // Ensure list is initialized
        }
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort processes by arrival time
        int currentTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Add arrived processes to the ready queue
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p.arrivalTime <= currentTime) {
                    readyQueue.offer(p);
                    iterator.remove();
                }
            }

            // Skip to next time unit if no processes are ready to execute
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Sort ready queue by FCAI factor
            List<Process> sortedQueue = new ArrayList<>(readyQueue);
            sortedQueue.sort(Comparator.comparingDouble(this::calculateFCAIFactor));
            readyQueue = new LinkedList<>(sortedQueue);

            Process currentProcess = readyQueue.poll();
            initializeQuantum(currentProcess); // Ensure quantum is initialized

            int quantum40Percent = (int) Math.ceil(currentProcess.quantum * 0.4);
            int executionTime = Math.min(quantum40Percent, currentProcess.remainingTime);

            currentProcess.remainingTime -= executionTime;
            currentTime += executionTime;

            executionOrder.add(currentProcess.name); // Record execution order

            // Update quantum history (now safe to access)
            quantumHistory.get(currentProcess.name).add(currentProcess.quantum);

            if (currentProcess.remainingTime > 0) {
                currentProcess.quantum += 2; // Increase quantum by 2 for the next round
                readyQueue.offer(currentProcess); // Re-add process to queue
            } else {
                currentProcess.completionTime = currentTime; // Set completion time
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime; // Calculate turnaround time
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime; // Calculate waiting time
                currentProcess.isCompleted = true; // Mark as completed
            }
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
            System.out.println(p.name + ": Waiting Time = " + p.waitingTime + ", Turnaround Time = " + p.turnaroundTime);
        }

        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processes.size()));

        System.out.println("\nQuantum Update History:");
        for (Map.Entry<String, List<Integer>> entry : quantumHistory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}