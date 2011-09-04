package g4p.tool.gui;

import g4p.tool.TFileConstants;
import g4p.tool.components.DApplication;
import g4p.tool.components.DBase;
import g4p.tool.components.DWindow;
import g4p.tool.components.IdGen;
import g4p.tool.components.NameGen;
import g4p.tool.gui.propertygrid.IPropView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Sketch;
import processing.app.SketchCode;

public class GuiControl implements TFileConstants {

	private Editor editor = null;

	private ITabView tabs;
	private ISketchView tree;
	private IPropView props;

	private String guiPdeBase = "";
	
	/**
	 * @param tabs
	 * @param tree
	 * @param props
	 */
	public GuiControl(Editor editor, ITabView tabs, ISketchView tree, IPropView props) {
		super();
		this.editor = editor;
		this.tabs = tabs;
		this.tree = tree;
		this.props = props;
		Base base = editor.getBase();
//		String fname = base.getSketchbookFolder() + SEP + GUI_PDE_BASE;
//		System.out.println(fname);
		try {
			File f = new File(base.getSketchbookFolder() + SEP + GUI_PDE_BASE);
			guiPdeBase = base.loadFile(f);
			System.out.println(guiPdeBase);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void captureCode(){

	}

	public void generateCode(){
		String code = "my first program\n\tquarks\n\t2011";
		Sketch sketch = editor.getSketch();
		SketchCode gui_tab = getTab(sketch, PDE_TAB_PRETTY_NAME);
		int gui_tab_index = sketch.getCodeIndex(gui_tab);
		sketch.setCurrentCode(gui_tab_index);
		// Generate code here then save using editor.
		editor.setText(code);
		try {
			gui_tab.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String makeGuiCode(){
		String code = "";
		ArrayList<String> declarations;
		ArrayList<String> definitions;
		
		
		
		return code;
	}
	
	private SketchCode getTab(Sketch s, String tabName){
		SketchCode[] tabs = s.getCode();
		SketchCode gui = null;
		for(SketchCode sc : tabs){
			if(sc.getPrettyName().equals(tabName)){
				gui = sc;
				break;
			}
		}
		return gui;
	}

	private int getTabIndex(Sketch s, String tabName){
		SketchCode[] tabs = s.getCode();
		int index = -1;
		for(int i = 0; i < tabs.length; i++){
			if(tabs[i].getPrettyName().equals(tabName)){
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * This method loads the serialised GUI layout (tree model)
	 */
	public void loadGuiLayout() {
		DefaultTreeModel dm = null;
		File file;
		if(editor == null){
			file = new File(MODEL_FILENAME);
		}
		else {
			file = new File(editor.getSketch().getFolder(), MODEL_FILENAME);
		}
		if(file.exists()){
			NameGen.instance().reset();
			IdGen.instance().reset();
			dm = tree.loadModel(file);
		}
		if(dm != null)
			this.makGUIfromTreeModel((CtrlSketchModel) dm);
		else
			this.initModel();		
	}

	/**
	 * This saves the GUI (tree) model) layout using serialisation.
	 */
	public void saveGuiLayout() {
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