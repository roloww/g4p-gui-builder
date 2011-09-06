package g4p.tool.components;

@SuppressWarnings("serial")
public class DCoreSelectable extends DCoreText {

	
	public Boolean _0050_selected  = false;
	public Boolean selected_edit = true;
	public Boolean selected_show = true;
	public String selected_label = "Selected?";

	
	public DCoreSelectable(){
		super();
		_0024_width = 90;
		_0025_height = 20;
	}
	
	public void setSelected(boolean selected){
		_0050_selected = selected;
	}
}
