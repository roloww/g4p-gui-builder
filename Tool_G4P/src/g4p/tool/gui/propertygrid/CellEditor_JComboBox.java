package g4p.tool.gui.propertygrid;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
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

	private CellEditor_JComboBox(){
		component = new JComboBox();
		component.setEditable(false);
//		component.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JComboBox cb = (JComboBox)e.getSource();
////		        String petName = (String)cb.getSelectedItem();
//				fireEditingStopped();				
//			}
//			
//		});
	}
	
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		component.setSelectedItem(value.toString());
		component.setModel((ComboBoxModel) validator.getModel());
		
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return (String)component.getSelectedItem();
	}

}
