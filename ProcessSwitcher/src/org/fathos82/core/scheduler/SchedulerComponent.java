package org.fathos82.core.scheduler;
import org.fathos82.core.kernel.KernelComponent;
import org.fathos82.core.process.NullProcess;
import org.fathos82.core.process.Process;
import org.fathos82.core.process.ProcessManager;

import java.util.LinkedList;
import java.util.Queue;

import static org.fathos82.core.process.ProcessStates.BLOCKED;
import static org.fathos82.core.process.ProcessStates.RUNNING;

public class SchedulerComponent extends KernelComponent {
    private final Queue<Integer> readyProcesses = new LinkedList<>();
    private final Queue<Integer> blockedProcesses = new LinkedList<>();
    ProcessManager processManager;

    @Override
    public void init() {
        processManager = kernel.getComponent(ProcessManager.class);
    }



    public void scaleProcess(int processId) {
        readyProcesses.add(processId);
    }
    public void removeProcess(int processId) {
        readyProcesses.remove(processId);
        blockedProcesses.remove(processId);
    }

    public int blockProcess(int processId) {
        if (readyProcesses.remove(processId)) {
            blockedProcesses.add(processId);
            Process blockedProcess = processManager.getProcess(processId);
            blockedProcess.setState(BLOCKED);
        } else {
            //System.out.println("Process ID " + processId + " not found in ready processes.");
        }
        return processId;
    }

    public Process getNextProcess() {
        if (readyProcesses.isEmpty()) {
            return NullProcess.getInstance();
        }
        int nextProcessId = readyProcesses.poll();
        readyProcesses.add(nextProcessId);
        Process process = processManager.getProcess(nextProcessId);
        process.setState(RUNNING);
        return process;
    }


    public void unblockProcess(Integer blockedProcessId) {
        if (blockedProcesses.remove(blockedProcessId)) {
            blockedProcesses.remove(blockedProcessId);
            readyProcesses.add(blockedProcessId);
        }

    }

}
