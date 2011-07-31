package g4p.tool.gui.propertygrid;

import g4p.tool.components.ListGen;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CellRender_JComboBox extends JComboBox implements TableCellRenderer {


	public CellRender_JComboBox(int type) {
		super();
		DefaultComboBoxModel model = ListGen.instance().getComboBoxModel(type);
		super.setModel(model);
		System.out.println("Making combo renderer");
	}


	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}

		// Select the current value
		setSelectedItem(value);
		return this;
	}


	//	public class MyComboBoxEditor extends DefaultCellEditor {
	//	    public MyComboBoxEditor(String[] items) {
	//	        super(new JComboBox(items));
	//	    }
	//	}


}
