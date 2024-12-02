package org.example;
import java.util.*;


public class NonPreemptivePriorityScheduler {

    static class Process {
        String name;
        int arrivalTime;
        int burstTime;
        int priority;
        int waitingTime;
        int turnaroundTime;

        Process(String name, int arrivalTime, int burstTime, int priority) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            this.waitingTime = 0;
            this.turnaroundTime = 0;
        }
    }

    // Method to schedule processes using Non-Preemptive Priority Scheduling
    public void schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time
        int currentTime = 0;

        while (!processes.isEmpty()) {
            // Select the process with the highest priority that has already arrived
            int finalCurrentTime = currentTime;
            Process selectedProcess = processes.stream()
                    .filter(p -> p.arrivalTime <= finalCurrentTime)
                    .min(Comparator.comparingInt(p -> p.priority))
                    .orElse(null);

            if (selectedProcess != null) {
                currentTime = Math.max(currentTime, selectedProcess.arrivalTime);
                selectedProcess.waitingTime = currentTime - selectedProcess.arrivalTime;
                currentTime += selectedProcess.burstTime;
                selectedProcess.turnaroundTime = selectedProcess.waitingTime + selectedProcess.burstTime;
                printProcess(selectedProcess);
                processes.remove(selectedProcess);
            } else {
                currentTime++; // Increment time if no process is ready
            }
        }
    }

    // Method to print results for each process
    private void printProcess(Process p) {
        System.out.printf("Process: %s | Arrival: %d | Burst: %d | Priority: %d | Waiting: %d | Turnaround: %d%n",
                p.name, p.arrivalTime, p.burstTime, p.priority, p.waitingTime, p.turnaroundTime);
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
            System.out.print("Priority (lower number = higher priority): ");
            int priority = scanner.nextInt();

            processes.add(new Process(name, arrivalTime, burstTime, priority));
        }

        NonPreemptivePriorityScheduler scheduler = new NonPreemptivePriorityScheduler();
        scheduler.schedule(processes);

        scanner.close();
    }
}
