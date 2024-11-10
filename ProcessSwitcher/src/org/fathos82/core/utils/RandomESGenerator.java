package org.fathos82.core.utils;

import org.fathos82.core.Runnable;
import org.fathos82.core.kernel.Kernel;
import org.fathos82.core.process.ProcessStates;
import org.fathos82.core.scheduler.SchedulerComponent;

import java.util.Random;

import static org.fathos82.core.process.ProcessStates.BLOCKED;

public class RandomESGenerator implements Runnable {
    private Random random = new Random();
    private Kernel kernel;
    private Integer blockedProcessId;
    public RandomESGenerator(Kernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public void run() {
        float nextFloat = random.nextFloat(100);
        if (blockedProcessId == null) {
            if (nextFloat <= 1) {
                blockedProcessId = kernel.getCurrentProcess().getPid();
                this.kernel.switchContext(BLOCKED);
            }
        }
        else if (nextFloat <= 30) {
            // InterruptController.interrupt();

            this.kernel.getComponent(SchedulerComponent.class).unblockProcess(blockedProcessId);
            blockedProcessId = null;
        }
        else if (nextFloat == 99.99) {
            this.kernel.terminateCurrentProcess();
        }


    }
}
