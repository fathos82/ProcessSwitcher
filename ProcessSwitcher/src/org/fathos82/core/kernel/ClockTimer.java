package org.fathos82.core;

public class ClockTimer {
    private long time;
    private long lastPulseTime;

    public ClockTimer() {
        time = 0;
        lastPulseTime = 0;
    }

    public void reset() {
        time = 0;
        lastPulseTime = 0;
    }

    public long pulse() {
        time++;

        long elapsedTime = time - lastPulseTime;

        lastPulseTime = time;

        return elapsedTime;
    }
}
