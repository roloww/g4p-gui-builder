package g4p.tool.components;

public class DActivityBar extends DCore {

	
	public DActivityBar(){
		super();

		componentClass = "GActivityBar";
		set_name(NameGen.instance().getNext("activityBar"));

		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		_0130_width = 64;
		_0131_height = 16;
		eventHandler_edit = eventHandler_show = false;
	}
	
	
}
