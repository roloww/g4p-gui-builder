package g4p.tool.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.Enumeration;

public class DButton extends DCoreText {
	
	transient protected RectangularShape face;
	transient float mitre = 10.0f;
	
	public DButton(){
		super();
		set_name(NameGen.instance().getNext("button"));
		fill = new Color(100,100,255);
		stroke = Color.black;
		face = new RoundRectangle2D.Float(0, 0, 10, 10, 2, 2);
	}
	
	public void draw(Graphics2D g, AffineTransform paf){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		((RoundRectangle2D) face).setRoundRect(0, 0, _0024_width, _0025_height, mitre, mitre);	
		g.setStroke(bs);
		g.setColor(fill);
		g.fill(face);			
		g.setColor(stroke);
		g.draw(face);			

		g.setTransform(paf);
	}

}
