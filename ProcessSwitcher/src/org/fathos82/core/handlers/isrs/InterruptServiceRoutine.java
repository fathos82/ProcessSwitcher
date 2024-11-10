package org.fathos82.core.handlers.isrs;

import org.fathos82.core.handlers.IrqTypes;
import org.fathos82.core.kernel.Kernel;

public abstract class InterruptServiceRoutine {
    public final Kernel kernel;

    public InterruptServiceRoutine(Kernel kernel) {
        this.kernel = kernel;
    }


    public abstract void handle();
    public abstract IrqTypes getIrqType();
}
