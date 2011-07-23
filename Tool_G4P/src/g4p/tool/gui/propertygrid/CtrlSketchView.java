package g4p.tool.gui.propertygrid;

import g4p.tool.TestDemo;
import g4p.tool.components.DBase;
import g4p.tool.gui.GuiDesigner;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class CtrlSketchView extends JTree {

    /**
     * Colors to be used for selection highlighting.
     */
    protected static Color selBColor = new Color(50, 50, 144);
    protected static Color childBColor = new Color(144, 144, 220);
    protected static Color selFColor = new Color(220, 220, 255);
    protected static Color childFColor = new Color(10, 10, 114);
    /**
     * Thi is used to hold copied or cut portions of the tree
     */
    protected DBase clipselected = null;

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
        setCellRenderer(new DataCellRenderer());

        // Add tree listener
        addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent tse) {
                System.out.println("Tree Selection Listener  " + tse.getPath());
                ((CtrlSketchModel) treeModel).selected = (DBase) tse.getPath().getLastPathComponent();
                System.out.println("     Selected     " + ((CtrlSketchModel) treeModel).selected);
                System.out.println("     Model        " + (((CtrlSketchModel) treeModel).selected).getTableModel());
                GuiDesigner.tblPropView.setModel(((DBase) ((CtrlSketchModel) treeModel).selected).getTableModel());
                //repaint();
            }
        });
        setEditable(false);
    }

    public void updateModelSelectionNode() {
        ((CtrlSketchModel) treeModel).selected =
                (DBase) getSelectionPath().getLastPathComponent();
    }

    public void expandSubTree(TreePath path) {
        TreePath newPath = null;
        DBase tn = (DBase) path.getLastPathComponent();
        int cc = tn.getChildCount();

        expandPath(path);
        if (cc != 0) {
            for (int i = 0; i < cc; i++) {
                newPath = path.pathByAddingChild(tn.getChildAt(i));
                expandSubTree(newPath);
            }
        }
        return;
    }

    /**
     * @return Returns the clipselected.
     */
    public DBase getClipselected() {
        return clipselected;
    }

    public void expandAll() {
        CtrlSketchModel dm = (CtrlSketchModel) treeModel;
        DBase rt = (DBase) dm.getRoot();

        TreePath tp = new TreePath(dm.getPathToRoot(rt));

        expandSubTree(tp);

    }

    /**
     * Deletes currently selected node and all children provided
     * the root node is not selected
     *
     */
    public void deleteNodes() {
        CtrlSketchModel model = (CtrlSketchModel) treeModel;
        TreePath selPath = getSelectionPath();
        DBase s = (DBase) selPath.getLastPathComponent();

        if (s != null && model.isRoot(s) != true) {
            // Remove node and all children from model
            model.removeNodeFromParent(s);
            // Change selection path to parent
            selPath = selPath.getParentPath();
            s = (DBase) selPath.getLastPathComponent();
            setSelectionPath(selPath);
            // Update DataModel node selection
            ((CtrlSketchModel) treeModel).selected = s;
        }
    }

    /**
     * Inserts nodes from the clipboard as children to the node
     * currently selected node
     *
     */
    public void pasteNodes() {
        CtrlSketchModel model = (CtrlSketchModel) treeModel;
        TreePath selPath = getSelectionPath();
        DBase s = (DBase) selPath.getLastPathComponent();

        if (s != null && clipselected != null) {
            // Insert nodes as first chaild
            model.insertNodeInto(clipselected, s, 0);
            // In make selected node = root of inserted nodes
            s = clipselected;
            clipselected = null;
            // Update DataModel node selection
            ((CtrlSketchModel) treeModel).selected = s;
            // Change selection to pasted node and expand sub tree
            TreePath tp = getSelectionPath();
            tp = tp.pathByAddingChild(s);
            setSelectionPath(tp);
            expandSubTree(tp);
        }
    }

//	/**
//	 * If node currently selected is not the root node, then  copies the
//	 * selected node and all its children ready for pasting
//	 *
//	 */
//	public void copyNodes(){
//		AppDataModel model = (AppDataModel) treeModel;
//		TreePath selPath = getSelectionPath();
//		DCore s = (DCore) selPath.getLastPathComponent();
//
//		if(s != null && model.isRoot(s) != true ){
//			clipselected = DCore.copyTree(model.selected);
//		}
//	}
    /**
     * Save the data model to disk
     *
     * @param file
     */
    public void saveModel(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject((CtrlSketchModel) treeModel);
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
    public void loadModel(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            CtrlSketchModel dm = (CtrlSketchModel) ois.readObject();
            fis.close();
            if (dm != null) {
                setModel(dm);
                // Restore node selection
                try {
                    TreePath tp = new TreePath(dm.getPathToRoot(((CtrlSketchModel) treeModel).selected));
                    setSelectionPath(tp);
                    expandPath(tp);
                } catch (Exception exc) {
                }
            }
            ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class to render the tree nodes in the display
     *
     * @author Peter Lager
     *
     */
    public class DataCellRenderer extends JLabel implements TreeCellRenderer {

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {

            String nodeName = null;

            DBase snode = (DBase) ((CtrlSketchModel) tree.getModel()).selected;
            DBase node = (DBase) value;

            setText(node.toString());
            setOpaque(true);
            if (snode == node) {
                setForeground(selFColor);
                setBackground(selBColor);
            } else if (snode != null && snode.isNodeDescendant(node)) {
                setForeground(childFColor);
                setBackground(childBColor);
            } else {
                setForeground(Color.BLACK);
                setOpaque(false);
            }
            return this;
        }
    }
}
