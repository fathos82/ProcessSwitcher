package org.fathos82.core.handlers.isrs;

import org.fathos82.core.handlers.IrqTypes;
import org.fathos82.core.kernel.Kernel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MouseIsr extends InterruptServiceRoutine{
    public MouseIsr(Kernel kernel) {
        super(kernel);
    }

    @Override
    public void handle() {
        try {
            String mouseInfo = new String(Files.readAllBytes(Path.of("/home/athos/IdeaProjects/ProcessSwitcher/src/org/fathos82/core/memory/keyboard-info")));
            System.out.println(mouseInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IrqTypes getIrqType() {
        return IrqTypes.MOUSE;
    }
}
