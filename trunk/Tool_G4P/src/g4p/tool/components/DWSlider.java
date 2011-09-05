package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;

@SuppressWarnings("serial")
public class DWSlider extends DSliderFloat {

	public String _0030_Slider_skin = "gwSlider";
	transient public CellEditor_Base Slider_skin_editor = new CellEditor_JComboBox(SLIDER_SKIN);
	public Boolean Slider_skin_edit = true;
	public Boolean Slider_skin_show = true;

	public DWSlider(){
		super();
		componentClass = "GWSlider";
		set_name(NameGen.instance().getNext("cool_slider"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Change"));
		_0024_width = 100;
		_0025_height = 40;
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
		Slider_skin_editor = new CellEditor_JComboBox(SLIDER_SKIN);
	}


//  public final Color csdrBack			= new Color(0,0,0, 32);
//  public final Color csdrBorder		= new Color(0,0,0, 64);
//  public final Color csdrSlideBack	= new Color(200);
//  public final Color csdrSlideBorder	= new Color(32);
//  public final Color csdrThumb		= new Color(255,255,0);

}
