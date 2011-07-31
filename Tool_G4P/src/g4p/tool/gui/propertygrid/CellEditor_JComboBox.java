package g4p.tool.gui.propertygrid;

import g4p.tool.components.ListGen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CellEditor_JComboBox extends CellEditor_Base {

	private static CellEditor_JComboBox instance = null;

	public static CellEditor_JComboBox instance(int type){
		if(instance == null)
			instance = new CellEditor_JComboBox();
		DefaultComboBoxModel model = ListGen.instance().getComboBoxModel(type);
		instance.component.setModel(model);
		return instance;
	}

	protected JComboBox component;

	private CellEditor_JComboBox(){
		component = new JComboBox();
		//		component.setEditable(false);
		component.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				System.out.println("ACTION");
				//		        String petName = (String)cb.getSelectedItem();
				//				fireEditingStopped();				
			}

		});
	}


	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		component.setSelectedItem(value.toString());

		TableCellRenderer r = table.getCellRenderer(row, column);
		Component c = r.getTableCellRendererComponent(table, value, isSelected, isSelected, row, column);
		if( c!= null){
			component.setOpaque(true);
			component.setBackground(c.getBackground());
			if(c instanceof JComponent)
				component.setBorder(((JComponent)c).getBorder());
			else
				component.setOpaque(false);
		}

//		DefaultComboBoxModel dcbm = (DefaultComboBoxModel) validator.getModel();
//		System.out.println("Model size is " + dcbm.getSize());
//		component.setModel((DefaultComboBoxModel) validator.getModel());
//		component.setMaximumRowCount(5);
		
		
		return component;
	}

//	public boolean isCellEditable(EventObject evt) {
//		if (evt instanceof MouseEvent) {
//			return ((MouseEvent) evt).getClickCount() >= 2;
//		}
//		return true;
//	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return (String)component.getSelectedItem();
	}

}
