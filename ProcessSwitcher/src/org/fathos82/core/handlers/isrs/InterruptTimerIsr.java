package org.fathos82.core.handlers.isrs;

import org.fathos82.core.handlers.InterruptServiceRoutine;
import org.fathos82.core.kernel.Kernel;
import org.fathos82.core.process.Process;

public class InterruptTimerIsr extends InterruptServiceRoutine<InterruptTimerIrq> {
    protected InterruptTimerIsr(Kernel kernel) {
        super(kernel);
    }

    @Override
    void handle(InterruptTimerIrq irq) {
        long elapsedTime = kernel.clockTimer.pulse();
        kernel.updateCurrentProcessTime(elapsedTime);
        Process currentProcess = kernel.getCurrentProcess();
        System.out.println(currentProcess.getCpuTime() % Kernel.QUANTUM);
        if (currentProcess.getCpuTime() % Kernel.QUANTUM == 0){
            kernel.switchContext();
        }
    }


    @Override
    Class<InterruptTimerIrq> getIrqType() {
        return InterruptTimerIrq.class;
    }




}
