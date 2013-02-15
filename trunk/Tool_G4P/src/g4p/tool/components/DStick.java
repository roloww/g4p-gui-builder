package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

import processing.core.PApplet;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p_controls.G4P;

public class DStick extends DBase {

	protected static final float RAD90 = PApplet.radians(90);
	protected static final float RAD45 = PApplet.radians(45);

	protected int mode = G4P.X4;
 
	public String 		_0930_stick_mode = "X4";
	transient public 	EditorBase stick_mode_editor = new EditorJComboBox(STICK_TYPE);
	public Boolean 		stick_mode_edit = true;
	public Boolean 		stick_mode_show = true;
	public String 		stick_mode_label = "Mode (4 or 8 position?";
	public String 		stick_mode_updater = "stickModeChange";


	public DStick(){
		super();
		componentClass = "GStick";
		set_name(NameGen.instance().getNext("stick"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_change"));
		_0826_width = 60;
		_0827_height = 60;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GSTICK, _0010_name, window, $(_0820_x), $(_0821_y), $(_0826_width), $(_0827_height));
		s += Messages.build(SET_STICK_MODE, _0010_name, _0930_stick_mode);
		s += super.get_creator(parent, window);		
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0020_eventHandler);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0820_x, _0821_y);
		g.setTransform(af);
		
		if(_0600_opaque){
			g.setColor(DBase.jpalette[6]);
			g.fillRect(0, 0, _0826_width, _0827_height);
		}
		af.translate(_0826_width/2, _0827_height/2);
		g.setTransform(af);

		int s = Math.min(_0826_width, _0827_height);
		float mag = s/50.0f;
		int ledWidth = Math.round(6 * mag);
		int ledHeight = Math.round(1.6f * ledWidth);
		int ledRingRad = Math.round((s - ledWidth - 3)/2.0f);
		int actionRad =  Math.round(ledRingRad/2.0f);
		int gripRad = Math.round(4.0f * mag);
		

		// Outer ring and surface
		g.setStroke(selStroke);
		g.setColor(DBase.jpalette[6]);
		g.fillOval(-ledRingRad, -ledRingRad, 2*ledRingRad, 2*ledRingRad);
		g.setColor(DBase.jpalette[1]);
		g.drawOval(-ledRingRad, -ledRingRad, 2*ledRingRad, 2*ledRingRad);

		AffineTransform af2 = new AffineTransform(af);
		g.setTransform(af2);
		
		int delta = 2/mode;
		for(int i = 0; i < 8; i++){
			g.setStroke(selStroke);
			g.setColor(DBase.jpalette[1]);
			g.drawLine(0, 0, ledRingRad, 0);
			if(i%2 == 0){
				g.setColor(DBase.jpalette[0]);
				g.fillOval(ledRingRad - ledWidth/2, -ledHeight/2, ledWidth, ledHeight);
			}
			af2.rotate(RAD45 * delta);
			g.setTransform(af2);
		}
		g.setTransform(af);

		// Inner ring and surface
		g.setColor(DBase.jpalette[5]);
		g.fillOval(-actionRad, -actionRad, 2*actionRad, 2*actionRad);
		g.setColor(DBase.jpalette[0]);
		g.drawOval(-actionRad, -actionRad, 2*actionRad, 2*actionRad);

		// Grip
		g.setColor(DBase.jpalette[3]);
		g.fillOval(-gripRad, -gripRad, 2*gripRad, 2*gripRad);
		g.setColor(DBase.jpalette[1]);
		g.drawOval(-gripRad, -gripRad, 2*gripRad, 2*gripRad);

		af.translate(-_0826_width/2, -_0827_height/2);
		g.setTransform(af);

		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	public void stickModeChange(){
		if(_0930_stick_mode.equals("X4"))
			mode = G4P.X4;
		else
			mode = G4P.X8;
	}
	
	protected void read(){
		super.read();
		stick_mode_editor = new EditorJComboBox(STICK_TYPE);	
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}


}
