package org.fathos82.core.kernel;

import org.fathos82.core.Runnable;
import org.fathos82.core.So;
import org.fathos82.core.handlers.IrqHandlerComponent;
import org.fathos82.core.hardware.InterruptController;
import org.fathos82.core.process.NullProcess;
import org.fathos82.core.process.Process;
import org.fathos82.core.process.ProcessManager;
import org.fathos82.core.process.ProcessStates;
import org.fathos82.core.scheduler.SchedulerComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.fathos82.core.So.*;

public class Kernel implements Runnable {

    public static final int QUANTUM = 100;

    public ClockTimer clockTimer;
    private Process currentProcess;
    private IrqHandlerComponent irqComponent;
    private SchedulerComponent schedule;
    private ProcessManager processManager;

    private List<KernelComponent> components = new ArrayList<>();

    public Kernel() {
        clockTimer = new ClockTimer();
        instantiateComponents();
    }

    private void instantiateComponents() {
        irqComponent = instantiateComponent(IrqHandlerComponent.class);
        schedule = instantiateComponent(SchedulerComponent.class);
        processManager = instantiateComponent(ProcessManager.class);
        initComponents();
    }

    private void initComponents() {
        for (KernelComponent component : components) {
            component.init();
        }
    }

    public <C extends KernelComponent> C instantiateComponent(Class<C> clazz) {
        try {
            KernelComponent component = clazz.getDeclaredConstructor().newInstance();
            component.setKernel(this);
            components.add(component);
            return (C) component;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public <C extends KernelComponent> C getComponent(Class<C> clazz) {
        for (KernelComponent component : components) {
            if (clazz.isAssignableFrom(component.getClass())) {
                return (C) component;
            }
        }
        throw new RuntimeException("Component not found");
    }

    public void run() {
        if (currentProcess == null) {
            setScheduledProcess();
        }
        process();
        if (hasInterrupt()) {
            resolveInterruptions();
        }
    }

    // Set the next scheduled process
    private void setScheduledProcess() {
        currentProcess = schedule.getNextProcess();
        currentProcess.incrementCpuUsageCount();
    }

    // Switch the context to the next process
    public void switchContext(ProcessStates targetState) {
        switch (targetState) {
            case READY:
                setScheduledProcess();
                printStatusFromSwitchContext(targetState, GREEN);
                break;
            case BLOCKED:
                blockCurrentProcess();
                setScheduledProcess();
                printStatusFromSwitchContext(targetState, YELLOW);
                break;
        }
    }

    private void printStatusFromSwitchContext(ProcessStates targetState, String color) {
        if (currentProcess == NullProcess.getInstance()) return;
        So.log("SWITCH CONTEXT", color);
        So.log("RUNNING >> "+targetState, color);

        So.log(currentProcess, color);
    }

    // Simulate the process execution by incrementing clock and program counter
    private void process() {
        clockTimer.pulse();
        currentProcess.addCpuTime(1);
        currentProcess.setProgramCounter(currentProcess.getProgramCounter() + 1);
    }

    // Handle interrupts if there are any
    private void resolveInterruptions() {
        while (hasInterrupt()) {
            irqComponent.handleIrq(InterruptController.getPriorityIrq());
        }
    }

    // Check if there are any interrupts
    private boolean hasInterrupt() {
        return InterruptController.hasIrqs();
    }

    // Getter and Setter for current process
    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process process) {
        currentProcess = process;
    }

    // Update the current process CPU time
    public void updateCurrentProcessTime(long time) {
        currentProcess.addCpuTime(time);
    }

    // Block the current process and schedule the next process
    public void blockCurrentProcess() {
        currentProcess.incrementIoOperationsCount();
        schedule.blockProcess(currentProcess.getPid());
    }

    public void terminateCurrentProcess() {
        processManager.kill(currentProcess.getPid());
        So.log( "TERMINATE CURRENT PROCESS", RED);
        So.log( currentProcess, RED);

    }
}
