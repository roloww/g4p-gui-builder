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

import g4p.tool.controls.DApplication;
import g4p.tool.controls.DButton;
import g4p.tool.controls.DCheckbox;
import g4p.tool.controls.DCustomSlider;
import g4p.tool.controls.DDropList;
import g4p.tool.controls.DImageButton;
import g4p.tool.controls.DImageToggleButton;
import g4p.tool.controls.DKnob;
import g4p.tool.controls.DLabel;
import g4p.tool.controls.DOption;
import g4p.tool.controls.DPanel;
import g4p.tool.controls.DSketchPad;
import g4p.tool.controls.DSlider;
import g4p.tool.controls.DSlider2D;
import g4p.tool.controls.DStick;
import g4p.tool.controls.DTextArea;
import g4p.tool.controls.DTextField;
import g4p.tool.controls.DTimer;
import g4p.tool.controls.DToggleGroup;
import g4p.tool.controls.DWindow;
import g4p.tool.gui.propertygrid.CtrlPropView;
import g4p.tool.gui.tabview.CtrlTabView;
import g4p.tool.gui.treeview.CtrlSketchView;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import processing.app.Editor;

/**
 * The GUI designer window for the tool.
 *
 *
 * @author Peter Lager
 */
@SuppressWarnings("serial")
public class GuiDesigner extends javax.swing.JFrame {

	private static GuiDesigner instance = null;
	private static Editor editor = null;
	//private static FontMetrics metrics = null;
	private static boolean stayOpen = false;
	private static boolean autoHide = false;

	public static GuiDesigner instance() {
		return instance;
	}

	public static Editor editor() {
		return editor;
	}

	//	public static FontMetrics metrics(){
	//		return metrics;
	//	}
	public static void keepOpen(boolean mode) {
		stayOpen = mode;
	}

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
		stayOpen = true;
		if (title == null || title.equals("")) {
			title = "Warning";
		}
		if (instance == null) {
			JOptionPane.showMessageDialog(new Frame(), message, title,
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(instance, message, title,
					JOptionPane.WARNING_MESSAGE);
		}
		stayOpen = false;
		if (e != null) {
			e.printStackTrace();
		}
	}
	private WindowListener winAdapt;
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
		guiControl = new GuiControl(null, tabWindows, treeSketchView, tblPropView);
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
		guiControl = new GuiControl(editor, tabWindows, treeSketchView, tblPropView);
		guiControl.loadGuiLayout();
		guiControl.getSketchSizeFromCode();

