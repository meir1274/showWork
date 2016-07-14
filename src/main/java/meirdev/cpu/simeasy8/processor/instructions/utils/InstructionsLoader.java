/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.cpu.simeasy8.processor.instructions.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import meirdev.cpu.simeasy8.processor.Cpu;
import meirdev.cpu.simeasy8.processor.CpuInstructionList;
import meirdev.cpu.simeasy8.processor.Instruction;
import meirdev.simulator.simeasy8.AssemblerManager;

/**
 *
 * @author MEIRKA
 */
public class InstructionsLoader {

    public InstructionsLoader() {
    }

    public Cpu loadInstructions() {
        Cpu cpu = null;
        try {
            Logger.getLogger(InstructionsLoader.class.getName()).log(Level.INFO, "Start loads CPU details.");

            InputStream is = getClass().getResourceAsStream("/Easy8Instructions.xml");
           // File file = new File(is);
            JAXBContext jaxbContext = JAXBContext.newInstance(Cpu.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            cpu = (Cpu) jaxbUnmarshaller.unmarshal(is);
            System.out.println(cpu);
            CpuInstructionList cil = cpu.getCpuInstructionList();
            
            if ( Logger.getLogger(InstructionsLoader.class.getName()).isLoggable(Level.FINE)) {
                for (Instruction object : cil.getInstruction()) {
                    Logger.getLogger(InstructionsLoader.class.getName()).log(Level.FINE, "Instruction is:" + object);
                }
            }
            Logger.getLogger(InstructionsLoader.class.getName()).log(Level.INFO, "Finish loads CPU details.");
            is.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(InstructionsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cpu;
    }
}
