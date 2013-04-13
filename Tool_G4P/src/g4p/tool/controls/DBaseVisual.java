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
//		try{
			colScheme = DBase.globalColorScheme;
			jpalette = DBase.globalJpalette;
			
//			try{
//				((EditorJComboBox)col_scheme_editor).setSelectedIndex(colScheme);
//			}
//			catch(Exception e){
//				System.out.println("Error in DBaseVisual constructor " + this.getClass().getSimpleName());
//				System.out.println("Can't select element " + colScheme);
//				e.printStackTrace();
//			}
//			_0940_col_scheme = ((EditorJComboBox)col_scheme_editor).getSelected();
//		}
//		catch(Exception e){
//			System.out.println("Error in DBaseVisual constructor " + this.getClass().getSimpleName());
//			if(col_scheme_editor == null){
//				System.out.println("No colour editor");
//			}
//			if(jpalette == null){
//				System.out.println("No colour palette");
//			}
//			System.out.println(_0940_col_scheme);
//			System.out.println(e.getStackTrace());
//		}
		colSchemeChanged = false;
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
		col_scheme_editor.setSelected(_0940_col_scheme);
	}

}
