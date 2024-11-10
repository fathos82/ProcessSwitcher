package org.fathos82.core.hardware;

import org.fathos82.core.Runnable;

import static org.fathos82.core.handlers.IrqTypes.INTERRUPT_TIMER;

public class ProgrammableIntervalTimer implements Runnable {
    private  int interval;
    private  int countTick;

    public ProgrammableIntervalTimer() {
        interval = 10; // 10 Ticks
        countTick = 0;
    }

    public void setInterval(int interval) {
        if (interval > 0) {
            this.interval = interval;
        } else {
            throw new IllegalArgumentException("O intervalo deve ser maior que zero.");
        }
    }
    public  int getInterval() {
        return interval;
    }
    private  void restartTick() {
        countTick = 0;
    }
    private  void countTick() {
        countTick++;
    }

    public void run() {
        countTick();
        if(countTick >= interval) {
            InterruptController.interrupt(INTERRUPT_TIMER);
            restartTick();
        }
    }

}
