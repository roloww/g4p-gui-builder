/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GuiDesigner.java
 *
 * Created on 09-Jul-2011, 16:53:21
 */
package g4p.tool.gui;

import g4p.tool.components.DApplication;
import g4p.tool.components.DBase;
import g4p.tool.components.DButton;
import g4p.tool.components.DHorzSlider;
import g4p.tool.components.DKnob;
import g4p.tool.components.DLabel;
import g4p.tool.components.DPanel;
import g4p.tool.components.DTextField;
import g4p.tool.components.DVertSlider;
import g4p.tool.components.DWindow;
import g4p.tool.gui.propertygrid.CtrlPropView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JCheckBoxMenuItem;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Sketch;

/**
 *
 * @author Peter Lager
 */
@SuppressWarnings("serial")
public class GuiDesigner extends javax.swing.JFrame {

	private Base base;
	private Editor editor;
	private Sketch sketch;
	private File config;


	private CtrlSketchView treeSketchView;
	private CtrlPropView tblPropView;
	private CtrlTabView tabWindows;
	private GuiControl guiControl;
	
	private DBase startNode;
	private CtrlSketchModel tm;

	/** 
	 * Creates new form GuiDesignFrame 
	 */
	public GuiDesigner() {
		initComponents();
		customComponents();
		setPreferredSize(new Dimension(800, 600));

		tm = getSimpleSketchModel();
		// Now create GUI
		makGUIfromTreeModel(tm);
		setSelectedComponent(startNode);
	}

	/**
	 * Creates new form GuiDesignFrame <br>
	 * Keep a reference to the editor
	 * @param theEditor
	 */
	public GuiDesigner(Editor theEditor, File configFile) {
		editor = theEditor;
		config = configFile;
		base = editor.getBase();
		sketch = editor.getSketch();

		initComponents();
		customComponents();
		setPreferredSize(new Dimension(800, 600));

		tm = getSimpleSketchModel();
		// Now create GUI
		makGUIfromTreeModel(tm);
		setSelectedComponent(startNode);
	}

	/**
	 * A fix since to make it work in Processing
	 */
	private void getIcons(){
		ClassIcon.instance().addElement(DApplication.class, btnWindow.getIcon());
		ClassIcon.instance().addElement(DWindow.class, btnWindow.getIcon());
		ClassIcon.instance().addElement(DPanel.class, btnPanel.getIcon());
		ClassIcon.instance().addElement(DButton.class, btnButton.getIcon());
		ClassIcon.instance().addElement(DLabel.class, btnLabel.getIcon());
		ClassIcon.instance().addElement(DHorzSlider.class, btnHorzSlider.getIcon());
		ClassIcon.instance().addElement(DVertSlider.class, btnVertSlider.getIcon());
		ClassIcon.instance().addElement(DTextField.class, btnTextfield.getIcon());
		ClassIcon.instance().addElement(DTextField.class, btnTextfield.getIcon());
		ClassIcon.instance().addElement(DKnob.class, btnKnob.getIcon());

//		btnCheckbox;
//		btnCombo;
//		btnImgButton;
//		btnOptGroup;
//		btnOption;
//		btnTimer;
//		btnCoolSlider;
	}

	private void customComponents() {
		getIcons();

		treeSketchView = new CtrlSketchView();
		tblPropView = new CtrlPropView();
		tabWindows = new CtrlTabView();
		guiControl =  new GuiControl(tabWindows, treeSketchView, tblPropView);
		
		spTop.setViewportView(treeSketchView);
		spBot.setViewportView(tblPropView);
		pnlWindowsView.setLayout(new BorderLayout());
		pnlWindowsView.add(tabWindows, BorderLayout.CENTER);

		treeSketchView.setViewLinks(tabWindows, tblPropView);
		tabWindows.setViewLinks(treeSketchView, tblPropView);
		tblPropView.setViewLinks(tabWindows, treeSketchView);
		tblPropView.setFillsViewportHeight(true);
	}

	/**
	 * This method is to prove that the entire GUI can be
	 * created from a Tree data model 
	 * @param m
	 */
	private void makGUIfromTreeModel(CtrlSketchModel m) {
		// Create Tree view
		treeSketchView.setModel(m);
		// Get root and initialise the property view
		tblPropView.showProprtiesFor(startNode);
		// Setup window display
		// Create tabbed pane for each window
		Enumeration<?> windows = ((DBase) m.getRoot()).children();
		while(windows.hasMoreElements()){
			DBase win = (DBase) windows.nextElement();
			tabWindows.addWindow(win);
		}

	}

