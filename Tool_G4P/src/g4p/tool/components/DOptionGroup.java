package g4p.tool.components;

public class DOptionGroup extends DBase {

	
	public DOptionGroup(){
		super();
//		System.out.println("\tDCore) ctor");
		selectable = false;
		resizeable = false;
		moveable = false;

		set_name(NameGen.instance().getNext("optGroup"));
		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		
//		x_edit = y_edit = false;
//		x_show = y_show = false;	
//		width_edit = height_edit = false;
//		width_show = height_show = false;
		
		allowsChildren = true;
	}
	
	
}
