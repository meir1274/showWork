/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.simeasy8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import meidev.compiler.ArgFormatError;
import meirdev.cpu.simeasy8.processor.Cpu;
import meirdev.cpu.simeasy8.processor.Instruction;
import meirdev.cpu.simeasy8.processor.OperationCode;
import meirdev.cpu.simeasy8.processor.instructions.utils.InstructionsLoader;
import meirdev.cpu.simeasy8.processor.instructions.utils.OptionalValue;

/**
 *
 * @author MEIRKA
 */
public class AssemblerManager {

    private Set<String> optionalCommands;
    private Map<String, Instruction> mapInstructions = new HashMap<String, Instruction>();

    public boolean verifyCommand(String cmd) {
        for (String optionalCommand : optionalCommands) {
            if (optionalCommand.equals(cmd)) {
                return true;
            }
        }
        return false;
    }

    public void loadCpuDetails() {
        InstructionsLoader il = new InstructionsLoader();
        Cpu cpu = il.loadInstructions();

        optionalCommands = new HashSet<>();
        for (Instruction ins : cpu.getCpuInstructionList().getInstruction()) {
            optionalCommands.add(ins.getInstructioName());
            mapInstructions.put(ins.getInstructioName(), ins);
        }
    }

    public List<compiledLine> compile(List<AssemblerLine> cmdList) throws ArgFormatError {
        List<compiledLine> als = new ArrayList<>(cmdList.size());
        int nextCpuAddress = 0;
        for (AssemblerLine assemblerLine : cmdList) {
            compiledLine line = new compiledLine(assemblerLine);

            CpuAddress address = new CpuAddress();
            address.setAddress(nextCpuAddress);
            line.setAddress(address);
            int usedBytes = compileLine(line);
            nextCpuAddress += usedBytes;
            als.add(line);
        }

        return als;

    }

    private int compileLine(compiledLine line) throws ArgFormatError {
        Scanner s = new Scanner(line.getAssemblerLine().getLineContent()).useDelimiter(" ");
        String cmdName = s.next();

        Instruction instruction = mapInstructions.get(cmdName);

        List<OperationCode> oc = instruction.getOperationList().getOperationCode();
        if (instruction.getOv().equals("NO")) {
            verify(s.hasNext());
            line.setOc(instruction.getOperationList().getOperationCode().get(0));
            buildCompiledLine(line);
            return 1;
        }

        for (OperationCode operationCode : oc) {
            int counter = handleArgs(s, line, instruction.getType(), operationCode);

            if (counter > 0) {
                buildCompiledLine(line);
                return counter;
            } else {
                Exception e = new Exception("Compilation error on linr: "+(-counter));
                throw new ArgFormatError(e);
            }
        }

        Exception e = new Exception("No arg for the command.");

        throw new ArgFormatError(e);

    }

    private void buildCompiledLine(compiledLine line) throws NumberFormatException {
        CpuAddress address = new CpuAddress();
        address.setAddress(line.getAddress().getAddress() + 1);
        String operCode = line.getOc().getValue();
        if (operCode.startsWith("0x")) {
            operCode = operCode.substring("0x".length());
        }
        byte bValue = Byte.parseByte(operCode, 16);
        line.getAddress().setValue(Byte.valueOf(Byte.parseByte(operCode, 16)));

        if (line.getReqWords() == 2) {
            int val = Integer.parseInt(line.getAssemblerLine().getArgValue(), 16);
            address.setValue((byte) val);
            line.setSecAddress(address);
        }
    }

    private boolean verifyTwoItems(String next) {
        Scanner s = new Scanner(next).useDelimiter(",");
        String firstValue = s.next();
        if (!s.hasNext()) {
            return false;
        }
        String secValue = s.next();
        if (secValue.length() > 2) {
            return false;
        }

        try {
            int val = Integer.parseInt(secValue, 16);
            System.out.println("val = " + val);
        } catch (java.lang.NumberFormatException e) {
            System.out.println("E :" + e.getMessage());
        }
        return true;
    }

    private int handleArgs(Scanner s, compiledLine line, String type, OperationCode operationCode) throws ArgFormatError {
        String realCommand = s.next();
        Scanner s2 = new Scanner(realCommand).useDelimiter(",");
        if (!s2.hasNext()) {
            Logger.getLogger(AssemblerManager.class.getName()).log(Level.SEVERE, "Assemble Error- Operand's command contains: :.["+realCommand+"],"+line.getAssemblerLine().getLineContent()+", In line :"+line.getAssemblerLine().getLineNumber());
            return -line.getAssemblerLine().getLineNumber();
        }
        String arg1 = s2.next();
        OptionalValue ov = null;

        if (type.equals("two_words")) {

            String arg2 = s2.next();

            if (arg1.equals("RA")) {
                ov = OptionalValue.valueOf("OV_" + arg1);
                if (ov != null && ov.equals(OptionalValue.valueOf(operationCode.getOvValue1()))) {
                    verify(OptionalValue.valueOf(operationCode.getOvValue2()), arg2);
                    line.setOc(operationCode);
                    line.setReqWords(2);
                    line.getAssemblerLine().setArgValue(arg2);
                    return 2;
                }
                return 0;
            } else if (arg2.equals("RA")) {
                ov = OptionalValue.valueOf("OV_" + arg2);
                if (ov != null && ov.equals(OptionalValue.valueOf(operationCode.getOvValue1()))) {
                    try {
                        verify(OptionalValue.valueOf(operationCode.getOvValue1()), arg1);
                        line.setOc(operationCode);
                        line.setReqWords(2);
                        line.getAssemblerLine().setArgValue(arg1);
                        return 2;
                    } catch (ArgFormatError ex) {
                        Logger.getLogger(AssemblerManager.class.getName()).log(Level.SEVERE, null, ex);
                        throw ex;
                    }
                }

            }
            return 0;

        }
        if (type.equals("one_word")) {
            if (arg1.equals("RA")) {
                ov = OptionalValue.valueOf("OV_" + arg1);
                if (ov != null && ov.equals(OptionalValue.valueOf(operationCode.getOvValue1()))) {
                    line.setOc(operationCode);
                    line.setReqWords(1);
                    return 1;
                } else {
                    verify(true);
                }
            }

            verify(OptionalValue.valueOf(operationCode.getOvValue1()), arg1);
            line.setOc(operationCode);
            line.setReqWords(2);
            line.getAssemblerLine().setArgValue(arg1);
            return 2;

        }
        return 1;
    }

    private void verify(OptionalValue ov, String arg) throws ArgFormatError {
        try {
            int val = Integer.parseInt(arg, 16);
        } catch (NumberFormatException nfe) {
            throw new ArgFormatError(nfe);
        }
    }

    private void verify(boolean hasNext) throws ArgFormatError {
        Exception e = new Exception("No arg for the command.");
        if (hasNext) {
            throw new ArgFormatError(e);
        }
    }
}
