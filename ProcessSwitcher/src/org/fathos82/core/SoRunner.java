package org.fathos82.core;

import org.fathos82.core.hardware.ProgrammableIntervalTimer;
import org.fathos82.core.kernel.Kernel;

import java.util.ArrayList;
import java.util.List;

public class SoRunner  {

    public List<Runnable> runnables;

    public SoRunner() {
        runnables = new ArrayList<Runnable>();
    }

    public void addRunnable(Runnable runnable) {
        runnables.add(runnable);
    }

    public void run() {
        while (true) {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        }
    }
}
