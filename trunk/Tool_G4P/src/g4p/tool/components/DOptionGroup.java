package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

@SuppressWarnings("serial")
public class DOptionGroup extends DBase {

	
	public DOptionGroup(){
		super();
		selectable = false;
		resizeable = false;
		moveable = false;

		set_name(NameGen.instance().getNext("optGroup"));
		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		
		allowsChildren = true;
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		Enumeration<?> e = children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g, af, selected);
		}
		g.setTransform(paf);
	}

}
