package org.fathos82.core;

import java.util.List;

public abstract class KernelComponent  {
    public  Kernel kernel;

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    public abstract void init();

}
