package org.example;
import java.util.*;


public class SRTFScheduler implements Scheduler{

    private List<Process> processes;
    private List<String> executionOrder;

    public SRTFScheduler(List<Process> processes) {
        this.processes = processes;
        this.executionOrder = new ArrayList<>();
    }

    @Override
    public void schedule() {

    }

    @Override
    public void Display() {

    }
}
