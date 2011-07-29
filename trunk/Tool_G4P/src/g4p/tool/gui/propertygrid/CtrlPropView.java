package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;
import g4p.tool.components.DWindow;
import g4p.tool.gui.ISketchView;
import g4p.tool.gui.ITabView;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class CtrlPropView extends JTable implements TableModelListener, IPropView {

	private ITabView tabs;
	private ISketchView tree;


	public CtrlPropView() {
		super();
	}

	public void setViewLinks(ITabView tabs, ISketchView tree){
		this.tabs = tabs;
		this.tree = tree;
	}

	/* (non-Javadoc)
	 * @see g4p.tool.gui.propertygrid.IPropView#showProprtiesFor(g4p.tool.components.DBase)
	 */
	@Override
	public void showProprtiesFor(DBase comp){
		setModel(comp.getTableModel());
	}


	public String getColumnName(int col) {
		return getModel().getColumnName(col);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return getModel().getValueAt(rowIndex, columnIndex);
	}

	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		if(e.getType() == TableModelEvent.UPDATE){
			int row = e.getFirstRow();
			if(row >= 0){
				String rowLabelText = (String) getModel().getValueAt(row, 0);
				if(rowLabelText.equalsIgnoreCase("name")){
					tabs.updateTabName();
				}
				else {
					tabs.repaint();
				}
			}
		}
	}

	public TableCellEditor getCellEditor(int row, int col) {
		CellEditor_Base editor = null;
		// Retrieve the property
		Property p = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row));
		Class<?> c = p.fclass;
		// Get special editor if none then use one based on data type
		editor = p.editor;
		if(editor == null){
			if (c == int.class || c == Integer.class || c == String.class) {
				editor = CellEditor_JTextfield.instance();
			}
			if (c == boolean.class || c == Boolean.class) {
				editor = CellEditor_Boolean.instance();
			}
		}
//		System.out.println("TableCellEditor getCellEditor()     for >>> = " + c);
		// If we have an editor get any validator specified
		if (editor != null) {
			editor.validator = p.validator;
			return editor;
		}
		return super.getCellEditor(row, col);
	}


	public TableCellRenderer getCellRenderer(int row, int col) {
		Class<?> c = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row)).fclass;
		if (col > 0) {
			if (c == boolean.class || c == Boolean.class) {
				return (TableCellRenderer) new Renderer_Boolean();
			}
		}
		return super.getCellRenderer(row, col);
	}
}
