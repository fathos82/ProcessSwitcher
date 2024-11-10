package org.fathos82.core;

import org.fathos82.core.handlers.IrqHandlerComponent;
import org.fathos82.core.hardware.InterruptController;
import org.fathos82.core.process.Process;
import org.fathos82.core.process.ProcessManager;
import org.fathos82.core.scheduler.SchedulerComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Kernel {
    protected final int QUANTUM = 4;
    public ClockTimer clockTimer;
    protected Process currentProcess;

    protected IrqHandlerComponent irqComponent;
    protected SchedulerComponent schedule;
    protected ProcessManager processManager;

    private int maxTries = 100;
    List<KernelComponent> components = new ArrayList<KernelComponent>();


    public Kernel() {


        clockTimer = new ClockTimer();
    }
    public void instantiateComponents() {
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


    public <C extends KernelComponent> C  instantiateComponent(Class<C> clazz) {
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
            if (hasInterrupt()){
                resolveInterruptions();
            }
            else {
                process();
            }
    }

    private void process() {
        processCurrentProcess();
        clockTimer.pulse();
    }

    private void processCurrentProcess() {
    }

    private void resolveInterruptions() {
        while (hasInterrupt()) {
            irqComponent.handleIrq(InterruptController.getPriorityIrq());
        }
    }



    public void switchContext(){
        schedule.switchContext();
    }



    private boolean hasInterrupt() {
        return InterruptController.hasIrqs();
    }



    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process process) {
        currentProcess = process;
    }

    public void updateCurrentProcessTime(long time) {

    }
}
