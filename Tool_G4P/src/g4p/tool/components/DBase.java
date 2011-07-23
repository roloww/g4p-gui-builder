package g4p.tool.components;

import g4p.tool.gui.propertygrid.CtrlPropModel;
import g4p.tool.gui.propertygrid.Property;

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

	public DBase(){
		allowsChildren = true;
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
