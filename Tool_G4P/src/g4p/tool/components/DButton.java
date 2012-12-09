package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DButton extends DTextIcon {

//	transient protected RectangularShape face;
//	transient protected float mitre = 6.0f;

//	int style = TEXT_ONLY;


	public DButton(){
		super();
		componentClass = "GButton";
		set_name(NameGen.instance().getNext("button"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_click"));
		_0030_text = "Face text";
		_0130_width = 80;
		_0131_height = 30;
		text_tooltip = "text to show on button";
		opaque_show = false;
		eventHandler_edit = eventHandler_show = true;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setColor(DBase.jpalette[4]);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setStroke(stdStroke);
		g.setColor(DBase.jpalette[3]);
		g.drawRect(0, 0, _0130_width, _0131_height);

		super.draw(g, paf, selected);
		
		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(dashedEdge);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0130_width, _0131_height);		
		}
		g.setTransform(paf);
	}


	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s = "";
		s = Messages.build(CTOR_GBUTTON, _0010_name, window, 
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
		s += super.get_creator(parent, window);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);		
		return s;
	}

	protected void read(){
		super.read();
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
//		NameGen.instance().add(_0010_name);
//		IdGen.instance().add(id[0]);
//
//		icon_file_editor = new EditorJFileChooser();
//		icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_3);
//		icon_y_alignment_editor = new EditorJComboBox(V_ALIGN);
//		icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_3);
//		icon_y_alignment_editor = new EditorJComboBox(V_ALIGN);
//		if(_0035_icon_file.length() > 0)
//			icon = getImageFromDataFolder(_0035_icon_file);
	}
}
