package g4p.tool.gui;

import g4p.tool.gui.propertygrid.IPropView;
import g4p.tool.components.*;
public class GuiControl {

	private ITabView tabs;
	private ISketchView tree;
	private IPropView props;

	/**
	 * @param tabs
	 * @param tree
	 * @param props
	 */
	public GuiControl(ITabView tabs, ISketchView tree, IPropView props) {
		super();
		this.tabs = tabs;
		this.tree = tree;
		this.props = props;
	}

	public boolean addComponent(DBase comp){
		System.out.println("Adding "+ comp);

		return true;
	}

	public boolean removeComponent(){
		System.out.println("Trashcan selected ");

		return true;
	}

	public void makeWindowSizeToFit(){
		tabs.scaleWindowToFit();
	}

	public void showGrid(boolean show){
		tabs.showGrid(show);
	}

	public void snapGrid(boolean snap){
		tabs.snapToGrid(snap);
	}

	public void setGridSize(int gs){
		tabs.gridSize(gs);
	}
}
