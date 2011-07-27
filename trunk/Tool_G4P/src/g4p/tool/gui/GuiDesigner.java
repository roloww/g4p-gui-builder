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
import g4p.tool.components.DPanel;
import g4p.tool.components.DWindow;
import g4p.tool.gui.propertygrid.CtrlPropModel;
import g4p.tool.gui.propertygrid.CtrlPropView;
import g4p.tool.gui.propertygrid.CtrlSketchModel;
import g4p.tool.gui.propertygrid.CtrlSketchView;

import java.awt.Dimension;
import java.io.File;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Sketch;

/**
 *
 * @author Peter Lager
 */
public class GuiDesigner extends javax.swing.JFrame {

	private Base base;
	private Editor editor;
	private Sketch sketch;
	private File config;
	//   private Set<String> words = new HashSet<String>();
	private CtrlSketchModel tm;
	private CtrlSketchView treeSketchView;
	private DBase startNode;
	private CtrlPropModel cpm;
	public static CtrlPropView tblPropView;

	//   private Icon tabIcon = new javax.swing.ImageIcon(getClass().getResource("/g4p/toolWindow.png"));
	private Icon tabIcon = ClassIcon.instance().getIcon(DWindow.class);

	/** Creates new form GuiDesignFrame */
	public GuiDesigner() {
		initComponents();
		setPreferredSize(new Dimension(1024, 800));

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
		setPreferredSize(new Dimension(1024, 800));

		tm = getSimpleSketchModel();
		// Now create GUI
		makGUIfromTreeModel(tm);

		setSelectedComponent(startNode);

		//       setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
	}

	/**
	 * This creates a simple tree model that we can use for testing
	 * @return
	 */
	private CtrlSketchModel getSimpleSketchModel() {
		CtrlSketchModel m = null;
		DApplication app = new DApplication();
		DWindow win1 = new DWindow(true);
		win1.set_width(800);
		win1.set_height(600);
		DWindow win2 = new DWindow(false);
		win2.set_width(320);
		win2.set_height(440);
		DButton btn1 = new DButton();
		DButton btn2 = new DButton();
		DPanel pnl = new DPanel();
		app.add(win1);
		win1.add(btn1);
		app.add(win2);
		win2.add(pnl);
		pnl.add(btn2);
		m = new CtrlSketchModel(app);
		startNode = pnl;
		return m;
	}

	/**
	 * This method is to prove that the entire GUI can be
	 * created from a Tree data model 
	 * @param m
	 */
	private void makGUIfromTreeModel(CtrlSketchModel m) {
		// Create Tree view
		treeSketchView = new CtrlSketchView(m);
		spTop.setViewportView(treeSketchView);
		System.out.println((DBase) m.getRoot());
		// Get root and initialise the property view
		tblPropView = new CtrlPropView(  ((DBase)m.getRoot()).getTableModel());
		spBot.setViewportView(tblPropView);
		// Create tabbed pane for each window
		Enumeration<?> windows = ((DBase) m.getRoot()).children();
		while(windows.hasMoreElements()){
			DBase win = (DBase) windows.nextElement();
			tabWindows.addTab(win.get_name(), tabIcon, new WindowView(tabWindows, win));
		}

	}

	public void setSelectedComponent(DBase comp){
		DefaultTreeModel m = (DefaultTreeModel) treeSketchView.getModel();
		TreeNode[] nodes = m.getPathToRoot(comp);
		TreePath tp = new TreePath(nodes);
		treeSketchView.setSelectionPath(tp);
		System.out.println(nodes.length);
		 int tabIndex = getTabIndexForComponent(comp);
		 if(tabIndex > 0)
			 tabWindows.setSelectedIndex(tabIndex);
	}

	public int getTabIndexForComponent(DBase comp){
		for(int i = 0; i < tabWindows.getTabCount(); i++){
			if( ((WindowView)tabWindows.getComponentAt(i)).getWindowComponent() == comp )
				return i;
		}
		return -1;
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
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
		panel1 = new java.awt.Panel();
		jLabel2 = new javax.swing.JLabel();
		spTop = new javax.swing.JScrollPane();
		tbarControls = new javax.swing.JToolBar();
		panel2 = new java.awt.Panel();
		jLabel1 = new javax.swing.JLabel();
		spBot = new javax.swing.JScrollPane();
		tabWindows = new javax.swing.JTabbedPane();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();

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
		tbarComponents.add(btnWindow);

		btnPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolPanel.png"))); // NOI18N
		btnPanel.setToolTipText("Floating Panel");
		btnPanel.setFocusable(false);
		btnPanel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnPanel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnPanel);

		btnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolButton.png"))); // NOI18N
		btnButton.setToolTipText("Button");
		btnButton.setFocusable(false);
		btnButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnButton);

		btnImgButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolButtonImg.png"))); // NOI18N
		btnImgButton.setToolTipText("Image Button");
		btnImgButton.setFocusable(false);
		btnImgButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnImgButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnImgButton);

		btnLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolLabel.png"))); // NOI18N
		btnLabel.setToolTipText("Label");
		btnLabel.setFocusable(false);
		btnLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnLabel);

		btnTextfield.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolTextField.png"))); // NOI18N
		btnTextfield.setToolTipText("Textfield");
		btnTextfield.setFocusable(false);
		btnTextfield.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnTextfield.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnTextfield);

		btnHorzSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSliderH.png"))); // NOI18N
		btnHorzSlider.setToolTipText("Slider");
		btnHorzSlider.setFocusable(false);
		btnHorzSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnHorzSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnHorzSlider);

		btnVertSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolSliderV.png"))); // NOI18N
		btnVertSlider.setToolTipText("Slider");
		btnVertSlider.setFocusable(false);
		btnVertSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnVertSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnVertSlider);

		btnCoolSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCoolSlider.png"))); // NOI18N
		btnCoolSlider.setToolTipText("Cool Slider");
		btnCoolSlider.setFocusable(false);
		btnCoolSlider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCoolSlider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnCoolSlider);

		btnKnob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolKnob.png"))); // NOI18N
		btnKnob.setToolTipText("Knob");
		btnKnob.setFocusable(false);
		btnKnob.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnKnob.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnKnob);

		btnCheckbox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCheckbox.png"))); // NOI18N
		btnCheckbox.setToolTipText("Checkbox");
		btnCheckbox.setFocusable(false);
		btnCheckbox.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCheckbox.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnCheckbox);

		btnOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolOption.png"))); // NOI18N
		btnOption.setToolTipText("Option");
		btnOption.setFocusable(false);
		btnOption.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnOption.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnOption);

		btnOptGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolOptGroup.png"))); // NOI18N
		btnOptGroup.setToolTipText("Option group");
		btnOptGroup.setFocusable(false);
		btnOptGroup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnOptGroup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnOptGroup);

		btnCombo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolCombo.png"))); // NOI18N
		btnCombo.setToolTipText("Combo Box");
		btnCombo.setFocusable(false);
		btnCombo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnCombo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		tbarComponents.add(btnCombo);

		btnTimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/g4p/toolTimer.png"))); // NOI18N
		btnTimer.setToolTipText("Timer");
		btnTimer.setFocusable(false);
		btnTimer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnTimer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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

		javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
		panel1.setLayout(panel1Layout);
		panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
				.addComponent(tbarControls, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
				.addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
		);
		panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panel1Layout.createSequentialGroup()
						.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(tbarControls, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(spTop, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
		);

		splitControl.setTopComponent(panel1);

		jLabel1.setBackground(new java.awt.Color(255, 255, 153));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("PROPERTIES");
		jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jLabel1.setOpaque(true);
		jLabel1.setPreferredSize(new java.awt.Dimension(56, 14));

		spBot.setBackground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
		panel2.setLayout(panel2Layout);
		panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
				.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
		);
		panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panel2Layout.createSequentialGroup()
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(spBot, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
		);

		splitControl.setRightComponent(panel2);

		tabWindows.setBackground(new java.awt.Color(255, 255, 255));

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(tbarComponents, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(tabWindows, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(splitControl, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(tbarComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(tabWindows, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
								.addComponent(splitControl, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)))
		);

		pack();
	}// </editor-fold>//GEN-END:initComponents

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
	private javax.swing.JButton btnCombo;
	private javax.swing.JButton btnCoolSlider;
	private javax.swing.JButton btnHorzSlider;
	private javax.swing.JButton btnImgButton;
	private javax.swing.JButton btnKnob;
	private javax.swing.JButton btnLabel;
	private javax.swing.JButton btnOptGroup;
	private javax.swing.JButton btnOption;
	private javax.swing.JButton btnPanel;
	private javax.swing.JButton btnTextfield;
	private javax.swing.JButton btnTimer;
	private javax.swing.JButton btnVertSlider;
	private javax.swing.JButton btnWindow;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private java.awt.Panel panel1;
	private java.awt.Panel panel2;
	private javax.swing.JScrollPane spBot;
	private javax.swing.JScrollPane spTop;
	private javax.swing.JSplitPane splitControl;
	private javax.swing.JTabbedPane tabWindows;
	private javax.swing.JToolBar tbarComponents;
	private javax.swing.JToolBar tbarControls;
	// End of variables declaration//GEN-END:variables
}
