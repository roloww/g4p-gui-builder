package g4p.tool.gui.propertygrid;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class Renderer_Boolean extends JCheckBox implements TableCellRenderer {

	private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	private Boolean userValue = false;

	public Renderer_Boolean() {
		super();
		setHorizontalAlignment(JLabel.CENTER);
		setBorderPainted(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		}
		else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
		try {
			userValue = Boolean.parseBoolean(value.toString());
			setSelected((Boolean)userValue);
		} 
		catch (Exception excp){
		}

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} 
		else {
			setBorder(noFocusBorder);
		}
		return this;
	}

}
