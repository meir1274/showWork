/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.logical;

import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.logical.cpumembers.ProcessorRegisters;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import meirdev.simulator.debugger.Breakpoint;
import meirdev.simulator.debugger.DebuggerAdapter;
import meirdev.simulator.debugger.DebuggerManager;
import meirdev.simulator.gui.events.GuiEvent;
import meirdev.simulator.gui.frmae.JumpEvent;
import meirdev.simulator.gui.customized.models.SimulatorJListModel;

/**
 *
 * @author MEIRKA
 */
public class LogicalCpu {

    private DebuggerManager debuggerManager;
    private static volatile LogicalCpu instance;
    private ProcessorRegisters pr;
    private Processor processor;
    private Deque<Byte> stack;
    private JumpEvent jumpEvent;
    private SimulatorJListModel memoryModel;
    private List<GuiEvent> guiEventsList;
    private DebuggerAdapter debuggerAdapter;

    public ProcessorRegisters getPr() {
        return pr;
    }

    private LogicalCpu() {
        pr = new ProcessorRegisters();
        processor = new Processor();
        guiEventsList = new ArrayList<GuiEvent>();
        debuggerManager = new DebuggerManager();
        debuggerAdapter = new DebuggerAdapter();
    }

    public static LogicalCpu getInstance() {
        LogicalCpu instanceInner = LogicalCpu.instance;
        if (instanceInner == null) {
            synchronized (LogicalCpu.class) {
                instanceInner = LogicalCpu.instance;
                if (instanceInner == null) {
                    LogicalCpu.instance = instanceInner = new LogicalCpu();
                }
            }
        }
        return (instance == instanceInner) ? instance : null;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public Deque<Byte> getStack() {
        if (stack == null) {
            stack = new ArrayDeque<Byte>();
        }
        return stack;
    }

    public JumpEvent getJumpEvent() {
        return jumpEvent;
    }

    public void setJumpEvent(JumpEvent jumpEvent) {
        this.jumpEvent = jumpEvent;
    }

    public SimulatorJListModel getMemoryModel() {
        return memoryModel;
    }

    public void setMemoryModel(SimulatorJListModel memoryModel) {
        this.memoryModel = memoryModel;
    }

    public List<GuiEvent> getGuiEvents() {
        return guiEventsList;
    }

    public List<GuiEvent> getGuiEventsList() {
        return guiEventsList;
    }

    public void setGuiEventsList(List<GuiEvent> guiEventsList) {
        this.guiEventsList = guiEventsList;
    }

    public DebuggerManager getDebuggerManager() {
        return debuggerManager;
    }

    public DebuggerAdapter getDebuggerAdapter() {
        if (debuggerAdapter == null) {
            debuggerAdapter = new DebuggerAdapter();
        }
        if (debuggerAdapter.getBreakoutActiveList() == null) {
            List<Breakpoint> currList = new ArrayList<>(4);
            debuggerAdapter.setBreakoutActiveList(currList);
        }
        return debuggerAdapter;
    }

    public void setDebuggerAdapter(DebuggerAdapter debuggerAdapter) {
        this.debuggerAdapter = debuggerAdapter;
    }

}
