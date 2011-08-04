package g4p.tool.components;

import g4p.tool.gui.WindowView.MutableDBase;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

@SuppressWarnings("serial")
public class DPanel extends DCoreText {

	final protected static int TAB_HEIGHT = 16;
	
	public Boolean 		_0030_opaque = false;
	public Boolean 		opaque_edit = true;
	public Boolean 		opaque_show = true;

	public Boolean 		_0032_collapsed = true;
	public Boolean 		collapsed_edit = true;
	public Boolean 		collapsed_show = true;

	public DPanel(){
		super();
		allowsChildren = true;
		set_name(NameGen.instance().getNext("panel"));
//		System.out.println("ctor DPanel()   " + _0005_name);
	}

	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(solidCompBack);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(pnlTabCol);
		g.fillRect(0, -TAB_HEIGHT, _0024_width, TAB_HEIGHT);
		g.setColor(blackEdge);
		g.drawString(_0005_name, 2, -4);
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
		g.drawRect(0, -TAB_HEIGHT,_0024_width, _0025_height + TAB_HEIGHT);
		
		drawHandle(g, _0024_width - HANDLE_SIZE, (_0025_height - HANDLE_SIZE)/2);
		drawHandle(g, (_0024_width - HANDLE_SIZE) / 2, _0025_height - HANDLE_SIZE);
		drawHandle(g, _0024_width - HANDLE_SIZE, _0025_height - HANDLE_SIZE);	
	}

	public void isOver(MutableDBase m, int x, int y) {
		if(selectable){
			x -= _0020_x;
			y -= _0021_y;
			//
			if(getSize() < m.area && isOverRectangle(x, y, 0, - TAB_HEIGHT, _0024_width, _0025_height + TAB_HEIGHT)){			
				m.selID = OVER_COMP;
				m.comp = this;
				m.area = getSize();
				if(isOverRectangle(x,y, _0024_width - HANDLE_SIZE, (_0025_height - HANDLE_SIZE)/2, HANDLE_SIZE, HANDLE_SIZE))
					m.selID = OVER_HORZ;
				else if(isOverRectangle(x,y, (_0024_width - HANDLE_SIZE) / 2, _0025_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
					m.selID = OVER_VERT;
				else if(isOverRectangle(x,y, _0024_width - HANDLE_SIZE, _0025_height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)) 
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
		return (x >= _0020_x && x <= _0020_x + _0024_width 
				&& y >= _0021_y - TAB_HEIGHT && y <= _0021_y + _0025_height);
	}

	public String show(){
		return ("Opaque = " + _0030_opaque + "       Collapsed = " + _0032_collapsed);
	}
}
