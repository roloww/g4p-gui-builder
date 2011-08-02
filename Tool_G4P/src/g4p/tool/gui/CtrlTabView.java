package g4p.tool.gui;

import g4p.tool.components.DBase;
import g4p.tool.components.DWindow;
import g4p.tool.gui.propertygrid.IPropView;

import java.util.HashMap;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CtrlTabView extends JTabbedPane implements ITabView, ChangeListener {

	private ISketchView tree;
	private IPropView props;


	HashMap<DBase, WindowView> tabMap; 

	public CtrlTabView(){
		tabMap =  new HashMap<DBase, WindowView>();
		this.addChangeListener(this);
	}

	public void setViewLinks(ISketchView tree, IPropView props){
		this.tree = tree;
		this.props = props;
	}

	/* (non-Javadoc)
	 * @see g4p.tool.gui.IWindowView#addWindow(g4p.tool.components.DBase)
	 */
	@Override
	public void addWindow(DBase winComp){
		WindowView winView = new WindowView(this, winComp);
		tabMap.put(winComp, winView);
		addTab(winComp.get_name(), ClassIcon.instance().getIcon(DWindow.class), winView);
	}

	/* (non-Javadoc)
	 * @see g4p.tool.gui.IWindowView#deleteWindow(g4p.tool.components.DBase)
	 */
	@Override
	public boolean deleteWindow(DBase window){
		WindowView winView = tabMap.get(window);
		if(winView != null){
			remove(winView); // remove tab
			tabMap.remove(window);
			return true;
		}
		return false;		
	}

	private void setSelectedTab(DBase window){
		WindowView winView = tabMap.get(window);
		if(winView != null)
			setSelectedComponent(winView);
	}

	/**
	 * Set selected component.
	 * If necessary change tab and then update component
	 * @param comp
	 */
	public void setSelectedComponent(DBase comp){
		WindowView winView = null;
		if(comp instanceof DWindow){
			winView = tabMap.get(comp); 
			setSelectedTab(comp);
		}
		else {
			DBase window = tree.getWindowFor(comp);
			if(window != null && window instanceof DWindow){
				setSelectedTab(window);
				// It it was something other than a window update the component
				// it is selecting
				if(window != comp){
					winView = tabMap.get(window);
					if(winView != null)
						winView.UpdateComponent(comp);
					tree.setSelectedComponent(comp);
				}
			}
		}
		winView.setSelected(comp);
		repaint();
	}
	
	/**
	 * Call this if the name of the window has changed
	 */
	public void updateTabName(){
		for(int i = 0; i < this.getTabCount(); i++){
			String winname = ( (WindowView) getComponentAt(i)).getWindowComponent().get_name();
			setTitleAt(i, winname);
		}
	}
	
	/**
	 * Change of state caused by clicking on a tab
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		CtrlTabView sourceTabbedPane = (CtrlTabView) changeEvent.getSource();
	    WindowView winView = (WindowView) sourceTabbedPane.getSelectedComponent();
	    DBase comp = winView.getWindowComponent();
		props.showProprtiesFor(comp);
		tree.setSelectedComponent(comp);
	}

	/**
	 * Called from WindowView when a component is selected using the mouse
	 * From ITabView
	 */
	@Override
	public void selectedComponentHasChanged(DBase comp) {
		tree.setSelectedComponent(comp);
		
	}

}
