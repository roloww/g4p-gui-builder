package g4p.tool.gui;

import g4p.tool.Messages;
import g4p.tool.components.DApplication;
import g4p.tool.components.DBase;
import g4p.tool.components.DOption;
import g4p.tool.components.DOptionGroup;
import g4p.tool.components.DTimer;
import g4p.tool.components.DWindow;
import g4p.tool.gui.propertygrid.IPropView;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class CtrlSketchView extends JTree implements ISketchView {


	private ITabView tabs;
	private IPropView props;

	/**
	 * Ctor creates an empty tree;
	 *
	 */
	public CtrlSketchView() {
		super();
		initialise();
		this.setRowHeight(24);
	}

	public void setViewLinks(ITabView tabs, IPropView props){
		this.tabs = tabs;
		this.props = props;
	}

	/**
	 * Set the characteristics for the tree
	 *
	 */
	private void initialise() {
		// Only allow single nodes to be selected
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setCellRenderer(new DataCellRenderer(null));

		// Add tree listener
		addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent tse) {
				DBase sel = (DBase) getLastSelectedPathComponent();
				tabs.setSelectedComponent(sel);
				props.showProprtiesFor(sel);
			}
		});
		setEditable(false);
	}

	// Methods for Interface   ====================================================================================================================

	/* (non-Javadoc)
	 * @see g4p.tool.gui.ISketchView#setSelectedNode(javax.swing.tree.DefaultMutableTreeNode)
	 */
	@Override
	public void setSelectedComponent(DBase comp){
		//		System.out.println("CtrlSketchView  setSelectedComponent");
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		TreeNode[] nodes = m.getPathToRoot(comp);
		TreePath tp = new TreePath(nodes);
		setSelectionPath(tp);
		repaint();
	}

	/* (non-Javadoc)
	 * @see g4p.tool.gui.ISketchView#getWindowFor(javax.swing.tree.DefaultMutableTreeNode)
	 */
	@Override
	public DBase getWindowFor(DBase comp){
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		TreeNode[] nodes = m.getPathToRoot(comp);
//		for(int i = 0; i < nodes.length; i++)
//			System.out.print("   " + nodes[i]);
//		System.out.println("\n");
		DBase w =  (DBase) ((nodes != null && nodes.length >= 2) ? nodes[1] : null);
		Messages.println("{0} is window for {1}", w, comp);
		return w;
	}

	public DBase getContainerFor(DBase comp){
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DBase c = null;
		TreeNode[] nodes = m.getPathToRoot(comp);
		if(nodes != null){
			for(int i = nodes.length - 1; i > 0; i--){
				if(nodes[i].getAllowsChildren()){
					c = (DBase)nodes[i];
					break;
				}
			}
		}
		return c;
	}

	@Override
	public void addComponent(DBase comp) {
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DefaultMutableTreeNode r = (DefaultMutableTreeNode) m.getRoot();
		if(comp instanceof DWindow){
			m.insertNodeInto(comp, r, r.getChildCount());
			tabs.addWindow(comp);
			setSelectedComponent(comp);
			//			repaint();
		}
		else if(comp instanceof DTimer){
			// add to active window
		}
		else if(comp instanceof DOptionGroup){
			// add to active window
		}
		else if(comp instanceof DOption){
			// add to option group
		}
		else {
			DBase selected = (DBase) getLastSelectedPathComponent();
			DBase window = getContainerFor(selected);
			if(window != null){
				comp.set_x( (window.get_width() - comp.get_width())/ 2);
				comp.set_y( (window.get_height() - comp.get_height())/ 2);
				m.insertNodeInto(comp, window, window.getChildCount());
				setSelectedComponent(comp);
			}	    	
		}
	}

	@Override
	public void removeComponent() {
		DBase comp = (DBase) getLastSelectedPathComponent();
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DefaultMutableTreeNode r = (DefaultMutableTreeNode) m.getRoot();
		if(comp instanceof DApplication){
			return;
		}
		if(comp instanceof DWindow){
			if(comp == r.getFirstChild()){
				System.out.println("Main sketch do not delete");
				return;
			}
			tabs.deleteWindow(comp);
			m.removeNodeFromParent(comp);
			setSelectedComponent((DBase) r);
			return;
		}
		DefaultMutableTreeNode p = (DefaultMutableTreeNode) comp.getParent();
		setSelectedComponent((DBase) p);
		m.removeNodeFromParent(comp);
	}

	@Override
	public DBase getRoot() {
		return (DBase) getModel().getRoot();
	}


	// ==================================================================================================================================================


	/**
	 * Class to render the tree nodes in the display
	 *
	 * @author Peter Lager
	 *
	 */
	class DataCellRenderer extends DefaultTreeCellRenderer {

		private Icon cellIcon;

		public DataCellRenderer(Icon icon) {
			cellIcon = icon;
		}

		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean sel,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(
					tree, value, sel,
					expanded, leaf, row,
					hasFocus);
			cellIcon = ClassIcon.instance().getIcon(value.getClass());
			setIcon(cellIcon);

			return this;
		}
	}


}