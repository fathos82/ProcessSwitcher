package org.fathos82.core.kernel;

public abstract class KernelComponent  {
    protected  Kernel kernel;

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    public abstract void init();

}
