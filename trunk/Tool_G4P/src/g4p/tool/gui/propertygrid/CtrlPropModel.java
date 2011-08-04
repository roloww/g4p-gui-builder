package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class CtrlPropModel extends AbstractTableModel implements TableModel {

	private String[] columnNames = new String[]{"Field", "Value"};
	private Property[] propData;
	private DBase owner;
	
	public CtrlPropModel(DBase component){
		super();
		owner = component;
		createProperties(component);
	}

	@SuppressWarnings("unchecked")
	public void createProperties(DBase cl){
		ArrayList<Property> props = new ArrayList<Property>();
		Property p;
		Class<? extends Object> c = cl.getClass();
		Field[] fs = c.getFields();
		for(Field field : fs){
			if(field.getName().startsWith("_")){
				p = new Property(cl, field);
				if(p.show)
					props.add(p);
			}
		}
		Collections.sort(props);
		propData = props.toArray(new Property[props.size()]);
	}


	public DBase getOwner(){
		return owner;
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
		return propData[rowIndex].getValue();
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

	public void modelChangedInGUI(){
		this.fireTableChanged(new TableModelEvent(this));
	}

}
