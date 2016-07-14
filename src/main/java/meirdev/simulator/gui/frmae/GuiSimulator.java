/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meirdev.simulator.gui.frmae;

import java.awt.Color;
import meirdev.simulator.logical.MemoryData;
import meirdev.simulator.logical.LogicalCpu;
import meirdev.simulator.logical.cpumembers.Processor;
import meirdev.simulator.logical.cpumembers.ProcessorRegisters;
import meirdev.simulator.logical.enums.RefType;
import meirdev.simulator.logical.StepsModel;
import java.util.logging.Logger;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import javafx.scene.control.CheckBox;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import static javax.swing.UIManager.getDefaults;
import javax.swing.filechooser.FileNameExtensionFilter;
import meidev.compiler.ArgFormatError;
import meirdev.simulator.debugger.Breakpoint;
import meirdev.simulator.debugger.DebuggerAdapter;
import meirdev.simulator.debugger.DebuggerManager;
import meirdev.simulator.debugger.StepListener;
import meirdev.simulator.gui.customized.models.SimulatorActivitiesJlistModel;
import meirdev.simulator.gui.events.GuiEvent;
import meirdev.simulator.gui.events.implementations.GuiItems;
import meirdev.simulator.gui.interfaces.ActivityPieceINT;
import meirdev.simulator.gui.interfaces.ComponnetsPartINT;
import meirdev.simulator.gui.interfaces.GuiClickActivites;
import meirdev.simulator.gui.interfaces.GuiClickActivitiesIMP;
import meirdev.simulator.gui.interfaces.utils.GuiFormat;
import meirdev.simulator.gui.interfaces.utils.WrapHashMap;
import meirdev.simulator.gui.customized.rendereners.FunctionsOnLinesRenderer;
import meirdev.simulator.gui.customized.models.SimulatorJListModel;
import meirdev.simulator.gui.customized.rendereners.FocusedTitleListCellRenderer;
import meirdev.simulator.gui.frmae.utils.GuiAdapter;
import meirdev.simulator.gui.frmae.utils.GuiUtils;
import meirdev.simulator.simeasy8.SimpleCmdDetails;
import meirdev.simulator.simeasy8.compiledLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import static meirdev.simulator.simeasy8.AssemblerReader.populateDisplay;
import static meirdev.simulator.simeasy8.AssemblerReader.populateDisplay;
import static meirdev.simulator.simeasy8.AssemblerReader.assebleLines;
import static meirdev.simulator.simeasy8.AssemblerReader.populateDisplay;
import static meirdev.simulator.simeasy8.AssemblerReader.populateDisplay;

/**
 *
 * @author MEIRKA
 */
public class GuiSimulator extends javax.swing.JFrame implements ActionListener, GuiItems {

    private String lastFileName = null;
    private String lastFolderName = null;
    
    private byte[] pcMemoryAddr;
    private byte[] pcCpuAddr;
    private final int memorySize = 256;
    ArrayList<String> v = new ArrayList<>();
    ProcessorRegisters pr = LogicalCpu.getInstance().getPr();
    private GuiAdapter ga = null;

    private StepsModel sm = null;
    private JPanel holderPanel = null;

    /**
     * Creates new form GuiSimulator
     */
    public GuiSimulator() {
        
        ga = new GuiAdapter();
        
        

        pcMemoryAddr = crtAddrList2(memorySize);

        pcCpuAddr = crtAddrList2(memorySize);
        

        Vector<String> vAddress = new Vector<>();
        GuiUtils gu = new GuiUtils(ga, pcMemoryList);
        SimulatorJListModel memoryModel = gu.loadMemoryToModel();
        addIndexToDataModel(pcMemoryAddr, vAddress);
        getDefaults().put("ScrollBar.minimumThumbSize", new Dimension(29, 1));
        initComponents();

        List<String> vCpuAddress = new ArrayList<>();
        addIndexToDataModel(pcCpuAddr, vCpuAddress);

        pcMemoryList.setModel(memoryModel);
        pcAddrList.setListData(vAddress);
        saveMenuItem.setEnabled(false);
        pcAddrList.setSelectedIndex(0);

        newMemValue.addActionListener(this);
        selectedMemAddr.addActionListener(this);
        Vector<String> vInstList = new Vector<>();

        instList.setVisible(false);
        GridBagLayout bagLayout = new GridBagLayout();
        hideViewJpanel.setLayout(bagLayout);

        p1 = new JPanel1();
        p2 = new JPAnel2();
        GridBagConstraints bagConstraints = new GridBagConstraints();
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        hideViewJpanel.add(p1, bagConstraints);
        hideViewJpanel.add(p2, bagConstraints);
        p1.setVisible(true);
        p2.setVisible(false);
        jLabel14.setVisible(false);

        ListCellRenderer renderer = new FocusedTitleListCellRenderer();
        instList.setCellRenderer(renderer);
        pcMemoryList.setCellRenderer(renderer);
        FunctionsOnLinesRenderer renderMemoryToolbar = new FunctionsOnLinesRenderer();
        pcCpuIndex.setCellRenderer(renderMemoryToolbar);


        pcCpuIndex.setModel(new SimulatorActivitiesJlistModel(vCpuAddress));

        resetDisplay();
    }

