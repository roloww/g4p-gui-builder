package g4p.tool.components;

import java.awt.Color;
import java.awt.Graphics2D;

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
public abstract class DBase extends DefaultMutableTreeNode {

	// The container this control is in
	public DBase parent = null;

	transient public CtrlPropModel propertyModel;
	transient public Property[] propList;

	// Important attributes
	public String 		_0005_name = "APPLICATION";
	public Boolean 		name_edit = false;
	public Boolean 		name_show = true;
	public Validator 	name_validator = Validator.getValidator("COMPONENT_NAME");
	
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
	
	
	
	public void makeTableModel(){
		propertyModel = new CtrlPropModel(this);
	}
	
	public CtrlPropModel getTableModel(){
		if(propertyModel == null)
			makeTableModel();
		return propertyModel;
	}
	
	/** 
	 * Get the nearest component than can contain other components
	 * i.e. the nearest GPanel or GWindow
	 * @return
	 */
	public DBase getContainer(){
		if(allowsChildren)
			return this;
		else
			return (parent == null) ? null : parent.getContainer();
	}

	// ====================================================================================================
	
	protected Color stroke;
	protected Color fill;
	
	public void draw(Graphics2D g){}
	
	public String show(){
		return Messages.build("{0}  {1} Pos [{2},{3}] Size [{4}, {5}]", this.getClass(), _0005_name, _0020_x, _0021_y, _0024_width, _0025_height);
	}
	
	public String toString(){
		return _0005_name;
	}
	
}
