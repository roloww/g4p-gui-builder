package g4p.tool.gui;

import g4p.tool.components.DBase;
import g4p.tool.components.DPanel;
import g4p.tool.gui.propertygrid.IPropView;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

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
    	this.setSelectionPath(tp);
    }

    /* (non-Javadoc)
	 * @see g4p.tool.gui.ISketchView#getWindowFor(javax.swing.tree.DefaultMutableTreeNode)
	 */
    @Override
	public DBase getWindowFor(DBase comp){
    	DefaultTreeModel m = (DefaultTreeModel) getModel();
    	TreeNode[] nodes = m.getPathToRoot(comp);
//    	for(int i = 0; i < nodes.length; i++)
//    		System.out.print("   " + nodes[i]);
//    	System.out.println();
    	DBase w =  (DBase) ((nodes.length >= 2) ? nodes[1] : null);
    	return w;
    }
    
    public DBase getContainerFor(DBase comp){
    	DefaultTreeModel m = (DefaultTreeModel) getModel();
    	TreeNode[] nodes = m.getPathToRoot(comp);
    	DBase c = null;
    	for(int i = nodes.length - 1; i > 0; i--){
    		if(nodes[i].getAllowsChildren()){
    			c = (DBase)nodes[i];
    			break;
    		}
    	}
    	return c;
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