     private void resetDisplay() {
        updateBatteryValue();
    }

   
    private JPanel1 p1;
    private JPAnel2 p2;

    /**
     *
     * @param vAddress
     * @throws NumberFormatException
     */
    public final void addIndexToDataModel(byte[] bList, List<String> vAddress) throws NumberFormatException {
        for (byte b : bList) {
            String bStr = Byte.toString(b);
            String hexValue = Integer.toHexString(Integer.decode(bStr));
            if (hexValue.length() > 2) {
                hexValue = hexValue.substring(hexValue.length() - 2);
            }
            if (hexValue.length() < 2) {
                hexValue = "0" + hexValue;
            }
            vAddress.add(hexValue.toUpperCase());
        }
    }

    /**
     * Create ZERO based block of running index to reqSize.
     *
     * @param reqSize
     * @return array of bytes contains list of index values
     */
    public final byte[] crtAddrList2(int reqSize) {
        byte[] localPcMemoryAddr = new byte[reqSize];
        for (int i = 0; i < reqSize; i++) {
            localPcMemoryAddr[i] = (byte) i;
        }
        return localPcMemoryAddr;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        cpuMemoryList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        hideViewJpanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pcCpuIndex = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pcAddrList = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        pcMemoryList = new javax.swing.JList<>();
        javax.swing.JPanel sdPanel = new meirdev.simulator.gui.customized.panels.CPanelSevenDigit();
        holderPanel = sdPanel;
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        stepButton1 = new javax.swing.JButton();
        resetButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jLabel9 = new javax.swing.JLabel();
        selectedMemAddr = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        newMemValue = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        cFlag = new javax.swing.JCheckBox();
        zFlag = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spValue = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        pcValue = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        raValue = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        valOfInp = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        instList = new javax.swing.JList<>();
        jLabel14 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        saveMenuItem1 = new javax.swing.JMenuItem();
        saveMenuItem2 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        runMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        cpuMemoryList.setFont(instList.getFont());
        cpuMemoryList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, instList, org.jdesktop.beansbinding.ELProperty.create("${background}"), cpuMemoryList, org.jdesktop.beansbinding.BeanProperty.create("background"));
        bindingGroup.addBinding(binding);

