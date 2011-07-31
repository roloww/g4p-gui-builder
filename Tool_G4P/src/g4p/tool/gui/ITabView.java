package g4p.tool.gui;

import g4p.tool.components.DBase;

public interface ITabView {

	/**
	 * Add a newly created window component as a tab.
	 * 
	 * @param window
	 */
	public abstract void addWindow(DBase window);

	/**
	 * Remove a tab for a window that is no longer needed
	 * @param window
	 * @return
	 */
	public abstract boolean deleteWindow(DBase window);

	/**
	 * Select selected tab and update name if required
	 * @param window
	 */
//	public abstract void setSelectedTab(DBase window);
	
	/** 
	 * Change the selected tab to the window with this control
	 * @param comp
	 */
	public abstract void setSelectedComponent(DBase comp);

	
	public void updateTabName();

	public void repaint();
	
	/**
	 * This method should be called by the WindowView when the selected
	 * component has changed from form display. 
	 * 
	 * @param comp the new component selected
	 */
	public abstract void selectedComponentHasChanged(DBase comp);
}