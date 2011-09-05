package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

@SuppressWarnings("serial")
public class DOption extends DCoreSelectable {

	public String selected_updater = "selectionChanged";

	public DOption(){
		super();
		componentClass = "GOption";
		set_name(NameGen.instance().getNext("option"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Clicked"));
	}
	
	public String get_event_header(){
		String pname = componentClass.substring(1).toLowerCase();
		return Messages.build(METHOD_START_2, _0101_eventHandler, componentClass, pname + "1", pname + "2", _0005_name, id.toString()).replace('[', '{');
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
		int top = (_0025_height - DOT_EDGE)/2;
		g.fillOval(3, top, DOT_EDGE, DOT_EDGE);
		g.setColor(blackEdge);
		g.drawOval(3, top, DOT_EDGE, DOT_EDGE);

		if(_0050_selected){
			int offset = (DOT_EDGE - DOT_SOLID)/2;
			g.setColor(optDot);
			g.fillOval(3 + offset, top + offset, DOT_SOLID, DOT_SOLID);
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
