package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DCheckbox extends DCoreSelectable{

	
	public DCheckbox(){
		super();
		componentClass = "GCheckbox";
		text_label = "Checkbox display text";
		set_name(NameGen.instance().getNext("checkbox"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Clicked"));
		_0015_text = "Checkbox text";
	}

//	public String get_event_header(){
//		return Messages.build(METHOD_START_1, _0101_eventHandler, componentClass, "checkBox", _0005_name, id.toString()).replace('[', '{');
//	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	public String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GCHECKBOX, _0005_name, "this", 
				_0015_text, _0020_x, _0021_y, _0024_width);
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
		g.setColor(cboxBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(cboxEdge);
		g.drawRect(0, 0, _0024_width, _0025_height);
		
		g.setColor(cboxFill);
		int markLeft = 2, markTop = (_0025_height - BOXSIZE)/2;
		g.fillRect(markLeft, markTop, BOXSIZE, BOXSIZE);
		g.setColor(blackEdge);
		g.drawRect(markLeft, markTop, BOXSIZE, BOXSIZE);

		if(_0050_selected){
			g.setColor(optDot);
			g.setStroke(needleStroke);
			int markBottom = markTop + BOXSIZE;
			int markRight = markLeft + BOXSIZE; 
			g.drawLine(markLeft + BOXINSET, markBottom - 2 * BOXINSET, markLeft + 2 * BOXINSET, markBottom - BOXINSET);
			g.drawLine(markLeft + 2 * BOXINSET, markBottom - BOXINSET, markRight - BOXINSET, markTop + BOXINSET);
		}
		
		g.setColor(blackEdge);
		
		g.drawString(_0005_name, 20, _0025_height/2 +4 );

		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

}
