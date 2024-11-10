package org.fathos82.core;


import org.fathos82.core.handlers.IrqTypes;
import org.fathos82.core.hardware.InterruptController;
import org.fathos82.core.hardware.ProgrammableIntervalTimer;
import org.fathos82.core.kernel.Kernel;
import org.fathos82.core.process.ProcessManager;
import org.fathos82.core.utils.RandomESGenerator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class So {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    public static void main(String[] args) {
        InterruptController.interrupt(IrqTypes.KEYBOARD);
        InterruptController.interrupt(IrqTypes.KEYBOARD);
        InterruptController.interrupt(IrqTypes.KEYBOARD);
        InterruptController.interrupt(IrqTypes.MOUSE);
        InterruptController.interrupt(IrqTypes.MOUSE);
        InterruptController.interrupt(IrqTypes.MOUSE);

        Kernel kernel = new Kernel();
        ProgrammableIntervalTimer timer = new ProgrammableIntervalTimer();
        RandomESGenerator randomESGenerator = new RandomESGenerator(kernel);

        SoRunner runner = new SoRunner();
//        kernel.getComponent(ProcessManager.class).create("Processo 01");
//        kernel.getComponent(ProcessManager.class).create("Processo 02");
//        kernel.getComponent(ProcessManager.class).create("Processo 03");
//        kernel.getComponent(ProcessManager.class).create("Processo 04");
//        kernel.getComponent(ProcessManager.class).create("Processo 05");
//
//        kernel.getComponent(ProcessManager.class).create("Processo 06");
//        kernel.getComponent(ProcessManager.class).create("Processo 07");
//        kernel.getComponent(ProcessManager.class).create("Processo 08");
//        kernel.getComponent(ProcessManager.class).create("Processo 09");
//        kernel.getComponent(ProcessManager.class).create("Processo 10");

        runner.addRunnable(timer);
        runner.addRunnable(kernel);
        runner.addRunnable(randomESGenerator);
        runner.run();






    }

    public static void log(Object message, String color) {
        System.out.println(color + message.toString() + RESET);
    }
}