/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import meirdev.simulator.gui.customized.panels.CPanelSevenDigit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MEIRKA
 */
public class SevenDigitViewIT {
    
    public SevenDigitViewIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPreferredSize method, of class CPanelSevenDigit.
     */
    @Test
    public void testGetPreferredSize() {
        System.out.println("getPreferredSize");
        CPanelSevenDigit instance = new CPanelSevenDigit();
        Dimension expResult = null;
        Dimension result = instance.getPreferredSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of crtHorizPoligon method, of class CPanelSevenDigit.
     */
    @Test
    public void testCrtHorizPoligon() {
        System.out.println("crtHorizPoligon");
        Polygon currPoligon = null;
        CPanelSevenDigit instance = new CPanelSevenDigit();
        instance.crtHorizPoligon(currPoligon);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of crtVertPoligon method, of class CPanelSevenDigit.
     */
    @Test
    public void testCrtVertPoligon() {
        System.out.println("crtVertPoligon");
        Polygon currPoligon = null;
        CPanelSevenDigit instance = new CPanelSevenDigit();
        instance.crtVertPoligon(currPoligon);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paintComponent method, of class CPanelSevenDigit.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        Graphics g = null;
        CPanelSevenDigit instance = new CPanelSevenDigit();
        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
