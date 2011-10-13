package g4p.tool.components;


@SuppressWarnings("serial")
public class DCore extends DBase {

	public DCore(){
		super();
		selectable = true;
		resizeable = true;
		moveable = true;

		name_label = "Variable name";
		name_tooltip = "Use Java naming rules";
		name_edit = true;
		
		x_edit = y_edit = true;
		x_show = y_show = true;	
		width_edit = height_edit = true;
		width_show = height_show = true;
		_0130_width = 80;
		_0131_height = 22;
		eventHandler_edit = eventHandler_show = true;
		allowsChildren = false;
	}


}
