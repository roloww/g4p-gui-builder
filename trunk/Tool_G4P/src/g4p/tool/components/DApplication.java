package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;

import javax.swing.event.TableModelEvent;

/**
 * This class represents the whole Processing sketch. <br>
 * 
 * It will be the root node for the tree view and its children 
 * should only be windows.
 * 
 * @author Peter Lager
 *
 */
@SuppressWarnings("serial")
public final class DApplication extends DBase {
	
	public Boolean name_edit = false;
	public Boolean x_show = false;
	public Boolean y_show = false;
	public Boolean width_show = false;
	public Boolean height_show = false;
	
	public String _0010_Colour_scheme = "RED_SCHEME";
	public CellEditor_Base Colour_scheme_editor = new CellEditor_JComboBox(COLOUR_SCHEME);
	public Boolean Colour_scheme_edit = true;
	public Boolean Colour_scheme_show = true;

	public Boolean _0020_Cursor_changer  = false;
	public Boolean Cursor_changer_edit = true;
	public Boolean Cursor_changer_show = true;
	public String Cursor_changer_updater = "updateCursorChanger";

	public String _0022_Cursor_off = "ARROW";
	public CellEditor_Base Cursor_off_editor = new CellEditor_JComboBox(CURSOR_OVER);
	public Boolean Cursor_off_edit = true;
	public Boolean Cursor_off_show = false;

	public String _0023_Cursor_over = "HAND";
	public CellEditor_Base Cursor_over_editor = new CellEditor_JComboBox(CURSOR_OVER);
	public Boolean Cursor_over_edit = true;
	public Boolean Cursor_over_show = false;

	/**
	 * 
	 */
	public DApplication() {
		super();
		selectable = true;
		resizeable = false;
		moveable = false;
		allowsChildren = true;
		_0005_name = "SKETCH";
		name_label = "PROCCESSING";
	}

	public String getCode(DBase parent){ 
		StringBuilder sb = new StringBuilder();
		sb.append(Messages.build("  G4P.setColorScheme(this, GCScheme.{0});\n", _0010_Colour_scheme));
		if(_0020_Cursor_changer) {
			sb.append(Messages.build("  G4P.cursor({0}, {1});\n", _0022_Cursor_off, _0023_Cursor_over));
			sb.append("  G4P.setMouseOverEnabled(true);\n");
		}
		return new String(sb);
	}

	public void updateCursorChanger(){
		System.out.println("update cursor changer done  " + _0020_Cursor_changer.toString());
		Cursor_off_show = _0020_Cursor_changer;
		Cursor_over_show = _0020_Cursor_changer;
		propertyModel.createProperties(this);
		propertyModel.fireTableChanged(new TableModelEvent(propertyModel));
	}
	

	public String toString(){
		return _0005_name;
	}
}
