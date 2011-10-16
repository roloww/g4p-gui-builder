package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.TDataConstants;
import g4p.tool.TFileConstants;
import g4p.tool.TGuiConstants;
import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.CtrlPropModel;
import g4p.tool.gui.propertygrid.Validator;
import g4p.tool.gui.tabview.WindowView.MutableDBase;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;

import processing.app.Editor;

/**
 * This class is used to represent non-visual components that do not contain
 * other components.
 * 
 * 
 * @author Peter Lager
 *
 */
@SuppressWarnings("serial")
public abstract class DBase extends DefaultMutableTreeNode implements Serializable, TDataConstants, TFileConstants, TGuiConstants {

	protected static String $(int i){
		return String.valueOf(i);
	}
	
	protected static String $(float f){
		return String.valueOf(f);
	}
	
	transient public CtrlPropModel propertyModel;

	// Whether it is selectable in the WindowView
	// set to false for DOptionGroup and DTimer
	protected boolean selectable = true;
	protected boolean resizeable = true;
	protected boolean moveable = true;

	public String componentClass = "";

	public Integer[] id = new Integer[1];
//	public Integer id = null;
	
	public String 		_0010_name = "APPLICATION";
	public String 		name_label = "Variable Name";
	public String 		name_tooltip = null;
	public Boolean 		name_edit = false;
	public Boolean 		name_show = true;
	public Validator 	name_validator = Validator.getValidator(COMPONENT_NAME);

	public int 			_0120_x = 0;
	public String 		x_label = "X";
	public String 		x_tooltip = "pixels";
	public Boolean 		x_edit = false;
	public Boolean 		x_show = false;
	public Validator 	x_validator = Validator.getValidator(int.class, -9999, 9999);

	public int 			_0121_y = 0;
	public String 		y_label = "Y";
	public String 		y_tooltip = "pixels";
	public Boolean 		y_edit = false;
	public Boolean 		y_show = false;
	public Validator 	y_validator = x_validator;

	public int 			_0130_width = 0;
	public String 		width_label = "Width";
	public String 		width_tooltip = "pixels";
	public Boolean 		width_edit = false;
	public Boolean 		width_show = false;
	public Validator 	width_validator = Validator.getValidator(int.class, 10, 9999);

	public int 			_0131_height = 0;
	public String 		height_label = "Height";
	public String 		height_tooltip = "pixels";
	public Boolean 		height_edit = false;
	public Boolean 		height_show = false;
	public Validator 	height_validator = width_validator;

	public String		_0701_eventHandler = "Event handler";
	public String 		eventHandler_label = "Event method";
	public String 		eventHandler_tooltip = "unique name for event handler method";
	public Boolean 		eventHandler_edit = false;
	public Boolean 		eventHandler_show = false;
	public Validator 	eventHandler_validator = Validator.getValidator(COMPONENT_NAME);


	public DBase(){
		allowsChildren = false;
		id[0] = IdGen.instance().getNext();
	}

	// ==========================================================================
	// ==========================================================================
	// ===============   Stuff for code generation   ============================
	// ==========================================================================
	
	/**
	 * Recursive function to get the event methods for all controls
	 * 
	 * @param lines
	 */
	public void make_event_method(ArrayList<String> lines){
		String methodDef = get_event_definition();
		if(methodDef != null)
			lines.add(methodDef);
		if(allowsChildren){
			Enumeration<?> e = children();
			while(e.hasMoreElements()){
				((DBase)e.nextElement()).make_event_method(lines);
			}
		}		
	}

	/**
	 * Recursive function to get the variable declaration.
	 * 
	 * @param lines
	 */
	public void make_declaration(ArrayList<String> lines){
		String decl = get_declaration();
		if(decl != null)
			lines.add(decl);
		if(allowsChildren){
			Enumeration<?> e = children();
			while(e.hasMoreElements()){
				((DBase)e.nextElement()).make_declaration(lines);
			}
		}		
	}

	/**
	 * Recursive function to first get the creator code for this
	 * component then repeat for any children. <br>
	 * This method is overridden in DWindow, DPanel and DOptionGroup.
	 * 
	 * @param lines
	 * @param parent
	 */
	public void make_creator(ArrayList<String> lines, DBase parent){
		DBase comp;
		Enumeration<?> e;
		String ccode = get_creator(parent);
		if(ccode != null && !ccode.equals(""))
			lines.add(ccode);
		if(allowsChildren){
			e = children();
			while(e.hasMoreElements()){
				comp = (DBase)e.nextElement();
				comp.make_creator(lines, this);
			}
		}				
	}
	
	/**
	 * Get the declaration for this control
	 */
	protected String get_declaration(){
		return componentClass + " " + _0010_name+ "; \n";
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		return null;
	}
	

	// ==========================================================================
	// ==========================================================================
	// =================   Stuff for event code generation   ====================
	// ==========================================================================

	/** get the event method for this control
	 * 
	 * @return
	 */
	protected String get_event_definition(){
		String ec = get_event_header() + get_event_code() + get_event_end(0);
		return ec;
	}
	
	/**
	 * Get the event code if none then return generic message
	 * @param code
	 * @return
	 */
	protected String get_event_code(){ 
		return get_event_code(0);
	}

	/**
	 * Get the event code if none then return generic message
	 * @param n
	 * @return
	 */
	protected String get_event_code(int n){
		if(n < 0 || n >= id.length)
			n = 0;
		String ev_code = Code.instance().get(id[n]);
		if(ev_code == null)
			return Messages.build(CODE_ANY, _0010_name, componentClass);
		else
			return ev_code; 
	}
	
