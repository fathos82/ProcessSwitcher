package org.fathos82.core.handlers.isrs;


import org.fathos82.core.handlers.IrqTypes;
import org.fathos82.core.kernel.Kernel;

public class KeyBoardIsr extends InterruptServiceRoutine {

    public KeyBoardIsr(Kernel kernel) {
        super(kernel);
    }

    @Override
    public void handle() {
        System.out.println("KeyBoard Pressed");
   }

    @Override
    public IrqTypes getIrqType() {
        return IrqTypes.KEYBOARD;
    }
}
