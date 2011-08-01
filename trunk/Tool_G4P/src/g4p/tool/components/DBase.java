package g4p.tool.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;

import g4p.tool.GTconstants;
import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.CtrlPropModel;
import g4p.tool.gui.propertygrid.Property;
import g4p.tool.gui.propertygrid.Validator;

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
public abstract class DBase extends DefaultMutableTreeNode implements GTconstants {

	transient public CtrlPropModel propertyModel;

	// Whether it is selectable in the WindowView
	// set to false for DOptionGroup and DTimer
	protected boolean selectable = true;
	
	// Important attributes
	public String 		_0005_name = "APPLICATION";
	public Boolean 		name_edit = false;
	public Boolean 		name_show = true;
	public Validator 	name_validator = Validator.getValidator(COMPONENT_NAME);
	
	public int 			_0020_x = 0;
	public Boolean 		x_edit = false;
	public Boolean 		x_show = false;
	public Validator 	x_validator = Validator.getValidator(int.class, -9999, 9999);
	
	public int 			_0021_y = 0;
	public Boolean 		y_edit = false;
	public Boolean 		y_show = false;
	public Validator 	y_validator = x_validator;

	public int 			_0024_width = 0;
	public Boolean 		width_edit = false;
	public Boolean 		width_show = false;
	public Validator 	width_validator = Validator.getValidator(int.class, 10, 999);

	public int 			_0025_height = 0;
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

	public int get_x() {	return _0020_x;	}

	public int get_y() { return _0021_y; }

	public int get_width() { return _0024_width; }

	public int get_height() { return _0025_height; }

	public String get_text() { return ""; }
	
	public String get_title() { return ""; }
	
	public boolean isSelectable(){
		return selectable;
	}
	
	public void makeTableModel(){
		propertyModel = new CtrlPropModel(this);
	}
	
	public CtrlPropModel getTableModel(){
		if(propertyModel == null)
			makeTableModel();
		return propertyModel;
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
	
	// Stuff for drawing
	transient protected BasicStroke bs = new BasicStroke(1.1f,
			BasicStroke.CAP_ROUND,	BasicStroke.JOIN_ROUND);
	transient protected Color stroke;
	transient protected Color fill;
	
	public void draw(Graphics2D g2, AffineTransform af) {
	}

	/**
	 * Call this method when a change is made to the object in the window view.
	 */
	public void update(){
	}

	public boolean isOver(int x, int y){
		return (x >= _0020_x && x <= _0020_x + _0024_width 
				&& y >= _0021_y && y <= _0021_y + _0025_height);
	}
	
	public int getSize(){
		return _0024_width * _0025_height;
	}
}
