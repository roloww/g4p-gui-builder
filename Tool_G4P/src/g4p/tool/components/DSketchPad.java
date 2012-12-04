package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.ToolImage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;


public class DSketchPad extends DBase {

	transient public BufferedImage icon;
	
	public DSketchPad(){
		super();
		componentClass = "GSketchPad";
		set_name(NameGen.instance().getNext("sketchPad"));
		_0130_width = 80;
		_0131_height = 60;
		eventHandler_edit = eventHandler_show = false;
		icon = ToolImage.getImage("SPAD_ICON");
	}
	
	/**
	 * There are no events for this control
	 * @return
	 */
	protected String get_event_definition(){
		return "";
	}
	
	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s = "";
		s = Messages.build(CTOR_SPAD, _0010_name, window, 
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
		s += super.get_creator(parent, window);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setColor(DBase.jpalette[6]);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.drawImage(icon, 0, 0, _0130_width, _0131_height, null);
		g.setStroke(stdStroke);

		super.draw(g, paf, selected);
		
		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(dashedEdge);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0130_width, _0131_height);		
		}
		g.setTransform(paf);
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		icon = ToolImage.getImage("SPAD_ICON");
	}

}
