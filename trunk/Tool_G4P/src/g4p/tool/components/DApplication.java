package g4p.tool.components;

import java.io.IOException;
import java.io.ObjectInputStream;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;

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
	
	public String 		_0710_col_scheme = "BLUE_SCHEME";
	transient public 	EditorBase col_scheme_editor = new EditorJComboBox(COLOUR_SCHEME);
	public Boolean 		col_scheme_edit = true;
	public Boolean 		col_scheme_show = true;
	public String 		col_scheme_label = "Colour scheme";
	
	public Boolean 		_0720_cursor  = false;
	public Boolean 		cursor_edit = true;
	public Boolean 		cursor_show = true;
	public String 		cursor_updater = "updateCursorChanger";
	public String 		cursor_label = "Enable mouse over";

	public String 		_0721_cursor_over = "CROSS";
	transient public 	EditorBase cursor_over_editor = new EditorJComboBox(CURSOR_CHANGER);
	public Boolean 		cursor_over_edit = true;
	public Boolean 		cursor_over_show = false;
	public String 		cursor_over_label = "Is over control";

	public String 		_0722_cursor_off = "ARROW";
	transient public 	EditorBase cursor_off_editor = new EditorJComboBox(CURSOR_CHANGER);
	public Boolean 		cursor_off_edit = true;
	public Boolean 		cursor_off_show = false;
	public String 		cursor_off_label = "Not over control";

	
	/**
	 * 
	 */
	public DApplication() {
		super();
		selectable = true;
		resizeable = false;
		moveable = false;
		allowsChildren = true;
		_0010_name = "SKETCH";
		name_label = "PROCCESSING";
	}

	public String get_creator(DBase parent){ 
		StringBuilder sb = new StringBuilder();
		sb.append(Messages.build("  G4P.setColorScheme(this, GCScheme.{0});\n", _0710_col_scheme));
		sb.append("  G4P.messagesEnabled(false);\n");
		if(_0720_cursor) {
			sb.append(Messages.build("  G4P.cursor({0}, {1});\n", _0722_cursor_off, _0721_cursor_over));
			sb.append("  G4P.setMouseOverEnabled(true);\n");
		}
		return new String(sb);
	}

	public void updateCursorChanger(){
		cursor_off_show = _0720_cursor;
		cursor_over_show = _0720_cursor;
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
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		col_scheme_editor = new EditorJComboBox(COLOUR_SCHEME);
		cursor_off_editor = new EditorJComboBox(CURSOR_CHANGER);
		cursor_over_editor = new EditorJComboBox(CURSOR_CHANGER);
	}

}
