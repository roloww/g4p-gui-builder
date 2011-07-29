package g4p.tool.gui.propertygrid;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;

public class CellEditor_JComboBox extends CellEditor_Base {
	
	private static CellEditor_JComboBox instance = null;
	
	public static CellEditor_JComboBox instance(){
		if(instance == null)
			instance = new CellEditor_JComboBox();
		return instance;
	}

	protected JComboBox component;

	
	public CellEditor_JComboBox(){
		makeEditorComponent();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void makeEditorComponent() {
		component = new JComboBox();
		
	}

}
