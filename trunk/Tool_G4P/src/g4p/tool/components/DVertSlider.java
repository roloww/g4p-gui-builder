package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DVertSlider extends DSliderInteger {

	public DVertSlider(){
		super();
		set_name(NameGen.instance().getNext("slider"));
		_0024_width = 12;
		_0025_height = 100;
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);

		g.setColor(sdrBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(sdrThumb);
		g.fillRect(0, _0025_height/2 - 5, _0024_width, 10);
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

}
