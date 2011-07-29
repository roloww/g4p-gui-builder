package g4p.tool.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

import g4p.tool.gui.propertygrid.Validator;

public final class DWindow extends DBase {

	public String 		_0010_title = "Window title";
	
	public int 			_0014_Display_scale = 100;
	public Boolean 		Display_scale_edit = true;
	public Boolean 		Display_scale_show = true;
	public Validator 	Display_scale_validator = Validator.getValidator(int.class, 10, 200);
	
	private float scale;
	
	/**
	 * Create a Window object
	 * @param mainSketch true if main sketch
	 */
	public DWindow(boolean mainSketch) {
		super();
		allowsChildren = true;
		if(mainSketch){
			_0005_name = "Sketch";
			_0024_width = 800;
			_0025_height = 600;
			_0010_title = "Main window title";
		}
		else {
			set_name(NameGen.instance().getNext("window"));
			name_edit = true;
			x_edit = y_edit = true;
			x_show = y_show = true;	
			_0024_width = 200;
			_0025_height = 120;
			_0010_title = "Secondary window title";
		}
		width_edit = height_edit = true;
		width_show = height_show = true;
		
		stroke = new Color(128,128,64);
		fill = new Color(255,255,128);
//		System.out.println("\tDWindow() ctor");
	}

	public String get_title(){
		return _0010_title;
	}

	
	public void draw(Graphics2D g, AffineTransform paf, float scale){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		af.scale(scale,scale);
		g.setTransform(af);
		g.setStroke(bs);
		
		g.setColor(fill);
		g.fillRect(_0020_x, _0021_y, _0024_width, _0025_height);
		g.setColor(stroke);
		g.drawRect(_0020_x, _0021_y, _0024_width, _0025_height);
		
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af, scale);
		}

		g.setTransform(paf); // popMatrix
	}

}
