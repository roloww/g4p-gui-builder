package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DLabel extends DTextIcon {

	
	public DLabel(){
		super();
		componentClass = "GLabel";
		set_name(NameGen.instance().getNext("label"));
		_0130_width = 80;
		_0131_height = 20;
		_0030_text = "My label";
		eventHandler_edit = eventHandler_show = false;
	}
	
	/**
	 * There are no events for this control
	 * @return
	 */
	protected String get_event_definition(){
		return null;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s = "";
		s = Messages.build(CTOR_GLABEL, _0010_name, window, 
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
		s += super.get_creator(parent, window);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		if(_0039_opaque){
			g.setColor(DBase.jpalette[6]);
			g.fillRect(0, 0, _0130_width, _0131_height);
		}
		g.setStroke(stdStroke);

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

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);

		icon_file_editor = new EditorJFileChooser();
		icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_3);
		icon_y_alignment_editor = new EditorJComboBox(V_ALIGN);
		icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_3);
		icon_y_alignment_editor = new EditorJComboBox(V_ALIGN);
		if(_0034_icon_file.length() > 0)
			icon = getImageFromDataFolder(_0034_icon_file);
	}
	
}
