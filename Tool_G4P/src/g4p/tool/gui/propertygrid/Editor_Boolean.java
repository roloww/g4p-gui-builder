package g4p.tool.gui.propertygrid;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;

public class Editor_Boolean extends Editor_Base {

	private static Editor_Boolean instance = null;
	
	public static Editor_Boolean instance(){
		if(instance == null)
			instance = new Editor_Boolean();
		return instance;
	}

	private Editor_Boolean(){
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
