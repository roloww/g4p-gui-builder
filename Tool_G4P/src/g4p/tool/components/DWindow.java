package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
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
	
	public String 		_0015_title = "";
	public String 		title_label = "Title text";
	
	public String 		_0021_renderer = "JAVA2D";
	public String 		renderer_label = "Renderer";
	transient public EditorBase renderer_editor = new EditorJComboBox(RENDERER);
	public Boolean 		renderer_edit = false;
	public Boolean 		renderer_show = false;

	public int 			_0025_Display_scale = 100;
	public String		Display_scale_label = "Scale (%)";
	public Boolean 		Display_scale_edit = true;
	public Boolean 		Display_scale_show = true;
	public Validator 	Display_scale_validator = Validator.getValidator(int.class, 10, 300);
	
	public String 		_0750_wdraw = "";
	public String 		wdraw_label = "Draw method";
	public String 		wdraw_tooltip = "The draw() method for this window";
	public Boolean 		wdraw_edit = true;
	public Boolean 		wdraw_show = true;
	public Validator 	wdraw_validator = Validator.getValidator(COMPONENT_NAME_0);

	public String 		_0751_wmouse = "";
	public String 		wmouse_label = "Mouse method";
	public String 		wmouse_tooltip = "The mouseEvent() method for this window";
	public Boolean 		wmouse_edit = true;
	public Boolean 		wmouse_show = true;
	public Validator 	wmouse_validator = Validator.getValidator(COMPONENT_NAME_0);

	public String 		_0752_wpre = "";
	public String 		wpre_label = "Pre method";
	public String 		wpre_tooltip = "The pre() method for this window";
	public Boolean 		wpre_edit = true;
	public Boolean 		wpre_show = true;
	public Validator 	wpre_validator = Validator.getValidator(COMPONENT_NAME_0);

	public String 		_0753_wpost = "";
	public String 		wpost_label = "Post method";
	public String 		wpost_tooltip = "The post() method for this window";
	public Boolean 		wpost_edit = true;
	public Boolean 		wpost_show = true;
	public Validator 	wpost_validator = Validator.getValidator(COMPONENT_NAME_0);

	
	/**
	 * Create a Window object
	 * @param mainSketch true if main sketch
	 */
	public DWindow(boolean mainSketch) {
		super();
		id = new Integer[4];
		for(int i = 0; i < id.length; i++)
			id[i] = IdGen.instance().getNext();

		selectable = true;
		resizeable = true;
		moveable = false;
		allowsChildren = true;
		componentClass = "GWindow";
		this.mainSketch = mainSketch;
		
		if(mainSketch){
			_0010_name = "Main window";
			name_label = "SKETCH";
			_0130_width = 480;
			_0131_height = 320;
			_0015_title = "My sketch title";
			wdraw_edit = wdraw_show = false;
			wmouse_edit = wmouse_show = false;
			wpre_edit = wpre_show = false;
			wpost_edit = wpost_show = false;
		}
		else {
			set_name(NameGen.instance().getNext("window"));
			name_label = "Variable name";
			name_edit = true;
			x_edit = y_edit = true;
			x_show = y_show = true;	
			_0130_width = 240;
			_0131_height = 120;
			
			_0015_title = "My window title";
			renderer_edit = renderer_show = true;
		}
		_0015_title = "Frame title text";
		width_edit = height_edit = true;
		width_show = height_show = true;
	}

	protected String get_event_definition(){
		StringBuilder sb = new StringBuilder();
		
		if(_0750_wdraw.length() > 0){
			sb.append(Messages.build(WIN_DRAW, _0750_wdraw, _0010_name, $(id[0])).replace('[', '{'));  // event header
			sb.append(get_event_code(0) + get_event_end(0));
		}
		if(_0751_wmouse.length() > 0){
			sb.append(Messages.build(WIN_MOUSE, _0751_wmouse, _0010_name, $(id[1])).replace('[', '{'));  // event header
			sb.append(get_event_code(1) + get_event_end(1));
		}
		if(_0752_wpre.length() > 0){
			sb.append(Messages.build(WIN_PRE, _0752_wpre, _0010_name, $(id[2])).replace('[', '{'));  // event header
			sb.append(get_event_code(2) + get_event_end(2));
		}
		if(_0753_wpost.length() > 0){
			sb.append(Messages.build(WIN_POST, _0753_wpost, _0010_name, $(id[3])).replace('[', '{'));  // event header
			sb.append(get_event_code(3) + get_event_end(3));
		}
		return new String(sb);
	}

	/**
	 * Get the event code if none then return generic message
	 * @param n 0-3 depending on method
	 * @return
	 */
	protected String get_event_code(int n){
		String ev_code = Code.instance().get(id[n]);
		if(ev_code == null){
			switch(n){
			case 0:
				ev_code = CODE_GWINDOW_DRAW;
				break;
			case 1:
				ev_code = Messages.build(CODE_GWINDOW_MOUSE, _0010_name);
				break;
			case 2:
				ev_code = Messages.build(CODE_GWINDOW_PEE, _0010_name);
				break;
			case 3:
				ev_code = Messages.build(CODE_GWINDOW_POST, _0010_name);
				break;
			}
		}
		return ev_code;
	}
	
	/**
	 * Get the declaration for this window
	 */
	protected String get_declaration(){
		if(mainSketch)
			return null;
		else
			return componentClass + " " + _0010_name+ ";\n";
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
			sb.append(Messages.build(CTOR_WINDOW_1, _0010_name, "this", _0015_title,
					$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height), false, _0021_renderer));
			if(_0750_wdraw.length() > 0){
				sb.append(Messages.build(ADD_DRAW_HANDLER, _0010_name, "this", _0750_wdraw));
			}
			if(_0751_wmouse.length() > 0){
				sb.append(Messages.build(ADD_MOUSE_HANDLER, _0010_name, "this", _0751_wmouse));
			}
			if(_0752_wpre.length() > 0){
				sb.append(Messages.build(ADD_PRE_HANDLER, _0010_name, "this", _0752_wpre));
			}
			if(_0753_wpost.length() > 0){
				sb.append(Messages.build(ADD_POST_HANDLER, _0010_name, "this", _0753_wpost));
			}
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
						lines.add(Messages.build(ADD_A_CHILD, _0010_name, comp._0010_name));
					}
				}
			}
		}				
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		for(int i = 0; i < id.length; i++)
			IdGen.instance().add(id[i]);
		renderer_editor = new EditorJComboBox(RENDERER);
	}


    @Override
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(winBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(greenEdge);
		g.drawRect(0, 0, _0130_width, _0131_height);
		if(this == selected)
			drawSelector(g);
		
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af, selected);
		}
		g.setTransform(paf);
	}

}
