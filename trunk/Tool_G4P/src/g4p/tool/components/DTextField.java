package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class DTextField extends DCoreText {

	public Boolean 		_0030_multiline = false;
	public Boolean 		multiline_edit = true;
	public Boolean 		multiline_show = true;

	public DTextField(){
		super();
		componentClass = "GTextField";
		set_name(NameGen.instance().getNext("textfield"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Enter"));
		_0015_text = "Some text";
		text_tooltip = "initial text to display";
		_0024_width = 80;
		_0025_height = 20;
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(txfBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(blackEdge);
		g.drawRect(0, 0, _0024_width, _0025_height);
		
		g.drawString(this._0005_name, 4, 12 );

		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

}
