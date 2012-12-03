package g4p.tool.components;

import g4p.tool.Messages;

@SuppressWarnings("serial")
public class DCoreSelectable extends DTextIcon {

	
	public Boolean _0685_selected  = false;
	public Boolean selected_edit = true;
	public Boolean selected_show = true;
	public String selected_label = "Selected?";
	public String selected_updater = "selectionChange";

	
	public DCoreSelectable(){
		super();
		_0130_width = 120;
		_0131_height = 20;
		iconNo = 0;
	}
	
	protected String get_creator(DBase parent, String window){
		String s = "";
		if(_0685_selected)
			s += Messages.build(SEL_OPTION, _0010_name, "true");
		s += super.get_creator(parent, window);
		return s;
	}

	public void selectionChange(){
		iconNo = _0685_selected ? 1 : 0;
	}
	
	public void setSelected(boolean selected){
		_0685_selected = selected;
	}
}
