package g4p.tool.components;

import g4p.tool.Messages;

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
		return componentClass + " " + _0005_name+ ";\n";
	}

	
	/** get the event method for this control
	 * 
	 * @return
	 */
	public String get_event_definition(){
		String ec = get_event_header() + get_event_code() + get_event_end();
		return ec;
	}

	/**
	 * Get the event code if none then return generic message
	 * @param code
	 * @return
	 */
	protected String get_event_code(){ 
		String ev_code = Code.instance().get(id);
		if(ev_code == null)
			return Messages.build(CODE_ANY, _0005_name, componentClass);
		else
			return ev_code; 
	}

	/**
	 * Get the event header
	 * @return
	 */
	protected String get_event_header(){
		return Messages.build(METHOD_START_1, _0101_eventHandler, componentClass, 
				componentClass.substring(1).toLowerCase(), 
				_0005_name, id.toString()).replace('[', '{');
	}

	/**
	 * Get the event method end with tag
	 * @return
	 */
	protected String get_event_end(){
		return Messages.build(METHOD_END, _0005_name, 
				id.toString()).replace(']', '}');
	}
	

}