	public void setSelectedComponent(DBase comp){
		tabWindows.setSelectedComponent(comp);
		treeSketchView.setSelectedComponent(comp);
		tblPropView.showProprtiesFor(comp);
	}

	/**
	 * This creates a simple tree model that we can use for testing
	 * @return
	 */
	private CtrlSketchModel getSimpleSketchModel() {
		CtrlSketchModel m = null;
		DApplication app = new DApplication();
		
		DWindow win1 = new DWindow(true);
		win1.set_width(400);
		win1.set_height(300);
		
		DWindow win2 = new DWindow(false);
		win2.set_width(200);
		win2.set_height(280);

		DButton btn1 = new DButton();
		btn1.set_x(110);
		btn1.set_y(230);
		btn1.set_width(80);
		btn1.set_height(20);
		
		DButton btn2 = new DButton();
		btn2.set_x(10);
		btn2.set_y(12);
		btn2.set_width(80);
		btn2.set_height(20);
		
		DPanel pnl = new DPanel();
		pnl.set_x(10);
		pnl.set_y(20);
		pnl.set_width(140);
		pnl.set_height(80);
		
		DLabel lbl1 = new DLabel();
		lbl1.set_x(220);
		lbl1.set_y(100);
		
		DTextField txf1 = new DTextField();
		txf1.set_x(230);
		txf1.set_y(50);
		
		DKnob knb1 = new DKnob();
		knb1.set_x(130);
		knb1.set_y(50);

		DHorzSlider hsdr1 = new DHorzSlider();
		hsdr1.set_x(40);
		hsdr1.set_y(10);
		
		DVertSlider vsdr1 = new DVertSlider();
		vsdr1.set_x(10);
		vsdr1.set_y(10);
		
		// Build sketch GUI
		app.add(win1);
		win1.add(btn1);
		win1.add(lbl1);
		win1.add(txf1);
		win1.add(knb1);
		win1.add(hsdr1);
		win1.add(vsdr1);
		
		app.add(win2);
		win2.add(pnl);
		pnl.add(btn2);
		m = new CtrlSketchModel(app);
		startNode = btn2;
		return m;
	}


	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgGridSize = new javax.swing.ButtonGroup();
        tbarComponents = new javax.swing.JToolBar();
        btnWindow = new javax.swing.JButton();
        btnPanel = new javax.swing.JButton();
        btnButton = new javax.swing.JButton();
        btnImgButton = new javax.swing.JButton();
        btnLabel = new javax.swing.JButton();
        btnTextfield = new javax.swing.JButton();
        btnHorzSlider = new javax.swing.JButton();
        btnVertSlider = new javax.swing.JButton();
        btnCoolSlider = new javax.swing.JButton();
        btnKnob = new javax.swing.JButton();
        btnCheckbox = new javax.swing.JButton();
        btnOption = new javax.swing.JButton();
        btnOptGroup = new javax.swing.JButton();
        btnCombo = new javax.swing.JButton();
        btnTimer = new javax.swing.JButton();
        splitControl = new javax.swing.JSplitPane();
        pnlTreeView = new java.awt.Panel();
        jLabel2 = new javax.swing.JLabel();
        spTop = new javax.swing.JScrollPane();
        tbarControls = new javax.swing.JToolBar();
        btnRemove = new javax.swing.JButton();
        pnlPropViiew = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();
        spBot = new javax.swing.JScrollPane();
        pnlWindowsView = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        mitemScaleWindow = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mitemSnapToGrid = new javax.swing.JCheckBoxMenuItem();
        mitemShowGrid = new javax.swing.JCheckBoxMenuItem();
        mitemGS4 = new javax.swing.JRadioButtonMenuItem();
        mitemGS5 = new javax.swing.JRadioButtonMenuItem();
        mitemGS8 = new javax.swing.JRadioButtonMenuItem();
        mitemGS10 = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GUI Builder");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setName("frmDesigner"); // NOI18N

