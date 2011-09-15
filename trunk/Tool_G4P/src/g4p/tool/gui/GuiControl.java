package g4p.tool.gui;

import g4p.tool.TDataConstants;
import g4p.tool.TFileConstants;
import g4p.tool.components.Code;
import g4p.tool.components.DApplication;
import g4p.tool.components.DBase;
import g4p.tool.components.DWindow;
import g4p.tool.components.IdGen;
import g4p.tool.components.NameGen;
import g4p.tool.gui.propertygrid.IPropView;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Sketch;
import processing.app.SketchCode;
import processing.core.PApplet;

public class GuiControl implements TFileConstants, TDataConstants {

	private Editor editor = null;

	private ITabView tabs;
	private ISketchView tree;
	private IPropView props;

	private String guiPdeBase = "";

	private Pattern pCode, pSize;
	private Matcher m;

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
		pCode = Pattern.compile(CODE_TAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		pSize = Pattern.compile(SK_SIZE, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
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

	/**
	 * Set the sketch size in the designer window if one is provided
	 * @param size width and height
	 */
	public void setSketchSize(Dimension size){
		if(size != null){
			DBase m = (DBase) tree.getRoot().getChildAt(0);
			m.set_width(size.width);
			m.set_height(size.height);
		}
	}

	/**
	 * Get the size of the sketch from the code
	 * @return null if no size found
	 */
	public Dimension getSketchSize(){
		Dimension s = null;
		Sketch sketch = editor.getSketch();
		SketchCode curr =  sketch.getCurrentCode();
		int currIndex = getTabIndex(sketch, curr.getPrettyName());
		if(currIndex != 0)
			sketch.setCurrentCode(0);
		String code = editor.getText();
		code = processing.mode.java.JavaBuild.scrubComments(code);
		m = pSize.matcher(code);
		System.out.println("Looking for size");
		if(m.find() && m.groupCount() >= 3){
			try	{
				int wide = Integer.parseInt(m.group(1));
				int high = Integer.parseInt(m.group(2));
				s = new Dimension(wide, high);
				System.out.println("Found " + s.width + "  " + s.height);
			} catch (NumberFormatException e) {
				s = null;
			}
		}
		sketch.setCurrentCode(currIndex);
		return s;
	}
	
//	  static public String[] match(String what, String regexp) {
//		    Pattern p = matchPattern(regexp);
//		    Matcher m = p.matcher(what);
//		    if (m.find()) {
//		      int count = m.groupCount() + 1;
//		      String[] groups = new String[count];
//		      for (int i = 0; i < count; i++) {
//		        groups[i] = m.group(i);
//		      }
//		      return groups;
//		    }
//		    return null;
//		  }



	public void captureCode(){
		Sketch sketch = editor.getSketch();
		SketchCode gui_tab = getTab(sketch, PDE_TAB_PRETTY_NAME);
		int gui_tab_index = sketch.getCodeIndex(gui_tab);
		sketch.setCurrentCode(gui_tab_index);
		//		String code = gui_tab.getProgram();
		String code = editor.getText();
		gui_tab.setProgram(code);
		try {
			gui_tab.save();
		} catch (IOException e) {
			System.out.println("CAPTURE unable to save code");
			e.printStackTrace();
		}
		//pCode.matcher(code);
		m = pCode.matcher(code);
		ArrayList<CodeTag> tags = new ArrayList<CodeTag>();
		while(m.find()){
			String[] sa = m.group().split(":");
			tags.add(new CodeTag(sa[2], m.start(), m.end()));
		}
		// Validate tags???
		System.out.println("\nCODE CAPTURED \n");
		Code.instance().reset();
		for(int t = 0; t < tags.size(); t += 2){
			String snippet = code.substring(tags.get(t).e + 1, tags.get(t+1).s - 2);
			Code.instance().add(tags.get(t).id, snippet);
		}

	}

	public void generateCode(){
		String code;
		Sketch sketch = editor.getSketch();
		SketchCode gui_tab = getTab(sketch, PDE_TAB_PRETTY_NAME);
		int gui_tab_index = sketch.getCodeIndex(gui_tab);
		sketch.setCurrentCode(gui_tab_index);
		// Generate code here then save using editor.
		code = makeGuiCode();
		// Set the tab code and save it
		gui_tab.setProgram(code);
		// Set the code to show in the editor
		editor.setText(code);
		editor.setSelection(0, 0);
		editor.repaint();
		// Save the generated code
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
		ArrayList<String> compDecs = new ArrayList<String>();

		evtMethods.add(guiPdeBase);

		compCreators.add("// Create all the GUI controls.");
		compCreators.add("// autogenerated do not edit\n");
		compCreators.add("void createGUI(){\n");

		compDecs.add("// Variable declarations ");
		compDecs.add("// autogenerated do not edit\n");

		tree.generateDeclarations(compDecs);
		tree.generateEvtMethods(evtMethods);
		tree.generateCreator(compCreators);

		// Close the create method
		compCreators.add("}\n\n");

		// Build up full sketch code
		evtMethods.addAll(compCreators);
		evtMethods.addAll(compDecs);
		String[] lines = evtMethods.toArray((new String[compCreators.size()]));
		code = PApplet.join(lines, "");
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
		// Editor == null if run outside of Processing
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
		win1._0024_width = 480;
		win1._0025_height = 320;
		app.add(win1);
		m = new CtrlSketchModel(app);
		return m;
	}

	public class CodeTag {
		public Integer id;
		public int s, e;

		/**
		 * @param name
		 * @param s
		 * @param e
		 */
		public CodeTag(String name, Integer s, Integer e) {
			super();
			this.id = Integer.parseInt(name);
			this.s = s;
			this.e = e;
		}
	} // End of code tag class


}
