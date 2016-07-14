/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.customized.rendereners;

import meirdev.simulator.gui.customized.models.SimulatorJListModel;
import java.awt.Color;
import java.awt.Component;
import java.util.logging.Level;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import meirdev.simulator.gui.frmae.GuiSimulator;

/**
 *
 * @author MEIRKA
 */
public class FocusedTitleListCellRenderer implements ListCellRenderer {

    protected static Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);
    protected static TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(),
            "Next instruction:");
    protected static TitledBorder changedDataBorder = new TitledBorder(LineBorder.createBlackLineBorder(),
            "Changed data:");
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
        renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);

        if (index == list.getSelectedIndex()) {
            return renderer;
        }
        ListModel temp = list.getModel();
        if (temp instanceof SimulatorJListModel) {
            SimulatorJListModel temp2 = (SimulatorJListModel) (temp);
         //   if (temp2.)
         
            if (index == temp2.getLastChangedValue()) {
                renderer.setBorder(changedDataBorder);

                renderer.setBackground(Color.red);
            }
        }
        return renderer;
    }

}
