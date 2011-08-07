package g4p.tool.gui;

import java.io.File;
import java.util.Enumeration;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Sketch;
import g4p.tool.TFileConstants;
import g4p.tool.gui.propertygrid.IPropView;
import g4p.tool.components.*;

public class GuiControl implements TFileConstants {

	private Editor editor = null;
	private Base base = null;
	private Sketch sketch;
	
	private ITabView tabs;
	private ISketchView tree;
	@SuppressWarnings("unused")
	private IPropView props;

	
	/**
	 * @param tabs
	 * @param tree
	 * @param props
	 */
	public GuiControl(Editor editor, ITabView tabs, ISketchView tree, IPropView props) {
		super();
		this.editor = editor;
		if(editor != null)
			base = editor.getBase();
		this.tabs = tabs;
		this.tree = tree;
		this.props = props;
	}

	public boolean addComponent(DBase comp){
		tree.addComponent(comp);
		return true;
	}

	public boolean removeComponent(){
		System.out.println("Trashcan selected ");
		tree.removeComponent();
		return true;
	}

	public void makeWindowSizeToFit(){
		tabs.scaleWindowToFit();
	}

	public void showGrid(boolean show){
		tabs.setShowGrid(show);
	}

	public void snapGrid(boolean snap){
		tabs.setSnapToGrid(snap);
	}

	public void setGridSize(int gs){
		tabs.setGridSize(gs);
	}
	
	
	public void loadGuiCode() {
		DefaultTreeModel dm = null;
	   	File file;
		if(editor == null){
			file = new File(MODEL_FILENAME);
		}
		else {
			file = new File(editor.getSketch().getFolder(), MODEL_FILENAME);
		}
		if(file.exists()){
			dm = tree.loadModel(file);
		}
		if(dm != null)
			this.makGUIfromTreeModel((CtrlSketchModel) dm);
		else
			this.initModel();
			
	}
	
    public void saveGuiCode() {
    	File file;
		if(editor == null){
			file = new File(MODEL_FILENAME);
		}
		else {
			file = new File(editor.getSketch().getFolder(), MODEL_FILENAME);
		}
		tree.saveModel(file);
	}

    
    /**
     * Temporary setup for testing purposes
     */
    public void initModel(){
    	makGUIfromTreeModel(getBaseSketchModel());
    }
    
	/**
	 * This method is to prove that the entire GUI can be
	 * created from a Tree data model 
	 * @param m
	 */
	private void makGUIfromTreeModel(CtrlSketchModel m) {
		tabs.deleteAllWindows();
		// Create Tree view
		tree.setModel((TreeModel)m);
		// Get start node
		DBase app = (DBase) m.getRoot();
		DBase mainDisplay = (DBase) app.getChildAt(0);
		// Get root and initialise the property view
		// Setup window display
		// Create tabbed pane for each window
		Enumeration<?> windows = ((DBase) m.getRoot()).children();
		while(windows.hasMoreElements()){
			DBase win = (DBase) windows.nextElement();
			tabs.addWindow(win);
		}
		props.showProprtiesFor(mainDisplay);
		tree.setSelectedComponent(mainDisplay);
		tabs.setSelectedComponent(mainDisplay);
	}

	/**
	 * Create a blank sketch
	 * @return
	 */
	private CtrlSketchModel getBaseSketchModel() {
		CtrlSketchModel m = null;
		DApplication app = new DApplication();
		DWindow win1 = new DWindow(true);
		app.add(win1);
		m = new CtrlSketchModel(app);
		return m;
	}




}
