package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;
import g4p.tool.gui.ISketchView;
import g4p.tool.gui.ITabView;

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
				String fieldName = ((CtrlPropModel) getModel()).getPropertyAt(row).fieldName;
				// See if we have to update the tab names
				if(fieldName.equalsIgnoreCase(DBase.COMP_NAME_PROPERTY))
					tabs.updateTabName();
				else 
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
		//		CellEditor_Base editor = null;
		CellEditor_Base editor = null;
		// Retrieve the property
		Property p = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row));
		Class<?> c = p.field.getType(); // p.fieldType;
		// Get special editor if none then use one based on data type
		editor = p.editor;
		if(editor == null){
			if (c == boolean.class || c == Boolean.class) {
				editor = new CellEditor_Boolean();
			}
			if (c == int.class || c == Integer.class || c == String.class) {
				editor = new CellEditor_JTextfield();
			}
			if (c == float.class || c == Float.class) {
				editor = new CellEditor_JTextfield();
			}
			p.editor = editor;
		}
		// If we have don't have a validator then get one based on the class
		if(p.validator == null) 
			p.validator = Validator.getDefaultValidator(c);
		if(editor != null)
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
			if (c == boolean.class || c == Boolean.class) {
				return (TableCellRenderer) new Renderer_Boolean();
			}
		}
		return super.getCellRenderer(row, col);
	}

	@Override
	public void modelHasBeenChanged() {
		((CtrlPropModel)getModel()).modelChangedInGUI();
	}

	public String getToolTipText(MouseEvent e) {
		String tip = null;
		int row = rowAtPoint(e.getPoint());

		if(row != -1){
			tip = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row)).tooltip;
		}
		return tip;
	}



}
