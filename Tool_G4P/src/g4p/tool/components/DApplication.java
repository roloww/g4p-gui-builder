package g4p.tool.components;

import java.io.IOException;
import java.io.ObjectInputStream;

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
	
	public String 		_0010_col_scheme = "BLUE_SCHEME";
	transient public 	CellEditor_Base col_scheme_editor = new CellEditor_JComboBox(COLOUR_SCHEME);
	public Boolean 		col_scheme_edit = true;
	public Boolean 		col_scheme_show = true;
	public String 		col_scheme_label = "Colour scheme";
	
	public Boolean 		_0020_cursor  = false;
	public Boolean 		cursor_edit = true;
	public Boolean 		cursor_show = true;
	public String 		cursor_updater = "updateCursorChanger";
	public String 		cursor_label = "Enable mouse over";

	public String 		_0024_cursor_off = "ARROW";
	transient public 	CellEditor_Base cursor_off_editor = new CellEditor_JComboBox(CURSOR_CHANGER);
	public Boolean 		cursor_off_edit = true;
	public Boolean 		cursor_off_show = false;
	public String 		cursor_off_label = "Not over control";
	
	public String 		_0023_cursor_over = "CROSS";
	transient public 	CellEditor_Base cursor_over_editor = new CellEditor_JComboBox(CURSOR_CHANGER);
	public Boolean 		cursor_over_edit = true;
	public Boolean 		cursor_over_show = false;
	public String 		cursor_over_label = "Is over control";

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

	public String get_creator(DBase parent){ 
		StringBuilder sb = new StringBuilder();
		sb.append(Messages.build("  G4P.setColorScheme(this, GCScheme.{0});\n", _0010_col_scheme));
		if(_0020_cursor) {
			sb.append(Messages.build("  G4P.cursor({0}, {1});\n", _0024_cursor_off, _0023_cursor_over));
			sb.append("  G4P.setMouseOverEnabled(true);\n");
			sb.append("  G4P.messagesEnabled(false);\n");
		}
		return new String(sb);
	}

	public void updateCursorChanger(){
		System.out.println("update cursor changer done  " + _0020_cursor.toString());
		cursor_off_show = _0020_cursor;
		cursor_over_show = _0020_cursor;
		propertyModel.createProperties(this);
		propertyModel.fireTableChanged(new TableModelEvent(propertyModel));
	}
	
	public String get_event_definition(){
		return null;
	}

	public String get_declaration(){
		return null;
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
		IdGen.instance().add(id);
		col_scheme_editor = new CellEditor_JComboBox(COLOUR_SCHEME);
		cursor_off_editor = new CellEditor_JComboBox(CURSOR_CHANGER);
		cursor_over_editor = new CellEditor_JComboBox(CURSOR_CHANGER);
	}

}
