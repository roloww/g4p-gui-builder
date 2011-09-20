package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DHorzSlider extends DSliderInteger {

	public DHorzSlider(){
		super();
		componentClass = "GHorzSlider";
		set_name(NameGen.instance().getNext("slider"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Change"));
		_0024_width = 100;
		_0025_height = 12;
	}
	
	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GHORZSLIDER, _0005_name, "this", 
				_0020_x, _0021_y, _0024_width, _0025_height);
		s += Messages.build(SET_LIMITS,_0005_name, _0040_value, _0041_min, _0042_max);
		s += Messages.build(ADD_HANDLER, _0005_name, "this", _0101_eventHandler);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setColor(sdrBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(sdrThumb);
		g.fillRect(_0024_width/2 - 5, 0, 10, _0025_height);
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}
	

}
