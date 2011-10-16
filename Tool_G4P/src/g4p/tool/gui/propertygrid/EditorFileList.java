package g4p.tool.gui.propertygrid;

import g4p.tool.TFileConstants;
import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.EditorJFileChooser.ImageFilter;
import g4p.tool.gui.propertygrid.EditorJFileChooser.ImagePreview;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Sketch;
import processing.core.PApplet;

public class EditorFileList extends EditorBase {

	protected static JTextField component = null;
	protected static JTextArea lister = null;
	protected static JScrollPane pane = null;
	
	
	public EditorFileList(){
		if(lister == null){
			lister = new JTextArea("",10,40);
			pane = new JScrollPane(lister, 
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		}
		if(component == null){
			component = new JTextField();
			component.setFocusable(false);
		}
	}
	

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		String content = "";
		component.setText(value.toString());
		GuiDesigner.keepOpen(true);
		
		String fname = GuiDesigner.editor().getSketch().getDataFolder() + SEP + value.toString();
		File file = new File(fname);
		try {
			content = Base.loadFile(file);
		} catch (IOException e1) {
			content = "";
		}
		lister.setText(content);
		// initialise the textarea chooser
		int result = JOptionPane.showConfirmDialog(null, pane, "ComboBox List (1 option per line)", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			try {
				content = Base.loadFile(file);
			} catch (IOException e1) {
				System.out.println("CANT SAVE OPTIONS");
			}
		}
		GuiDesigner.keepOpen(false);
		this.fireEditingStopped();
		return component;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return component.getText();
	}

}
