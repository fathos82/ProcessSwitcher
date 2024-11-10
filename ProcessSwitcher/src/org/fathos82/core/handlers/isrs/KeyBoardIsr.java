package org.fathos82.core.handlers.isrs;


import org.fathos82.core.handlers.IrqTypes;
import org.fathos82.core.handlers.irqs.KeyBoardIrq;
import org.fathos82.core.kernel.Kernel;

public class KeyBoardIsr extends InterruptServiceRoutine<KeyBoardIrq> {

    public KeyBoardIsr(Kernel kernel) {
        super(kernel);
    }

    @Override
    public void handle(KeyBoardIrq irq) {
        System.out.println("KeyBoard Pressed");
        System.out.println(irq.key);

    }

    @Override
    public IrqTypes getIrqType() {
        return IrqTypes.KEYBOARD;
    }
}
