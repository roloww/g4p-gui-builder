package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DTextField extends DTextIcon {

	public Boolean 		_0030_multiline = false;
	public Boolean 		multiline_edit = true;
	public Boolean 		multiline_show = true;

	public DTextField(){
		super();
		componentClass = "GTextField";
		set_name(NameGen.instance().getNext("textfield"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Enter"));
		_0029_text = "Some text";
		text_tooltip = "initial text to display";
		_0130_width = 80;
		_0131_height = 20;
	}
	
	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GTEXTFIELD, _0010_name, window, 
				_0029_text, _0120_x, _0121_y, _0130_width, _0131_height, _0030_multiline);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(txfBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(blackEdge);
		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.drawString(this._0010_name, 4, 12 );

		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

}
