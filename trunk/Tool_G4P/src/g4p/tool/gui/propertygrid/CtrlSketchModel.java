package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;


public class CtrlSketchModel extends DefaultTreeModel {

	/**
	 * Each model has its own selected node which is used in
	 * copy and paste operations
	 */
//	public DBase selected = null;

	/**
	 * Ctor creates a DataModel object with the given root
	 * 
	 * @param root the node that is the top of a tree
	 */
	public CtrlSketchModel(TreeNode root) {
		super(root);
	}

	/**
	 * Ctor for an empty DataModel
	 *
	 */
	public CtrlSketchModel() {
		super(null);
	}

	/**
	 * Determines whether a given node is the root node of the 
	 * data model
	 * 
	 * @param node
	 * @return true if node is the root else false
	 */
	public boolean isRoot(DefaultMutableTreeNode node){
		return (node == root);
	}

}
