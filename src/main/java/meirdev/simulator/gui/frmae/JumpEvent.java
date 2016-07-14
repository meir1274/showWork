/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import meirdev.simulator.logical.StepsModel;

/**
 *
 * @author MEIRKA
 */
public interface JumpEvent extends baseInterface{
    public int jumpTo(StepsModel sm);
}
