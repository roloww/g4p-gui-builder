package g4p.tool.components;

import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;
import g4p.tool.gui.propertygrid.CellEditor_JSpinner;
import g4p.tool.gui.propertygrid.CellRender_JComboBox;
import g4p.tool.gui.propertygrid.Validator;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellRenderer;

/**
 * This class represents the who;e application. <br>
 * 
 * It will be the root node for the tree view and its children should only be windows.
 * 
 * @author Peter Lager
 *
 */
public final class DApplication extends DBase {
	
	public Boolean name_edit = false;
	public Boolean x_show = false;
	public Boolean y_show = false;
	public Boolean width_show = false;
	public Boolean height_show = false;
	
	public String _0010_Colour_scheme = "RED_SCHEME";
	
//	public CellEditor_Base Colour_scheme_editor = CellEditor_JSpinner.instance();
//	public Validator Colour_scheme_validator = Validator.getValidator(COLOUR_SCHEME);
	public CellEditor_Base Colour_scheme_editor = CellEditor_JComboBox.instance(COLOUR_SCHEME);
	public TableCellRenderer Colour_scheme_renderer = new CellRender_JComboBox(COLOUR_SCHEME);
//	public Validator Colour_scheme_validator = Validator.getValidator(COLOUR_SCHEME);

	public Boolean Colour_scheme_edit = true;
	public Boolean Colour_scheme_show = true;

	/**
	 * 
	 */
	public DApplication() {
		super();
		allowsChildren = true;
		_0005_name = "APPLICATION";
	}


	public String toString(){
		return _0005_name;
	}
}
