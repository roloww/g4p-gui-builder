package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Enumeration;

@SuppressWarnings("serial")
public final class DWindow extends DBase {

	public boolean 		mainSketch = false;
	
	public String 		_0010_title = "";
	public String 		title_label = "Title text";
	
	public String 		_0011_renderer = "JAVA2D";
	public String 		renderer_label = "Renderer";
	transient public CellEditor_Base renderer_editor = new CellEditor_JComboBox(RENDERER);
	public Boolean 		renderer_edit = false;
	public Boolean 		renderer_show = false;

	
	public int 			_0014_Display_scale = 100;
	public String		Display_scale_label = "Scale (%)";
	public Boolean 		Display_scale_edit = true;
	public Boolean 		Display_scale_show = true;
	public Validator 	Display_scale_validator = Validator.getValidator(int.class, 10, 300);
	
	/**
	 * Create a Window object
	 * @param mainSketch true if main sketch
	 */
	public DWindow(boolean mainSketch) {
		super();
		selectable = true;
		resizeable = true;
		moveable = false;
		allowsChildren = true;
		componentClass = "GWindow";
		this.mainSketch = mainSketch;
		
		if(mainSketch){
			_0005_name = "Main window";
			name_label = "SKETCH";
			_0024_width = 480;
			_0025_height = 320;
			
			_0010_title = "My sketch title";
		}
		else {
			set_name(NameGen.instance().getNext("window"));
			name_label = "Variable name";
			name_edit = true;
			x_edit = y_edit = true;
			x_show = y_show = true;	
			_0024_width = 240;
			_0025_height = 120;
			
			_0010_title = "My window title";
			renderer_edit = renderer_show = true;
		}
		_0010_title = "Frame title text";
		width_edit = height_edit = true;
		width_show = height_show = true;
	}

    @Override
 	public String get_title(){
		return _0010_title;
	}

	/**
	 * Get the declaration for this control
	 */
	public String get_declaration(){
		if(mainSketch)
			return null;
		else
			return componentClass + " " + _0005_name+ ";\n";
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	public String get_create_code(){
		if(mainSketch)
			return null;
		else
			return Messages.build("", componentClass + " " + _0005_name);
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
//		NameGen.instance().add(_0101_eventHandler);
//		IdGen.instance().add(id);
		renderer_editor = new CellEditor_JComboBox(RENDERER);
	}


    @Override
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(winBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(greenEdge);
		g.drawRect(0, 0, _0024_width, _0025_height);
		if(this == selected)
			drawSelector(g);
		
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af, selected);
		}
		g.setTransform(paf);
	}

}
