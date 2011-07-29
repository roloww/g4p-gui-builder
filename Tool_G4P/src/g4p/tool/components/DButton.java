package g4p.tool.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

public class DButton extends DCoreText {
	
	public DButton(){
		super();
		set_name(NameGen.instance().getNext("button"));
	}
	
	public void draw(Graphics2D g, AffineTransform paf){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(bs);
		g.setColor(Color.white);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(Color.black);
		g.drawRect(0, 0, _0024_width, _0025_height);

		g.setTransform(paf);
	}

}
