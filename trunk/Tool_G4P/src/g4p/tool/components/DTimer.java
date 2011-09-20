package g4p.tool.components;

import javax.swing.event.TableModelEvent;

import g4p.tool.Messages;
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
	public String 		timer_starts_updater = "updateTimerStart";

	public int 			_0078_repeats = 1;
	public String 		repeats_label = "Number of repeats";
	public String 		repeats_tooltip = "=0 repeat forever";
	public Boolean 		repeats_edit = true;
	public Boolean 		repeats_show = false;
	public Validator 	repeats_validator = Validator.getValidator(int.class, 0, 999999);


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
	 * Get the event header
	 * @return
	 */
	protected String get_event_header(){
		return Messages.build(METHOD_START_0, _0101_eventHandler, _0005_name, id.toString()).replace('[', '{');
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GTIMER, _0005_name, "this", "this", _0101_eventHandler, _0075_interval);
		if(_0077_timer_starts){
			if(_0078_repeats <= 0)
				s += Messages.build(START_TIMER_0,_0005_name);
			else
				s += Messages.build(START_TIMER_1,_0005_name, _0078_repeats);
		}	
		
		return s;
	}

	public void updateTimerStart(){
		System.out.println("update timer start done  ");
		repeats_show = _0077_timer_starts;
		propertyModel.createProperties(this);
		propertyModel.fireTableChanged(new TableModelEvent(propertyModel));
	}

	
}
