package g4p.tool.components;

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

	public String 	_0005_name = "APPLICATION";
	public int 		_0020_x = 0;
	public int 		_0021_y = 0;
	public int 		_0024_width = 100;
	public int 		_0025_height = 20;

	public DBase(){
		allowsChildren = false;
	}


	public void setName(String name){
		_0005_name = name;
	}
	
	public void setX(int x){
		_0020_x = x;
	}
	
	public void setY(int y){
		_0021_y = y;
	}
	
	public void setWidth(int width){
		_0024_width = width;
	}
	
	public void setHeight(int height){
		_0025_height = height;
	}
	
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

	
	public String toString(){
		return "SKETCH";
	}
	
}
