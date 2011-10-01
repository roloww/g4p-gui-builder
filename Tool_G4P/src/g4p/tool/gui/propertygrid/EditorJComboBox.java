package g4p.tool.gui.propertygrid;

import g4p.tool.components.ListGen;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class EditorJComboBox extends EditorBase {

	protected static JComboBox component = null;

	public EditorJComboBox(int type){
		validator = Validator.getValidator(type);
		if(component == null){
			component = new JComboBox(ListGen.instance().getComboBoxModel(type));
			component.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();				
				}

			});
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
//		component.setModel((ComboBoxModel) validator.getModel());
		validator.preEditAction(this);
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
		return component;
	}


	@Override
	public Object getCellEditorValue() {
		return component.getSelectedItem().toString();
	}

}
