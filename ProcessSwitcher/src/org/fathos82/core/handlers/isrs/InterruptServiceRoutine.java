package org.fathos82.core.handlers;

import org.fathos82.core.kernel.Kernel;

public abstract class InterruptServiceRoutine<T extends Irq> {
    public final Kernel kernel;

    protected InterruptServiceRoutine(Kernel kernel) {
        this.kernel = kernel;
    }


    protected abstract void handle(T irq);
    abstract Class<T> getIrqType();
}
