package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

public class CtrlPropModel extends AbstractTableModel {

	private String[] columnNames = new String[]{"Field", "Value"};
	private Property[] propData;
	
	public CtrlPropModel(DBase cl){
		super();
		createProperties(cl);
	}
	
	@SuppressWarnings("unchecked")
	public void createProperties(DBase cl){
		ArrayList<Property> props = new ArrayList<Property>();
		Class<? extends Object> c = cl.getClass();
		Field[] fs = c.getFields();
		for(Field field : fs){
			if(field.getName().startsWith("_")){
				props.add(new Property(cl, field));
			}
		}
		Collections.sort(props);
		propData = props.toArray(new Property[props.size()]);
	}

	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return propData.length;
	}

	public Property getPropertyAt(int row){
		return propData[row];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex >= propData.length || columnIndex > 1)
			return new String("");

		if(columnIndex == 0)
			return propData[rowIndex].cellText;
		else if(propData[rowIndex].fvalue == null)
			return "";
		else 
			return propData[rowIndex].fvalue;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex == 1){
			propData[rowIndex].setValue(aValue);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public boolean isCellEditable(int row, int col)	{ 
		return (col > 0) ? propData[row].allowEdit : false; 
	}


}
