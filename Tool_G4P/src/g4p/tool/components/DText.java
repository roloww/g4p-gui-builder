package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.Validator;
import g4p_controls.StyledString;
import g4p_controls.StyledString.TextLayoutInfo;

import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;


@SuppressWarnings("serial")
public class DText extends DBase {

	protected int textWidth, textX;
	protected int textHAlign, textVAlign;
	
	protected int lastLength;
	protected boolean textChanged = true;
	transient public StyledString stext;
	
	public String 		_0030_text = "???";
	public String 		text_label = "Text";
	public String 		text_tooltip = "component label text";
	public Boolean 		text_edit = true;
	public Boolean 		text_show = true;
	public Validator 	text_validator = Validator.getDefaultValidator(String.class);
	public String		text_updater = "textChanged";

	// Set edit to false for GPanel
	public String 		_0031_text_x_alignment = "CENTER";
	transient public 	EditorBase text_x_alignment_editor = new EditorJComboBox(H_ALIGN_3);
	public Boolean 		text_x_alignment_edit = true;
	public Boolean 		text_x_alignment_show = true;
	public String 		text_x_alignment_label = "Text X align";
	public String 		text_x_alignment_updater = "textAlignChanged";

	public String 		_0032_text_y_alignment = "MIDDLE";
	transient public 	EditorBase text_y_alignment_editor = new EditorJComboBox(V_ALIGN);
	public Boolean 		text_y_alignment_edit = true;
	public Boolean 		text_y_alignment_show = true;
	public String 		text_y_alignment_label = "Text Y align";
	public String 		text_y_alignment_updater = "textAlignChanged";

	public String 		width_updater = "sizeChanged";
	public String 		height_updater = "sizeChanged";

	public DText(){
		super();
		selectable = true;
		resizeable = true;
		moveable = true;
		allowsChildren = false;
		_0130_width = 80;
		_0131_height = 22;
		textX = 0;
		textWidth = _0130_width;
		textHAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0031_text_x_alignment);
		textVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0032_text_y_alignment);
		lastLength = _0030_text.length();
		eventHandler_edit = eventHandler_show = true;
	}

	protected boolean isTextAlignDefaults(){
		return _0031_text_x_alignment.equals("CENTER") && _0032_text_y_alignment.equals("MIDDLE");
	}

	protected String get_creator(DBase parent, String window){
		String s = "";
		if(_0030_text.length() > 0){
			s = Messages.build(SET_TEXT, _0010_name, _0030_text);
			if(!isTextAlignDefaults())
				s += Messages.build(SET_TEXT_ALIGN, _0010_name, _0031_text_x_alignment, _0032_text_y_alignment);
		}
		s += super.get_creator(parent, window);		
		return s;
	}

	// Override this method if needed
	public void textChanged(){
		textChanged = true;
		if(text_x_alignment_edit || text_y_alignment_edit){
			int currLength = _0030_text.length();
			if(currLength != lastLength){
				// Do we have to change the show alignment property.
				// If yes then the property model needs updating
				if(currLength == 0 || lastLength == 0){
					boolean hasText = (_0030_text.length() != 0);
					text_x_alignment_show = hasText;
					text_y_alignment_show = hasText;
					propertyModel.createProperties(this);
				}
				lastLength = currLength; // remember the current length
			}
		}
		propertyModel.hasBeenChanged();
	}

	public void textAlignChanged(){
		textHAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0031_text_x_alignment);
		textVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0032_text_y_alignment);
	}
	
	/**
	 * This will be called if the text box has to me moved in a child class.
	 * e.g. if we have an icon
	 * @param x_offset
	 * @param text_width
	 */
	protected void setHorzTextBoxValues(int x_offset, int text_width){
		textX = x_offset;
		if(textWidth != text_width){
			textWidth = text_width;
			if(stext == null)
				stext = new StyledString(_0030_text, textWidth);
			else
				stext.setWrapWidth(textWidth);
			textChanged = true;
		}
	}
	
	/**
	 * If the width or height is changed then we need to update the text etc.
	 */
	public void sizeChanged(){
		System.out.println("DText size changed");
		textWidth = _0130_width;
		textChanged = true;
//		propertyModel.hasBeenChanged();
	}
	
	public String get_text(){
		return _0030_text;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		if(textChanged){
			stext = new StyledString(_0030_text, textWidth);
			textChanged = false;
		}
		else
			stext.setWrapWidth(textWidth);
		
		LinkedList<TextLayoutInfo> lines = stext.getLines(g);

		float deltaY = stext.getMaxLineHeight(), currY = 0, startX;

		switch(textVAlign){
		case TOP:
			currY = deltaY;
			break;
		case MIDDLE:
			currY = (_0131_height - stext.getTextAreaHeight() + deltaY)/2;
			break;
		case BOTTOM:
			currY = _0131_height - stext.getTextAreaHeight() + deltaY;
		}

		for(TextLayoutInfo lineInfo : lines){
			TextLayout layout = lineInfo.layout;
			switch(textHAlign){
			case CENTER:
				startX = (textWidth - layout.getVisibleAdvance())/2;
				break;
			case RIGHT:
				startX = (textWidth - layout.getVisibleAdvance());
				break;
			case LEFT:
			default:
				startX = 0;		
			}
			// display text
			g.setColor(DApplication.jpalette[2]);
			layout.draw(g, textX + startX, currY);
			currY += deltaY;
		}
	}
	
}
