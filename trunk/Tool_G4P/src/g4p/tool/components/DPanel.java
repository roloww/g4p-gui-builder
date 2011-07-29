package g4p.tool.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

public class DPanel extends DCoreText {

	protected Color fillTitle;
	
	public DPanel(){
		super();
		allowsChildren = true;
		set_name(NameGen.instance().getNext("panel"));
		this._0020_x = 25;
		this._0021_y = 80;
		this._0024_width = 100;
		this._0025_height = 60;
//		System.out.println("ctor DPanel()   " + _0005_name);
		fill = new Color(100,100,255);
		fillTitle = new Color(50,50,255);
	}

	
	public void draw(Graphics2D g, AffineTransform paf){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(bs);
		g.setColor(fill);
		g.fillRect(_0020_x, _0021_y, _0024_width, _0025_height);
		g.setColor(fillTitle);
		g.fillRect(_0020_x, _0021_y, _0024_width, 10);
		
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af);
		}

		g.setTransform(paf); // popMatrix
	}


}
