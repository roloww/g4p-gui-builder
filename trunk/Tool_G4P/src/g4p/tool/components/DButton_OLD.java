package g4p.tool.components;

import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;

public class DButton_OLD extends DTextIcon {
	protected int iconWidth, iconHeight, iconX, iconY;
	protected int textWidth, textHeight,  textX, textY, textMin, textMax;

	// Alignments
	protected int textHAlign, textVAlign, iconAlign, iconAlignModel;;
	protected int style;

	public String		_0028_btn_style = "Text only";
	transient public 	EditorBase btn_style_editor = new EditorJComboBox(BUTTON_STYLE);
	public Boolean 		btn_style_edit = true;
	public Boolean 		btn_style_show = true;
	public String 		btn_style_label = "Button style";
	public String		btn_style_updater = "btnStyleChanged";

//	public String 		_0033_xtAlignment = "CENTER";
//	transient public 	EditorBase xtAlignment_editor = new EditorJComboBox(H_ALIGN_3);
//	public Boolean 		xtAlignment_edit = true;
//	public Boolean 		xtAlignment_show = true;
//	public String 		xtAlignment_label = "Horz text alignment";
//	public String 		xtAlignment_updater = "textAlignChanged";
//
//	public String 		_0034_ytAlignment = "MIDDLE";
//	transient public 	EditorBase ytAlignment_editor = new EditorJComboBox(V_ALIGN);
//	public Boolean 		ytAlignment_edit = true;
//	public Boolean 		ytAlignment_show = true;
//	public String 		ytAlignment_label = "Vert text alignment";
//	public String 		ytAlignment_updater = "textAlignChanged";

//	public int	 		_0036_nbr_tiles = 3;
//	public Boolean 		nbr_tiles_edit = true;
//	public Boolean 		nbr_tiles_show = false;
//	public String 		nbr_tiles_label = "Nbr of tiles in icon";
//	public String 		nbr_tiles_updater = "nbrTilesChanged";
//	public Validator 	nbr_tiles_validator = Validator.getValidator(int.class, 1, 3);
//
//	public String 		_0037_icon_file = "";
//	transient public 	EditorJFileChooser icon_file_editor = new EditorJFileChooser();
//	public Boolean 		icon_file_edit = true;
//	public Boolean 		icon_file_show = false;
//	public String 		icon_file_label = "Icon file";
//	public String 		icon_file_updater = "iconChanged";
//
//	public String 		_0038_icon_alignment = "LEFT";
//	transient public 	EditorBase icon_alignment_editor = new EditorJComboBox(H_ALIGN_3);
//	public Boolean 		icon_alignment_edit = true;
//	public Boolean 		icon_alignment_show = false;
//	public String 		icon_alignment_label = "Icon alignment";
//	public String 		icon_alignment_updater = "iconAlignChanged";

	/**
	 * Recalculate the alignment values for drawing this button in the designer.
	 */
	public void XXupdatedInGUI(){
		calculateAlignmentValues();
	}

