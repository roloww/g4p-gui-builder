package g4p.tool.gui.propertygrid;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CellEditor_JCheckbox extends CellEditor_Base {

	private static CellEditor_JCheckbox instance = null;
	
	public static CellEditor_JCheckbox instance(){
		if(instance == null)
			instance = new CellEditor_JCheckbox();
		return instance;
	}

	private CellEditor_JCheckbox(){
		makeEditorComponent();
	}

	protected JCheckBox component;
	
	private void makeEditorComponent(){
		component = new JCheckBox();
		component.setHorizontalAlignment(JCheckBox.CENTER);
//		component.addItemListener(new ItemListener(){
//
//			public void itemStateChanged(ItemEvent e) {
////				validValue = component.isSelected();	
//			}
//			
//		});
	
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if(validator != null)
			validator.setOriginalValue(value);
		return component;
	}

	/**
	 * This is called when the editor component looses focus and retrieves the final
	 * value for the table model
	 */
	public Object getCellEditorValue() {
		return (Boolean)component.isSelected();
	}

}
