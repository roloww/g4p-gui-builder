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
		name_tooltip = "Use Java naming rules";
		name_edit = true;
		
		x_edit = y_edit = true;
		x_show = y_show = true;	
		width_edit = height_edit = true;
		width_show = height_show = true;
		eventHandler_edit = eventHandler_show = true;
		allowsChildren = false;
	}

	/**
	 * Get the declaration for this control
	 */
	public String get_declaration(){
		return componentClass + " " + _0005_name+ "; ";
	}

}
