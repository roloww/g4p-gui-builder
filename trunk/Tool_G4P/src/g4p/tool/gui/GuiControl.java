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
import processing.core.PApplet;

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
		if(editor != null){
			try {
				File f = new File(editor.getBase().getSketchbookFolder() + SEP + GUI_PDE_BASE);
				guiPdeBase = Base.loadFile(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addComponent(DBase comp){
		tree.addComponent(comp);
		return true;
	}

	public boolean removeComponent(){
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
		String code;
		Sketch sketch = editor.getSketch();
		SketchCode gui_tab = getTab(sketch, PDE_TAB_PRETTY_NAME);
		int gui_tab_index = sketch.getCodeIndex(gui_tab);
		sketch.setCurrentCode(gui_tab_index);
		try {
			gui_tab.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		code = gui_tab.getProgram();
		System.out.println("\nCODE  \n");
		System.out.println(code);
	}

	public void generateCode(){
		String code;
		Sketch sketch = editor.getSketch();
		SketchCode gui_tab = getTab(sketch, PDE_TAB_PRETTY_NAME);
		int gui_tab_index = sketch.getCodeIndex(gui_tab);
		sketch.setCurrentCode(gui_tab_index);
		// Generate code here then save using editor.
		code = makeGuiCode();
		editor.setText(code);
		editor.setSelection(0, 0);
		editor.repaint();
		try {
			gui_tab.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String makeGuiCode(){
		String code = "";
		ArrayList<String> evtMethods = new ArrayList<String>();	
		ArrayList<String> compCreators = new ArrayList<String>();
		ArrayList<String> addToWin = new ArrayList<String>();
		ArrayList<String> compDecs = new ArrayList<String>();

		evtMethods.add(guiPdeBase);

		compCreators.add("// Create all the GUI controls.");
		compCreators.add("// autogenerated do not edit");
		compCreators.add("void createGUI(){");

		compDecs.add("// Variable declarations ");
		compDecs.add("// autogenerated do not edit\n");

		tree.generateDeclarations(compDecs);
		tree.generateEvtMethods(evtMethods);
		tree.generateCreator(compCreators);
		tree.generateAddToWin(addToWin);

		DBase app = tree.getRoot();

		// Close the create method
		compCreators.addAll(addToWin);
		compCreators.add("}\n\n\n\n");
		
		// Build up full sketch code
		evtMethods.addAll(compCreators);
		evtMethods.addAll(compDecs);
		String[] lines = evtMethods.toArray((new String[compCreators.size()]));
		code = PApplet.join(lines, "\n");
		return code;
	}

	/**
	 * For a particular sketch get the SketchCode for a given
	 * tab name.
	 * 
	 * @param s
	 * @param tabName
	 * @return
	 */
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

	/**
	 * For a particular sketch get the index number of a tab
	 * with a given name. 
	 * 
	 * @param s
	 * @param tabName
	 * @return
	 */
	@SuppressWarnings("unused")
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
		// Editor == null if run outside of Processing
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
