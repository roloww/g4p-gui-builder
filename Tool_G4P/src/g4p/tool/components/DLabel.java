package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DLabel extends DCoreText {

	
	public DLabel(){
		super();
		componentClass = "GLabel";
		set_name(NameGen.instance().getNext("label"));
		_0024_width = 80;
		_0025_height = 20;
		_0015_text = "My label";
		eventHandler_edit = eventHandler_show = false;
	}
	
	/**
	 * There are no events for this control
	 * @return
	 */
	protected String get_event_definition(){
		return null;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GLABEL, _0005_name, "this", 
				_0015_text, _0020_x, _0021_y, _0024_width, _0025_height);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(lblBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(blackEdge);
		g.drawRect(0, 0, _0024_width, _0025_height);
		
		g.drawString(this._0005_name, 4, _0025_height/2 +4 );

		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}


}
