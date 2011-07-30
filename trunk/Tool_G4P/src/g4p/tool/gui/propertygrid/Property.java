package g4p.tool.gui.propertygrid;

import g4p.tool.components.DBase;

import java.lang.reflect.Field;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellRenderer;

import processing.app.Editor;

public class Property implements Comparable {

	public Object fieldFromObject;
	public Field field;
	// Class name without package or primitive data type
	public Class<?> ftype;
	// Includes ordering key
	public String fieldName;
	public Object fvalue;

	public String cellText;

	public boolean allowEdit = true;
	public boolean show = true;
	
	// The validator to use with this property
	public Validator validator = null;
	public CellEditor_Base editor = null;
	public TableCellRenderer renderer = null;
	
	public Property(Object o, Field f){
		fieldFromObject = o;
		field = f;
		ftype = f.getType();
		fieldName = field.getName(); // e.g. _1234_name
		cellText = fieldName.substring(6);
		
		fvalue = this.getFieldValue(field, fieldFromObject);

		//		System.out.println("Cell text " + cellText);
		
		// Get cell editor if any
		try {
			Field field = fieldFromObject.getClass().getField(cellText + "_editor");
			editor =  (CellEditor_Base) field.get(fieldFromObject);
		}
		catch(Exception excp){
			// Nothing to do if no editor is specified
		}
		// Get cell renderer if any
		try {
			Field field = fieldFromObject.getClass().getField(cellText + "_renderer");
			renderer = (TableCellRenderer) field.get(fieldFromObject);
		}
		catch(Exception excp){
			// Nothing to do if no editor is specified
		}
		// Get validator if any
		try {
			Field field = fieldFromObject.getClass().getField(cellText + "_validator");
			validator = (Validator) field.get(fieldFromObject);
		}
		catch(Exception excp){
			validator = Validator.getDefaultValidator(ftype);
		}
		// Get edit status if any
		try {
			Field field = fieldFromObject.getClass().getField(cellText + "_edit");
			allowEdit = (Boolean) field.get(fieldFromObject);
		}
		catch(Exception excp){
			// Nothing to do but assume the fields is editable
		}
		// See if we need to show this property
		try {
			Field field = fieldFromObject.getClass().getField(cellText + "_show");
			show = (Boolean) field.get(fieldFromObject);
		}
		catch(Exception excp){
			// Nothing to do but assume the fields is to be shown
		}

	}

	// Called when table cell loses focus
	public void setValue(Object value){
		fvalue = value;
		try {
			setFieldValue(field, fieldFromObject, fvalue);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
	}


	public Object getFieldValue(Field f, Object obj){
		Object fvalue;
		Class<?> c = f.getClass();
		if(c == boolean.class || c == Boolean.class){
			try {
				System.out.println("Convert field of type "+ c);
				fvalue = f.getBoolean(obj);
			} catch (Exception e) {
				System.out.println("FAILED Boolean " + cellText);
				e.printStackTrace();
				fvalue = new Boolean(false);
			} 
		}
		else if(c == short.class || c == Short.class){
			try {
				fvalue = f.getShort(obj);
			} catch (Exception e) {
				fvalue = new Short((short) 0);
			} 
		}
		else if(c == int.class || c == Integer.class){
			try {
				fvalue = f.getInt(obj);
			} catch (Exception e) {
				System.out.println("FAILED Integer " + cellText);
				fvalue = new Integer(0);
			} 
		}
		else if(c == long.class || c == Long.class){
			try {
				fvalue = f.getLong(obj);
			} catch (Exception e) {
				fvalue = new Long(0);
			} 
		}
		else if(c == float.class || c == Float.class){
			try {
				fvalue = f.getFloat(obj);
			} catch (Exception e) {
				fvalue = new Float(0);
			} 
		}
		else if(c == double.class || c == Double.class){
			try {
				fvalue = f.getDouble(obj);
			} catch (Exception e) {
				fvalue = new Double(0);
			} 
		}
		else if(c == char.class || c == Character.class){
			try {
				fvalue = f.getChar(obj);
			} catch (Exception e) {
				fvalue = new Character(' ');
			} 
		}
		else if(c == String.class){
			try {
				fvalue = f.get(obj).toString();
			} catch (Exception e) {
				fvalue = new String("");
			} 
		}
		else {
			try {
				fvalue = f.get(obj);
			} catch (Exception e) {
				fvalue = new Object();
			} 
		}
		return fvalue;
	}

	public void setFieldValue(Field f, Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
		System.out.println("Set Field " + field);
		Class<?> c = f.getClass();
		if(c == boolean.class || c == Boolean.class)
			f.setBoolean(obj, Boolean.valueOf(value.toString()));
		else if(c == int.class || c == Integer.class)
			f.setInt(obj, (Integer)value);
		else if(c == long.class || c == Long.class)
			f.setLong(obj, (Long)value);
		else if(c == float.class || c == Float.class)
			f.setFloat(obj, (Float)value);
		else if(c == double.class || c == Double.class)
			f.setChar(obj, (Character)value);
		else if(c == String.class)
			f.set(obj, (String)value);
		else 
			f.set(obj, value);
	}


	public Object getValue(){
		return fvalue;
	}

	public int compareTo(Object o) {
		Property p = (Property) o; 
		return fieldName.compareTo(p.fieldName);
	}


}
