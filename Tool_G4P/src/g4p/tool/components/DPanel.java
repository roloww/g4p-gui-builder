package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.ToolImage;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.tabview.WindowView.MutableDBase;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Enumeration;

@SuppressWarnings("serial")
public class DPanel extends DTextIcon {

	final protected static int TAB_HEIGHT = 20;
	
	public Boolean 		_0030_collapsed = false;
	public String 		collapsed_label = "Collapsed?";
	public Boolean 		collapsed_edit = true;
	public Boolean 		collapsed_show = true;

	public DPanel(){
		super();
		componentClass = "GPanel";
		set_name(NameGen.instance().getNext("panel"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		allowsChildren = true;
		_0030_text = "Tab bar text";
		text_label = "Panel tab text";
		text_tooltip = "text to appear in panel tab";
		_0130_width = 100;
		_0131_height = 60;
		opaque_edit = opaque_show = false;
		icon_file_edit = icon_file_show = false;
		text_x_alignment_edit = text_x_alignment_show = false;
		text_y_alignment_edit = text_y_alignment_show = false;
	}

	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GPANEL, _0010_name, window,
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height), _0030_text);
		s += Messages.build(COLLAPSED, _0010_name, _0030_collapsed);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);
		return s;
	}

	public void make_creator(ArrayList<String> lines, DBase parent, String window){
		DBase comp;
		Enumeration<?> e;
		String ccode = get_creator(parent, window);
		if(ccode != null && !ccode.equals(""))
			lines.add(ccode);
		if(allowsChildren){
			e = children();
			while(e.hasMoreElements()){
				comp = (DBase)e.nextElement();
				comp.make_creator(lines, this, window);
			}
			e = children();
			while(e.hasMoreElements()){
				comp = (DBase)e.nextElement();
				if(!(comp instanceof DToggleGroup))
					lines.add(Messages.build(ADD_A_CHILD, _0010_name, comp._0010_name));
			}
		}				
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
//		if(_0030_collapsed)
//			g.setColor(pnlBackClear);
//		else
//			g.setColor(pnlBackOpaque);

		// Panel back
		g.setColor(DBase.jpalette[5]);
		g.fillRect(0, 0, _0130_width, _0131_height);
		
		// Tab
		g.setColor(DBase.jpalette[3]);
		g.fillRect(0, 0, _0130_width, TAB_HEIGHT);
		
		// Text
		g.setColor(DBase.jpalette[12]);
		g.drawString(_0010_name, 2, 12);
		if(this == selected)
			drawSelector(g);
		
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af, selected);
		}
		g.setTransform(paf);
	}

	public void drawSelector(Graphics2D g){
		g.setStroke(stdStroke);
		g.setColor(Color.red);
		g.drawRect(0, 0,_0130_width, _0131_height);
		
		drawHandle(g, _0130_width - HANDLE_SIZE, (_0131_height - HANDLE_SIZE)/2);
		drawHandle(g, (_0130_width - HANDLE_SIZE) / 2, _0131_height - HANDLE_SIZE);
		drawHandle(g, _0130_width - HANDLE_SIZE, _0131_height - HANDLE_SIZE);	
	}

	public void isOver(MutableDBase m, int x, int y) {
		if(selectable){
			x -= _0120_x;
			y -= _0121_y;
			//
			if(getSize() < m.area && isOverRectangle(x, y, 0, - TAB_HEIGHT, _0130_width, _0131_height + TAB_HEIGHT)){			
				m.selID = OVER_COMP;
				m.comp = this;
				m.area = getSize();
				if(isOverRectangle(x,y, _0130_width - HANDLE_SIZE, (_0131_height - HANDLE_SIZE)/2, HANDLE_SIZE, HANDLE_SIZE))
					m.selID = OVER_HORZ;
				else if(isOverRectangle(x,y, (_0130_width - HANDLE_SIZE) / 2, _0131_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
					m.selID = OVER_VERT;
				else if(isOverRectangle(x,y, _0130_width - HANDLE_SIZE, _0131_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
					m.selID = OVER_DIAG;
			}
		}
		if(this.allowsChildren){
			Enumeration<?> e = children();
			while(e.hasMoreElements()){
				((DBase)e.nextElement()).isOver(m, x, y);
			}
		}
	}

	/**
	 * Make allowance for the panel tab
	 */
	public boolean isOver(int x, int y){
		return (x >= _0120_x && x <= _0120_x + _0130_width 
				&& y >= _0121_y - TAB_HEIGHT && y <= _0121_y + _0131_height);
	}

	protected void read(){
		super.read();
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}
}
