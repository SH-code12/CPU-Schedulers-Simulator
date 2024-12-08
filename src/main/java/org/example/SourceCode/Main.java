package org.example.SourceCode;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();
        List<fcai_process> prcs = new ArrayList<>();
        System.out.println("Welcome  \uD83D\uDE0A \uD83D\uDC4B ");

        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter the context switching time: ");
        int contextSwitchTime = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter Name of Process " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.print("Enter Arrival Time of Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter Burst Time of Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter Priority of Process " +  (i + 1) + " (Low number has high Priority) " + ": ");
            int priority = scanner.nextInt();
            System.out.print("Enter Quantum of Process " +  (i + 1)  + ": ");
            int Quantum = scanner.nextInt();
            processes.add(new Process(name, arrivalTime, burstTime, priority));
            fcai_process pro = new fcai_process(name, arrivalTime, burstTime, priority, Quantum, 0);
            prcs.add(pro);
        }

        System.out.println("Choose Scheduling Algorithm:\n"
                + "1. Non-Preemptive Priority Scheduling\n"
                + "2. Non-Preemptive SJF Scheduling\n"
                + "3. SRTF(Preemptive SJF) Scheduling\n"
                + "4. FCAI Scheduling\n"
                + "5. Exit");
        int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Scheduler PriorityScheduler = new NonPreemptivePriorityScheduler(processes , contextSwitchTime);
                    PriorityScheduler.schedule();
                    PriorityScheduler.Display();
                    break;
                case 2:
                    Scheduler SJFScheduler = new SJFScheduler(processes, contextSwitchTime);
                    SJFScheduler.schedule();
                    SJFScheduler.Display();

                    break;
                case 3:
                    Scheduler SRTFScheduler = new SRTFScheduler(processes , contextSwitchTime);
                    SRTFScheduler.schedule();
                    SRTFScheduler.Display();
                    break;
                case 4:
                    // Calculate v1 and v2
                    double v1 = prcs.get(prcs.size() - 1).arrivalTime / 10.0;
                    System.out.println("v1 is " + v1);
                    double v2 = 0;
                    for (fcai_process x : prcs) {
                        v2 = Math.max(v2, (double) x.burstTime);
                    }
                    v2 /= 10.0;
                    System.out.println("v2 is " + v2);

                    // Calculate fcai for each process
                    for (fcai_process x : prcs) {
                        double num = (10 - x.priority) + ((double) x.arrivalTime / v1) + ((double) x.remainingTime / v2);
                        x.fcai = (int) Math.ceil(num);
                        System.out.println("FCAI Factor for " + x.name + " = " + x.fcai);
                    }

                    new FCAI(prcs, v1, v2).schedule();
                    scanner.close();
                    break;
                case 5:
                    System.out.println("Thank you for using our Program \n");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.Please Enter (1 OR 2 OR 3 OR 4 OR 5) \n");
                    break;

            }
    }
}
