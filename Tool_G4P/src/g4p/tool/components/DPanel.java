package g4p.tool.components;

import g4p.tool.gui.propertygrid.Renderer_Boolean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

import javax.swing.table.TableCellRenderer;

public class DPanel extends DCoreText {

	
	public Boolean 		_0030_opaque = false;
	public Boolean 		opaque_edit = true;
	public Boolean 		opaque_show = true;

	public Boolean 		_0032_collapsed = true;
	public Boolean 		collapsed_edit = true;
	public Boolean 		collapsed_show = true;
//	public TableCellRenderer 		collapsed_renderer = new Renderer_Boolean();;

	protected Color fillTitle;

	public DPanel(){
		super();
		allowsChildren = true;
		set_name(NameGen.instance().getNext("panel"));
//		System.out.println("ctor DPanel()   " + _0005_name);
		fill = new Color(192,192,255);
		fillTitle = new Color(50,50,255);
	}

	
	public void draw(Graphics2D g, AffineTransform paf){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);
		
		g.setStroke(bs);
		g.setColor(fill);
		g.fillRect(0, 0, _0024_width, _0025_height);
		g.setColor(fillTitle);
		g.fillRect(0, -16, _0024_width, 16);
		
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af);
		}
		g.setTransform(paf);
	}

	public String show(){
		return ("Opaque = " + _0030_opaque + "       Collapsed = " + _0032_collapsed);
	}
}
