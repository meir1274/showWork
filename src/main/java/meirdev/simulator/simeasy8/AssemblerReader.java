/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.simeasy8;

import com.beust.jcommander.JCommander;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import meidev.compiler.ArgFormatError;
import meirdev.cpu.simeasy8.processor.instructions.utils.InstructionsLoader;
import meirdev.simulator.simeasy8.commands.CommandFile;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author MEIRKA
 */
public class AssemblerReader {

    
    static private int maxLineNumber = SimEasy8Constants.SIZE_OF_MEMORY;
    public boolean verbose;
    static private JCommander cmdr;

    public static void main(String[] args) {
        CommandFile assemblerReader = new CommandFile();
        cmdr = new JCommander(assemblerReader);
        cmdr.parse(args);

        Logger.getLogger(AssemblerReader.class.getName()).log(Level.INFO, "Verify file exist.");
        try {
            File f = new File(assemblerReader.fileName.get(0));
            if (!f.exists()) {
                Logger.getLogger(AssemblerReader.class.getName()).log(Level.SEVERE, "Parameter issue - file does not exist:["+f.getAbsolutePath()+"]");
                System.exit(-2);
            }
            List<String> assemblerLines = readLinesFromFile(f.getAbsolutePath());
            
            List<compiledLine> temp = assebleLines(assemblerLines);
            System.out.println(populateDisplay(temp));
        } catch (Exception e) {
            Logger.getLogger(AssemblerReader.class.getName()).log(Level.SEVERE, "Compilation error.", e);
        }
    }   

    static public List<compiledLine> assebleLines(List<String> assemblerLines) throws ArgFormatError {
        try {

            

            AssemblerManager am = new AssemblerManager();
            am.loadCpuDetails();
            verifyFileSize(assemblerLines);
            
            int lineNumber = 1;
            int errorCounter = 0;
            for (String asmLine : assemblerLines) {
                asmLine = asmLine.toUpperCase();
                Scanner sc = new Scanner(asmLine).useDelimiter(" ");
                String asmCommand = sc.next();
                if (!am.verifyCommand(asmCommand)) {
                    System.out.println("Compilation Error, line :[" + lineNumber + "] - no such command: " + asmCommand);
                    ++errorCounter;
                }

                System.out.println(asmLine);
                ++lineNumber;
            }
            if (errorCounter > 0) {
                System.exit(1);
            }

            List<AssemblerLine> als = new ArrayList<AssemblerLine>(assemblerLines.size());

            lineNumber = 1;
            for (String asmLine : assemblerLines) {
                AssemblerLine al = new AssemblerLine();
                al.setLineContent(asmLine);
                al.setLineNumber(lineNumber);
                als.add(al);
                lineNumber++;
            }
            List<compiledLine> temp = am.compile(als);
            
            return temp;
        } catch (Exception ex) {
            Logger.getLogger(AssemblerReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    public static List<SimpleCmdDetails> populateDisplay(List<compiledLine> cmpLines, String type) {
        List<SimpleCmdDetails> temp = new LinkedList<>();
        
        StringBuilder sb = new StringBuilder();
        int startingPlace = 0;
        for (compiledLine line : cmpLines) {
            
            String cmdText = line.getAssemblerLine().getLineContent();
            int reqBytes = 1;
            if (line.getSecAddress() != null) {
                ++reqBytes;
            }
            
            SimpleCmdDetails scd = new SimpleCmdDetails(cmdText, reqBytes);
            scd.setStartingAddress(startingPlace);
            temp.add(scd);
            startingPlace+=reqBytes;
        }
        return temp;
    }
    
    public static String populateDisplay(List<compiledLine> temp) {
        StringBuilder sb = new StringBuilder();
        for (compiledLine line : temp) {
            sb.append(buildLineDisplay(line, 1)+"\n");
            if (line.getSecAddress() != null) {
                buildLineDisplay(line, 2);
                sb.append(buildLineDisplay(line, 2)+"\n");
            }
        }
        return sb.toString();
    }
    public static byte[]  populateDisplay(List<compiledLine> temp, int type) {
        int reqSize = Math.min(256,temp.size()*2);
        byte[]  sb = new byte[reqSize];
        int currCounter =0;
        for (compiledLine line : temp) {
            CpuAddress currAddr = null;
            currAddr = line.getAddress();
            sb[currCounter++] = currAddr.getValue();
            if (line.getSecAddress() != null) {
                currAddr = line.getSecAddress();
                sb[currCounter++] = currAddr.getValue();
            }
        }
        return sb;
    }
    private static List<String> readLinesFromFile(String fileName) throws IOException {
        File asmFileName = new File(fileName);
        logFileName(asmFileName);
        Charset charset = Charset.defaultCharset();
        List<String> assemblerLines = FileUtils.readLines(asmFileName, charset);
        return assemblerLines;
    }

    private static void logFileName(File asmFileName) {
        String strCfgLog = crtFileNameLog(asmFileName);
        Logger.getLogger(AssemblerReader.class.getName()).log(Level.CONFIG, strCfgLog);
    }

    private static String crtFileNameLog(File f) {
        String realFileName = f.getAbsolutePath();
        String logTitle =  "File name founded: ";
        String strCfgLog = logTitle+realFileName;
        return strCfgLog;
    }

    private static String buildLineDisplay(compiledLine line, int type) {
        CpuAddress currAddr = null;
        if (type == 1) {
            currAddr = line.getAddress();
        } else {
            currAddr = line.getSecAddress();
        }
        int startingAddress = currAddr.getAddress();
        int byteValue = currAddr.getValue();
        String valTemp = Integer.toHexString(byteValue);
        if (valTemp.length() == 8) {
            valTemp = valTemp.substring(6);
        } else if (valTemp.length() == 1) {
            valTemp = "0" + valTemp;
        }
        String byteBin = "00000000" + Integer.toBinaryString(byteValue);
        byteBin = byteBin.substring(byteBin.length() - 8);
        String cmdVal = "                                        " + line.getAssemblerLine().getLineContent();
        cmdVal = cmdVal.substring(cmdVal.length() - 30);
        return(cmdVal + " " + Integer.toHexString(startingAddress) + "," + byteBin + "  (" + valTemp.toUpperCase() + ")");
    }

    static private void verifyFileSize(List<String> fileLines) {
        if (fileLines.size() > maxLineNumber) {
            Logger.getLogger(AssemblerReader.class.getName()).log(Level.SEVERE, "The file contains too much lines. :["+fileLines.size()+"]. The max number could be :"+maxLineNumber);
            
            System.exit(0);
        }
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}
