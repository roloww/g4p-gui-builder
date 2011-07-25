package g4p.tool.gui.propertygrid;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;

public abstract class CellEditor_Base extends AbstractCellEditor implements TableCellEditor {

	// Validation object 
	public Validator validator;

}
