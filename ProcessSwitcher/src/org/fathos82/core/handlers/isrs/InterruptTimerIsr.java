package org.fathos82.core.handlers.isrs;

import org.fathos82.core.handlers.IrqTypes;
import org.fathos82.core.kernel.Kernel;
import org.fathos82.core.process.Process;

import static org.fathos82.core.process.ProcessStates.READY;

public class InterruptTimerIsr extends InterruptServiceRoutine {
    public InterruptTimerIsr(Kernel kernel) {
        super(kernel);
    }

    @Override
    public void handle() {
        long elapsedTime = kernel.clockTimer.pulse();
        kernel.updateCurrentProcessTime(elapsedTime);
        Process currentProcess = kernel.getCurrentProcess();
        if (currentProcess.getProcessingTime() % Kernel.QUANTUM == 0){
            kernel.switchContext(READY);
        }
    }


    @Override
    public IrqTypes getIrqType() {
        return IrqTypes.INTERRUPT_TIMER;
    }




}
