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
		_0029_text = "Checkbox text";
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GCHECKBOX, _0010_name, window, 
				_0029_text, $(_0120_x), $(_0121_y), $(_0130_width));
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
		g.setColor(cboxBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(cboxEdge);
		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.setColor(cboxFill);
		int markLeft = 2, markTop = (_0131_height - BOXSIZE)/2;
		g.fillRect(markLeft, markTop, BOXSIZE, BOXSIZE);
		g.setColor(blackEdge);
		g.drawRect(markLeft, markTop, BOXSIZE, BOXSIZE);

		if(_0685_selected){
			g.setColor(optDot);
			g.setStroke(needleStroke);
			int markBottom = markTop + BOXSIZE;
			int markRight = markLeft + BOXSIZE; 
			g.drawLine(markLeft + BOXINSET, markBottom - 2 * BOXINSET, markLeft + 2 * BOXINSET, markBottom - BOXINSET);
			g.drawLine(markLeft + 2 * BOXINSET, markBottom - BOXINSET, markRight - BOXINSET, markTop + BOXINSET);
		}
		
		g.setColor(blackEdge);
		
		g.drawString(_0010_name, 20, _0131_height/2 +4 );

		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

}
