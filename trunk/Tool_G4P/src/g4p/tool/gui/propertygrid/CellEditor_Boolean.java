package g4p.tool.gui.propertygrid;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CellEditor_Boolean extends CellEditor_Base {

	private static CellEditor_Boolean instance = null;

	public static CellEditor_Boolean instance(){
		if(instance == null)
			instance = new CellEditor_Boolean();
		return instance;
	}

	protected JCheckBox component;

	public CellEditor_Boolean(){
		component = new JCheckBox();
		component.setHorizontalAlignment(JCheckBox.CENTER);
		component.setRequestFocusEnabled(false);	
		component.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
			
		});
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		component.setSelected(Boolean.valueOf(value.toString()));
		System.out.println("JChexkBox getTableCellEditorComponent()   " + value.toString());
		// Prevent cell background flicker
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

	/**
	 * This is called when the editor component looses focus and retrieves the final
	 * value for the table model
	 */
	public Object getCellEditorValue() {
		return Boolean.valueOf(component.isSelected());
	}
}