	public void btnStyleChanged(){
		int newStyle = ListGen.instance().getIndexOf(BUTTON_STYLE, _0028_btn_style);
		switch(newStyle){
		case TEXT_ONLY:
			// show text stuff
			text_show = true;
			xtAlignment_show = true;
			ytAlignment_show = true;
			// hide image properties
			icon_file_show = false;
			nbr_tiles_show = false;
			icon_x_alignment_show = false;

			break;
		case ICON_ONLY:
			// hide text stuff
			text_show = false;
			xtAlignment_show = false;
			ytAlignment_show = false;
			// show image properties
			icon_file_show = true;
			nbr_tiles_show = true;
			icon_x_alignment_show = true;
			// New h-alignment
			if(style != ICON_ONLY){
				icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_3);
				// centre changed to right alignment
				iconAlign = (iconAlign == CENTER) ? iconAlign - 1 : 0;
				iconAlignModel = H_ALIGN_2;
			}
			break; 
		case TEXT_AND_ICON: // text + icon
			// show text stuff
			text_show = true;
			xtAlignment_show = true;
			ytAlignment_show = true;
			// show image properties
			icon_file_show = true;
			nbr_tiles_show = true;
			icon_x_alignment_show = true;
			// New h-alignment
			if(style != TEXT_AND_ICON){
				icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_2);
				// Maintain right alignment
				iconAlign = (iconAlign == 1) ? 2 : 0;
				iconAlignModel = H_ALIGN_3;
			}
			break;
		}
		style = newStyle;
		propertyModel.createProperties(this);
		propertyModel.hasBeenChanged();
	}

	public void textChanged(){
		textWidth = GuiDesigner.metrics().stringWidth(_0029_text);
		validateButtonSize();
		calculateAlignmentValues();
		propertyModel.hasBeenChanged();
	}

	public void textAlignChanged(){
		textHAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0033_xtAlignment);
		textVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0034_ytAlignment);
		calculateAlignmentValues();	
		propertyModel.hasBeenChanged();
	}

	public void iconChanged(){
		icon = this.getImageFromDataFolder(_0035_icon_file);
		if(icon != null){
			iconWidth = icon.getWidth() / _0037_nbr_tiles;
			iconHeight = icon.getHeight();
			validateButtonSize();
			calculateAlignmentValues();
			propertyModel.hasBeenChanged();
		}
	}

	public void iconAlignChanged(){
		if(iconAlignModel == H_ALIGN_3)
			iconAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0038_icon_alignment);
		else
			iconAlign = ListGen.instance().getIndexOf(H_ALIGN_2, _0038_icon_alignment);
		calculateAlignmentValues();		
		propertyModel.hasBeenChanged();
	}

	public void nbrTilesChanged(){
		if(icon == null && _0035_icon_file.length() > 0)
			icon = this.getImageFromDataFolder(_0035_icon_file);
		if(icon != null){
			iconWidth = icon.getWidth() / _0037_nbr_tiles;
			validateButtonSize();
			calculateAlignmentValues();
			propertyModel.hasBeenChanged();
		}
	}

	protected void validateButtonSize(){
		int w = textWidth, h = textHeight;
		switch(style){
		case ICON_ONLY:  // icon only
			w = iconWidth;
			h = iconHeight;
			break;
		case TEXT_AND_ICON:  // text + icon
			w = iconWidth + textWidth;
			h = Math.max(iconHeight, textHeight);
		}
		w = Math.max(_0130_width, iconWidth + textWidth);
		h = Math.max(_0131_height, h);
		if( w != _0130_width || h != _0131_height){
			_0130_width = w;
			_0131_height = h;
		}
	}

	protected void calculateAlignmentValues(){
		// Calculate icon position and range limits for text
		textMin = 0;
		textMax = _0130_width;
		switch(style){
		case ICON_ONLY: // icon only
		case TEXT_AND_ICON: // text + icon
			iconY = (_0131_height - iconHeight)/2;
			switch(iconAlign){
			case LEFT:
				iconX = 0;
				textMin = iconWidth;
				break;
			case CENTER:
				iconX = (_0130_width - iconWidth)/2;
				break;
			case RIGHT:
				iconX = _0130_width - iconWidth;
				textMax = iconX;
				break;
			}
			break;
		}
		// Calculate text position
		if(style == TEXT_ONLY || style == TEXT_AND_ICON){
			// Horizontal
			switch(textHAlign){
			case LEFT:
				textX = textMin;
				break;
			case CENTER:
				textX = textMin + (textMax - textMin - textWidth)/2;
				break;
			case RIGHT:
				textX = textMax - textWidth;
				break;
			}
			// Vertical
			switch(textVAlign){
			case TOP:
				textY = textHeight + 1;
				break;
			case MIDDLE:
				textY = (_0131_height + textHeight)/2;				
				break;
			case BOTTOM:
				textY = _0131_height - 1;
				break;
			}
		}
	}
}
