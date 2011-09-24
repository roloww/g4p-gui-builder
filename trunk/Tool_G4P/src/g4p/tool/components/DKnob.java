package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;
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
	transient public 	CellEditor_Base controller_editor = new CellEditor_JComboBox(KNOB_CTRL);
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
		_0024_width = 40;
		_0025_height = 40;
	}

	/**
	 * Get the declaration for this control
	 */
	protected String get_declaration(){
		if(_0024_width == _0025_height)
			return "GKnob " + _0005_name+ ";\n ";
		else
			return "GKnobOval " + _0005_name+ ";\n ";			
	}

	/**
	 * Get the event header
	 * @return
	 */
	protected String get_event_header(){
		if(_0024_width == _0025_height)
			return Messages.build(METHOD_START_1, _0101_eventHandler, "GKnob", "knob", 
					_0005_name, id[0].toString()).replace('[', '{');
		else
			return Messages.build(METHOD_START_1, _0101_eventHandler, "GKnobOval", "knob", 
					_0005_name, id[0].toString()).replace('[', '{');
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		if(_0024_width == _0025_height)
			s = Messages.build(CTOR_GKNOB, _0005_name, "this", _0020_x, _0021_y, _0024_width, _0032_dial_start_angle, _0033_dial_end_angle);
		else
			s = Messages.build(CTOR_GKNOBOVAL, _0005_name, "this", _0020_x, _0021_y,_0024_width, _0025_height, _0032_dial_start_angle, _0033_dial_end_angle);
		s += Messages.build(SET_F_LIMITS,_0005_name, _0040_value, _0041_min, _0042_max);
		s += Messages.build(SET_ARC_ONLY,_0005_name, _0035_arc_only);
		s += Messages.build(SET_NBR_TICKS,_0005_name, _0037_ticks);
		s += Messages.build(SET_CONTROLLER,_0005_name, _0038_controller);
		s += Messages.build(ADD_HANDLER, _0005_name, "this", _0101_eventHandler);
		return s;
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
		IdGen.instance().add(id[0]);
		controller_editor = new CellEditor_JComboBox(KNOB_CTRL);
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);

		g.setColor(knbBezel);
		g.fillOval(0, 0, _0024_width, _0025_height);
		int ox = Math.round(0.16f * _0024_width);
		int oy = Math.round(0.16f * _0025_height);
		g.setColor(knbBack);
		g.fillOval(ox, oy, _0024_width - 2*ox, _0025_height - 2*oy);
		g.setColor(knbNeedle);
		g.setStroke(needleStroke);
		g.drawLine(_0024_width/2, _0025_height/2, 
				_0024_width/2 + Math.round(0.3535f*_0024_width),
				_0025_height/2 + Math.round(0.3535f*_0025_height));
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}


}
