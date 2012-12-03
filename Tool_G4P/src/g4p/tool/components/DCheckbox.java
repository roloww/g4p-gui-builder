package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.ToolImage;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DCheckbox extends DCoreSelectable{

	public DCheckbox(){
		super();
		componentClass = "GCheckbox";
		text_label = "Checkbox text";
		set_name(NameGen.instance().getNext("checkbox"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_clicked"));
		// Set up text
		_0030_text = "checkbox text";
		_0031_text_x_alignment = "LEFT";
		textHAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0031_text_x_alignment);
		textVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0032_text_y_alignment);
		// Set up icon
		icon = ToolImage.getImage("CB_ICON");
		_0035_nbr_tiles = 2;
		_0036_icon_x_alignment = "LEFT";
		iconHAlign = ListGen.instance().getIndexOf(H_ALIGN_2, _0036_icon_x_alignment);
		iconVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0037_icon_y_alignment);
		iconWidth = icon.getWidth() / _0035_nbr_tiles;
		iconHeight = icon.getHeight();
		icon_x_alignment_show = true;
		icon_y_alignment_show = true;
		icon_file_show = false;
		nbr_tiles_show = false;
		iconAlignChanged();
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GCHECKBOX, _0010_name, window, 
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
		s += super.get_creator(parent, window);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);		
		return s;
	}

	protected boolean isIconAlignDefaults(){
		return _0036_icon_x_alignment.equals("LEFT") && _0037_icon_y_alignment.equals("MIDDLE");
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

	public void drawOLD(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setStroke(stdStroke);
		g.setColor(cboxBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(cboxEdge);
		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.setColor(cboxFill);
		int markLeft = 2, markTop = (_0131_height - BOXSIZE)/2;
		g.fillRect(markLeft, markTop, BOXSIZE, BOXSIZE);
		g.setColor(blackEdge);
		g.drawRect(markLeft, markTop, BOXSIZE, BOXSIZE);

		if(_0685_selected){
			g.setColor(optDot);
			g.setStroke(needleStroke);
			int markBottom = markTop + BOXSIZE;
			int markRight = markLeft + BOXSIZE; 
			g.drawLine(markLeft + BOXINSET, markBottom - 2 * BOXINSET, markLeft + 2 * BOXINSET, markBottom - BOXINSET);
			g.drawLine(markLeft + 2 * BOXINSET, markBottom - BOXINSET, markRight - BOXINSET, markTop + BOXINSET);
		}
		
		g.setColor(blackEdge);
		
		g.drawString(_0010_name, 20, _0131_height/2 +4 );

		if(this == selected)
			drawSelector(g);
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
		icon = ToolImage.getImage("CB_ICON");
	}
	
}
