package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.TDataConstants;
import g4p.tool.TGuiConstants;
import g4p.tool.gui.WindowView.MutableDBase;
import g4p.tool.gui.propertygrid.CtrlPropModel;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class is used to represent none visual components that can containing
 * other components.
 * 
 * This class is used to represent the SKERTCH application and GOptionGroup
 * 
 * @author Peter Lager
 *
 */
@SuppressWarnings("serial")
public abstract class DBase extends DefaultMutableTreeNode implements Serializable, TDataConstants, TGuiConstants {

	public static final String COMP_NAME_PROPERTY = "_0005_name";
	
	transient public CtrlPropModel propertyModel;

	// Whether it is selectable in the WindowView
	// set to false for DOptionGroup and DTimer
	protected boolean selectable = true;
	protected boolean resizeable = true;
	protected boolean moveable = true;

	// Important attributes
	public String 		_0005_name = "APPLICATION";
	public String 		name_label = "Variable Name";
	public String 		name_tooltip = null;
	public Boolean 		name_edit = false;
	public Boolean 		name_show = true;
	public Validator 	name_validator = Validator.getValidator(COMPONENT_NAME);

	public int 			_0020_x = 0;
	public String 		x_label = "X";
	public String 		x_tooltip = "pixels";
	public Boolean 		x_edit = false;
	public Boolean 		x_show = false;
	public Validator 	x_validator = Validator.getValidator(int.class, -9999, 9999);

	public int 			_0021_y = 0;
	public String 		y_label = "Y";
	public String 		y_tooltip = "pixels";
	public Boolean 		y_edit = false;
	public Boolean 		y_show = false;
	public Validator 	y_validator = x_validator;

	public int 			_0024_width = 0;
	public String 		width_label = "Width";
	public String 		width_tooltip = "pixels";
	public Boolean 		width_edit = false;
	public Boolean 		width_show = false;
	public Validator 	width_validator = Validator.getValidator(int.class, 10, 999);

	public int 			_0025_height = 0;
	public String 		height_label = "Height";
	public String 		height_tooltip = "pixels";
	public Boolean 		height_edit = false;
	public Boolean 		height_show = false;
	public Validator 	height_validator = width_validator;



	public DBase(){
		allowsChildren = false;
		//		System.out.println("\tDBase() ctor");
	}


	// SETTERS

	public void set_name(String name){
		_0005_name = name;
	}

	public void set_x(int x){
		_0020_x = x;
	}

	public void set_y(int y){
		_0021_y = y;
	}

	public void set_width(int width){
		_0024_width = width;
	}

	public void set_height(int height){
		_0025_height = height;
	}


	// GETTERS


	public String get_name() { return _0005_name; }

	public int get_x() { return _0020_x;	}

	public int get_y() { return _0021_y; }

	public int get_width() { return _0024_width; }

	public int get_height() { return _0025_height; }

	public String get_text() { return ""; }

	public String get_title() { return ""; }

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

	public CtrlPropModel getTableModel(){
		if(propertyModel == null)
			makeTableModel();
		return propertyModel;
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
	}
	
	// ====================================================================================================

	/**
	 * Display details - used for debugging only
	 */
	public String show(){
		return Messages.build("{0}  {1} Pos [{2},{3}] Size [{4}, {5}]", this.getClass(), _0005_name, _0020_x, _0021_y, _0024_width, _0025_height);
	}

	/**
	 * Use this to return the name of the component
	 */
	public String toString(){
		return _0005_name;
	}


	// ====================================================================================================
	// ====================================================================================================
	// ==========================    Stuff for drawing   ==================================================
	// ====================================================================================================

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
		g.drawRect(0, 0,_0024_width, _0025_height);

		drawHandle(g, _0024_width - HANDLE_SIZE, (_0025_height - HANDLE_SIZE)/2);
		drawHandle(g, (_0024_width - HANDLE_SIZE) / 2, _0025_height - HANDLE_SIZE);
		drawHandle(g, _0024_width - HANDLE_SIZE, _0025_height - HANDLE_SIZE);	
	}

	protected void drawHandle(Graphics2D g, int x, int y){
		g.setColor(Color.white);
		g.fillRect(x, y , HANDLE_SIZE, HANDLE_SIZE);
		g.setColor(Color.red);
		g.drawRect(x, y , HANDLE_SIZE, HANDLE_SIZE);
	}

	public void isOver(MutableDBase m, int x, int y) {
		if(selectable){
			x -= _0020_x;
			y -= _0021_y;
			//
			if(getSize() < m.area && isOverRectangle(x, y, 0, 0, _0024_width, _0025_height)){			
				m.selID = OVER_COMP;
				m.comp = this;
				m.area = getSize();
				if(isOverRectangle(x,y, _0024_width - HANDLE_SIZE, (_0025_height - HANDLE_SIZE)/2, HANDLE_SIZE, HANDLE_SIZE))
					m.selID = OVER_HORZ;
				else if(isOverRectangle(x,y, (_0024_width - HANDLE_SIZE) / 2, _0025_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
					m.selID = OVER_VERT;
				else if(isOverRectangle(x,y, _0024_width - HANDLE_SIZE, _0025_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
					m.selID = OVER_DIAG;
			}
		}
		if(this.allowsChildren){
			Enumeration<?> e = children();
			while(e.hasMoreElements()){
				((DBase)e.nextElement()).isOver(m, x, y);
			}
		}
	}

	protected boolean isOverRectangle(int px, int py, int x, int y, int w, int h){
		return px >= x && px <= x + w && py >= y && py <= y + h;
	}

	public int getSize(){
		return _0024_width * _0025_height;
	}



}
