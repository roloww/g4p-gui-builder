package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

@SuppressWarnings("serial")
public class DTimer extends DBase {

	public int 			_0075_interval = 20;
	public String 		interval_label = "Interval (ms)";
	public String 		interval_tooltip = ">=1 millseconds";
	public Boolean 		interval_edit = true;
	public Boolean 		interval_show = true;
	public Validator 	interval_validator = Validator.getValidator(int.class, 1, 999999);

	public Boolean 		_0077_timer_starts  = false;
	public String 		timer_starts_label = "Start when created";
	public String 		timer_starts_tooltip = "false - will need to be started in your program";
	public Boolean 		timer_starts_edit = true;
	public Boolean 		timer_starts_show = true;

	public DTimer(){
		super();
		selectable = false;
		resizeable = false;
		moveable = false;
		
		componentClass = "GTimer";
		set_name(NameGen.instance().getNext("timer"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Action"));

		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		eventHandler_edit = eventHandler_show = true;		
	}

	/**
	 * Get the declaration for this control
	 */
	public String get_declaration(){
		return componentClass + " " + _0005_name+ "; ";
	}

	
}
