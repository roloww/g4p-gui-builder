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
	 * Change the selected tab to the window with this control
	 * @param comp
	 */
	public abstract void setSelectedComponent(DBase comp);

	/**
	 * Called when window name is changed in property view
	 */
	public void updateTabName();

	public void repaint();
	
	/**
	 * This method should be called by the WindowView when a 
	 * different component has been selected. 
	 * 
	 * @param comp the new component selected
	 */
	public abstract void componentHasBeenSelected(DBase comp);
	
	/**
	 * The size or position of the component has changed
	 * @param comp
	 */
	public abstract void componentPropertyChange(DBase comp);
	
	
	public abstract void scaleWindowToFit();
	
	public abstract void showGrid(boolean show);
	public abstract void snapToGrid(boolean snap);
	public abstract void gridSize(int gsize);
	
}