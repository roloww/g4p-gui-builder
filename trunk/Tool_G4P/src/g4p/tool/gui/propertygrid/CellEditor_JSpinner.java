package g4p.tool.gui.propertygrid;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.table.TableCellRenderer;

public class CellEditor_JSpinner extends CellEditor_Base {

	private static CellEditor_JSpinner instance = null;

	public static CellEditor_JSpinner instance(){
		if(instance == null)
			instance = new CellEditor_JSpinner();
		return instance;
	}

	protected JSpinner component;

	private CellEditor_JSpinner(){
		component = new JSpinner();
		//		component.setEditable(false);

	}

	public void SpinnerEditor(String[] items) {
		component.setModel((SpinnerModel) new SpinnerListModel(Arrays.asList(items)));
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column) {
//		component.setModel((SpinnerModel) validator.getModel());
		component.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
		component.getEditor().setAlignmentX(JComponent.LEFT_ALIGNMENT);
		System.out.println(value);
		component.setValue(value.toString());

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

//	public boolean isCellEditable(EventObject evt) {
//		if (evt instanceof MouseEvent) {
//			return ((MouseEvent) evt).getClickCount() >= 2;
//		}
//		return true;
//	}

	public Object getCellEditorValue() {
		return component.getValue();
	}

}