	/**
	 * Get the event header
	 * @return
	 */
	protected String get_event_header(int n){
		if(n < 0 || n >= id.length)
			n = 0;
		return Messages.build(METHOD_START_1, _0701_eventHandler, componentClass, 
				componentClass.substring(1).toLowerCase(), 
				_0010_name, $(id[n])).replace('[', '{');
	}
	
	protected String get_event_header(){
		return get_event_header(0);
	}
	
	/**
	 * Get the event method end with tag
	 * @return
	 */
	protected String get_event_end(int n){
		if(n < 0 || n >= id.length)
			n = 0;
		return Messages.build(METHOD_END, _0010_name, 
				$(id[n])).replace(']', '}');
	}
	
	protected String get_event_end(){
		return get_event_end(0);
	}
	
	
	// ==========================================================================
	// ==========================================================================
	// =================   Stuff for code serialisation   =======================
	// ==========================================================================

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		NameGen.instance().add(_0701_eventHandler);
		IdGen.instance().add(id[0]);
	}
	
	
	// ==========================================================================
	// ==========================================================================
	// =================    Stuff for debugging   ===============================
	// ==========================================================================

	/**
	 * Display details - used for debugging only
	 */
	public String show(){
		return Messages.build("{0}  {1} Pos [{2},{3}] Size [{4}, {5}]", this.getClass(), _0010_name, _0120_x, _0121_y, _0130_width, _0131_height);
	}

	/**
	 * Use this to return the name of the component
	 */
	public String toString(){
		return _0010_name;
	}


	// ==========================================================================
	// ==========================================================================
	// =================    Stuff for drawing   =================================
	// ==========================================================================

	public void draw(Graphics2D g2, AffineTransform af, DBase selected) {
	}
	
	/**
	 * Call this method when a change is made to the object in the window view.
	 */
	public void update(){
	}

	public void drawSelector(Graphics2D g){
		g.setStroke(stdStroke);
		g.setColor(Color.red);
		g.drawRect(0, 0,_0130_width, _0131_height);
		if(resizeable){
			drawHandle(g, _0130_width - HANDLE_SIZE, (_0131_height - HANDLE_SIZE)/2);
			drawHandle(g, (_0130_width - HANDLE_SIZE) / 2, _0131_height - HANDLE_SIZE);
			drawHandle(g, _0130_width - HANDLE_SIZE, _0131_height - HANDLE_SIZE);
		}
	}

	protected void drawHandle(Graphics2D g, int x, int y){
		g.setColor(Color.white);
		g.fillRect(x, y , HANDLE_SIZE, HANDLE_SIZE);
		g.setColor(Color.red);
		g.drawRect(x, y , HANDLE_SIZE, HANDLE_SIZE);
	}

	public void isOver(MutableDBase m, int x, int y) {
		if(selectable){
			x -= _0120_x;
			y -= _0121_y;
			
			if(getSize() < m.area && isOverRectangle(x, y, 0, 0, _0130_width, _0131_height)){			
				m.selID = OVER_COMP;
				m.comp = this;
				m.area = getSize();
				if(resizeable){
					if(isOverRectangle(x,y, _0130_width - HANDLE_SIZE, (_0131_height - HANDLE_SIZE)/2, HANDLE_SIZE, HANDLE_SIZE))
						m.selID = OVER_HORZ;
					else if(isOverRectangle(x,y, (_0130_width - HANDLE_SIZE) / 2, _0131_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
						m.selID = OVER_VERT;
					else if(isOverRectangle(x,y, _0130_width - HANDLE_SIZE, _0131_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
						m.selID = OVER_DIAG;
				}
			}
		}
		if(allowsChildren){
			Enumeration<?> e = children();
			while(e.hasMoreElements()){
				((DBase)e.nextElement()).isOver(m, x, y);
			}
		}
	}

	protected boolean isOverRectangle(int px, int py, int x, int y, int w, int h){
		return px >= x && px < x + w && py >= y && py < y + h;
	}

	
	// ==========================================================================
	// ==========================================================================
	// ==================   Setters and getters   ===============================
	// ==========================================================================

	public void set_name(String name){
		_0010_name = name;
	}

	public void set_event_name(String e_name){
		_0701_eventHandler = e_name;
	}
	
	public String get_name() { return _0010_name; }
	
	public boolean isSelectable(){
		return selectable;
	}

	public boolean isResizeable(){
		return this.resizeable;
	}
	
	public boolean isMoveable(){
		return moveable;
	}
	
	public void makeTableModel(){
		propertyModel = new CtrlPropModel(this);
	}

	/**
	 * Get the property model for this component. If it does not exist then
	 * create it first
	 * @return
	 */
	public CtrlPropModel getTableModel(){
		if(propertyModel == null)
			makeTableModel();
		return propertyModel;
	}

	/**
	 * Will return null if no image
	 * @param filename
	 * @return
	 */
	public BufferedImage getImageFromDataFolder(String filename){
		BufferedImage img = null;
		Editor ed = GuiDesigner.editor();
		File f = new File(ed.getSketch().getDataFolder(), filename);
		try {
			img = ImageIO.read(f);
		} 
		catch (IOException e) {
			img = null;
		}
		return img;
	}
	
	public int getSize(){
		return _0130_width * _0131_height;
	}
	
	public void updatedInGUI(){		
	}
}
