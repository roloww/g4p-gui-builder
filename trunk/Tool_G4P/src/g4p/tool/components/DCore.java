package g4p.tool.components;

@SuppressWarnings("serial")
public class DCore extends DBase {

	public DCore(){
		super();
//		System.out.println("\tDCore) ctor");
		selectable = true;
		resizeable = true;
		moveable = true;

		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		
		x_edit = y_edit = true;
		x_show = y_show = true;	
		width_edit = height_edit = true;
		width_show = height_show = true;
		
		allowsChildren = false;
	}

	
}