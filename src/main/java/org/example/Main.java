package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter Name of Process " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.print("Enter Arrival Time of Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter Burst Time of Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter Priority of Process " +  (i + 1) + " (Low number has high Priority) " + ": ");
            int priority = scanner.nextInt();
            processes.add(new Process(name, arrivalTime, burstTime, priority));
        }

        System.out.println("Choose Scheduling Algorithm:\n"
                + "1. Non-Preemptive Priority Scheduling\n"
                + "2. Non-Preemptive SJF Scheduling\n"
                + "3. SRTF(Preemptive SJF) Scheduling\n"
                + "4. FCAI Scheduling\n"
                + "5. Exit\n");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                Scheduler PriorityScheduler = new NonPreemptivePriorityScheduler(processes);
                PriorityScheduler.schedule();
                PriorityScheduler.Display();
                break;
            case 2:
                Scheduler SJFScheduler = new SJFScheduler(processes);
                SJFScheduler.schedule();
                SJFScheduler.Display();

                break;
            case 3:
                Scheduler SRTFScheduler = new SRTFScheduler(processes);
                SRTFScheduler.schedule();
                SRTFScheduler.Display();
                break;
            case 4:
                Scheduler FCAIScheduler= new FCAIScheduler(processes);
                FCAIScheduler.schedule();
                FCAIScheduler.Display();
                break;
                case 5:
                    System.out.println("Thank you for using our Program \n");
                    System.exit(0);
            default:
                System.out.println("Invalid choice.");
                return;
        }

    }
}
