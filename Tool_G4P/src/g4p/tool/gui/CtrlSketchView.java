package g4p.tool.gui;

import g4p.tool.components.DApplication;
import g4p.tool.components.DBase;
import g4p.tool.components.DOption;
import g4p.tool.components.DOptionGroup;
import g4p.tool.components.DPanel;
import g4p.tool.components.DTimer;
import g4p.tool.components.DWindow;
import g4p.tool.components.IdGen;
import g4p.tool.components.NameGen;
import g4p.tool.gui.propertygrid.IPropView;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
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
	public DBase getWindowFor(DBase comp){
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		TreeNode[] nodes = m.getPathToRoot(comp);
		//		for(int i = 0; i < nodes.length; i++)
		//			System.out.print("   " + nodes[i]);
		//		System.out.println("\n");
		DBase w =  (DBase) ((nodes != null && nodes.length >= 2) ? nodes[1] : null);
		return w;
	}

	public DBase getOptionGroupFor(DBase comp){
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DBase c = null;
		TreeNode[] nodes = m.getPathToRoot(comp);
		if(nodes != null){
			for(int i = nodes.length - 1; i > 0; i--){
				if(nodes[i] instanceof DOptionGroup){
					c = (DBase)nodes[i];
					break;
				}
			}
		}
		return c;
	}

	// Get the first
	public DBase getGuiContainerFor(DBase comp){
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DBase c = null;
		TreeNode[] nodes = m.getPathToRoot(comp);
		if(nodes != null){
			for(int i = nodes.length - 1; i > 0; i--){
				if(nodes[i].getAllowsChildren() && nodes[i] instanceof DWindow || nodes[i] instanceof DPanel){
					c = (DBase)nodes[i];
					break;
				}
			}
		}
		return c;
	}

	/**
	 * Certain rules must apply
	 * 1) All window components are added to the application node (root)
	 * 2) In general all controls are added to a window
	 * 3) Option buttons must be added to an option group component
	 */
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
			DBase window = (DBase) r.getChildAt(0);
			m.insertNodeInto(comp, window, window.getChildCount());
			setSelectedComponent(comp);			
		}
		else if(comp instanceof DOptionGroup){
			DBase selected = (DBase) getLastSelectedPathComponent();
			DBase window = getGuiContainerFor(selected);
			if(window != null){
				m.insertNodeInto(comp, window, window.getChildCount());
				setSelectedComponent(comp);
			}
			else {
				undoComponent(comp);
			}
		}
		else if(comp instanceof DOption){
			DBase selected = (DBase) getLastSelectedPathComponent();
			DBase window = getGuiContainerFor(selected);
			DBase opg = getOptionGroupFor(selected);
			if(window != null && opg != null){
				comp.set_x( (window.get_width() - comp.get_width())/ 2);
				comp.set_y( (window.get_height() - comp.get_height())/ 2);
				if(opg.getChildCount() == 0)
					((DOption)comp)._0050_selected = true;
				m.insertNodeInto(comp, opg, opg.getChildCount());
				setSelectedComponent(comp);
			}
			else {
				undoComponent(comp);
			}
		}
		else {
			DBase selected = (DBase) getLastSelectedPathComponent();
			DBase window = getGuiContainerFor(selected);
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
			// Remove window from tab view
			tabs.deleteWindow(comp);
//			m.removeNodeFromParent(comp);
//			NameGen.instance().remove(comp.get_name());
//			NameGen.instance().remove(comp.get_event_name());
//			IdGen.instance().remove(comp.get_id());
//			setSelectedComponent((DBase) r);
//			return;
		}
		// Component is valid for removal
		DefaultMutableTreeNode p = (DefaultMutableTreeNode) comp.getParent();
		m.removeNodeFromParent(comp);
		undoComponent(comp);
		setSelectedComponent((DBase) p);
	}

	/**
	 * Release component id, name and event name
	 * @param comp
	 */
	private void undoComponent(DBase comp){
		NameGen.instance().remove(comp.get_name());			
		NameGen.instance().remove(comp.get_event_name());
		IdGen.instance().remove(comp.get_id());
	}
	
	@Override
	public DBase getRoot() {
		return (DBase) getModel().getRoot();
	}


	@Override
	public void generateDeclarations(ArrayList<String> lines) {
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DBase r = (DBase) m.getRoot();
		r.make_declaration(lines);
	}

	@Override
	public void generateEvtMethods(ArrayList<String> lines) {
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DBase r = (DBase) m.getRoot();
		r.make_event_method(lines);
	}

	@Override
	public void generateCreator(ArrayList<String> lines) {
		DefaultTreeModel m = (DefaultTreeModel) getModel();
		DBase r = (DBase) m.getRoot();
		r.make_creator(lines, null);
	}

	
	@Override
	public void generateAddToWin(ArrayList<String> lines) {
		// TODO Auto-generated method stub
		
	}

	// ==========================================================================
	// ==========================================================================
	// ======================   Load / save routines   ==========================
	// ==========================================================================


	/**
	 * Save the data model to disk
	 * 
	 * @param file
	 */
	public void saveModel(File file){
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject( (CtrlSketchModel) treeModel);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the data model from file and if successful attach it to 
	 * this tree and if a node was selected when saved ractivate it
	 *  
	 * @param file
	 */
	public DefaultTreeModel loadModel(File file){
		DefaultTreeModel dm = null;
		System.out.println("Load model");
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			dm = (DefaultTreeModel) ois.readObject();
			fis.close();
			//			if(dm != null){
			//				setModel(dm);
			//				setSelectedComponent((DBase)dm.getRoot());
			//			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dm;
	}

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
