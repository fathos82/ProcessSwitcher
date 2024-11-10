package org.fathos82.core.process;

public class Process {
    private final String name;
    private int pid;              // Identificador de Processo (PID)
    private long processingTime;  // Tempo de Processamento (TP)
    private int programCounter;   // Contador de Programa (CP)
    private ProcessStates state;   // Estado do Processo (EP)
    private int ioOperationsCount; // Número de vezes que realizou uma operação de E/S (NES)
    private int cpuUsageCount;     // Número de vezes que usou a CPU (N_CPU)

    // Construtor
    public Process(String processName, int pid, ProcessStates state) {
        this.name = processName;
        this.pid = pid;
        this.state = state;
        this.processingTime = 0;
        this.programCounter = 0;
        this.ioOperationsCount = 0; // Inicializa como 0
        this.cpuUsageCount = 0;      // Inicializa como 0
    }

    // Getters e Setters
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public long getProcessingTime() {
        return processingTime;
    }


    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public ProcessStates getState() {
        return state;
    }

    public void setState(ProcessStates state) {
        this.state = state;
    }

    public int getIoOperationsCount() {
        return ioOperationsCount;
    }

    public void incrementIoOperationsCount() {
        this.ioOperationsCount++;
    }

    public int getCpuUsageCount() {
        return cpuUsageCount;
    }

    public void addCpuTime(long time) {
        this.processingTime += time;
    }

    public void incrementCpuUsageCount() {
        this.cpuUsageCount++;
    }
    // Método toString para exibir as informações do processo
    @Override
    public String toString() {
        return String.format(
                """
                Process Information:
                ----------------------------
                PID             : %d
                Processing Time : %d ms
                Program Counter : %d
                State           : %s
                I/O Operations   : %d
                CPU Usage Count  : %d
                ----------------------------""",
                pid, processingTime, programCounter, state, ioOperationsCount, cpuUsageCount
        );
    }
}
