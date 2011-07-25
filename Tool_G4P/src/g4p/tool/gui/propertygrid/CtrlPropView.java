package g4p.tool.gui.propertygrid;

import g4p.tool.TestDemo;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CtrlPropView extends JTable implements TableModelListener {

	public CtrlPropView() {
		super();
	}

	public CtrlPropView(CtrlPropModel m) {
		super(m);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		getColumnModel().getColumn(0).setPreferredWidth(40);
//		getColumnModel().getColumn(1).setPreferredWidth(80);
	}

	public String getColumnName(int col) {
		return getModel().getColumnName(col);
	}

	public CtrlPropModel getModel() {
		return (CtrlPropModel) dataModel;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return getModel().getValueAt(rowIndex, columnIndex);
	}

	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		System.out.println("TestDemo \t" + TestDemo.b);
	}

	public TableCellEditor getCellEditor(int row, int col) {
		CellEditor_Base editor = null;
		// Retrieve the property
		Property p = ((Property) getModel().getPropertyAt(row));
		Class<?> c = p.fclass;
		// Get special editor if none then use one based on data type
		editor = p.editor;
		if(editor == null){
			if (c == int.class || c == Integer.class || c == String.class) {
				editor = CellEditor_String.instance();
			}
			if (c == boolean.class || c == Boolean.class) {
				editor = CellEditor_Boolean.instance();
			}
		}
		System.out.println("TableCellEditor getCellEditor()     for >>> = " + c);
		// If we have an editor get any validator specified
		if (editor != null) {
			editor.validator = p.validator;
			return editor;
		}
		return super.getCellEditor(row, col);
	}


	public TableCellRenderer getCellRenderer(int row, int col) {
		Class<?> c = ((Property) getModel().getPropertyAt(row)).fclass;
		if (col > 0) {
			if (c == boolean.class || c == Boolean.class) {
				return (TableCellRenderer) new Renderer_Boolean();
			}
		}
		return super.getCellRenderer(row, col);
	}
}
