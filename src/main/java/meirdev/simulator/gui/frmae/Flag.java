/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

/**
 *
 * @author MEIRKA
 */
public class Flag {

    private int flagValue;
    private Boolean innerValue;

    public Flag(int newValue) {
        flagValue = newValue;
    }

    public Boolean getInnerValue() {
        if (flagValue == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }

    }

    public void setInnerValue(Boolean innerValue) {
        flagValue = 0;
        if (innerValue.equals(Boolean.TRUE)) {
            flagValue = 1;
        }
    }

    public int getFlagValue() {
        return flagValue;
    }

    public void setFlagValue(int flagValue) {
        this.flagValue = flagValue;
    }

}
