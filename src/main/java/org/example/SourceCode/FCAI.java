package org.example.SourceCode;
import java.util.*;

class FCAI {
    private double v1, v2;
    private List<fcai_process> processes;

    FCAI(List<fcai_process> procs, double vo, double vt) {
        processes = procs;
        v1 = vo;
        v2 = vt;
    }

    void schedule() {
        fcai_process p1 = processes.remove(0);
        int currentTime = p1.arrivalTime;
        boolean run = false;

        while (!processes.isEmpty() || run) {
            if (p1.q == 0) {
                // First time (non-preemptive)
                double num = (double) p1.quantum * (40.0 / 100.0);
                int q = Math.min((int) Math.ceil(num), p1.remainingTime);
                currentTime += q;
                p1.q = q;
                p1.remainingTime -= q;
            } else {
                // Check if quantum finished
                if ((p1.q == p1.quantum && p1.remainingTime <= 0) || p1.remainingTime <= 0) {
                    System.out.println("=====================================");
                    System.out.println(p1.name + " executes for " + p1.q + " units of time");
                    System.out.println(p1.name + " completed");
                    System.out.println("Current time is " + currentTime);
                    System.out.println("=====================================");

                    if (!processes.isEmpty()) {
                        p1 = processes.remove(0);
                        run = true;
                        continue;
                    } else {
                        // Processes finished
                        return;
                    }
                } else if (p1.q == p1.quantum) {
                    System.out.println("=====================================");
                    System.out.println(p1.name + " executes for " + p1.q + " units of time");
                    System.out.println("Remaining time is " + p1.remainingTime);
                    System.out.println("Current time is " + currentTime);
                    System.out.println("=====================================");

                    p1.quantum += 2;
                    double num = (10 - p1.priority) + ((double) p1.arrivalTime / v1) + ((double) p1.remainingTime / v2);
                    p1.fcai = (int) Math.ceil(num);
                    p1.q = 0;
                    fcai_process x = p1;

                    if (!processes.isEmpty()) {
                        p1 = processes.remove(0);
                        boolean ins = false;
                        for (int i = 0; i < processes.size(); i++) {
                            if (processes.get(i).arrivalTime > currentTime) {
                                processes.add(i, x);
                                ins = true;
                                break;
                            }
                        }
                        if (!ins) {
                            processes.add(x);
                        }
                        continue;
                    }
                }

                // Not first (preemptive)
                // Work 1 sec
                p1.q++;
                p1.remainingTime--;
                currentTime++;
            }

            boolean bolen = true;
            while (bolen && !processes.isEmpty()) {
                fcai_process chosen = null;
                int index = -1;
                boolean y = false;
                for (int i = 0; i < processes.size(); i++) {
                    fcai_process temp = processes.get(i);
                    if (temp.fcai < p1.fcai && temp.arrivalTime <= currentTime) {
                        bolen = false;
                        if (!y) {
                            chosen = temp;
                            index = i;
                            y = true;
                        } else {
                            if (temp.fcai < chosen.fcai) {
                                chosen = temp;
                                index = i;
                            } else if (temp.fcai == chosen.fcai && temp.arrivalTime < chosen.arrivalTime) {
                                chosen = temp;
                                index = i;
                            }
                        }
                    }
                }
                if (!bolen) {
                    int unused = p1.quantum - p1.q;
                    p1.quantum += unused;
                    double num = (10 - p1.priority) + ((double) p1.arrivalTime / v1) + ((double) p1.remainingTime / v2);
                    p1.fcai = (int) Math.ceil(num);
                    System.out.println(p1.name + " executes for " + p1.q + " units of time");
                    System.out.println("Remaining time is " + p1.remainingTime);
                    System.out.println("Current time is " + currentTime);
                    p1.q = 0;
                    fcai_process x = p1;
                    p1 = processes.remove(index);
                    boolean ins = false;
                    for (int i = 0; i < processes.size(); i++) {
                        if (processes.get(i).arrivalTime > currentTime) {
                            processes.add(i, x);
                            ins = true;
                            break;
                        }
                    }
                    if (!ins) {
                        processes.add(x);
                    }
                } else {
                    bolen = false;
                }
            }
        }
    }
}

