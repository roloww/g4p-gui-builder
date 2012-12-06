package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;

public class DLinearTrack extends DValueControl {
	

	public Boolean 		_0062_show_value  = false;
	public Boolean 		show_value_edit = true;
	public Boolean 		show_value_show = true;
	public String 		show_value_label = "Show value?";

	public Boolean 		_0063_show_limits  = false;
	public Boolean 		show_limitse_edit = true;
	public Boolean 		show_limits_show = true;
	public String 		show_limits_label = "Show limits?";
	
	public String 		_0064_text_orient = "ORIENT_TRACK";
	transient public EditorBase text_orient_editor = new EditorJComboBox(TEXT_ORIENT);
	public String 		text_orient_label = "Text direction";
	public Boolean 		text_orient_edit = true;
	public Boolean 		text_orient_show = true;


	protected String get_creator(DBase parent, String window){
		String s = "";
		if(_0062_show_value)
			s += Messages.build(SET_SHOW_VALUE, _0010_name, _0062_show_value);
		if(_0063_show_limits)
			s += Messages.build(SET_SHOW_LIMITS, _0010_name, _0063_show_limits);
		if(!_0064_text_orient.equals("ORIENT_TRACK"))
			s += Messages.build(SET_TEXT_ORIENT, _0010_name, _0064_text_orient);
			
		s += super.get_creator(parent, window);		
		return s;
	}

	
	
	
}
