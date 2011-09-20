package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.CellEditor_Base;
import g4p.tool.gui.propertygrid.CellEditor_JComboBox;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
	
	public String 		_0060_wdraw = "";
	public String 		wdraw_label = "Draw method Name";
	public String 		wdraw_tooltip = "The draw() method for this window";
	public Boolean 		wdraw_edit = true;
	public Boolean 		wdraw_show = true;
	public Validator 	wdraw_validator = Validator.getValidator(COMPONENT_NAME_0);

//	public String 		_0062_wmouse = "";
//	public String 		wmouse_label = "Mouse method Name";
//	public String 		wmouse_tooltip = "The draw() method for this window";
//	public Boolean 		wmouse_edit = true;
//	public Boolean 		wmouse_show = true;
//	public Validator 	wmouse_validator = Validator.getValidator(COMPONENT_NAME_0);

	
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
			wdraw_edit = wdraw_show = false;
//			wmouse_edit = wmouse_show = false;
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

	protected String get_event_definition(){
		StringBuilder sb = new StringBuilder();
		
		if(_0060_wdraw.length() > 0){
			sb.append(Messages.build(WIN_DRAW, _0060_wdraw, _0005_name, id.toString()).replace('[', '{'));  // event header
			sb.append(get_draw_event_code() + get_event_end());
		}
//		if(_0062_wmouse.length() > 0){
//			sb.append(Messages.build(WIN_MOUSE, _0062_wmouse, _0005_name, id.toString()).replace('[', '{'));  // event header
//			sb.append(get_mouse_event_code() + get_event_end());
//		}
		return new String(sb);
	}

	/**
	 * Get the event code if none then return generic message
	 * @param code
	 * @return
	 */
	protected String get_draw_event_code(){ 
		String ev_code = Code.instance().get(id);
		if(ev_code == null)
			return CODE_GWINDOW_DRAW;
		else
			return ev_code; 
	}

	protected String get_mouse_event_code(){ 
		String ev_code = Code.instance().get(id);
		if(ev_code == null)
			return Messages.build(CODE_GWINDOW_MOUSE, _0005_name, componentClass);
		else
			return ev_code; 
	}

	/**
	 * Get the event method end with tag
	 * @return
	 */
	protected String get_event_end(){
		return Messages.build(METHOD_END, _0005_name, 
				id.toString()).replace(']', '}');
	}
	
	/**
	 * Get the declaration for this window
	 */
	protected String get_declaration(){
		if(mainSketch)
			return null;
		else
			return componentClass + " " + _0005_name+ ";\n";
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		if(mainSketch)
			return null;
		else {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.build(CTOR_WINDOW_1, _0005_name, "this", _0010_title, _0020_x, _0021_y, _0024_width, _0025_height, false, _0011_renderer));
			if(_0060_wdraw.length() > 0){
				sb.append(Messages.build(ADD_DRAW_HANDLER, _0005_name, "this", _0060_wdraw));
			}
//			if(_0062_wmouse.length() > 0){
//				sb.append(Messages.build(ADD_MOUSE_HANDLER, _0005_name, "this", _0062_wmouse));
//			}
			return new String(sb);
		}
	}
	
	// recursive method
	public void make_creator(ArrayList<String> lines, DBase parent){
		DBase comp;
		Enumeration<?> e;
		String ccode = get_creator(parent);
		if(ccode != null && !ccode.equals(""))
			lines.add(ccode);
		if(allowsChildren){
			e = children();
			while(e.hasMoreElements()){
				comp = (DBase)e.nextElement();
				if(mainSketch)
					comp.make_creator(lines, null);
				else
					comp.make_creator(lines, this);
			}
			if(!mainSketch){
				e = children();
				while(e.hasMoreElements()){
					comp = (DBase)e.nextElement();
					if( !(comp instanceof DOptionGroup) )	{
						lines.add(Messages.build(ADD_A_CHILD, _0005_name, comp._0005_name));
					}
				}
			}
		}				
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
		renderer_editor = new CellEditor_JComboBox(RENDERER);
	}


    @Override
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
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
