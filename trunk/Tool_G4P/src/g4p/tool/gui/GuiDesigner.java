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
import g4p.tool.components.DButton;
import g4p.tool.components.DCheckbox;
import g4p.tool.components.DCombo;
import g4p.tool.components.DHorzSlider;
import g4p.tool.components.DImageButton;
import g4p.tool.components.DKnob;
import g4p.tool.components.DLabel;
import g4p.tool.components.DOption;
import g4p.tool.components.DOptionGroup;
import g4p.tool.components.DPanel;
import g4p.tool.components.DTextField;
import g4p.tool.components.DTimer;
import g4p.tool.components.DVertSlider;
import g4p.tool.components.DWSlider;
import g4p.tool.components.DWindow;
import g4p.tool.gui.propertygrid.CtrlPropView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;

import processing.app.Editor;

/**
 *
 * @author Peter Lager
 */
@SuppressWarnings("serial")
public class GuiDesigner extends javax.swing.JFrame {

	private static GuiDesigner instance = null;

	private static boolean warningOn = false;

	/**
	 * This is provided because the GuiDesigner window is specified as 
	 * always-on-top and this conflicts with using a new Frame with
	 * JOptionPane.
	 * 
	 * Use this in preference to the static method in the class 
	 * processing.app.Base
	 * 
	 * @param title
	 * @param message
	 * @param e option exception
	 */
	public static void showWarning(String title, String message, Exception e) {
		warningOn = true;
		if (title == null || title.equals("")) 
			title = "Warning";
		if (instance == null) {
			JOptionPane.showMessageDialog(new Frame(), message, title,
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(instance, message, title,
					JOptionPane.WARNING_MESSAGE);
		}
		warningOn = false;
		if (e != null) e.printStackTrace();
	}

	private WindowListener winAdapt;

	private Editor editor = null;


	private CtrlSketchView treeSketchView;
	private CtrlPropView tblPropView;
	private CtrlTabView tabWindows;
	private GuiControl guiControl;


	/** 
	 * Creates new form GuiDesignFrame 
	 */
	public GuiDesigner() {
		instance = this;

		initComponents();
		initCustomComponents();
		guiControl =  new GuiControl(null, tabWindows, treeSketchView, tblPropView);
		createWindowAdapter();
	}

	/**
	 * Creates new form GuiDesignFrame <br>
	 * Keep a reference to the editor
	 * @param theEditor
	 * @param size 
	 */
	public GuiDesigner(Editor theEditor) {
		instance = this;

		editor = theEditor;
		initComponents();
		initCustomComponents();
		guiControl =  new GuiControl(editor, tabWindows, treeSketchView, tblPropView);
		guiControl.loadGuiLayout();
		Dimension size = guiControl.getSketchSize();
		if(size == null){
			final String message =
				"The size of this sketch could not automatically be\n" +
				"determined from your code. You'll have to set the\n" +
				"width and height in the designer window.";

			showWarning("Could not find applet size", message, null);
		}
		createWindowAdapter();
	}

	private void createWindowAdapter(){
		winAdapt = new WindowAdapter(){

			/**
			 * Invoked when a window is in the process of being closed.
			 * The close operation can be overridden at this point.
			 */
			public void windowClosing(WindowEvent e) {
//				System.out.println("CLOSING");
				setVisible(false);
				setExtendedState(ICONIFIED);				              	
			}

			/**
			 * Invoked when a window has been closed.
			 */
			public void windowClosed(WindowEvent e) {
//				System.out.println("CLOSED");
			}

			/**
			 * Invoked when a window is iconified.
			 */
			public void windowIconified(WindowEvent e) {
//				System.out.println("ICONIFIED");
			}

			/**
			 * Invoked when a window is de-iconified.
			 */
			public void windowDeiconified(WindowEvent e) {
//				System.out.println("DEICONIFIED");
			}

			/**
			 * Invoked when a window is activated.
			 */
			public void windowActivated(WindowEvent e) {
//				System.out.println("ACTIVATED");
				setVisible(true);
				setExtendedState(NORMAL);
				guiControl.setSketchSize(guiControl.getSketchSize());
				guiControl.codeCapture();
			}

			/**
			 * Invoked when a window is de-activated.
			 */
			public void windowDeactivated(WindowEvent e) {
//				System.out.println("DEACTIVATED");
				if(!warningOn){
					setExtendedState(ICONIFIED);
					guiControl.saveGuiLayout();
					guiControl.codeGeneration();
					return;
				}
			}

		};
		addWindowListener(winAdapt);
	}

	/**
	 * A fix since to make it work in Processing
	 */
	private void getIcons(){
		// Messy way to do it but stops error when used with Processing IDE
		ClassIcon.instance().addElement(DApplication.class, btnWindow.getIcon());
		ClassIcon.instance().addElement(DWindow.class, btnWindow.getIcon());
		ClassIcon.instance().addElement(DPanel.class, btnPanel.getIcon());
		ClassIcon.instance().addElement(DButton.class, btnButton.getIcon());
		ClassIcon.instance().addElement(DLabel.class, btnLabel.getIcon());
		ClassIcon.instance().addElement(DHorzSlider.class, btnHorzSlider.getIcon());
		ClassIcon.instance().addElement(DVertSlider.class, btnVertSlider.getIcon());
		ClassIcon.instance().addElement(DTextField.class, btnTextfield.getIcon());
		ClassIcon.instance().addElement(DCheckbox.class, btnCheckbox.getIcon());
		ClassIcon.instance().addElement(DOptionGroup.class, btnOptGroup.getIcon());
		ClassIcon.instance().addElement(DOption.class, btnOption.getIcon());
		ClassIcon.instance().addElement(DTimer.class, btnTimer.getIcon());
		ClassIcon.instance().addElement(DWSlider.class, btnCoolSlider.getIcon());
		ClassIcon.instance().addElement(DImageButton.class, btnImgButton.getIcon());
		ClassIcon.instance().addElement(DCombo.class, btnCombo.getIcon());
		ClassIcon.instance().addElement(DKnob.class, btnKnob.getIcon());
	}

	private void initCustomComponents() {
		getIcons();

		treeSketchView = new CtrlSketchView();
		tblPropView = new CtrlPropView();
		tabWindows = new CtrlTabView();

		spTop.setViewportView(treeSketchView);
		spBot.setViewportView(tblPropView);
		pnlWindowsView.setLayout(new BorderLayout());
		pnlWindowsView.add(tabWindows, BorderLayout.CENTER);

		treeSketchView.setViewLinks(tabWindows, tblPropView);
		tabWindows.setViewLinks(treeSketchView, tblPropView);
		tblPropView.setViewLinks(tabWindows, treeSketchView);
		tblPropView.setFillsViewportHeight(true);

		mitemGS8.setSelected(true);
		tabWindows.setGridSize(8);
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
		jSeparator2 = new javax.swing.JToolBar.Separator();
		btnScale = new javax.swing.JButton();
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
		menuGrid = new javax.swing.JMenu();
		jSeparator1 = new javax.swing.JPopupMenu.Separator();
		mitemSnapToGrid = new javax.swing.JCheckBoxMenuItem();
		mitemShowGrid = new javax.swing.JCheckBoxMenuItem();
		menuGridSize = new javax.swing.JMenu();
		mitemGS4 = new javax.swing.JRadioButtonMenuItem();
		mitemGS5 = new javax.swing.JRadioButtonMenuItem();
		mitemGS8 = new javax.swing.JRadioButtonMenuItem();
		mitemGS10 = new javax.swing.JRadioButtonMenuItem();
		menuZoom = new javax.swing.JMenu();
		mitemScaleWindow = new javax.swing.JMenuItem();
		mitem200 = new javax.swing.JMenuItem();
		mitem150 = new javax.swing.JMenuItem();
		mitem100 = new javax.swing.JMenuItem();
		mitem75 = new javax.swing.JMenuItem();
		mitem50 = new javax.swing.JMenuItem();
		mitem25 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("GUI Builder");
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

		jSeparator2.setPreferredSize(new java.awt.Dimension(50, 0));
		jSeparator2.setRequestFocusEnabled(false);
		jSeparator2.setSeparatorSize(new java.awt.Dimension(50, 0));
		tbarComponents.add(jSeparator2);

		btnScale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolResize2.png"))); // NOI18N
		btnScale.setToolTipText("Scale to fit");
		btnScale.setFocusable(false);
		btnScale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnScale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnScale.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnScaleActionPerformed(evt);
			}
		});
		tbarComponents.add(btnScale);

		splitControl.setDividerLocation(260);
		splitControl.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		splitControl.setDoubleBuffered(true);
		splitControl.setMinimumSize(new java.awt.Dimension(3, 5));
		splitControl.setPreferredSize(new java.awt.Dimension(250, 525));

		jLabel2.setBackground(new java.awt.Color(255, 255, 153));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("CONTROLS");
		jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jLabel2.setOpaque(true);

		spTop.setBackground(new java.awt.Color(255, 255, 255));

		tbarControls.setFloatable(false);
		tbarControls.setRollover(true);

		btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/bin.png"))); // NOI18N
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
				.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
				.addComponent(tbarControls, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
				.addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
		);
		pnlTreeViewLayout.setVerticalGroup(
				pnlTreeViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlTreeViewLayout.createSequentialGroup()
						.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(tbarControls, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
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
				.addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
				.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
		);
		pnlPropViiewLayout.setVerticalGroup(
				pnlPropViiewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlPropViiewLayout.createSequentialGroup()
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
		);

		splitControl.setRightComponent(pnlPropViiew);

		pnlWindowsView.setToolTipText("Winodws Panel");
		pnlWindowsView.setDoubleBuffered(false);

		javax.swing.GroupLayout pnlWindowsViewLayout = new javax.swing.GroupLayout(pnlWindowsView);
		pnlWindowsView.setLayout(pnlWindowsViewLayout);
		pnlWindowsViewLayout.setHorizontalGroup(
				pnlWindowsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 535, Short.MAX_VALUE)
		);
		pnlWindowsViewLayout.setVerticalGroup(
				pnlWindowsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 534, Short.MAX_VALUE)
		);

		menuGrid.setText("Grid");
		menuGrid.add(jSeparator1);

		mitemSnapToGrid.setText("Snap to grid");
		mitemSnapToGrid.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemSnapToGridActionPerformed(evt);
			}
		});
		menuGrid.add(mitemSnapToGrid);

		mitemShowGrid.setText("Show grid");
		mitemShowGrid.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemShowGridActionPerformed(evt);
			}
		});
		menuGrid.add(mitemShowGrid);

		menuGridSize.setText("Grid Size");

		bgGridSize.add(mitemGS4);
		mitemGS4.setText("4px");
		mitemGS4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemGS4ActionPerformed(evt);
			}
		});
		menuGridSize.add(mitemGS4);

		bgGridSize.add(mitemGS5);
		mitemGS5.setText("5px");
		mitemGS5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemGS5ActionPerformed(evt);
			}
		});
		menuGridSize.add(mitemGS5);

		bgGridSize.add(mitemGS8);
		mitemGS8.setSelected(true);
		mitemGS8.setText("8px");
		mitemGS8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemGS8ActionPerformed(evt);
			}
		});
		menuGridSize.add(mitemGS8);

		bgGridSize.add(mitemGS10);
		mitemGS10.setText("10px");
		mitemGS10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemGS10ActionPerformed(evt);
			}
		});
		menuGridSize.add(mitemGS10);

		menuGrid.add(menuGridSize);

		jMenuBar1.add(menuGrid);

		menuZoom.setText("Zoom");

		mitemScaleWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolResize.png"))); // NOI18N
		mitemScaleWindow.setText("Scale window to fit");
		mitemScaleWindow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScaleWindowActionPerformed(evt);
			}
		});
		menuZoom.add(mitemScaleWindow);

		mitem200.setText("200%");
		mitem200.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScale200(evt);
			}
		});
		menuZoom.add(mitem200);

		mitem150.setText("150%");
		mitem150.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScale150(evt);
			}
		});
		menuZoom.add(mitem150);

		mitem100.setText("100%");
		mitem100.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScale100(evt);
			}
		});
		menuZoom.add(mitem100);

		mitem75.setText("75%");
		mitem75.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScale75(evt);
			}
		});
		menuZoom.add(mitem75);

		mitem50.setText("50%");
		mitem50.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScale50(evt);
			}
		});
		menuZoom.add(mitem50);

		mitem25.setText("25%");
		mitem25.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mitemScale25(evt);
			}
		});
		menuZoom.add(mitem25);

		jMenuBar1.add(menuZoom);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addComponent(pnlWindowsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(splitControl, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(tbarComponents, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(tbarComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(splitControl, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
								.addComponent(pnlWindowsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
		showWarning("GUI Builder warning", "Image buttons not yet available", null);            
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
		guiControl.addComponent(new DWSlider());
	}//GEN-LAST:event_btnCoolSliderActionPerformed

	private void btnKnobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKnobActionPerformed
		guiControl.addComponent(new DKnob());
	}//GEN-LAST:event_btnKnobActionPerformed

	private void btnCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckboxActionPerformed
		guiControl.addComponent(new DCheckbox());
	}//GEN-LAST:event_btnCheckboxActionPerformed

	private void btnOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionActionPerformed
		guiControl.addComponent(new DOption());
	}//GEN-LAST:event_btnOptionActionPerformed

	private void btnOptGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptGroupActionPerformed
		guiControl.addComponent(new DOptionGroup());
	}//GEN-LAST:event_btnOptGroupActionPerformed

	private void btnComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComboActionPerformed
		showWarning("GUI Builder warning", "Drop-down lists not yet available", null);
	}//GEN-LAST:event_btnComboActionPerformed

	private void btnTimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimerActionPerformed
		guiControl.addComponent(new DTimer());
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

	private void mitemGS4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS4ActionPerformed
		guiControl.setGridSize(4);
	}//GEN-LAST:event_mitemGS4ActionPerformed

	private void mitemGS5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS5ActionPerformed
		guiControl.setGridSize(5);
	}//GEN-LAST:event_mitemGS5ActionPerformed

	private void mitemGS8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS8ActionPerformed
		guiControl.setGridSize(8);
	}//GEN-LAST:event_mitemGS8ActionPerformed

	private void mitemGS10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemGS10ActionPerformed
		guiControl.setGridSize(10);
	}//GEN-LAST:event_mitemGS10ActionPerformed

	private void mitemScaleWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScaleWindowActionPerformed
		guiControl.makeWindowSizeToFit();
	}//GEN-LAST:event_mitemScaleWindowActionPerformed

	private void btnScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScaleActionPerformed
		guiControl.makeWindowSizeToFit();
	}//GEN-LAST:event_btnScaleActionPerformed

	private void mitemScale200(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScale200
		guiControl.setScale(200);
	}//GEN-LAST:event_mitemScale200

	private void mitemScale150(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScale150
		guiControl.setScale(150);
	}//GEN-LAST:event_mitemScale150

	private void mitemScale100(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScale100
		guiControl.setScale(100);
	}//GEN-LAST:event_mitemScale100

	private void mitemScale75(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScale75
		guiControl.setScale(75);
	}//GEN-LAST:event_mitemScale75

	private void mitemScale50(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScale50
		guiControl.setScale(50);
	}//GEN-LAST:event_mitemScale50

	private void mitemScale25(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemScale25
		guiControl.setScale(25);
	}//GEN-LAST:event_mitemScale25

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
	private javax.swing.JButton btnScale;
	private javax.swing.JButton btnTextfield;
	private javax.swing.JButton btnTimer;
	private javax.swing.JButton btnVertSlider;
	private javax.swing.JButton btnWindow;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPopupMenu.Separator jSeparator1;
	private javax.swing.JToolBar.Separator jSeparator2;
	private javax.swing.JMenu menuGrid;
	private javax.swing.JMenu menuGridSize;
	private javax.swing.JMenu menuZoom;
	private javax.swing.JMenuItem mitem100;
	private javax.swing.JMenuItem mitem150;
	private javax.swing.JMenuItem mitem200;
	private javax.swing.JMenuItem mitem25;
	private javax.swing.JMenuItem mitem50;
	private javax.swing.JMenuItem mitem75;
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
