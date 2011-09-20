package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DButton extends DCoreText {

	transient protected RectangularShape face;
	transient float mitre = 6.0f;

	public DButton(){
		super();
		componentClass = "GButton";
		set_name(NameGen.instance().getNext("button"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		_0015_text = "Button face text";
		_0024_width = 80;
		_0025_height = 20;
		text_tooltip = "text to show on button";
		face = new RoundRectangle2D.Float(0, 0, _0024_width, _0025_height, mitre, mitre);
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);

		((RoundRectangle2D) face).setRoundRect(0, 0, _0024_width, _0025_height, mitre, mitre);	
		g.setStroke(stdStroke);
		g.setColor(btnBack);
		g.fill(face);			
		g.setColor(blackEdge);
		g.draw(face);
		g.drawString(this._0005_name, 6, _0025_height/2 +4 );
		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GBUTTON_1, _0005_name, "this", 
				_0015_text, _0020_x, _0021_y, _0024_width, _0025_height);
		s += Messages.build(ADD_HANDLER, _0005_name, "this", _0101_eventHandler);
		return s;
	}
	

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		mitre = 6.0f;
		face = new RoundRectangle2D.Float(0, 0, _0024_width, _0025_height, mitre, mitre);
	}
}
