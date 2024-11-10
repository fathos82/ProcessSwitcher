package org.fathos82.core.process;

import org.fathos82.core.KernelComponent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static org.fathos82.core.process.ProcessStates.*;

public class ProcessManager extends KernelComponent {
    private final Queue<Integer> readyProcesses = new LinkedList<>();
    private final Queue<Integer> blockedProcesses = new LinkedList<>();
    private final HashMap<Integer, Process> procesTable = new HashMap<>();
    private final Queue<Integer> killedProcessesId =  new LinkedList<>();
    private int nextId =0;


    public void fork(){
        
    }

    public void kill(int processId){
        procesTable.remove(processId);
        blockedProcesses.remove(processId);
        killedProcessesId.add(processId);
    }
    public int create(String processName){
        int pid = createNextId();
        Process process = new Process(pid, processName, 0, kernel.QUANTUM, READY, 1);
        procesTable.put(pid, process);
        readyProcesses.add(pid);
        return pid;
    }

    Process getNextProcess(){
        return  null;
    }

    private int createNextId() {
        if (killedProcessesId.isEmpty()) {
            return nextId++;
        }
        else {
            return killedProcessesId.poll();
        }
    }


    public void switchContext() {
        saveContext();
        kernel.setCurrentProcess(getNextProcess());
    }

    public void saveContext(){
        Process  process = kernel.getCurrentProcess();
        processTable.addProcess(new Process(process.id(), process.cpuTime() + kernel.currentProcessTime(), process.quantumTime()));
    }
}
