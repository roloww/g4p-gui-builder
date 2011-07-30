package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;
import g4p.tool.gui.ISketchView;
import g4p.tool.gui.ITabView;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

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

	/**
	 * Get a cell editor and a validator.
	 * 
	 * If no editor avaialble use default editor and renderer
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		CellEditor_Base editor = null;
		// Retrieve the property
		Property p = ((Property) ((CtrlPropModel) getModel()).getPropertyAt(row));
		Class<?> c = p.ftype;
		Validator v = p.validator;
		// Get special editor if none then use one based on data type
		editor = p.editor;
		if(editor == null){
			if (c == boolean.class || c == Boolean.class) {
				editor = CellEditor_Boolean.instance();
			}
			if (c == int.class || c == Integer.class || c == String.class) {
				editor = CellEditor_JTextfield.instance();
			}
		}
//		System.out.println("TableCellEditor getCellEditor()     for >>> = " + c);
		// If we have an editor get any validator specified
		if (editor != null) {
			editor.validator = (p.validator == null) ? Validator.getDefaultValidator(c) : p.validator;
			return editor;
		}
		return super.getCellEditor(row, col);
	}


	public TableCellRenderer getCellRenderer(int row, int col) {
		Property p = (Property) ((CtrlPropModel) getModel()).getPropertyAt(row);
		Class<?> c = p.ftype;
			
		if (col > 0) {
//			if(p.renderer != null){
//				System.out.println("Got you ");
//				return p.renderer;
//			}
			if (c == boolean.class || c == Boolean.class) {
				return (TableCellRenderer) new Renderer_Boolean();
			}
		}
		return super.getCellRenderer(row, col);
	}
}
