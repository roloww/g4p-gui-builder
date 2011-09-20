package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;

@SuppressWarnings("serial")
public class DWSlider extends DSliderFloat {

	public String _0030_skin = "gwSlider";
	transient public CellEditor_Base skin_editor = new CellEditor_JComboBox(SLIDER_SKIN);
	public Boolean skin_edit = true;
	public Boolean skin_show = true;
	public String skin_label = "Style";
	
	public DWSlider(){
		super();
		componentClass = "GWSlider";
		set_name(NameGen.instance().getNext("cool_slider"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Change"));
		_0024_width = 100;
		height_show = height_edit = false;
		_0025_height = 40;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GWSLIDER, _0005_name, "this", _0030_skin,
				_0020_x, _0021_y, _0024_width);
		s += Messages.build(SET_F_LIMITS,_0005_name, _0040_value, _0041_min, _0042_max);
		s += Messages.build(ADD_HANDLER, _0005_name, "this", _0101_eventHandler);
		return s;
	}

	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		int cx = _0024_width/2;
		int cy = _0025_height/2;
		
		g.setColor(csdrBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(csdrBorder);
		g.drawRect(0, 0, _0024_width, _0025_height);
		
		g.setColor(csdrSlideBack);
		g.fillRect(0, (_0025_height - 6)/2, _0024_width, 6);
		g.setColor(csdrSlideBorder);
		g.drawRect(0, (_0025_height - 6)/2, _0024_width, 6);
		
		
		g.setColor(csdrThumb);
		g.fillOval(cx - 3, cy - 8, 6, 16);
		g.setColor(csdrSlideBorder);
		g.drawOval(cx - 3, cy - 8, 6, 16);
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
		NameGen.instance().add(_0101_eventHandler);
		IdGen.instance().add(id);
		skin_editor = new CellEditor_JComboBox(SLIDER_SKIN);
	}
}