        tbarComponents.setFloatable(false);
        tbarComponents.setRollover(true);
        tbarComponents.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolWindow.png"))); // NOI18N
        btnWindow.setToolTipText("Window");
        btnWindow.setFocusable(false);
        btnWindow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnWindow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWindowActionPerformed(evt);
            }
        });
        tbarComponents.add(btnWindow);

        btnPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolPanel.png"))); // NOI18N
        btnPanel.setToolTipText("Floating Panel");
        btnPanel.setFocusable(false);
        btnPanel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPanel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPanelActionPerformed(evt);
            }
        });
        tbarComponents.add(btnPanel);

        btnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolButton.png"))); // NOI18N
        btnButton.setToolTipText("Button");
        btnButton.setFocusable(false);
        btnButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnButtonActionPerformed(evt);
            }
        });
        tbarComponents.add(btnButton);

        btnImgButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolButtonImg.png"))); // NOI18N
        btnImgButton.setToolTipText("Image Button");
        btnImgButton.setFocusable(false);
        btnImgButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImgButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImgButtonActionPerformed(evt);
            }
        });
        tbarComponents.add(btnImgButton);

        btnLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolLabel.png"))); // NOI18N
        btnLabel.setToolTipText("Label");
        btnLabel.setFocusable(false);
        btnLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLabelActionPerformed(evt);
            }
        });
        tbarComponents.add(btnLabel);

        btnTextfield.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolTextField.png"))); // NOI18N
        btnTextfield.setToolTipText("Textfield");
        btnTextfield.setFocusable(false);
        btnTextfield.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTextfield.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTextfieldActionPerformed(evt);
            }
        });
        tbarComponents.add(btnTextfield);

        btnHorzSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSliderH.png"))); // NOI18N
        btnHorzSlider.setToolTipText("Slider");
        btnHorzSlider.setFocusable(false);
        btnHorzSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHorzSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHorzSlider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHorzSliderActionPerformed(evt);
            }
        });
        tbarComponents.add(btnHorzSlider);

        btnVertSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSliderV.png"))); // NOI18N
        btnVertSlider.setToolTipText("Slider");
        btnVertSlider.setFocusable(false);
        btnVertSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVertSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVertSlider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVertSliderActionPerformed(evt);
            }
        });
        tbarComponents.add(btnVertSlider);

        btnCoolSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCoolSlider.png"))); // NOI18N
        btnCoolSlider.setToolTipText("Cool Slider");
        btnCoolSlider.setFocusable(false);
        btnCoolSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCoolSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCoolSlider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoolSliderActionPerformed(evt);
            }
        });
        tbarComponents.add(btnCoolSlider);

        btnKnob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolKnob.png"))); // NOI18N
        btnKnob.setToolTipText("Knob");
        btnKnob.setFocusable(false);
        btnKnob.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKnob.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKnob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKnobActionPerformed(evt);
            }
        });
        tbarComponents.add(btnKnob);

        btnCheckbox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCheckbox.png"))); // NOI18N
        btnCheckbox.setToolTipText("Checkbox");
        btnCheckbox.setFocusable(false);
        btnCheckbox.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheckbox.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckboxActionPerformed(evt);
            }
        });
        tbarComponents.add(btnCheckbox);

        btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolOption.png"))); // NOI18N
        btnOption.setToolTipText("Option");
        btnOption.setFocusable(false);
        btnOption.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOption.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionActionPerformed(evt);
            }
        });
        tbarComponents.add(btnOption);

        btnOptGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolOptGroup.png"))); // NOI18N
        btnOptGroup.setToolTipText("Option group");
        btnOptGroup.setFocusable(false);
        btnOptGroup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOptGroup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOptGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptGroupActionPerformed(evt);
            }
        });
        tbarComponents.add(btnOptGroup);

        btnCombo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCombo.png"))); // NOI18N
        btnCombo.setToolTipText("Combo Box");
        btnCombo.setFocusable(false);
        btnCombo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCombo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComboActionPerformed(evt);
            }
        });
        tbarComponents.add(btnCombo);

        btnTimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolTimer.png"))); // NOI18N
        btnTimer.setToolTipText("Timer");
        btnTimer.setFocusable(false);
        btnTimer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTimer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimerActionPerformed(evt);
            }
        });
        tbarComponents.add(btnTimer);

        splitControl.setDividerLocation(300);
        splitControl.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitControl.setDoubleBuffered(true);

        jLabel2.setBackground(new java.awt.Color(255, 255, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CONTROLS");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);

        spTop.setBackground(new java.awt.Color(255, 255, 255));

        tbarControls.setFloatable(false);
        tbarControls.setRollover(true);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/bin..png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setFocusable(false);
        btnRemove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRemove.setMaximumSize(new java.awt.Dimension(80, 47));
        btnRemove.setPreferredSize(new java.awt.Dimension(80, 29));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        tbarControls.add(btnRemove);

        javax.swing.GroupLayout pnlTreeViewLayout = new javax.swing.GroupLayout(pnlTreeView);
        pnlTreeView.setLayout(pnlTreeViewLayout);
        pnlTreeViewLayout.setHorizontalGroup(
            pnlTreeViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(tbarControls, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );
        pnlTreeViewLayout.setVerticalGroup(
            pnlTreeViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTreeViewLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbarControls, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
        );

        splitControl.setTopComponent(pnlTreeView);

        jLabel1.setBackground(new java.awt.Color(255, 255, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PROPERTIES");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(56, 14));

        spBot.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlPropViiewLayout = new javax.swing.GroupLayout(pnlPropViiew);
        pnlPropViiew.setLayout(pnlPropViiewLayout);
        pnlPropViiewLayout.setHorizontalGroup(
            pnlPropViiewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );
        pnlPropViiewLayout.setVerticalGroup(
            pnlPropViiewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPropViiewLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
        );

        splitControl.setRightComponent(pnlPropViiew);

        pnlWindowsView.setToolTipText("Winodws Panel");
        pnlWindowsView.setDoubleBuffered(false);

        javax.swing.GroupLayout pnlWindowsViewLayout = new javax.swing.GroupLayout(pnlWindowsView);
        pnlWindowsView.setLayout(pnlWindowsViewLayout);
        pnlWindowsViewLayout.setHorizontalGroup(
            pnlWindowsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        pnlWindowsViewLayout.setVerticalGroup(
            pnlWindowsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );

        jMenu1.setText("Code");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Window");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        mitemScaleWindow.setText("Scale window to fit");
        jMenu2.add(mitemScaleWindow);
        jMenu2.add(jSeparator1);

        mitemSnapToGrid.setSelected(true);
        mitemSnapToGrid.setText("Snap to grid");
        mitemSnapToGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemSnapToGridActionPerformed(evt);
            }
        });
        jMenu2.add(mitemSnapToGrid);

        mitemShowGrid.setSelected(true);
        mitemShowGrid.setText("Show grid");
        mitemShowGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemShowGridActionPerformed(evt);
            }
        });
        jMenu2.add(mitemShowGrid);

        bgGridSize.add(mitemGS4);
        mitemGS4.setSelected(true);
        mitemGS4.setText("4px");
        mitemGS4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemGS4ActionPerformed(evt);
            }
        });
        jMenu2.add(mitemGS4);

        bgGridSize.add(mitemGS5);
        mitemGS5.setText("5px");
        mitemGS5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemGS5ActionPerformed(evt);
            }
        });
        jMenu2.add(mitemGS5);

        bgGridSize.add(mitemGS8);
        mitemGS8.setText("8px");
        mitemGS8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemGS8ActionPerformed(evt);
            }
        });
        jMenu2.add(mitemGS8);

        bgGridSize.add(mitemGS10);
        mitemGS10.setText("10px");
        jMenu2.add(mitemGS10);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbarComponents, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlWindowsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(splitControl, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbarComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(splitControl, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlWindowsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void btnPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPanelActionPerformed
            guiControl.addComponent(new DPanel());
        }//GEN-LAST:event_btnPanelActionPerformed

        private void btnWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWindowActionPerformed
            guiControl.addComponent(new DWindow(false));
        }//GEN-LAST:event_btnWindowActionPerformed

        private void btnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnButtonActionPerformed
            guiControl.addComponent(new DButton());
        }//GEN-LAST:event_btnButtonActionPerformed

        private void btnImgButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImgButtonActionPerformed
            Base.showWarning("GUI Builder warning", "Image buttons not yet avaialable", null);            
        }//GEN-LAST:event_btnImgButtonActionPerformed

        private void btnLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLabelActionPerformed
            guiControl.addComponent(new DLabel());
        }//GEN-LAST:event_btnLabelActionPerformed

        private void btnTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTextfieldActionPerformed
            guiControl.addComponent(new DTextField());
        }//GEN-LAST:event_btnTextfieldActionPerformed

        private void btnHorzSliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHorzSliderActionPerformed
            guiControl.addComponent(new DHorzSlider());
        }//GEN-LAST:event_btnHorzSliderActionPerformed

        private void btnVertSliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVertSliderActionPerformed
            guiControl.addComponent(new DVertSlider());
        }//GEN-LAST:event_btnVertSliderActionPerformed

        private void btnCoolSliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoolSliderActionPerformed
            Base.showWarning("GUI Builder warning", "Cool sliders not yet avaialable", null);            
        }//GEN-LAST:event_btnCoolSliderActionPerformed

        private void btnKnobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKnobActionPerformed
            guiControl.addComponent(new DKnob());
        }//GEN-LAST:event_btnKnobActionPerformed

        private void btnCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckboxActionPerformed
           Base.showWarning("GUI Builder warning", "Image buttons not yet avaialable", null);
        }//GEN-LAST:event_btnCheckboxActionPerformed

        private void btnOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionActionPerformed
           Base.showWarning("GUI Builder warning", "Option buttons not yet avaialable", null);
        }//GEN-LAST:event_btnOptionActionPerformed

        private void btnOptGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptGroupActionPerformed
           Base.showWarning("GUI Builder warning", "Option groups  not yet avaialable", null);
        }//GEN-LAST:event_btnOptGroupActionPerformed

        private void btnComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComboActionPerformed
           Base.showWarning("GUI Builder warning", "Drop-down lists not yet avaialable", null);
        }//GEN-LAST:event_btnComboActionPerformed

        private void btnTimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimerActionPerformed
           Base.showWarning("GUI Builder warning", "Timers not yet avaialable", null);
        }//GEN-LAST:event_btnTimerActionPerformed

        private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
            guiControl.removeComponent();
        }//GEN-LAST:event_btnRemoveActionPerformed

        private void mitemSnapToGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemSnapToGridActionPerformed
            guiControl.snapGrid(((JCheckBoxMenuItem)evt.getSource()).isSelected());
        }//GEN-LAST:event_mitemSnapToGridActionPerformed

        private void mitemShowGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemShowGridActionPerformed
           guiControl.showGrid(((JCheckBoxMenuItem)evt.getSource()).isSelected());
        }//GEN-LAST:event_mitemShowGridActionPerformed

        private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_jMenu2ActionPerformed

        private void mitemGS4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS4ActionPerformed
           guiControl.setGridSize(4);
        }//GEN-LAST:event_mitemGS4ActionPerformed

        private void mitemGS5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS5ActionPerformed
           guiControl.setGridSize(5);
        }//GEN-LAST:event_mitemGS5ActionPerformed

        private void mitemGS8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS8ActionPerformed
            guiControl.setGridSize(8);
        }//GEN-LAST:event_mitemGS8ActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new GuiDesigner().setVisible(true);
			}
		});
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgGridSize;
    private javax.swing.JButton btnButton;
    private javax.swing.JButton btnCheckbox;
    private javax.swing.JButton btnCombo;
    private javax.swing.JButton btnCoolSlider;
    private javax.swing.JButton btnHorzSlider;
    private javax.swing.JButton btnImgButton;
    private javax.swing.JButton btnKnob;
    private javax.swing.JButton btnLabel;
    private javax.swing.JButton btnOptGroup;
    private javax.swing.JButton btnOption;
    private javax.swing.JButton btnPanel;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnTextfield;
    private javax.swing.JButton btnTimer;
    private javax.swing.JButton btnVertSlider;
    private javax.swing.JButton btnWindow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JRadioButtonMenuItem mitemGS10;
    private javax.swing.JRadioButtonMenuItem mitemGS4;
    private javax.swing.JRadioButtonMenuItem mitemGS5;
    private javax.swing.JRadioButtonMenuItem mitemGS8;
    private javax.swing.JMenuItem mitemScaleWindow;
    private javax.swing.JCheckBoxMenuItem mitemShowGrid;
    private javax.swing.JCheckBoxMenuItem mitemSnapToGrid;
    private java.awt.Panel pnlPropViiew;
    private java.awt.Panel pnlTreeView;
    private javax.swing.JPanel pnlWindowsView;
    private javax.swing.JScrollPane spBot;
    private javax.swing.JScrollPane spTop;
    private javax.swing.JSplitPane splitControl;
    private javax.swing.JToolBar tbarComponents;
    private javax.swing.JToolBar tbarControls;
    // End of variables declaration//GEN-END:variables
}
