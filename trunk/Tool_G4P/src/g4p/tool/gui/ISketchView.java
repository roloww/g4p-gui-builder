package g4p.tool.gui;

import g4p.tool.components.DBase;

/** 
 * 
 * View of available methods in CtrlSketchView
 *  
 * 
 * @author Peter Lager
 *
 */
public interface ISketchView {

	/**
	 * Sets the selected node in this tree view.
	 * 
	 * @param comp
	 */
	public abstract void setSelectedComponent(DBase comp);

	/**
	 * For a given component find which window its is in
	 * @param comp
	 * @return the window or null if it is not inside a window
	 */
	public abstract DBase getWindowFor(DBase comp);

	/**
	 * This is used when a new component is being added. Starting with comp it 
	 * Searches for the first component that supports children (i.e. Application/Window/Panel/OptGroup)
	 * 
	 * @param comp
	 * @return
	 */
	public abstract DBase getContainerFor(DBase comp);
}