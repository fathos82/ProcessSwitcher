package org.fathos82.core.process;

public class NullProcess extends Process {
    private static NullProcess process = new NullProcess();
    public NullProcess() {
        super(null, 0, null);
    }

    public static NullProcess getInstance() {
        return process;
    }

    @Override
    public String toString() {
        return "";
    }
}
