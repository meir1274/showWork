/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 *
 * @author MEIRKA
 */
public class MouseMove implements MouseMotionListener{

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       JList l = (JList)e.getSource();
            ListModel m = l.getModel();
            int index = l.locationToIndex(e.getPoint());
            if( index>-1 ) {
                l.setToolTipText(m.getElementAt(index).toString());
            }
    }
    
}
