package g4p.tool.components;

import java.awt.Color;
import java.awt.Graphics2D;

import g4p.tool.gui.propertygrid.Validator;

public final class DWindow extends DBase {

	public String _0010_title = "Window title";
	
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
	}

	public String get_title(){
		return _0010_title;
	}

	
	public void draw(Graphics2D g){
		g.setColor(fill);
		g.fillRect(_0020_x, _0021_y, _0024_width, _0025_height);
		g.setColor(fill);
		g.drawRect(_0020_x, _0021_y, _0024_width, _0025_height);
		
		
	}

}
