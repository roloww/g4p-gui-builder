package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DKnob extends DSliderFloat {

	public int 			_0032_dial_start_angle = 110;
	public String		dial_start_angle_label = "Dial starts at angle";
	public String		dial_start_angle_tooltip = "0-360 degrees";
	public Boolean 		dial_start_angle_edit = true;
	public Boolean 		dial_start_angle_show = true;
	public Validator 	dial_start_angle_validator = Validator.getValidator(int.class, 0, 360);

	public int 			_0033_dial_end_angle = 70;
	public String		dial_end_angle_label = "Dial ends at angle";
	public String		dial_end_angle_tooltip = "0-360 degrees";
	public Boolean 		dial_end_angle_edit = true;
	public Boolean 		dial_end_angle_show = true;
	public Validator 	dial_end_angle_validator = Validator.getValidator(int.class, 0, 360);

	public String 		_0038_controller = "HORIZONTAL";
	transient public 	EditorBase controller_editor = new EditorJComboBox(KNOB_CTRL);
	public Boolean		controller_edit = true;
	public Boolean		controller_show = true;
	public String		controller_label = "Mouse controller scheme";
	
	public Boolean		_0035_arc_only  = false;
	public Boolean		arc_only_edit = true;
	public Boolean		arc_only_show = true;
	public String		arc_only_label = "Show arc only";

	public int 			_0037_ticks = 2;
	public String 		ticks_label = "Number of tick marks";
	public String 		ticks_tooltip = "must be >= 2";
	public Boolean 		ticks_edit = false;
	public Boolean 		ticks_show = false;
	public Validator 	ticks_validator = Validator.getValidator(int.class, 2, 999);

	public DKnob(){
		super();
		componentClass = "GKnob";
		set_name(NameGen.instance().getNext("knob"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Turn"));
		_0130_width = 40;
		_0131_height = 40;
	}

	/**
	 * Get the declaration for this control
	 */
	protected String get_declaration(){
		if(_0130_width == _0131_height)
			return "GKnob " + _0010_name+ ";\n ";
		else
			return "GKnobOval " + _0010_name+ ";\n ";			
	}

	/**
	 * Get the event header
	 * @return
	 */
	protected String get_event_header(){
		if(_0130_width == _0131_height)
			return Messages.build(METHOD_START_1, _0701_eventHandler, "GKnob", "knob", 
					_0010_name, id[0].toString()).replace('[', '{');
		else
			return Messages.build(METHOD_START_1, _0701_eventHandler, "GKnobOval", "knob", 
					_0010_name, id[0].toString()).replace('[', '{');
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		if(_0130_width == _0131_height)
			s = Messages.build(CTOR_GKNOB, _0010_name, "this", _0120_x, _0121_y, _0130_width, _0032_dial_start_angle, _0033_dial_end_angle);
		else
			s = Messages.build(CTOR_GKNOBOVAL, _0010_name, "this", _0120_x, _0121_y,_0130_width, _0131_height, _0032_dial_start_angle, _0033_dial_end_angle);
		s += Messages.build(SET_F_LIMITS,_0010_name, String.valueOf(_0630_value), String.valueOf(_0631_min), String.valueOf(_0632_max));
		s += Messages.build(SET_ARC_ONLY,_0010_name, _0035_arc_only);
		s += Messages.build(SET_NBR_TICKS,_0010_name, _0037_ticks);
		s += Messages.build(SET_CONTROLLER,_0010_name, _0038_controller);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		controller_editor = new EditorJComboBox(KNOB_CTRL);
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);

		g.setColor(knbBezel);
		g.fillOval(0, 0, _0130_width, _0131_height);
		int ox = Math.round(0.16f * _0130_width);
		int oy = Math.round(0.16f * _0131_height);
		g.setColor(knbBack);
		g.fillOval(ox, oy, _0130_width - 2*ox, _0131_height - 2*oy);
		g.setColor(knbNeedle);
		g.setStroke(needleStroke);
		g.drawLine(_0130_width/2, _0131_height/2, 
				_0130_width/2 + Math.round(0.3535f*_0130_width),
				_0131_height/2 + Math.round(0.3535f*_0131_height));
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}


}
