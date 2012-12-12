package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DSlider extends DLinearTrack {

	
	public Float 		_0018_thick = 10.0f;
	public String 		thick_label = "Track thickness";
	public String 		thick_tooltip = "track thickness";
	public Boolean 		thick_edit = true;
	public Boolean 		thick_show = true;
	public Validator 	thick_validator = Validator.getValidator(float.class, 8, 30);

	public DSlider(){
		super();
		componentClass = "GSlider";
		set_name(NameGen.instance().getNext("slider"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_change"));
		_0130_width = 100;
		_0131_height = 40;
	}
	
	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		String x, y, w, h;
		if(_0065_vert){
			x = $(_0120_x + _0130_width);
			y = $(_0121_y);
			w = $(_0131_height);
			h = $(_0130_width);		
		}
		else {
			x = $(_0120_x);
			y = $(_0121_y);
			w = $(_0130_width);
			h = $(_0131_height);
		}
//		s = Messages.build(CTOR_GSLIDER, _0010_name, window, 
//				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height), $(_0018_thick));
		s = Messages.build(CTOR_GSLIDER, _0010_name, window, 
				x, y, w, h, $(_0018_thick));
		if(_0065_vert){
			s += Messages.build(MAKE_VERT, _0010_name, "PI/2"); 
		}
		s += super.get_creator(parent, window);		
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);		
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		if(_0060_opaque){
			g.setColor(DBase.jpalette[6]);
			g.fillRect(0, 0, _0130_width, _0131_height);
		}
		int thick, topY, leftX;
		
		thick = Math.round(_0018_thick);
		
		if(_0065_vert){
			leftX =  (_0130_width - thick) /2;
			topY = (_0131_height - thick) /2;
			// Track
			g.setColor(DBase.jpalette[5]);
			g.fillRect(leftX, 2, thick, _0131_height - 4);
		}
		else {
			leftX =  (_0130_width - thick) /2;
			topY = (_0131_height - thick) /2;
			// track
			g.setColor(DBase.jpalette[5]);
			g.fillRect(2, topY, _0130_width - 4, thick);
		}
		// thumb
		g.setColor(DBase.jpalette[0]);
		g.fillRect(leftX, topY, thick, thick);
		
		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(DASHED_EDGE_COLOR);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0130_width, _0131_height);		
		}
		g.setTransform(paf);
	}
	
	protected void read(){
		super.read();
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}
}
