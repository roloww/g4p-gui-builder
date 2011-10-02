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
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		_0020_text = "Option text";
	}
	
	protected String get_event_header(){
		return Messages.build(METHOD_START_2, _0701_eventHandler, componentClass, "opt_selected", "opt_deselected", _0010_name, id[0].toString()).replace('[', '{');
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GOPTION, _0010_name, "this", 
				_0020_text, _0120_x, _0121_y, _0130_width);
		s += Messages.build(ADD_OPTION, parent._0010_name, _0010_name);
		if(_0685_selected)
			s += Messages.build(SEL_OPTION, _0010_name, "true");
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);		
		return s;
	}


	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(optBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(optEdge);
		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.setColor(optFill);
		int top = (_0131_height - DOT_EDGE)/2;
		g.fillOval(3, top, DOT_EDGE, DOT_EDGE);
		g.setColor(blackEdge);
		g.drawOval(3, top, DOT_EDGE, DOT_EDGE);
		if(_0685_selected){
			int offset = (DOT_EDGE - DOT_SOLID)/2;
			g.setColor(optDot);
			g.fillOval(3 + offset, top + offset, DOT_SOLID, DOT_SOLID);
		}
		g.setColor(blackEdge);
		g.drawString(_0010_name, 20, _0131_height/2 +4 );
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	
	public void selectionChanged(){
		// test to see if this object has been selected
		// and if so de-select all then select this
		DOptionGroup optg = (DOptionGroup) this.getParent();
		Enumeration<?> e = optg.children();
		while(e.hasMoreElements()){
			((DOption)e.nextElement()).setSelected(false);
		}
		_0685_selected = true;
	}
}
