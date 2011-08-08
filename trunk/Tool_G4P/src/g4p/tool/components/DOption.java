package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

public class DOption extends DCoreSelectable {

	public String selected_updater = "selectionChanged";

	public DOption(){
		super();
		set_name(NameGen.instance().getNext("option"));
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(optBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(optEdge);
		g.drawRect(0, 0, _0024_width, _0025_height);
		
		g.setColor(optFill);
		g.fillOval(3, (_0025_height - DOTSIZE)/2, DOTSIZE, DOTSIZE);
		g.setColor(blackEdge);
		g.drawOval(3, (_0025_height - DOTSIZE)/2, DOTSIZE, DOTSIZE);

		if(_0050_selected){
			g.setColor(optDot);
			g.fillOval(10, (_0025_height - DOTSIZE/2)/2, DOTSIZE/2, DOTSIZE/2);
		}
		
		g.setColor(blackEdge);
		
		g.drawString(_0005_name, 20, _0025_height/2 +4 );

		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	public void selectionChanged(){
		// test to see if this object has been selected
		// and if so deselect all then select this
		DOptionGroup optg = (DOptionGroup) this.getParent();
		Enumeration<?> e = optg.children();
		while(e.hasMoreElements()){
			((DOption)e.nextElement()).setSelected(false);
		}
		_0050_selected = true;
	}
}
