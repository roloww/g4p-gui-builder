package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;

@SuppressWarnings("serial")
public class DCustomSlider extends DLinearTrack {

	public String _0620_skin = "grey_blue";
	transient public EditorBase skin_editor = new EditorJComboBox(SLIDER_SKIN);
	public Boolean skin_edit = true;
	public Boolean skin_show = true;
	public String skin_label = "Slider skin";
	
	
	public DCustomSlider(){
		super();
		componentClass = "GCustomSlider";
		set_name(NameGen.instance().getNext("custom_slider"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_change"));
		_0130_width = 100;
		height_show = height_edit = false;
		_0131_height = 40;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GCUSTOMSLIDER, _0010_name, window,
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height), _0620_skin);
		s += super.get_creator(parent, window);		
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);
		return s;
	}

	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		int cx = _0130_width/2;
		int cy = _0131_height/2;
		
		g.setColor(csdrBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(csdrBorder);
		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.setColor(csdrSlideBack);
		g.fillRect(0, (_0131_height - 6)/2, _0130_width, 6);
		g.setColor(csdrSlideBorder);
		g.drawRect(0, (_0131_height - 6)/2, _0130_width, 6);
		
		
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
		NameGen.instance().add(_0010_name);
		NameGen.instance().add(_0012_eventHandler);
		IdGen.instance().add(id[0]);
		skin_editor = new EditorJComboBox(SLIDER_SKIN);
	}
}
