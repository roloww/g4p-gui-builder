package g4p.tool.components;

import g4p.tool.Messages;

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

		componentClass = "GOptionGroup";
		set_name(NameGen.instance().getNext("optGroup"));
		name_label = "Variable name";
		name_tooltip = "Java naming rules apply";
		name_edit = true;
		
		allowsChildren = true;
	}
	
	public String get_creator(DBase parent){
		return Messages.build(CTOR_GOPTIONGROUP, _0005_name);
	}

	/**
	 * Get the declaration for this control
	 */
	public String get_declaration(){
		return componentClass + " " + _0005_name+ "; ";
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
