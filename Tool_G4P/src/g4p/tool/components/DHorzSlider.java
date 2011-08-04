package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class DHorzSlider extends DSliderInteger {

	public DHorzSlider(){
		super();
		set_name(NameGen.instance().getNext("label"));
		_0024_width = 100;
		_0025_height = 12;
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
//		g.setStroke(stdStroke);
		g.setColor(sdrBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(sdrThumb);
		g.fillRect(_0024_width/2 - 5, 0, 10, _0025_height);
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	
}
