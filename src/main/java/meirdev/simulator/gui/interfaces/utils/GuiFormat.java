/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.interfaces.utils;

/**
 *
 * @author MEIRKA
 */
public class GuiFormat {

    public String crtMemoryDisp(Integer b) throws NumberFormatException {
        String bStr = Integer.toString(b);
        String byteBin = "00000000" + Integer.toBinaryString(Integer.decode(bStr));
        byteBin = byteBin.substring(byteBin.length() - 8);
        String hexValue = Integer.toHexString(Integer.decode(bStr));
        if (hexValue.length() > 2) {
            hexValue = hexValue.substring(hexValue.length() - 2);
        }
        if (hexValue.length() < 2) {
            hexValue = "0" + hexValue;
        }
        String dataDisplay = byteBin + " (0x" + hexValue.toUpperCase() + ")";
        return dataDisplay;
    }
}
