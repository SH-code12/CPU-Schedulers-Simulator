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
        if (p.quantum == 0) {
            p.quantum = Math.max(2, (10 - p.priority) / 2);
            quantumHistory.putIfAbsent(p.name, new ArrayList<>());
            quantumHistory.get(p.name).add(p.quantum);
        }
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
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

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Sort ready queue by FCAI factor
            List<Process> sortedQueue = new ArrayList<>(readyQueue);
            sortedQueue.sort(Comparator.comparingDouble(this::calculateFCAIFactor));
            readyQueue = new LinkedList<>(sortedQueue);

            Process currentProcess = readyQueue.poll();
            initializeQuantum(currentProcess);

            int quantum40Percent = (int) Math.ceil(currentProcess.quantum * 0.4);
            int executionTime = Math.min(quantum40Percent, currentProcess.remainingTime);

            currentProcess.remainingTime -= executionTime;
            currentTime += executionTime;

            executionOrder.add(currentProcess.name);

            if (currentProcess.remainingTime > 0) {
                currentProcess.quantum += 2;
                quantumHistory.get(currentProcess.name).add(currentProcess.quantum);
                readyQueue.offer(currentProcess);
            } else {
                currentProcess.completionTime = currentTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                currentProcess.isCompleted = true;
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
            System.out.println(p.name + ": Waiting Time = " + p.waitingTime
                    + ", Turnaround Time = " + p.turnaroundTime);
        }
        double avgWaitingTime = totalWaitingTime / processes.size();
        double avgTurnaroundTime = totalTurnaroundTime / processes.size();

        System.out.println("Average Waiting Time: " + avgWaitingTime
                + ", Average Turnaround Time: " + avgTurnaroundTime);

        System.out.println("\nQuantum Update History:");
        for (Map.Entry<String, List<Integer>> entry : quantumHistory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
