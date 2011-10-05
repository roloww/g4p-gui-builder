package g4p.tool.gui.propertygrid;

import g4p.tool.components.ListGen;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class EditorJComboBox extends EditorBase {

	protected static JComboBox component = null;

	protected JTable table = null;;
	protected int row, column;
	
	
	public EditorJComboBox(int type){
		validator = Validator.getValidator(type);
		if(component == null){
			component = new JComboBox(ListGen.instance().getComboBoxModel(type));
//			component.addActionListener(new ActionListener(){
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					if(table != null){
//						((CtrlPropView)table).updateProperty(getCellEditorValue(), row, column);
//						System.out.println("Combo - item selected " + getCellEditorValue());
//					}
//					fireEditingStopped();				
//				}
//
//			});
		}
	}


	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
//		component.setModel((ComboBoxModel) validator.getModel());
		// Set the list of selections
		validator.preEditAction(this);
		// Set the selected
		component.setSelectedItem(value.toString());
		this.table = table;
		this.row = row;
		this.column = column;
		
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
		return component;
	}


	@Override
	public Object getCellEditorValue() {
		return component.getSelectedItem().toString();
	}

	public void setSelected(Object value) {
		component.setSelectedItem(value.toString());
	}

}
