package g4p.tool.gui.propertygrid;

import g4p.tool.Messages;
import g4p.tool.components.DBase;
import g4p.tool.gui.tabview.ITabView;
import g4p.tool.gui.treeview.ISketchView;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings({ "serial", "unused" })
public class CtrlPropView extends JTable implements TableModelListener, IPropView {

	private ITabView tabs;
	private ISketchView tree;


	public CtrlPropView() {
		super();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(22);
		setTableHeader(null); // hides column names
	}

	public void setViewLinks(ITabView tabs, ISketchView tree){
		this.tabs = tabs;
		this.tree = tree;
	}


	/**
	 * This method should be called when the selected object in 
	 * either tab or tree view changes
	 */
	public void showProprtiesFor(DBase comp){
		if(comp != null)
			setModel(comp.getTableModel());
	}


	public String getColumnName(int col) {
		return getModel().getColumnName(col);
	}

	/**
	 * Retrieve a value from a given position in the table
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getModel().getValueAt(rowIndex, columnIndex);
	}

	/**
	 * This method is triggered when a value in the table is
	 * changed using the setValueAt method in the CtrlPropModel
	 * class.
	 */
	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		if(e.getType() == TableModelEvent.UPDATE){
			int row = e.getFirstRow();
			if(row >= 0){
				// Just in case the window name was changed
				tabs.updateTabName();
				tabs.repaint();
			}
		}
	}

	/**
	 * Get a cell editor and a validator.
	 * 
	 * If no editor avaialble use default editor and renderer
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		EditorBase editor = null;
		// Retrieve the property
		Property p = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row));
		Class<?> c = p.field.getType(); // p.fieldType;
		// Get special editor if none then use one based on data type
		editor = p.editor;
		if(editor == null){
			if (c == boolean.class || c == Boolean.class) {
				editor = new EditorBoolean();
			}
			if (c == int.class || c == Integer.class || c == String.class) {
				editor = new EditorJTextfield();
			}
			if (c == float.class || c == Float.class) {
				editor = new EditorJTextfield();
			}
			p.editor = editor;
		}
		// If we have don't have a validator then get one based on the class
		if(p.validator == null) 
			p.validator = Validator.getDefaultValidator(c);
		if(editor != null && editor.validator == null)
			editor.validator = p.validator;
		return (editor == null) ? super.getCellEditor(row, col) : editor;
	}

	public TableCellRenderer getCellRenderer(int row, int col) {
		Property p = (Property) ((CtrlPropModel) getModel()).getPropertyAt(row);
		Class<?> c = p.field.getType();

		if (col > 0) {
			if(p.renderer != null){
				return p.renderer;
			}
			else if (c == boolean.class || c == Boolean.class) {
				p.renderer =  (TableCellRenderer) new Renderer_Boolean();
				return p.renderer;
			}
		}
		return super.getCellRenderer(row, col);
	}

	@Override
	public void modelHasBeenChanged() {
		((CtrlPropModel)getModel()).modelChangedInGUI();
	}

	public void updateProperty(Object value, int row, int col){
		getModel().setValueAt(value, row, col);
	}
	
	public String getToolTipText(MouseEvent e) {
		String tip = null;
		int row = rowAtPoint(e.getPoint());
		if(row != -1){
			tip = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row)).tooltip;
//			Messages.println("Row {0}  ::  {1}", row, tip);
		}
		return tip;
	}

}
