package g4p.tool.gui.propertygrid;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class Editor_JTextfield extends Editor_Base { 

	// Singleton class stuff
	private static Editor_JTextfield instance = null;

	public static Editor_JTextfield instance(){
		if(instance == null)
			instance = new Editor_JTextfield();
		System.out.println("===========================================================================================");
		return instance;
	}

	// Stuff for edit component
	protected static JTextField component;

	protected static Color ok = new Color(200,255,200);
	protected static Color notok = new Color(255,200,200);

	protected void doneWithEditing(){
		component.setBackground(Color.WHITE);
		component.setForeground(Color.BLACK);
	}

	protected void setValidHint(boolean valid){
		if(valid)
			component.setBackground(ok);
		else
			component.setBackground(notok);
	}

	/**
	 * Create an integer editor component that accepts any valid integer.
	 */
	public Editor_JTextfield() {
		System.out.println("JTextField Editor constructor()");
		component = new JTextField();
		component.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent e) {
				isValid(component.getText());
			}

			public void keyPressed(KeyEvent e) {}

			public void keyReleased(KeyEvent e) {
				isValid(component.getText());
			}
		});
	}

	/**
	 * See if the user supplied in
	 * @param vo
	 * @return
	 */
	private boolean isValid(Object vo){
		System.out.println("JTextField Editor isValid()");
		boolean result = (validator == null) ? true : validator.isValid(vo);
		setValidHint(result);
		return result;
	}

	/**
	 * Get the latest value and make sure it is valid or empty. <br>
	 * If it is invalid then prevent the focus leaving the component.
	 */
	public boolean stopCellEditing() {	
		System.out.println("JTextField stopCellEditing()");
		boolean valid = isValid(component.getText());
		if(valid){
			doneWithEditing();
			return super.stopCellEditing();			
		}
		else {
			component.requestFocus();
			return false;
		}
	}

	/**
	 * This is called when the table cell gets focus and returns the editor component.
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, 
			boolean isSelected, int row, int column) {
		System.out.println("JTextField getTableCellEditorComponent()");
		if(validator != null)
			validator.setOriginalValue(value);
		component.setBorder(new LineBorder(Color.black));
		component.setText(value.toString());
		setValidHint((validator == null) ? true : validator.isValid(value));
		return component;
	}

	/**
	 * This is called when the editor component looses focus and retrieves the final
	 * value for the table model
	 */
	public Object getCellEditorValue() {
		System.out.println("JTextField getCellEditorValue()");
		return (validator == null) ? new Object() : validator.getCellValue();
	}

}