        cpuMemoryList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                cpuMemoryListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(cpuMemoryList);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(244, 208, 19));

        jEditorPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                changeText(evt);
            }
        });
        jEditorPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                handleOptionalActivities(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jEditorPane1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jEditorPane1);

        javax.swing.GroupLayout hideViewJpanelLayout = new javax.swing.GroupLayout(hideViewJpanel);
        hideViewJpanel.setLayout(hideViewJpanelLayout);
        hideViewJpanelLayout.setHorizontalGroup(
            hideViewJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        hideViewJpanelLayout.setVerticalGroup(
            hideViewJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 143, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hideViewJpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hideViewJpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, new javax.swing.border.MatteBorder(null)));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, instList, org.jdesktop.beansbinding.ELProperty.create("${background}"), jPanel2, org.jdesktop.beansbinding.BeanProperty.create("background"));
        bindingGroup.addBinding(binding);

        pcCpuIndex.setFont(instList.getFont());
        pcCpuIndex.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, instList, org.jdesktop.beansbinding.ELProperty.create("${background}"), pcCpuIndex, org.jdesktop.beansbinding.BeanProperty.create("background"));
        bindingGroup.addBinding(binding);

        pcCpuIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pcCpuIndexMouseClicked(evt);
            }
        });
        pcCpuIndex.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                pcCpuIndexValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(pcCpuIndex);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Editor");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Memory");

        jPanel3.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel3.setFont(instList.getFont());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pcMemoryList, org.jdesktop.beansbinding.ELProperty.create("${background}"), jPanel3, org.jdesktop.beansbinding.BeanProperty.create("background"));
        bindingGroup.addBinding(binding);

        pcAddrList.setFont(instList.getFont());
        pcAddrList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pcMemoryList, org.jdesktop.beansbinding.ELProperty.create("${background}"), pcAddrList, org.jdesktop.beansbinding.BeanProperty.create("background"));
        bindingGroup.addBinding(binding);

        pcAddrList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pcAddrListMouseClicked(evt);
            }
        });
        pcAddrList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                pcAddrListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(pcAddrList);

        pcMemoryList.setBackground(new java.awt.Color(0, 51, 255));
        pcMemoryList.setFont(instList.getFont());
        pcMemoryList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        pcMemoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pcMemoryList.setToolTipText("");
        pcMemoryList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pcMemoryListMouseClicked(evt);
            }
        });
        pcMemoryList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                pcMemoryListValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(pcMemoryList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                .addContainerGap())
        );

        sdPanel.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout sdPanelLayout = new javax.swing.GroupLayout(sdPanel);
        sdPanel.setLayout(sdPanelLayout);
        sdPanelLayout.setHorizontalGroup(
            sdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        sdPanelLayout.setVerticalGroup(
            sdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Processor");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Display");

        jToolBar1.setRollover(true);

        jButton2.setText("Test Activities");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        resetButton.setText("Reset");
        resetButton.setFocusable(false);
        resetButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(resetButton);

        stepButton.setText("Step");
        stepButton.setFocusable(false);
        stepButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stepButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(stepButton);

        stepButton1.setText("Run");
        stepButton1.setFocusable(false);
        stepButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stepButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stepButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(stepButton1);

        resetButton1.setText("Reset memory");
        resetButton1.setToolTipText("Reset all the memory to ZERO");
        resetButton1.setFocusable(false);
        resetButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        resetButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetMemoryButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(resetButton1);

        jPanel7.setBackground(new java.awt.Color(255, 255, 153));
        jPanel7.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel7.setFont(instList.getFont());

        jSlider1.setBackground(new java.awt.Color(255, 255, 153));
        jSlider1.setMaximum(255);
        jSlider1.setMinorTickSpacing(1);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Addr");

        selectedMemAddr.setFont(jPanel7.getFont());
        selectedMemAddr.setText("jTextField1");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Val");

        newMemValue.setFont(jPanel7.getFont());
        newMemValue.setText("jTextField1");
        newMemValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newMemValueKeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Ok");
        jButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jButton1StateChanged(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedMemAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newMemValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(selectedMemAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(newMemValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 255, 102));
        jPanel6.setBorder(new javax.swing.border.MatteBorder(null));

        cFlag.setBackground(new java.awt.Color(102, 255, 51));
        cFlag.setText("c");
        cFlag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cFlagActionPerformed(evt);
            }
        });

        zFlag.setBackground(new java.awt.Color(51, 255, 0));
        zFlag.setText("z");
        zFlag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zFlagActionPerformed(evt);
            }
        });

        jLabel8.setText("Flags");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cFlag)
                    .addComponent(zFlag))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(zFlag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cFlag)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 102, 51));

        jLabel5.setText("SP");

        spValue.setText("jTextField1");
        spValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spValueActionPerformed(evt);
            }
        });

        jLabel6.setText("PC");

        pcValue.setText("jTextField1");

        jLabel7.setLabelFor(raValue);
        jLabel7.setText("RA");

        raValue.setText("jTextField1");
        raValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raValueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pcValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(raValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(pcValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(raValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel9MousePressed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox1.setText("Bit 1");
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox2.setText("Bit 2");
        jCheckBox2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox3.setText("Bit 3");
        jCheckBox3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox4.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox4.setText("Bit 4");
        jCheckBox4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox5.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox5.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox5.setText("Bit 5");
        jCheckBox5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox6.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox6.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox6.setText("Bit 6");
        jCheckBox6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox7.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox7.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox7.setText("Bit 7");
        jCheckBox7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox8.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox8.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jCheckBox8.setText("Bit 8");
        jCheckBox8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jLabel11.setText("Inputs");

        valOfInp.setBackground(new java.awt.Color(255, 255, 0));
        valOfInp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        valOfInp.setText("jLabel15");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valOfInp))
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(valOfInp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(102, 102, 0));
        jPanel10.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel10.setFont(instList.getFont());

        jList1.setBackground(jPanel10.getBackground());
        jList1.setFont(jPanel10.getFont());
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(jList1);

        jList2.setBackground(jPanel10.getBackground());
        jList2.setFont(jPanel10.getFont());
        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane8.setViewportView(jList2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Stack");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Instruction cpu");

        instList.setBackground(new java.awt.Color(255, 255, 0));
        instList.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        instList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        instList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                instListMouseClicked(evt);
            }
        });
        instList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                instListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(instList);

        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("jLabel14");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFile(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveHandling(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        saveMenuItem1.setMnemonic('s');
        saveMenuItem1.setText("Save memory as ...");
        saveMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMemoryHandling(evt);
            }
        });
        fileMenu.add(saveMenuItem1);

        saveMenuItem2.setMnemonic('s');
        saveMenuItem2.setText("Load memory");
        saveMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMemoryHandler(evt);
            }
        });
        fileMenu.add(saveMenuItem2);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");
        editMenu.setEnabled(false);

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        runMenu.setText("Run");

        jMenuItem1.setText("Assemble");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        runMenu.add(jMenuItem1);

        menuBar.add(runMenu);

        jMenu1.setText("Reset");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem3.setText("Reset memory");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Reset RA ");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Reset SP ");
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Reset PC");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setText("jMenuItem7");
        jMenu1.add(jMenuItem7);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("RESET");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator2);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(sdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void handleOptionalActivities(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_handleOptionalActivities
        // TODO add your handling code here:
    }//GEN-LAST:event_handleOptionalActivities

    private void saveHandling(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveHandling
        if (lastFileName != null) {
            try {
                saveFileActivty(new File(lastFileName));
                return;
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        saveAsFile();
    }//GEN-LAST:event_saveHandling

    public void saveAsFile() throws HeadlessException {
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ASM FILES", "asm", "asembler");
        fc.setFileFilter(filter);

        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                if (FilenameUtils.getExtension(file.getName()) == null || !FilenameUtils.getExtension(file.getName()).equals("asm")) {
                    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + ".asm");
                }
                lastFileName = file.getAbsolutePath();
                saveFileActivty(file);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Logger.getLogger(this.getClass().getName()).info("Save command cancelled by user.");

        }
    }

    private void saveFileActivty(File file) throws IOException {
        Logger.getLogger(this.getClass().getName()).info("Saving: " + file.getName() + ".");
        FileUtils.writeStringToFile(file, jEditorPane1.getText());
    }

    private void saveMemoryActivty(File file) throws IOException {
        Logger.getLogger(this.getClass().getName()).info("Saving: " + file.getName() + ".");

        FileUtils.writeStringToFile(file, Arrays.toString(ga.getPcMemory()));
    }

    private void changeText(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_changeText
        Logger.getLogger(this.getClass().getName()).info("Save command cancelled by user.");
        JEditorPane editorPane = (JEditorPane) evt.getSource();
        if (editorPane.getText().length() > 15) {
            saveMenuItem.setEnabled(true);
        } else {
            saveMenuItem.setEnabled(false);
        }
    }//GEN-LAST:event_changeText

    private void openFile(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFile
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ASM FILES", "asm", "asembler");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                Logger.getLogger(this.getClass().getName()).info("Saving: " + file.getName() + ".");
                String lines = FileUtils.readFileToString(file);
                jEditorPane1.setText(lines);
                lastFileName = file.getAbsolutePath();
                lastFolderName = file.getParent();
                Logger.getLogger(this.getClass().getName()).info("Folder name: " + lastFolderName);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Logger.getLogger(this.getClass().getName()).info("Save command cancelled by user.");

        }
    }//GEN-LAST:event_openFile

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {

            p1.setVisible(false);
            p2.setVisible(true);
            jLabel14.setVisible(false);
            String[] lines = jEditorPane1.getText().split("\\r?\\n");

            List<String> asmLines = Arrays.asList(lines);
            List<compiledLine> temp = assebleLines(asmLines);
            byte[] str = populateDisplay(temp, 1);
            ArrayList<String> vAddress = new ArrayList<>();
            Map<String, String> temp5 = new HashMap<>();
            WrapHashMap hashMap = new WrapHashMap();
            hashMap.setCurrMap(temp5);
            hashMap.setRealList(vAddress);
            int index = 0;
            for (byte b : str) {

                GuiFormat gf = new GuiFormat();
                String dataDisplay = gf.crtMemoryDisp(Integer.valueOf(b));

                vAddress.add(dataDisplay);
                temp5.put(Integer.toString(index), Integer.valueOf((int) b).toString());
                ++index;
            }

            SimulatorJListModel memoryModel = new SimulatorJListModel(hashMap);
            cpuMemoryList.setModel(memoryModel);
            cpuMemoryList.updateUI();
            List<SimpleCmdDetails> temp3 = populateDisplay(temp, "");
            ArrayList<String> vInstructions = new ArrayList<>();
            sm = new StepsModel(pr, temp3, str);

            for (SimpleCmdDetails simpleCmdDetails : temp3) {
                String addr = Integer.toHexString(simpleCmdDetails.getStartingAddress());
                if (addr.length() == 1) {
                    addr = " " + addr;
                }
                String cmdDisp = addr + " " + simpleCmdDetails.getInstructionText().toUpperCase();
                cmdDisp = cmdDisp.toUpperCase();

                vInstructions.add(" " + cmdDisp);
                if (simpleCmdDetails.getReqRecords() == 2) {
                    vInstructions.add("*" + cmdDisp);

                }
            }

            SimulatorJListModel model = new SimulatorJListModel(vInstructions);
            instList.setModel(model);
            instList.setVisible(true);
            instList.updateUI();
            System.out.println(str);

            MemoryData md = new MemoryData(pcMemoryList, instList, cpuMemoryList);

            md.showImpactCommand(0);
            instList.ensureIndexIsVisible(0);

            stpesList = sm.iterator();

        } catch (Exception ex) {
            jLabel14.setVisible(true);
            jLabel14.setText("Assembled has been faild. Please fix the program lines and trye again.");
            java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void cpuMemoryListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_cpuMemoryListValueChanged
        // TODO add your handling code here:
        if (pcCpuIndex.getSelectedIndex() != cpuMemoryList.getSelectedIndex()) {
            pcCpuIndex.setSelectedIndex(cpuMemoryList.getSelectedIndex());
            pcCpuIndex.ensureIndexIsVisible(cpuMemoryList.getSelectedIndex());
            instList.setSelectedIndex(cpuMemoryList.getSelectedIndex());
            instList.ensureIndexIsVisible(cpuMemoryList.getSelectedIndex());
        }
    }//GEN-LAST:event_cpuMemoryListValueChanged

    private void spValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spValueActionPerformed

    private void zFlagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zFlagActionPerformed
        JCheckBox cCheckedBox = (JCheckBox) evt.getSource();
        pr.getzFlag().setFlagValue(cCheckedBox.isSelected() ? 1 : 0);
    }//GEN-LAST:event_zFlagActionPerformed

    private void cFlagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cFlagActionPerformed
        // TODO add your handling code here:
        JCheckBox cCheckedBox = (JCheckBox) evt.getSource();
        pr.getcFlag().setFlagValue(cCheckedBox.isSelected() ? 1 : 0);
    }//GEN-LAST:event_cFlagActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton1ActionPerformed

    private void pcAddrListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_pcAddrListValueChanged
        // TODO add your handling code here:
        pcMemoryList.setSelectedIndex(pcAddrList.getSelectedIndex());
        pcMemoryList.updateUI();
        pcMemoryList.ensureIndexIsVisible(pcAddrList.getSelectedIndex());
        selectedMemAddr.setText(Integer.toString(pcAddrList.getSelectedIndex()));
        selectedMemAddr.updateUI();
    }//GEN-LAST:event_pcAddrListValueChanged

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            newMemValue.setText(Integer.toString(source.getValue()));
        }
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jButton1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1StateChanged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        GuiClickActivites guiActivities = new GuiClickActivitiesIMP();
        guiActivities.updateMemory(pcMemoryList, pcMemoryList, selectedMemAddr, newMemValue);

    }//GEN-LAST:event_jButton1MouseClicked

    private void instListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_instListValueChanged

        if (cpuMemoryList.getSelectedIndex() != instList.getSelectedIndex()) {
            cpuMemoryList.setSelectedIndex(instList.getSelectedIndex());
            pcCpuIndex.setSelectedIndex(instList.getSelectedIndex());
            cpuMemoryList.ensureIndexIsVisible(instList.getSelectedIndex());
            pcCpuIndex.ensureIndexIsVisible(instList.getSelectedIndex());
        }
    }//GEN-LAST:event_instListValueChanged

    private void pcCpuIndexValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_pcCpuIndexValueChanged

        if (instList.getSelectedIndex() != pcCpuIndex.getSelectedIndex()) {
            instList.setSelectedIndex(pcCpuIndex.getSelectedIndex());
            instList.ensureIndexIsVisible(pcCpuIndex.getSelectedIndex());
            cpuMemoryList.setSelectedIndex(pcCpuIndex.getSelectedIndex());
            cpuMemoryList.ensureIndexIsVisible(pcCpuIndex.getSelectedIndex());
        }

    }//GEN-LAST:event_pcCpuIndexValueChanged

    private void pcMemoryListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcMemoryListMouseClicked

        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
            SimulatorJListModel temp = (SimulatorJListModel) list.getModel();
            String textValue = (String) temp.getIntElementAt(list.getSelectedIndex(), RefType.RT_REF);

            selectedMemAddr.setText(Integer.toString(list.getSelectedIndex()));
            newMemValue.setText(textValue);
            newMemValue.requestFocusInWindow();
        }
    }//GEN-LAST:event_pcMemoryListMouseClicked

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed

        saveAsFile();
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void instListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_instListMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_instListMouseClicked

    private void newMemValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newMemValueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_newMemValueKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        List<Breakpoint> tempList = LogicalCpu.getInstance().getDebuggerAdapter().getBreakoutActiveList();

        Breakpoint breakpoint = new Breakpoint();
        SimpleCmdDetails cmdDetails = new SimpleCmdDetails("TEST", 4);
        cmdDetails.setStartingAddress(12);
        breakpoint.setLinkToLine(cmdDetails);
        tempList.add(breakpoint);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
        if (stpesList.hasNext()) {

            doOneStep(stpesList.next());
        }
        dispNextPC();
    }//GEN-LAST:event_stepButtonActionPerformed

    private void raValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raValueActionPerformed
        // TODO add your handling code here:
        Integer newArValue = Integer.valueOf(raValue.getText());
        java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.INFO, "Ra value from user is {" + newArValue.toString() + "}");
        LogicalCpu.getInstance().getProcessor().setRa(newArValue.byteValue());
    }//GEN-LAST:event_raValueActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed

        int response;

        response = JOptionPane.showConfirmDialog(null, "Should i delete all files?");
        if (JOptionPane.YES_OPTION != response) {
            return;
        }
        ComponnetsPartINT cpint = LogicalCpu.getInstance().getProcessor();
        ComponnetsPartINT cprint = LogicalCpu.getInstance().getPr();
        cpint.reset();
        cprint.reset();
        this.updatePc(LogicalCpu.getInstance().getProcessor().getPc());
        this.updateRa(LogicalCpu.getInstance().getProcessor().getRa());
        this.updateSp(LogicalCpu.getInstance().getProcessor().getSp());
        
        GuiUtils gu = new GuiUtils(ga, pcMemoryList);
        gu.resetMemory();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void saveMemoryHandling(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMemoryHandling
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MEM FILES", "mem", "memory");
        fc.setFileFilter(filter);

        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                if (FilenameUtils.getExtension(file.getName()) == null || !FilenameUtils.getExtension(file.getName()).equals("mem")) {
                    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + ".mem");
                }
                lastFileName = file.getAbsolutePath();
                GuiUtils gu = new GuiUtils(ga,pcMemoryList);
                gu.modelToMemory();
                saveMemoryActivty(file);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Logger.getLogger(this.getClass().getName()).info("Save command cancelled by user.");

        }
    }//GEN-LAST:event_saveMemoryHandling

    private void loadMemoryHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMemoryHandler
        // TODO add your handling code here:

        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MEM FILES", "mem", "memory");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                GuiUtils gu = new GuiUtils(ga,pcMemoryList);
                gu.fillMemoryFromFile(fc);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Logger.getLogger(this.getClass().getName()).info("Save command cancelled by user.");

        }
    }//GEN-LAST:event_loadMemoryHandler



    private void pcMemoryListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_pcMemoryListValueChanged
        if (pcAddrList.getSelectedIndex() != pcMemoryList.getSelectedIndex()) {
            pcAddrList.setSelectedIndex(pcMemoryList.getSelectedIndex());
            pcAddrList.updateUI();
            pcAddrList.ensureIndexIsVisible(pcMemoryList.getSelectedIndex());
        }
        selectedMemAddr.setText(Integer.toString(pcMemoryList.getSelectedIndex()));
        selectedMemAddr.updateUI();
    }//GEN-LAST:event_pcMemoryListValueChanged

    private Iterator<Integer> stpesList;


    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        DebuggerManager dm = LogicalCpu.getInstance().getDebuggerManager();
        if (listeners == null) {
            listeners = new ArrayList<StepListener>();
        }
        listeners.add(new StepListener());

        while (stpesList.hasNext() && dm.couldRun()) {
            Integer nextStep = stpesList.next();
            doOneStep(nextStep);
            byte currPc = LogicalCpu.getInstance().getProcessor().getPc();
            fireAfterExecution(currPc);
        }
        dispNextPC();
        listeners.remove(listeners.get(0));
    }//GEN-LAST:event_runButtonActionPerformed

    private void pcAddrListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcAddrListMouseClicked
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            System.exit(4);
        };
    }//GEN-LAST:event_pcAddrListMouseClicked

    private void pcCpuIndexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pcCpuIndexMouseClicked

        DebuggerAdapter de = LogicalCpu.getInstance().getDebuggerAdapter();

        de.addRemoveBreakpoint(pcCpuIndex);


    }//GEN-LAST:event_pcCpuIndexMouseClicked
    private int counter = 0;
    private void jEditorPane1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane1KeyTyped
        // TODO add your handling code here:
        ++counter;
        if (counter > 15000) {
            JEditorPane editorPane = (JEditorPane) evt.getSource();
            if (editorPane.getText().length() < 2500) {
                counter = editorPane.getText().length();
            }
            jEditorPane1.setBackground(Color.CYAN);
        }
    }//GEN-LAST:event_jEditorPane1KeyTyped

    private void resetMemoryButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetMemoryButton1ActionPerformed
        GuiUtils gu = new GuiUtils(ga,pcMemoryList);
        gu.resetMemory();

    }//GEN-LAST:event_resetMemoryButton1ActionPerformed



    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        GuiUtils gu = new GuiUtils(ga,pcMemoryList);
        gu.resetMemory();
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jPanel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MousePressed
        if (SwingUtilities.isRightMouseButton(evt)) {
            Integer selectedValue =  pullInValue();
            
            String hexValue = Integer.toHexString(selectedValue);
            
            JOptionPane.showMessageDialog(this,
        "IN value contains:  '" + selectedValue.toString() + "'\nox"+hexValue);
        };
    }//GEN-LAST:event_jPanel9MousePressed

    public Integer pullInValue() throws NumberFormatException {
        String tempValue =  extractValueFromInput();
        Integer selectedValue = Integer.parseInt(tempValue,2);
        return selectedValue;
    }

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        updateBatteryValue();
    }//GEN-LAST:event_jCheckBox1StateChanged

    public void updateBatteryValue() throws NumberFormatException {
        // TODO add your handling code here:
        
        Integer selectedValue =  pullInValue();
        
        String hexValue = Integer.toHexString(selectedValue).toUpperCase();
        if (hexValue.length()==1){
            hexValue ="0"+ hexValue;
        }
        valOfInp.setText(hexValue);
    }

    public String extractValueFromInput() {
        List<JCheckBox> tempCheckBoxes = buildCheckBoxList();
        
        String tempStr = "";
        StringBuffer sb = new StringBuffer();
        for (JCheckBox inpComp : tempCheckBoxes) {
            Integer tempI = inpComp.isSelected() ==true ? 1:0;
            sb.append(tempI.toString());
        }
        
        return sb.toString();
    }

    public List<JCheckBox> buildCheckBoxList() {
        List<JCheckBox> tempCheckBoxes = new ArrayList<>();
        tempCheckBoxes.add(jCheckBox1);
        tempCheckBoxes.add(jCheckBox2);
        tempCheckBoxes.add(jCheckBox3);
        tempCheckBoxes.add(jCheckBox4);
        tempCheckBoxes.add(jCheckBox5);
        tempCheckBoxes.add(jCheckBox6);
        tempCheckBoxes.add(jCheckBox7);
        tempCheckBoxes.add(jCheckBox8);
        return tempCheckBoxes;
    }

    public void doOneStep(Integer nextStep) {

        nextStepActivity(nextStep);

        zFlag.setSelected(pr.getzFlag().getFlagValue() == 1);
        cFlag.setSelected(pr.getcFlag().getFlagValue() == 1);

        showPre();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        getDefaults().put("ScrollBar.minimumThumbSize", new Dimension(29, 1));
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiSimulator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JCheckBox cFlag;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JList<String> cpuMemoryList;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPanel hideViewJpanel;
    private javax.swing.JList<String> instList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField newMemValue;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JList<String> pcAddrList;
    private javax.swing.JList<String> pcCpuIndex;
    private javax.swing.JList<String> pcMemoryList;
    private javax.swing.JTextField pcValue;
    private javax.swing.JTextField raValue;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton resetButton1;
    private javax.swing.JMenu runMenu;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveMenuItem1;
    private javax.swing.JMenuItem saveMenuItem2;
    private javax.swing.JTextField selectedMemAddr;
    private javax.swing.JTextField spValue;
    private javax.swing.JButton stepButton;
    private javax.swing.JButton stepButton1;
    private javax.swing.JLabel valOfInp;
    private javax.swing.JCheckBox zFlag;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != newMemValue) {
            if (e.getSource() == selectedMemAddr) {
                int address = 255;
                address = handleInvalidNumber(address);
                pcAddrList.setSelectedIndex(address);
                pcMemoryList.setSelectedIndex(address);
                pcAddrList.ensureIndexIsVisible(address);
                pcMemoryList.ensureIndexIsVisible(address);

            }
            return;
        }
        String text = newMemValue.getText();
        try {
            Integer num = Integer.parseInt(text);
        } catch (Exception err) {
            int pic = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(null, "enter only numbers. Wrong value was received, " + text, "", pic);
        }
        if (Integer.valueOf(text) > 255) {
            JOptionPane.showMessageDialog(null, "Change to the highest optional value.", "alert", JOptionPane.CANCEL_OPTION);
            newMemValue.setText("255");
            jSlider1.setValue(Integer.valueOf(newMemValue.getText()));
            return;
        }
        jSlider1.setValue(Integer.valueOf(text));
    }

    public int handleInvalidNumber(int address) throws HeadlessException {
        try {
            address = Integer.parseInt(selectedMemAddr.getText());
            if (address > 255) {
                address = 255;
                JOptionPane.showMessageDialog(null, "Change to the highest optional address.", "alert", JOptionPane.CANCEL_OPTION);
            }
            if (address < 0) {
                address = 255;
                JOptionPane.showMessageDialog(null, "Change to the highest optional address.", "alert", JOptionPane.CANCEL_OPTION);
            }
        } catch (Exception err) {
            int pic = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(null, "enter only numbers. Wrong value was received, " + selectedMemAddr.getText(), "", pic);
        }
        return address;
    }

    public void showPre() {
        if (Math.PI != Math.E) {
            return;
        }
        int nextStep = 0;
        JList memoryValue = pcMemoryList;
        Command cmd = sm.getCurrCommand();

        cmd.showPre();
        int impactAddress = cmd.fetchCmdValue();
        MemoryData md = new MemoryData(pcMemoryList, instList, cpuMemoryList);
        md.showImpactMemory(impactAddress);
//        ShowImpactFlag();
        //       showInput(raValue);

    }

    private void showImpactMemory(JList memoryValue, int impactAddress) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showInput(JTextField raValue) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void ShowImpactFlag() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private transient List<StepListener> listeners;

    protected void firePreExecution(SimpleCmdDetails cmd) {
        // if we have no listeners, do nothing...
        if (listeners != null && !listeners.isEmpty()) {

            // create the event object to send
            ActionEvent event
                    = new ActionEvent(cmd, System.identityHashCode(cmd), cmd.toString());

            // make a copy of the listener list in case
            //   anyone adds/removes listeners
            List<StepListener> targets;
            synchronized (this) {
                targets = listeners.subList(0, listeners.size() - 1);
            }

            // walk through the listener list and
            //   call the sunMoved method in each
            targets.stream().forEach((target) -> {
                target.actionPerformed(event);
            });
        }
    }

    private void nextStepActivity(Integer nextStepPC) {

        //    instList.setBackground(Color.WHITE);
        MemoryData md = new MemoryData(pcMemoryList, instList, cpuMemoryList);
        Command cmd = sm.getCurrCommand();

        //    firePreExecution(cmd.getCmdDetails());
        List queue = md.executeCommand(cmd);
        getGuiEventList().clear();
        handleEvents(queue);

        handleGuiEvents(getGuiEventList());
        JumpEvent je = LogicalCpu.getInstance().getJumpEvent();
        if (je != null) {
            nextStepPC = je.jumpTo(sm);
        }
        LogicalCpu.getInstance().setJumpEvent(null);
        Processor p = LogicalCpu.getInstance().getProcessor();

        byte mPcValue = nextStepPC.byteValue();
        p.setPc(mPcValue);
        updateRa(p.getRa());

    }

    public void dispNextPC() {
        Processor p = LogicalCpu.getInstance().getProcessor();
        showCurrPC(p);
        updateRa(p.getRa());
    }

    protected void showCurrPC(Processor p) {
        MemoryData md = new MemoryData(pcMemoryList, instList, cpuMemoryList);
        updatePc(p.getPc());
        md.showImpactCommand(p.getPc());
        instList.ensureIndexIsVisible(p.getPc());
    }

    private void handleGuiEvents(List<GuiEvent> queue) {
        for (GuiEvent activityPieceINT : queue) {
            activityPieceINT.setGs(this);
            activityPieceINT.updateGui();
        }
    }

    private void handleEvents(List<ActivityPieceINT> queue) {
        for (ActivityPieceINT activityPieceINT : queue) {
            activityPieceINT.setGs(this);
        }
        queue.stream().forEach((activityPieceINT) -> activityPieceINT.execute());

    }

    public void updateRa(byte newArVal) {
        java.util.logging.Logger.getLogger(GuiSimulator.class.getName()).log(Level.INFO, "Update register value: {" + Integer.toHexString((int) newArVal) + "}");
        raValue.setText(Integer.toString((int) newArVal));
    }

    public void updateSp(byte newSpVal) {
        spValue.setText(Integer.toString((int) newSpVal));
    }

    public void updatePc(byte newPcVal) {
        pcValue.setText(Integer.toString((int) newPcVal));
    }



    @Override
    public JPanel getSevenDigit() {
        return holderPanel;
    }
    private List<GuiEvent> guiEventList;

    @Override
    public List<GuiEvent> getGuiEventList() {
        if (guiEventList == null) {
            guiEventList = LogicalCpu.getInstance().getGuiEvents();
        }
        return guiEventList;
    }

    private void crtNewBreakpoint(JList<String> pcCpuIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void fireAfterExecution(byte currPc) {
        // if we have no listeners, do nothing...
        if (listeners != null && !listeners.isEmpty()) {
            Command cmd = new Command(sm.getNextCommand());
            // create the event object to send
            ActionEvent event
                    = new ActionEvent(cmd.getCmdDetails(), System.identityHashCode(cmd), "verify_exe_status");

            // make a copy of the listener list in case
            //   anyone adds/removes listeners
            List<StepListener> targets;
            synchronized (this) {
                targets = listeners.subList(0, listeners.size());
            }

            // walk through the listener list and
            //   call the sunMoved method in each
            targets.stream().forEach((target) -> {
                target.actionPerformed(event);
            });
        }
    }

}
