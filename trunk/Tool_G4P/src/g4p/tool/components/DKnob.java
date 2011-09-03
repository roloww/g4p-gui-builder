package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DKnob extends DSliderFloat {
	
	public int 			_0032_dial_start_angle = 0;
	public String		dial_start_angle_label = "Dial starts at angle";
	public String		dial_start_angle_tooltip = "0-360 degrees";
	public Boolean 		dial_start_angle_edit = true;
	public Boolean 		dial_start_angle_show = true;
	public Validator 	dial_start_angle_validator = Validator.getValidator(int.class, 0, 360);

	public int 			_0033_dial_end_angle = 0;
	public String		dial_end_angle_label = "Dial ends at angle";
	public String		dial_end_angle_tooltip = "0-360 degrees";
	public Boolean 		dial_end_angle_edit = true;
	public Boolean 		dial_end_angle_show = true;
	public Validator 	dial_end_angle_validator = Validator.getValidator(int.class, 0, 360);


	public DKnob(){
		super();
		componentClass = "GKnob";
		set_name(NameGen.instance().getNext("knob"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Turn"));
		_0024_width = 40;
		_0025_height = 40;
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
