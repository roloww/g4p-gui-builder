package g4p.tool.gui.propertygrid;

import javax.swing.AbstractCellEditor;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public abstract class CellEditor_Base extends AbstractCellEditor implements TableCellEditor, UIResource {

	
	// Validation object 
	public Validator validator;

	
}
