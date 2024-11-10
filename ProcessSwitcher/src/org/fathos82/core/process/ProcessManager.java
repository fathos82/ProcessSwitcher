package org.fathos82.core.process;

import org.fathos82.core.kernel.KernelComponent;
import org.fathos82.core.scheduler.SchedulerComponent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static org.fathos82.core.process.ProcessStates.*;

public class ProcessManager extends KernelComponent {

    private final HashMap<Integer, Process> procesTable = new HashMap<>();
    private final Queue<Integer> killedProcessesId =  new LinkedList<>();
    private int nextId =0;
    private SchedulerComponent schedule;
    @Override
    public void init() {
        schedule = kernel.getComponent(SchedulerComponent.class);
    }


    public void kill(int processId){
        killedProcessesId.add(processId);
        procesTable.remove(processId);
        schedule.removeProcess(processId);

    }
    public int create(String processName){
        int pid = createNextId();
        Process process = new Process(processName, pid,  READY);
        procesTable.put(pid, process);
        schedule.scaleProcess(pid);
        return pid;
    }



    private int createNextId() {
        if (killedProcessesId.isEmpty()) {
            return nextId++;
        }
        else {
            return killedProcessesId.poll();
        }
    }




    public void saveContext(){
        System.out.println("Saving context");
    }


    public Process getProcess(int pid) {
        return procesTable.get(pid);
    }
}
