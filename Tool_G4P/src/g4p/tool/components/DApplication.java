package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p_controls.GCScheme;

import java.io.IOException;
import java.io.ObjectInputStream;

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
	public String 		col_scheme_updater = "colourSchemeChange";
	
	public Boolean 		_0720_cursor  = true;
	public Boolean 		cursor_edit = true;
	public Boolean 		cursor_show = true;
	public String 		cursor_updater = "updateCursorChanger";
	public String 		cursor_label = "Enable mouse over";

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
		name_edit = false;
		x_show = false;
		y_show = false;
		width_show = false;
		height_show = false;
		opaque_show = false;
		eventHandler_show = false;
	}

	public String get_creator(DBase parent, String window){ 
		StringBuilder sb = new StringBuilder();
		sb.append(Messages.build("  G4P.setGlobalColorScheme(GCScheme.{0});\n", _0710_col_scheme));
		sb.append("  G4P.messagesEnabled(false);\n");
		if(_0720_cursor) {
			sb.append(Messages.build("  G4P.setCursorOff({0});\n", _0722_cursor_off));
		}
		else {
			sb.append("  G4P.setMouseOverEnabled(false);\n");			
		}
		return new String(sb);
	}
	
	public String get_event_definition(){
		return null;
	}

	public String get_declaration(){
		return null;
	}

	public void colourSchemeChange(){
		DBase.colScheme = ListGen.instance().getIndexOf(COLOUR_SCHEME, _0710_col_scheme);
		DBase.jpalette = GCScheme.getJavaColor(colScheme);
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		col_scheme_editor = new EditorJComboBox(COLOUR_SCHEME);
		cursor_off_editor = new EditorJComboBox(CURSOR_CHANGER);
//		int cs = ListGen.instance().getIndexOf(COLOUR_SCHEME, _0710_col_scheme);
//		palette = GCScheme.getColor(cs);
//		jpalette = GCScheme.getJavaColor(cs);
	}

}
