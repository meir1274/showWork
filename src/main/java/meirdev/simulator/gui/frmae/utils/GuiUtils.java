/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JList;
import meirdev.simulator.gui.customized.models.SimulatorJListModel;
import meirdev.simulator.gui.interfaces.utils.GuiFormat;
import meirdev.simulator.gui.interfaces.utils.WrapHashMap;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.logical.enums.RefType;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author MEIRKA
 */
public class GuiUtils {

    private int memorySize = 256;
    private GuiAdapter ga = null;
    private JList<String> pcMemoryList;

    public GuiUtils(GuiAdapter ga, JList<String> mPcMemoryList) {
        this.ga = ga;
        this.pcMemoryList = mPcMemoryList;
    }

    public void resetMemoryModel() {

        Arrays.fill(ga.getPcMemory(), (byte) 0);
    }

    public SimulatorJListModel loadMemoryToModel() throws NumberFormatException {
        GuiFormat gf = new GuiFormat();
        ArrayList<String> v = new ArrayList<>();
        Map<String, String> temp5 = new HashMap<>();
        WrapHashMap hashMap = new WrapHashMap();
        v.clear();

        int[] mPcMemory = ga.getPcMemory();
        for (int b : mPcMemory) {

            String dataDisplay = gf.crtMemoryDisp(b);
            v.add(dataDisplay);
            temp5.put(dataDisplay, Integer.toString(b));
        }
        hashMap.setCurrMap(temp5);
        hashMap.setRealList(v);
        SimulatorJListModel memoryModel = new SimulatorJListModel(hashMap);
        LogicalCpu.getInstance().setMemoryModel(memoryModel);
        return memoryModel;
    }

    private void reloadMemory() {
        SimulatorJListModel memoryModel = loadMemoryToModel();
        pcMemoryList.setModel(memoryModel);
        pcMemoryList.updateUI();
    }

    public void resetMemory() {
        resetMemoryModel();
        reloadMemory();
    }

    public void modelToMemory() {
        for (int i = 0; i < 256; i++) {
            int nextValue = Integer.valueOf(LogicalCpu.getInstance().getMemoryModel().getIntElementAt(i, RefType.RT_REF));
            ga.getPcMemory()[i] = nextValue;
        }
    }

 

    public void fillMemoryFromFile(final JFileChooser fc) throws IOException {
        File file = fc.getSelectedFile();
        Logger.getLogger(this.getClass().getName()).info("Loadin: " + file.getName() + ".");
        String lines = FileUtils.readFileToString(file);
        lines = lines.replace("[", "");
        lines = lines.replace(" ", "");
        lines = lines.replace("]", "");
        Logger.getLogger(this.getClass().getName()).info("List of values from memory file : " + lines);
        Scanner s = new Scanner(lines);
        s.useDelimiter(",");
        int i = 0;
        while (s.hasNext()) {
            ga.getPcMemory()[i] = s.nextInt();

            ++i;
        }
        reloadMemory();
        Logger.getLogger(this.getClass().getName()).info("Folder name: " + file.getCanonicalPath());
    }
}
