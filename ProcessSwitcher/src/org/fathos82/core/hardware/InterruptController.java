package org.fathos82.core;

import org.fathos82.core.handlers.Irq;

import java.util.ArrayDeque;

import java.util.Queue;

public class InterruptController {
    public static boolean hasIrqs() {
        return !irqList.isEmpty();
    }

    private static Queue<Irq> irqList = new ArrayDeque<>();
    public static void interrupt(Irq irq) {
        irqList.add(irq);
    }
    public static Irq getPriorityIrq() {
        return irqList.poll();
    }
}
