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
		String x, y, w, h;
		if(_0065_vert){
			x = $(_0120_x + _0130_width);
			y = $(_0121_y);
			w = $(_0131_height);
			h = $(_0130_width);		
		}
		else {
			x = $(_0120_x);
			y = $(_0121_y);
			w = $(_0130_width);
			h = $(_0131_height);
		}
		s = Messages.build(CTOR_GCUSTOMSLIDER, _0010_name, window, 
				x, y, w, h, _0620_skin);
		if(_0065_vert)
			s += Messages.build(MAKE_VERT, _0010_name, "PI/2"); 		
//		s = Messages.build(CTOR_GCUSTOMSLIDER, _0010_name, window,
//				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height), _0620_skin);
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
		
		g.setStroke(stdStroke);
		
		g.setColor(csdrBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(csdrBorder);
		g.drawRect(0, 0, _0130_width, _0131_height);
		
		if(_0065_vert){
			g.setColor(csdrSlideBack);
			g.fillRect((_0130_width - 10)/2, 0, 10, _0131_height);
			g.setColor(csdrSlideBorder);
			g.drawRect((_0130_width - 10)/2, 0, 10, _0131_height);			
			
			g.setColor(csdrThumb);
			g.fillOval(cx - 12, cy - 5, 24, 10);
			g.setColor(csdrSlideBorder);
			g.drawOval(cx - 12, cy - 5, 24, 10);
		}
		else {
			g.setColor(csdrSlideBack);
			g.fillRect(0, (_0131_height - 6)/2, _0130_width, 6);
			g.setColor(csdrSlideBorder);
			g.drawRect(0, (_0131_height - 6)/2, _0130_width, 6);
			
			g.setColor(csdrThumb);
			g.fillOval(cx - 5, cy - 12, 10, 24);
			g.setColor(csdrSlideBorder);
			g.drawOval(cx - 5, cy - 12, 10, 24);
		}
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}
	
	protected void read(){
		super.read();
		skin_editor = new EditorJComboBox(SLIDER_SKIN);		
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}
}
