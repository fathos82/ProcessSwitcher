package org.fathos82.core.handlers;

import org.fathos82.core.handlers.isrs.InterruptServiceRoutine;
import org.fathos82.core.handlers.isrs.InterruptTimerIsr;
import org.fathos82.core.handlers.isrs.KeyBoardIsr;
import org.fathos82.core.handlers.isrs.MouseIsr;
import org.fathos82.core.kernel.Kernel;
import org.fathos82.core.kernel.KernelComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;



@SuppressWarnings("ALL")
public class IrqHandlerComponent extends KernelComponent {
    private  Map<IrqTypes, InterruptServiceRoutine> irqList;

    @Override
    public void init() {
        this.irqList = new HashMap<>();
        registerIsrs();
    }
    public void registerIsrs(){
        registerIsr(InterruptTimerIsr.class);
        registerIsr(KeyBoardIsr.class);
        registerIsr(MouseIsr.class);
    }
    public void registerIsr(Class<? extends InterruptServiceRoutine> clazzz) {
        try {
            InterruptServiceRoutine isr = clazzz.getDeclaredConstructor(Kernel.class).newInstance(kernel);
            if (!irqList.containsKey(isr.getIrqType())) {
                irqList.put(isr.getIrqType(), isr);
            } else {
                throw new IllegalStateException("Handler already registered for this IRQ type");
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    public void handleIrq(IrqTypes irq){
        irqList.get(irq).handle();
    }

}
