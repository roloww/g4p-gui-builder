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
		text_label = "Option display text";
		componentClass = "GOption";
		set_name(NameGen.instance().getNext("option"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Clicked"));
		_0015_text = "Option text";
	}
	
	public String get_event_header(){
		return Messages.build(METHOD_START_2, _0101_eventHandler, componentClass, "opt_selected", "opt_deselected", _0005_name, id.toString()).replace('[', '{');
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	public String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GOPTION, _0005_name, "this", 
				_0015_text, _0020_x, _0021_y, _0024_width);
		s += Messages.build(ADD_OPTION, parent._0005_name, _0005_name);
		if(_0050_selected)
			s += Messages.build(SEL_OPTION, _0005_name, "true");
		s += Messages.build(ADD_HANDLER, _0005_name, "this", _0101_eventHandler);		
		return s;
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
