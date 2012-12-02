package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DButton extends DTextIcon {

	transient protected RectangularShape face;
	transient protected float mitre = 6.0f;

	int style = TEXT_ONLY;


	public DButton(){
		super();
		componentClass = "GButton";
		set_name(NameGen.instance().getNext("button"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		_0030_text = "Face text";
		_0130_width = 80;
		_0131_height = 30;
		text_tooltip = "text to show on button";

		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, mitre, mitre);
//		textWidth = GuiDesigner.metrics().stringWidth(_0029_text);
//		textHeight = GuiDesigner.metrics().getHeight();
//		style = ListGen.instance().getIndexOf(BUTTON_STYLE, _0028_btn_style);
//		textHAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0033_xtAlignment);
//		textVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0034_ytAlignment);
//		iconAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0038_icon_alignment);
//		iconAlignModel = H_ALIGN_3;
//		calculateAlignmentValues();
	}



	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);

		((RoundRectangle2D) face).setRoundRect(0, 0, _0130_width, _0131_height, mitre, mitre);	
		g.setStroke(stdStroke);
		g.setColor(btnBack);
		g.fill(face);			
		// Draw text if required. Use face text if available else use variable name
//		if(style == TEXT_ONLY || style == TEXT_AND_ICON &&_0029_text.length() > 0){
//			g.setColor(txfFore);
//			g.drawString(_0029_text, textX, textY );
//		}
//		else {
//			g.setColor(blackEdge);
//			g.drawString(_0010_name, textX, textY );			
//		}
//		// Draw icon is required and available
//		if(style == ICON_ONLY || style == TEXT_AND_ICON && icon != null){
//			g.drawImage(icon, iconX, iconY, iconX + iconWidth, iconY + iconHeight, 
//					0, 0, iconWidth, iconHeight, null);
//		}
		// draw border
		g.setColor(blackEdge);
		g.draw(face);

		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s = "";
//		switch(style){
//		case TEXT_ONLY:
//			s = Messages.build(CTOR_GBUTTON_1, _0010_name, window,
//					_0029_text, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
//			break;
//		case ICON_ONLY:
//			s = Messages.build(CTOR_GBUTTON_2, _0010_name, window, 
//					_0035_icon_file, _0037_nbr_tiles, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
//			break;
//		case TEXT_AND_ICON:
//			s = Messages.build(CTOR_GBUTTON_3, _0010_name, window,  _0029_text,
//					_0035_icon_file, _0037_nbr_tiles, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
//			break;
//		}
//		s += Messages.build(BTN_TEXT_ALIGN, _0010_name, _0033_text_x_alignment, _0034_ytAlignment);
//		if(icon != null)
//			s += Messages.build(BTN_ICON_ALIGN, _0010_name, _0038_icon_alignment);
//		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
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
		mitre = 6.0f;
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, mitre, mitre);
	}
}