		//		Dimension size = guiControl.getSketchSizeFromCode();
		//		if(size == null){
		//			final String message =
		//				"The size of this sketch could not automatically be\n" +
		//				"determined from your code. You'll have to set the\n" +
		//				"width and height in the designer window.";
		//
		//			showWarning("Could not find applet size", message, null);
		//		}
		createWindowAdapter();
	}

	private void createWindowAdapter() {
		winAdapt = new WindowAdapter() {

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
				setVisible(true);
				setExtendedState(NORMAL);
				guiControl.setSketchSize(guiControl.getSketchSizeFromCode());
				if (treeSketchView != null) { // the GUI is drawn safe to repaint
					treeSketchView.repaint();
					tblPropView.repaint();
					tabWindows.repaint();
				}
				guiControl.codeCapture();
			}

			/**
			 * Invoked when a window is de-activated.
			 */
			public void windowDeactivated(WindowEvent e) {
				if (!stayOpen) {
					if (autoHide) {
						setExtendedState(ICONIFIED);
					}
					guiControl.saveGuiLayout();
					guiControl.codeGeneration();
				}
			}
		};
		addWindowListener(winAdapt);
	}

	/**
	 * A fix since to make it work in Processing. Uses the images loaded during creation available
	 * for the dynamic elements e.g. icons in treeview and tab icons
	 */
	private void getIcons() {
		// Messy way to do it but stops error when used with Processing IDE
		ToolIcon.addIcon(DApplication.class, btnWindow.getIcon());
		ToolIcon.addIcon(DWindow.class, btnWindow.getIcon());
		ToolIcon.addIcon(DPanel.class, btnPanel.getIcon());
		ToolIcon.addIcon(DButton.class, btnButton.getIcon());
		ToolIcon.addIcon(DLabel.class, btnLabel.getIcon());
		ToolIcon.addIcon(DSlider.class, btnSlider.getIcon());
		ToolIcon.addIcon(DTextField.class, btnTextfield.getIcon());
		ToolIcon.addIcon(DCheckbox.class, btnCheckbox.getIcon());
		ToolIcon.addIcon(DToggleGroup.class, btnOptGroup.getIcon());
		ToolIcon.addIcon(DOption.class, btnOption.getIcon());
		ToolIcon.addIcon(DTimer.class, btnTimer.getIcon());
		ToolIcon.addIcon(DCustomSlider.class, btnCoolSlider.getIcon());
		ToolIcon.addIcon(DImageButton.class, btnImgButton.getIcon());
		ToolIcon.addIcon(DDropList.class, btnDropList.getIcon());
		ToolIcon.addIcon(DKnob.class, btnKnob.getIcon());
		ToolIcon.addIcon(DSketchPad.class, btnSketchPad.getIcon());
		ToolIcon.addIcon(DSlider2D.class, btnSlider2D.getIcon());
		ToolIcon.addIcon(DStick.class, btnStick.getIcon());
		ToolIcon.addIcon(DImageToggleButton.class, btnImgTogButton.getIcon());

		ToolIcon.addIcon("DL_DIALOG_ICON", new javax.swing.ImageIcon(getClass().getResource("/g4p/cbox_icon2.png")));


		// Load images from resource
		try {
			ToolImage.addImage("CB_ICON", ImageIO.read(getClass().getResource("/g4p/tick.png")));
			ToolImage.addImage("OP_ICON", ImageIO.read(getClass().getResource("/g4p/pinhead.png")));
			ToolImage.addImage("SPAD_ICON", ImageIO.read(getClass().getResource("/g4p/spad.jpg")));
			ToolImage.addImage("IMG_TOG_BTN_ICON", ImageIO.read(getClass().getResource("/g4p/toggle.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		tabWindows.setGridSize(10);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlToolBars = new javax.swing.JPanel();
        tbarGrid = new javax.swing.JToolBar();
        lblGridTitle = new javax.swing.JLabel();
        cbxShowGrid = new javax.swing.JCheckBox();
        spacer01 = new javax.swing.JLabel();
        btnSnap = new javax.swing.JCheckBox();
        lblGridSizeTitle = new javax.swing.JLabel();
        lblGridSize = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sdrGridSize = new javax.swing.JSlider();
        tbarWIndow = new javax.swing.JToolBar();
        lblScaleTitle = new javax.swing.JLabel();
        cbxScale = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        cbxAutoHide = new javax.swing.JCheckBox();
        tbarComponents = new javax.swing.JToolBar();
        btnWindow = new javax.swing.JButton();
        btnPanel = new javax.swing.JButton();
        btnButton = new javax.swing.JButton();
        btnImgButton = new javax.swing.JButton();
        btnImgTogButton = new javax.swing.JButton();
        btnLabel = new javax.swing.JButton();
        btnTextfield = new javax.swing.JButton();
        btnTextarea = new javax.swing.JButton();
        btnSlider = new javax.swing.JButton();
        btnCoolSlider = new javax.swing.JButton();
        btnSlider2D = new javax.swing.JButton();
        btnStick = new javax.swing.JButton();
        btnKnob = new javax.swing.JButton();
        btnCheckbox = new javax.swing.JButton();
        btnOption = new javax.swing.JButton();
        btnOptGroup = new javax.swing.JButton();
        btnDropList = new javax.swing.JButton();
        btnSketchPad = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GUI Builder");
        setBackground(new java.awt.Color(255, 255, 255));
        setName("frmDesigner"); // NOI18N

        pnlToolBars.setBackground(new java.awt.Color(-16192,true));
        pnlToolBars.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(-16777216,true), 3, true));

        tbarGrid.setFloatable(false);
        tbarGrid.setRollover(true);
        tbarGrid.setOpaque(false);

        lblGridTitle.setText("  Grid :   ");
        tbarGrid.add(lblGridTitle);

        cbxShowGrid.setText("Show");
        cbxShowGrid.setFocusable(false);
        cbxShowGrid.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cbxShowGrid.setOpaque(false);
        cbxShowGrid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cbxShowGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxShowGridActionPerformed(evt);
            }
        });
        tbarGrid.add(cbxShowGrid);

        spacer01.setText("     ");
        tbarGrid.add(spacer01);

        btnSnap.setText("Snap to");
        btnSnap.setFocusable(false);
        btnSnap.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnSnap.setOpaque(false);
        btnSnap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSnap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSnapActionPerformed(evt);
            }
        });
        tbarGrid.add(btnSnap);

        lblGridSizeTitle.setText("     Size ");
        tbarGrid.add(lblGridSizeTitle);

        lblGridSize.setBackground(new java.awt.Color(-1,true));
        lblGridSize.setFont(new java.awt.Font("Monospaced", 0, 12));
        lblGridSize.setText(" 10 ");
        lblGridSize.setOpaque(true);
        tbarGrid.add(lblGridSize);

        jLabel3.setText("  ");
        tbarGrid.add(jLabel3);

        sdrGridSize.setBackground(new java.awt.Color(-32640,true));
        sdrGridSize.setForeground(new java.awt.Color(-65536,true));
        sdrGridSize.setMajorTickSpacing(2);
        sdrGridSize.setMaximum(20);
        sdrGridSize.setMinimum(4);
        sdrGridSize.setMinorTickSpacing(2);
        sdrGridSize.setPaintTicks(true);
        sdrGridSize.setSnapToTicks(true);
        sdrGridSize.setToolTipText("Grid size");
        sdrGridSize.setValue(10);
        sdrGridSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sdrGridSizeStateChanged(evt);
            }
        });
        tbarGrid.add(sdrGridSize);

        tbarWIndow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(-16777216,true)));
        tbarWIndow.setRollover(true);
        tbarWIndow.setBorderPainted(false);
        tbarWIndow.setOpaque(false);

        lblScaleTitle.setText("  Scale : ");
        tbarWIndow.add(lblScaleTitle);

        cbxScale.setBackground(new java.awt.Color(-3618576,true));
        cbxScale.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Scale to fit", "200", "175", "150", "125", "100", "75", "50", "25" }));
        cbxScale.setSelectedIndex(5);
        cbxScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxScaleActionPerformed(evt);
            }
        });
        tbarWIndow.add(cbxScale);
        tbarWIndow.add(jSeparator2);

        cbxAutoHide.setText("Auto-hide");
        cbxAutoHide.setFocusable(false);
        cbxAutoHide.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbxAutoHide.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cbxAutoHide.setOpaque(false);
        cbxAutoHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAutoHideActionPerformed(evt);
            }
        });
        tbarWIndow.add(cbxAutoHide);

        tbarComponents.setFloatable(false);
        tbarComponents.setRollover(true);
        tbarComponents.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbarComponents.setOpaque(false);

        btnWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolWindow.png"))); // NOI18N
        btnWindow.setToolTipText("Window");
        btnWindow.setFocusable(false);
        btnWindow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnWindow.setOpaque(false);
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
        btnPanel.setOpaque(false);
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
        btnButton.setOpaque(false);
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
        btnImgButton.setOpaque(false);
        btnImgButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImgButtonActionPerformed(evt);
            }
        });
        tbarComponents.add(btnImgButton);

        btnImgTogButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolButtonImgTog.png"))); // NOI18N
        btnImgTogButton.setToolTipText("Image Toggle Button");
        btnImgTogButton.setFocusable(false);
        btnImgTogButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImgTogButton.setOpaque(false);
        btnImgTogButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImgTogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImgTogButtonActionPerformed(evt);
            }
        });
        tbarComponents.add(btnImgTogButton);

        btnLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolLabel.png"))); // NOI18N
        btnLabel.setToolTipText("Label");
        btnLabel.setFocusable(false);
        btnLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLabel.setOpaque(false);
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
        btnTextfield.setOpaque(false);
        btnTextfield.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTextfieldActionPerformed(evt);
            }
        });
        tbarComponents.add(btnTextfield);

        btnTextarea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolTextArea.png"))); // NOI18N
        btnTextarea.setFocusable(false);
        btnTextarea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTextarea.setOpaque(false);
        btnTextarea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTextarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTextareaActionPerformed(evt);
            }
        });
        tbarComponents.add(btnTextarea);

        btnSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSlider.png"))); // NOI18N
        btnSlider.setToolTipText("Slider");
        btnSlider.setFocusable(false);
        btnSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSlider.setOpaque(false);
        btnSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSlider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSliderActionPerformed(evt);
            }
        });
        tbarComponents.add(btnSlider);

        btnCoolSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCoolSlider.png"))); // NOI18N
        btnCoolSlider.setToolTipText("Custom Slider");
        btnCoolSlider.setFocusable(false);
        btnCoolSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCoolSlider.setOpaque(false);
        btnCoolSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCoolSlider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoolSliderActionPerformed(evt);
            }
        });
        tbarComponents.add(btnCoolSlider);

        btnSlider2D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSlider2D.png"))); // NOI18N
        btnSlider2D.setToolTipText("2D Slider");
        btnSlider2D.setFocusable(false);
        btnSlider2D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSlider2D.setOpaque(false);
        btnSlider2D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSlider2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSlider2DActionPerformed(evt);
            }
        });
        tbarComponents.add(btnSlider2D);

        btnStick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolStick.png"))); // NOI18N
        btnStick.setToolTipText("Stick");
        btnStick.setFocusable(false);
        btnStick.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStick.setOpaque(false);
        btnStick.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStickActionPerformed(evt);
            }
        });
        tbarComponents.add(btnStick);

        btnKnob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolKnob.png"))); // NOI18N
        btnKnob.setToolTipText("Knob");
        btnKnob.setFocusable(false);
        btnKnob.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKnob.setOpaque(false);
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
        btnCheckbox.setOpaque(false);
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
        btnOption.setOpaque(false);
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
        btnOptGroup.setOpaque(false);
        btnOptGroup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOptGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptGroupActionPerformed(evt);
            }
        });
        tbarComponents.add(btnOptGroup);

        btnDropList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolDropList.png"))); // NOI18N
        btnDropList.setToolTipText("Drop List");
        btnDropList.setFocusable(false);
        btnDropList.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDropList.setOpaque(false);
        btnDropList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDropList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropListActionPerformed(evt);
            }
        });
        tbarComponents.add(btnDropList);

        btnSketchPad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSketchPad.png"))); // NOI18N
        btnSketchPad.setToolTipText("Sketchpad");
        btnSketchPad.setFocusable(false);
        btnSketchPad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSketchPad.setOpaque(false);
        btnSketchPad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSketchPad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSketchPadActionPerformed(evt);
            }
        });
        tbarComponents.add(btnSketchPad);

        btnTimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolTimer.png"))); // NOI18N
        btnTimer.setToolTipText("Timer");
        btnTimer.setFocusable(false);
        btnTimer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTimer.setOpaque(false);
        btnTimer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimerActionPerformed(evt);
            }
        });
        tbarComponents.add(btnTimer);

        javax.swing.GroupLayout pnlToolBarsLayout = new javax.swing.GroupLayout(pnlToolBars);
        pnlToolBars.setLayout(pnlToolBarsLayout);
        pnlToolBarsLayout.setHorizontalGroup(
            pnlToolBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolBarsLayout.createSequentialGroup()
                .addGroup(pnlToolBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolBarsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tbarGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                        .addComponent(tbarWIndow, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlToolBarsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(tbarComponents, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlToolBarsLayout.setVerticalGroup(
            pnlToolBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolBarsLayout.createSequentialGroup()
                .addGroup(pnlToolBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tbarGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbarWIndow, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbarComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        splitControl.setDividerLocation(160);
        splitControl.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitControl.setDoubleBuffered(true);
        splitControl.setMinimumSize(new java.awt.Dimension(3, 5));
        splitControl.setPreferredSize(new java.awt.Dimension(250, 525));

        jLabel2.setBackground(new java.awt.Color(255, 255, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CONTROLS");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
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
                .addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
        );

        splitControl.setTopComponent(pnlTreeView);

        jLabel1.setBackground(new java.awt.Color(255, 255, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PROPERTIES");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
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
                .addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
        );

        splitControl.setRightComponent(pnlPropViiew);

        pnlWindowsView.setToolTipText("Winodws Panel");
        pnlWindowsView.setDoubleBuffered(false);

        javax.swing.GroupLayout pnlWindowsViewLayout = new javax.swing.GroupLayout(pnlWindowsView);
        pnlWindowsView.setLayout(pnlWindowsViewLayout);
        pnlWindowsViewLayout.setHorizontalGroup(
            pnlWindowsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );
        pnlWindowsViewLayout.setVerticalGroup(
            pnlWindowsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlWindowsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(splitControl, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnlToolBars, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlToolBars, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlWindowsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(splitControl, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)))
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
		guiControl.addComponent(new DImageButton());
	}//GEN-LAST:event_btnImgButtonActionPerformed

	private void btnLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLabelActionPerformed
		guiControl.addComponent(new DLabel());
	}//GEN-LAST:event_btnLabelActionPerformed

	private void btnTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTextfieldActionPerformed
		guiControl.addComponent(new DTextField());
	}//GEN-LAST:event_btnTextfieldActionPerformed

	private void btnSliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSliderActionPerformed
		guiControl.addComponent(new DSlider());
	}//GEN-LAST:event_btnSliderActionPerformed

	private void btnCoolSliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoolSliderActionPerformed
		guiControl.addComponent(new DCustomSlider());
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
		guiControl.addComponent(new DToggleGroup());
	}//GEN-LAST:event_btnOptGroupActionPerformed

	private void btnDropListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropListActionPerformed
		guiControl.addComponent(new DDropList());
	}//GEN-LAST:event_btnDropListActionPerformed

	private void btnTimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimerActionPerformed
		guiControl.addComponent(new DTimer());
	}//GEN-LAST:event_btnTimerActionPerformed

	private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
		guiControl.removeComponent();
	}//GEN-LAST:event_btnRemoveActionPerformed

	private void btnTextareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTextareaActionPerformed
		guiControl.addComponent(new DTextArea());
	}//GEN-LAST:event_btnTextareaActionPerformed

	private void btnSketchPadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSketchPadActionPerformed
		guiControl.addComponent(new DSketchPad());
	}//GEN-LAST:event_btnSketchPadActionPerformed

	private void btnSlider2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSlider2DActionPerformed
		guiControl.addComponent(new DSlider2D());
	}//GEN-LAST:event_btnSlider2DActionPerformed

	private void btnStickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStickActionPerformed
		guiControl.addComponent(new DStick());
	}//GEN-LAST:event_btnStickActionPerformed

	private void btnImgTogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImgTogButtonActionPerformed
		guiControl.addComponent(new DImageToggleButton());
	}//GEN-LAST:event_btnImgTogButtonActionPerformed

	private void cbxScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxScaleActionPerformed
		JComboBox cbx = (JComboBox) evt.getSource();
		switch (cbx.getSelectedIndex()) {
		case 0:
			guiControl.makeWindowSizeToFit();
			break;
		case 1:
			guiControl.setScale(200);
			break;
		case 2:
			guiControl.setScale(175);
			break;
		case 3:
			guiControl.setScale(150);
			break;
		case 4:
			guiControl.setScale(125);
			break;
		case 5:
			guiControl.setScale(100);
			break;
		case 6:
			guiControl.setScale(75);
			break;
		case 7:
			guiControl.setScale(50);
			break;
		case 8:
			guiControl.setScale(25);
			break;
		}
	}//GEN-LAST:event_cbxScaleActionPerformed

	private void cbxShowGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxShowGridActionPerformed
		guiControl.showGrid(((JCheckBox) evt.getSource()).isSelected());
	}//GEN-LAST:event_cbxShowGridActionPerformed

	private void btnSnapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSnapActionPerformed
		guiControl.snapGrid(((JCheckBox) evt.getSource()).isSelected());
	}//GEN-LAST:event_btnSnapActionPerformed

	private void sdrGridSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sdrGridSizeStateChanged
		int gs = ((JSlider) evt.getSource()).getValue();
		if(gs % 2 == 1)
			gs++;
		if (gs < 10) {
			lblGridSize.setText("  " + gs + " ");
		} else {
			lblGridSize.setText(" " + gs + " ");
		}
		guiControl.setGridSize(gs);
	}//GEN-LAST:event_sdrGridSizeStateChanged

	private void cbxAutoHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxAutoHideActionPerformed
		autoHide = ((JCheckBox)evt.getSource()).isSelected();
	}//GEN-LAST:event_cbxAutoHideActionPerformed

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
    private javax.swing.JButton btnButton;
    private javax.swing.JButton btnCheckbox;
    private javax.swing.JButton btnCoolSlider;
    private javax.swing.JButton btnDropList;
    private javax.swing.JButton btnImgButton;
    private javax.swing.JButton btnImgTogButton;
    private javax.swing.JButton btnKnob;
    private javax.swing.JButton btnLabel;
    private javax.swing.JButton btnOptGroup;
    private javax.swing.JButton btnOption;
    private javax.swing.JButton btnPanel;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSketchPad;
    private javax.swing.JButton btnSlider;
    private javax.swing.JButton btnSlider2D;
    private javax.swing.JCheckBox btnSnap;
    private javax.swing.JButton btnStick;
    private javax.swing.JButton btnTextarea;
    private javax.swing.JButton btnTextfield;
    private javax.swing.JButton btnTimer;
    private javax.swing.JButton btnWindow;
    private javax.swing.JCheckBox cbxAutoHide;
    private javax.swing.JComboBox cbxScale;
    private javax.swing.JCheckBox cbxShowGrid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JLabel lblGridSize;
    private javax.swing.JLabel lblGridSizeTitle;
    private javax.swing.JLabel lblGridTitle;
    private javax.swing.JLabel lblScaleTitle;
    private java.awt.Panel pnlPropViiew;
    private javax.swing.JPanel pnlToolBars;
    private java.awt.Panel pnlTreeView;
    private javax.swing.JPanel pnlWindowsView;
    private javax.swing.JSlider sdrGridSize;
    private javax.swing.JScrollPane spBot;
    private javax.swing.JScrollPane spTop;
    private javax.swing.JLabel spacer01;
    private javax.swing.JSplitPane splitControl;
    private javax.swing.JToolBar tbarComponents;
    private javax.swing.JToolBar tbarControls;
    private javax.swing.JToolBar tbarGrid;
    private javax.swing.JToolBar tbarWIndow;
    // End of variables declaration//GEN-END:variables
}
