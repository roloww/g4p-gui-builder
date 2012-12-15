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
public class DKnob extends DValueControl {

	public float 			_0041_dial_start_angle = 110;
	public String		dial_start_angle_label = "Dial starts at angle";
	public String		dial_start_angle_tooltip = "0-360 degrees";
	public Boolean 		dial_start_angle_edit = true;
	public Boolean 		dial_start_angle_show = true;
	public Validator 	dial_start_angle_validator = Validator.getValidator(float.class, 0, 360);

	public float 			_0042_dial_end_angle = 70;
	public String		dial_end_angle_label = "Dial ends at angle";
	public String		dial_end_angle_tooltip = "0-360 degrees";
	public Boolean 		dial_end_angle_edit = true;
	public Boolean 		dial_end_angle_show = true;
	public Validator 	dial_end_angle_validator = Validator.getValidator(float.class, 0, 360);

	public String 		_0043_controller = "HORIZONTAL";
	transient public 	EditorBase controller_editor = new EditorJComboBox(KNOB_CTRL);
	public Boolean		controller_edit = true;
	public Boolean		controller_show = true;
	public String		controller_label = "Mouse controller scheme";
	public String		controller_updater = "updateController";
	
	public Float 		_0044_sensitivity = 1.0f;
	public String 		sensitivity_label = "Drag sensitivity";
	public Boolean 		sensitivity_edit = true;
	public Boolean 		sensitivity_show = true;
	public Validator 	sensitivity_validator = Validator.getValidator(float.class, 0.2f, 5.0f);

	public Float 		_0046_grip_ratio = 0.8f;
	public String 		grip_ratio_label = "Grip size (ratio)";
	public Boolean 		grip_ratio_edit = true;
	public Boolean 		grip_ratio_show = true;
	public Validator 	grip_ratio_validator = Validator.getValidator(float.class, 0.0f, 1.0f);

	public Boolean		_0047_over_arc_only  = false;
	public Boolean		over_arc_only_edit = true;
	public Boolean		over_arc_only_show = true;
	public String		over_arc_only_label = "Mouse over arc only";

	public Boolean		_0047_over_grip_only  = true;
	public Boolean		over_grip_only_edit = true;
	public Boolean		over_grip_only_show = true;
	public String		over_grip_only_label = "Mouse over grip only";

	public Boolean		_0063_show_arc_only  = false;
	public Boolean		show_arc_only_edit = true;
	public Boolean		show_arc_only_show = true;
	public String		show_arc_only_label = "Show arc only";

	public Boolean		_0066_show_track  = true;
	public Boolean		show_track_edit = true;
	public Boolean		show_tracky_show = true;
	public String		show_track_label = "Show track";


	public DKnob(){
		super();
		componentClass = "GKnob";
		set_name(NameGen.instance().getNext("knob"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_turn"));
		_0130_width = 60;
		_0131_height = 60;
		_0060_show_ticks = true;
		vtype_show = false;
		precision_show = false;
	}

	public void updateController(){
		if(_0043_controller.equals("ANGULAR")){
			_0044_sensitivity = 1.0f;
			sensitivity_show = false;
		}
		else {
			sensitivity_show = true;			
		}
		propertyModel.createProperties(this);
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GKNOB, _0010_name, window, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height), $(_0046_grip_ratio));

		s += Messages.build(SET_TURN_RANGE,_0010_name, _0041_dial_start_angle, _0042_dial_end_angle);
		s += Messages.build(SET_CONTROLLER,_0010_name, _0043_controller);
		if(sensitivity_show) // only happens when not angular
			s += Messages.build(SET_DRAG_SENSITIVITY, _0010_name, _0044_sensitivity);	
		s += Messages.build(SET_SHOW_ARC_ONLY, _0010_name, _0063_show_arc_only);
		s += Messages.build(SET_OVER_ARC_ONLY, _0010_name, _0047_over_arc_only);
		s += Messages.build(SET_OVER_GRIP_ONLY, _0010_name, !_0047_over_grip_only);
		s += Messages.build(SET_SHOW_TRACK, _0010_name, _0066_show_track);
		s += super.get_creator(parent, window);		
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		// Bezel
		g.setColor(DBase.jpalette[5]);
		g.fillOval(0, 0, _0130_width, _0131_height);
		int ox = Math.round(0.16f * _0130_width);
		int oy = Math.round(0.16f * _0131_height);
		g.setColor(DBase.jpalette[4]);
		g.fillOval(ox, oy, _0130_width - 2*ox, _0131_height - 2*oy);
		g.setColor(DBase.jpalette[14]);
		g.setStroke(needleStroke);
		g.drawLine(_0130_width/2, _0131_height/2, 
				_0130_width/2 + Math.round(0.3535f*_0130_width),
				_0131_height/2 + Math.round(0.3535f*_0131_height));
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	protected void read(){
		super.read();
		controller_editor = new EditorJComboBox(KNOB_CTRL);	
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}


}
