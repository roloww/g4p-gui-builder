package g4p.tool.gui;

import g4p.tool.components.DBase;

import java.awt.Component;

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

public class CtrlSketchView extends JTree {

    /**
     * Ctor creates an empty tree;
     *
     */
    public CtrlSketchView() {
        super();
        initialise();
    }

    /**
     * Ctor creates a tree with the given data model
     * @param model
     */
    public CtrlSketchView(CtrlSketchModel model) {
        super(model);
        initialise();
    }

    /**
     * Set the charateristics for the tree
     *
     */
    private void initialise() {
        // Only allow single nodes to be selected
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setCellRenderer(new DataCellRenderer(null));

        // Add tree listener
        addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent tse) {
            	// Update the property view
         //       GuiDesigner.tblPropView.setModel( ((DBase) tse.getPath().getLastPathComponent()).getTableModel() );
                GuiDesigner.tblPropView.setModel( ((DBase) getLastSelectedPathComponent()).getTableModel() );
                System.out.println( ((DBase) getLastSelectedPathComponent()).show() );
            }
        });
        setEditable(false);
    }

//    public void setSelectedNode(DefaultMutableTreeNode node){
//    	DefaultTreeModel m = (DefaultTreeModel) getModel();
//    	TreeNode[] nodes = m.getPathToRoot(node);
//    	TreePath tp = new TreePath(nodes);
//    	this.setSelectionPath(tp);
//    }

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
