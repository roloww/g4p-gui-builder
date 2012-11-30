package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DActivityBar extends DText {

	transient protected RectangularShape face;

	public String width_updater = "widthChanged";
	public String height_updater = "heightChanged";

	public DActivityBar(){
		super();

		componentClass = "GActivityBar";
		set_name(NameGen.instance().getNext("activityBar"));

		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		_0130_width = 64;
		_0131_height = 16;
		width_tooltip = ">= 4 * height";
		height_tooltip = ">= 10";
		width_validator = Validator.getValidator(int.class, 40, 9999);
		height_validator = Validator.getValidator(int.class, 10, 9999);
		eventHandler_edit = eventHandler_show = false;
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, 6, 6);
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String	s = Messages.build(CTOR_GACTIVITYBAR, _0010_name, window,
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
		return s;
	}

	/**
	 * This class has no events
	 */
	protected String get_event_definition(){
		return null;
	}

	public void widthChanged(){
		_0130_width = Math.max(_0130_width, 4 * _0131_height);
		if(_0130_width < 4 * _0131_height){
			_0130_width = 4 * _0131_height;
			propertyModel.hasBeenChanged();
		}
	}

	public void heightChanged(){
		int height = _0131_height;
		int width = _0130_width;
		if(height % 2 == 1)
			height++;
		if(height < 10)
			height = 10;
		if(width < 4 * height)
			width = 4 * height;
		if(width != _0130_width || height != _0131_height){
			_0130_width = width;
			_0131_height = height;
			propertyModel.hasBeenChanged();
		}
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);

		((RoundRectangle2D) face).setRoundRect(0, 0, _0130_width, _0131_height, 6, 6);	
		g.setColor(actBarBack);
		g.fill(face);	
		int rad = _0131_height - 2;
		int sx = (_0130_width - rad)/2;
		int dx = Math.min(4, _0131_height/3);
		g.setColor(actBar2);
		g.fillOval(sx + dx, 1, rad, rad);
		g.setColor(actBar1);
		g.fillOval(sx, 1, rad, rad);
		g.setColor(actBar0);
		g.fillOval(sx - dx, 1, rad, rad);
		g.setStroke(stdStroke);
		g.setColor(blackEdge);
		g.draw(face);

		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, 6, 6);
	}

}
