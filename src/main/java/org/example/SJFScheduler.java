package org.example;
import java.util.*;


public class SJFScheduler {

    // Inner class to represent a process
    static class Process {
        String name;
        int arrivalTime;
        int burstTime;
        int waitingTime;
        int turnaroundTime;
        int age; // Tracks how long the process has been waiting

        Process(String name, int arrivalTime, int burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.waitingTime = 0;
            this.turnaroundTime = 0;
            this.age = 0; // Initially, age is zero
        }
    }

    // Method to schedule processes using SJF with aging to resolve starvation
    public void schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time
        int currentTime = 0;
        List<Process> readyQueue = new ArrayList<>();

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Move processes to the ready queue if they have arrived
            for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext(); ) {
                Process p = iterator.next();
                if (p.arrivalTime <= currentTime) {
                    readyQueue.add(p);
                    iterator.remove();
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Apply aging: increment the age of processes in the ready queue
            for (Process p : readyQueue) {
                p.age++;
            }

            // Select process with the shortest burst time and resolve starvation via aging
            readyQueue.sort(Comparator.comparingInt((Process p) -> p.burstTime)
                    .thenComparingInt(p -> p.age)); // Use age as a tie-breaker

            Process selectedProcess = readyQueue.get(0); // Get the process to execute
            currentTime += selectedProcess.burstTime;
            selectedProcess.waitingTime = currentTime - selectedProcess.arrivalTime - selectedProcess.burstTime;
            selectedProcess.turnaroundTime = selectedProcess.waitingTime + selectedProcess.burstTime;

            printProcess(selectedProcess);
            readyQueue.remove(selectedProcess);
        }
    }

    // Method to print results for each process
    private void printProcess(Process p) {
        System.out.printf("Process: %s | Arrival: %d | Burst: %d | Waiting: %d | Turnaround: %d%n",
                p.name, p.arrivalTime, p.burstTime, p.waitingTime, p.turnaroundTime);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for process " + (i + 1));
            System.out.print("Name: ");
            String name = scanner.next();
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            processes.add(new Process(name, arrivalTime, burstTime));
        }

        SJFScheduler scheduler = new SJFScheduler();
        scheduler.schedule(processes);

        scanner.close();
    }
}
