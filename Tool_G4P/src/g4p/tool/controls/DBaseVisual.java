package g4p.tool.controls;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p_controls.GCScheme;

public class DBaseVisual extends DBase {

	public boolean colSchemeChanged;
	public int colScheme;
	public Color[] jpalette;

	public String 		_0940_col_scheme = "BLUE_SCHEME";
	transient public 	EditorBase col_scheme_editor = new EditorJComboBox(COLOUR_SCHEME);
	public Boolean 		col_scheme_edit = true;
	public Boolean 		col_scheme_show = true;
	public String 		col_scheme_label = "Control colour scheme";
	public String 		col_scheme_updater = "colourSchemeChange";


	public DBaseVisual() {
		super();
		colScheme = DBase.globalColorScheme;
		colSchemeChanged = false;
		jpalette = DBase.globalJpalette;
	}
	
	protected String get_creator(DBase parent, String window){
		String s = "";
		if(colSchemeChanged || colScheme != DBase.globalColorScheme)
			s = Messages.build(SET_LOCAL_COLOR, _0010_name, _0940_col_scheme);
		s += super.get_creator(parent, window);		
		return s;
	}

	public void colourSchemeChange(){
		colScheme = ListGen.instance().getIndexOf(COLOUR_SCHEME, _0940_col_scheme);
		jpalette = GCScheme.getJavaColor(colScheme);
		propertyModel.hasBeenChanged();
		colSchemeChanged = true;
	}
	
	protected void read(){
		super.read();
		col_scheme_editor = new EditorJComboBox(COLOUR_SCHEME);
		col_scheme_editor.setSelected(colScheme);
	}

}
