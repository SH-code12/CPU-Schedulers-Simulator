package org.example;

import java.util.*;

public class FCAIScheduler implements Scheduler {
    private List<Process> processes;
    private List<String> executionOrder;

    public FCAIScheduler(List<Process> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(p -> p.fcaiFactor));

        double V1 = (double) processes.stream().mapToInt(p -> p.arrivalTime).max().orElse(1) / 10;
        double V2 = (double) processes.stream().mapToInt(p -> p.burstTime).max().orElse(1) / 10;

        int index = 0;

        while (!readyQueue.isEmpty() || index < processes.size()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                Process process = processes.get(index);
                process.calculateFCAIFactor(V1, V2);
                readyQueue.add(process);
                index++;
            }

            if (readyQueue.isEmpty()) {
                currentTime = processes.get(index).arrivalTime;
                continue;
            }

            Process currentProcess = readyQueue.poll();
            int allocatedQuantum = currentProcess.quantum;
            int executionTime = (int) Math.ceil(allocatedQuantum * 0.4); // 40% non-preemptive execution

            if (currentProcess.remainingTime <= executionTime) {
                currentTime += currentProcess.remainingTime;
                executionOrder.add(currentProcess.name);
                currentProcess.remainingTime = 0;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
            } else {
                currentTime += executionTime;
                currentProcess.remainingTime -= executionTime;

                if (currentProcess.remainingTime > 0) {
                    int unusedQuantum = allocatedQuantum - executionTime;

                    // Update quantum based on the rules:
                    if (unusedQuantum > 0) {
                        currentProcess.quantum += unusedQuantum;
                    } else {
                        currentProcess.quantum += 2;
                    }

                    currentProcess.calculateFCAIFactor(V1, V2);
                    readyQueue.add(currentProcess);
                }
            }

            executionOrder.add(currentProcess.name);
        }
    }

    @Override
    public void Display() {
        System.out.println("Execution Order: " + String.join(" -> ", executionOrder));
        for (Process p : processes) {
            System.out.println("Process: " + p.name + " | Waiting Time: " + p.waitingTime + " | Turnaround Time: " + p.turnaroundTime);
        }
    }
}
