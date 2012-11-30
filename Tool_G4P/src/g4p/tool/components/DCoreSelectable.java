package g4p.tool.components;

@SuppressWarnings("serial")
public class DCoreSelectable extends DTextIcon {

	
	public Boolean _0685_selected  = false;
	public Boolean selected_edit = true;
	public Boolean selected_show = true;
	public String selected_label = "Selected?";

	
	public DCoreSelectable(){
		super();
		_0130_width = 90;
		_0131_height = 20;
	}
	
	public void setSelected(boolean selected){
		_0685_selected = selected;
	}
}
