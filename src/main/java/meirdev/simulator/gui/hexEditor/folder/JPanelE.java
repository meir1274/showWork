/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.hexEditor.folder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author MEIRKA
 */
public class JPanelE extends JComponent{

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
    }

 

    public JPanelE() {
    }
    
 
    private final Dimension SIZE = new Dimension( 20, 50 );
    @Override
   public Dimension getPreferredSize() {
      return SIZE;
   }
}
