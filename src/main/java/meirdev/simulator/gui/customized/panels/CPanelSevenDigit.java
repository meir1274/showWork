/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.customized.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author MEIRKA
 */
public class CPanelSevenDigit extends JPanel {

    private Polygon[][] segments;
    private int x;
    private int y;
    private final static int OFF = 0;
    private final static int ON = 1;
    private int[] number;
    private int[][] digits;

    private final static int one[] = {OFF, OFF, ON, ON, OFF, OFF, OFF};
    private final static int two[] = {ON, OFF, ON, OFF, ON, ON, ON};
    private final static int tree[] = {ON, OFF, ON, ON, OFF, ON, ON};
    private final static int four[] = {OFF, ON, ON, ON, OFF, ON, OFF};
    private final static int five[] = {ON, ON, OFF, ON, OFF, ON, ON};
    private final static int six[] = {ON, ON, OFF, ON, ON, ON, ON};
    private final static int seven[] = {ON, OFF, ON, ON, OFF, OFF, OFF};
    private final static int eight[] = {ON, ON, ON, ON, ON, ON, ON};
    private final static int nine[] = {ON, ON, ON, ON, OFF, ON, ON};
    private final static int zero[] = {ON, ON, ON, ON, ON, OFF, ON};
    private final static int aLetter[] = {ON, ON, ON, ON, ON, ON, OFF};
    private final static int bLetter[] = {ON, ON, ON, ON, ON, ON, ON};
    private final static int cLetter[] = {ON, ON, ON, ON, ON, ON, ON};
    private final static int dLetter[] = {ON, ON, ON, ON, ON, OFF, ON};
    private final static int eLetter[] = {ON, ON, OFF, OFF, ON, ON, ON};
    private final static int fLetter[] = {ON, ON, OFF, OFF, ON, ON, OFF};

    public CPanelSevenDigit() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        x = 0;
        y = 0;
        segments = new Polygon[2][7];
        int startingX = 0;
        segments[0] = createSegments(startingX);
        startingX += 55;
        segments[1] = createSegments(startingX);
        number = six;
        digits = new int[2][7];
        digits[0]=tree;
        digits[1]=four;
    }

    public Dimension getPreferredSize() {
        return new Dimension(120, 95);
    }

    private Polygon[] createSegments(int startingX) {
        Polygon[] localSegments = new Polygon[7];
        y = 0;
        x = 5 + startingX;
        y -= 5;
        int i = 0;
        localSegments[i] = new Polygon();
        crtHorizPoligon(localSegments[i]);

        x = 5 + startingX;
        y = 32;
        i = 5;
        localSegments[i] = new Polygon();
        crtHorizPoligon(localSegments[i]);

        x = 5 + startingX;
        y = 68;
        i = 6;
        localSegments[i] = new Polygon();
        crtHorizPoligon(localSegments[i]);

        x = 0 + startingX;
        y = 0;
        i = 1;
        localSegments[i] = new Polygon();
        crtVertPoligon(localSegments[i]);
        x = 38 + startingX;
        i = 2;
        localSegments[i] = new Polygon();
        crtVertPoligon(localSegments[i]);
        x = 38 + startingX;
        y = 38;
        i = 3;
        localSegments[i] = new Polygon();
        crtVertPoligon(localSegments[i]);
        x = 0 + startingX;
        y = 38;
        i = 4;
        localSegments[i] = new Polygon();
        crtVertPoligon(localSegments[i]);
        return localSegments;
    }

    public void crtHorizPoligon(Polygon currPoligon) {

        currPoligon.addPoint(x + 9, y + 12);
        currPoligon.addPoint(x + 40, y + 12);
        currPoligon.addPoint(x + 40, y + 20);
        currPoligon.addPoint(x + 9, y + 20);
    }

    public void crtVertPoligon(Polygon currPoligon) {

        currPoligon.addPoint(x + 9, y + 12);
        currPoligon.addPoint(x + 10, y + 12 - 2);
        currPoligon.addPoint(x + 15, y + 12);
        currPoligon.addPoint(x + 15, y + 40);
        currPoligon.addPoint(x + 10, y + 40 + 2);
        currPoligon.addPoint(x + 9, y + 40);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 7; i++) {
                setSegmentState(g, segments[j][i], digits[j][i]);
            }
        }
    }

    private void setSegmentState(Graphics graphics, Polygon segment, int state) {
        Color off = Color.red.brighter();
        Color on = Color.red.brighter().brighter().brighter().brighter().brighter();
        if (state == OFF) {
            graphics.setColor(off);
        } else {
            graphics.setColor(on);
            graphics.fillPolygon(segment);
        }

        graphics.drawPolygon(segment);
    }

    public int[] getNumber() {
        return number;
    }

    public void setNumber(int selectedNumber) {
        String hexValue = Integer.toHexString(selectedNumber);
        if (hexValue.length()==1) {
            hexValue ="0"+hexValue;
        }else if (hexValue.length() >2  ) {
            hexValue = hexValue.substring(hexValue.length()-2);
        }
        int []twoDigits = new int[2];
        

        for (int i=0; i<twoDigits.length;i++) {
            digits[i] = extract(Integer.valueOf(""+hexValue.charAt(i)));
        }
       
        this.updateUI();
    }

    private int[] extract(int twoDigit) {
        int []mSelectedNumber = new int[2];
        switch (twoDigit) {
            case 0:
                mSelectedNumber = zero;
                break;
            case 1:
                mSelectedNumber = one;
                break;
            case 2:
                mSelectedNumber = two;
                break;
            case 3:
                mSelectedNumber = tree;
                break;
            case 4:
                mSelectedNumber = four;
                break;
            case 5:
                mSelectedNumber = five;
                break;
            case 6:
                mSelectedNumber = six;
                break;
            case 7:
                mSelectedNumber = seven;
                break;
            case 8:
                mSelectedNumber = eight;
                break;
            case 9:
                mSelectedNumber = nine;
                break;
            
                
        }
        return mSelectedNumber;
    }

}
