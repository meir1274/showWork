/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author MEIRKA
 */
public class JFrameExample {

    private JFrame frame;
    private JButton button;
    private JTextField tfield;
    private String nameTField;
    private int count;

    public JFrameExample() {
        nameTField = "tField";
        count = 0;
    }

    private void displayGUI() {
        frame = new JFrame("JFrame Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1, 2, 2));
        button = new JButton("Add JTextField");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                TestBean bean = new TestBean();
                BeanMap beanMap = new BeanMap(TestBean.class);
                Iterator<String> it = beanMap.keyIterator();
                
                Field[] fields = bean.getClass().getDeclaredFields();
                JLabel jlabel = new JLabel();
                for (Field field : fields) {
                    jlabel = new JLabel();
                    jlabel.setText(field.getName()+ count);
                    frame.add(jlabel);
                    tfield = new JTextField();
                tfield.setName(field.getName()+ count);
                count++;
                frame.add(tfield);
                }


                tfield = new JTextField();
                tfield.setName(nameTField + count);
                count++;
                frame.add(tfield);
                frame.revalidate();  // For JDK 1.7 or above.
                //frame.getContentPane().revalidate(); // For JDK 1.6 or below.
                frame.repaint();

            }
        });
        frame.add(button);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFrameExample().displayGUI();
            }
        });
    }
}
