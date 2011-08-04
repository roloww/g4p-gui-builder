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
		_0024_width = 80;
		_0025_height = 20;
		face = new RoundRectangle2D.Float(0, 0, _0024_width, _0025_height, mitre, mitre);
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		((RoundRectangle2D) face).setRoundRect(0, 0, _0024_width, _0025_height, mitre, mitre);	
		g.setStroke(stdStroke);
		g.setColor(back100);
		g.fill(face);			
		g.setColor(blackEdge);
		g.draw(face);
		g.drawString(this._0005_name, 6, _0025_height/2 +4 );
		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

}
