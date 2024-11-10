package org.fathos82.core.hardware;
import org.fathos82.core.handlers.IrqTypes;
import java.util.ArrayDeque;
import java.util.Queue;

// Recebe interruções e repassa ao processador de acordo com a prioridade
public class InterruptController {
    public static boolean hasIrqs() {
        return !irqList.isEmpty();
    }

    private static Queue<IrqTypes> irqList = new ArrayDeque<>();
    public static void interrupt(IrqTypes irq) {
        irqList.add(irq);
    }
    public static IrqTypes getPriorityIrq() {
        return irqList.poll();
    }
}
