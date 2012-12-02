package g4p.tool.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

@SuppressWarnings("serial")
public class DTextIcon extends DText {

	transient protected BufferedImage icon = null;

	protected int iconX, iconY, iconWidth, iconHeight;
	protected int iconHAlign, iconVAlign;//, iconAlignModel;;

	public String 		_0034_icon_file = "";
	transient public 	EditorJFileChooser icon_file_editor = new EditorJFileChooser();
	public Boolean 		icon_file_edit = true;
	public Boolean 		icon_file_show = true;
	public String 		icon_file_label = "Icon file";
	public String 		icon_file_updater = "iconChanged";

	public int	 		_0035_nbr_tiles = 1;
	public Boolean 		nbr_tiles_edit = true;
	public Boolean 		nbr_tiles_show = false;
	public String 		nbr_tiles_label = "Nbr of tiles in icon";
	public String 		nbr_tiles_updater = "nbrTilesChanged";
	public Validator 	nbr_tiles_validator = Validator.getValidator(int.class, 1, 3);

	public String 		_0036_icon_x_alignment = "RIGHT";
	transient public 	EditorBase icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_2);
	public Boolean 		icon_x_alignment_edit = true;
	public Boolean 		icon_x_alignment_show = false;
	public String 		icon_x_alignment_label = "Icon horz alignment";
	public String 		icon_x_alignment_updater = "iconAlignChanged";

	public String 		_0037_icon_y_alignment = "MIDDLE";
	transient public 	EditorBase icon_y_alignment_editor = new EditorJComboBox(V_ALIGN);
	public Boolean 		icon_y_alignment_edit = true;
	public Boolean 		icon_y_alignment_show = false;
	public String 		icon_y_alignment_label = "Icon vert alignment";
	public String 		icon_y_alignment_updater = "iconAlignChanged";

	
	public DTextIcon(){
		super();
		iconHAlign = ListGen.instance().getIndexOf(H_ALIGN_2, _0036_icon_x_alignment);
		iconVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0037_icon_y_alignment);
	}

	protected String get_creator(DBase parent, String window){
		String s = "";
		if(icon != null){
			s = Messages.build(SET_ICON, _0010_name, _0034_icon_file, _0035_nbr_tiles, _0036_icon_x_alignment, _0037_icon_y_alignment);
		}
		s += super.get_creator(parent, window);
		return s;
	}

	// Override this method if needed
	public void textChanged(){
		super.textChanged();
	}
	
	public String get_text(){
		return _0030_text;
	}
	
	public void updatedInGUI(){
		sizeChanged();
	}

	/**
	 * If the width or height is changed then we need to update the text etc.
	 */
	public void sizeChanged(){
		System.out.println("DTextIcon size changed");
		textWidth = _0130_width;
//		textChanged = true;
		iconAlignChanged();
//		if(iconHAlign == LEFT)
//			setHorzTextBoxValues(iconWidth, _0130_width - iconWidth);
//		else 
//			setHorzTextBoxValues(0, _0130_width - iconWidth);
//		propertyModel.hasBeenChanged();
	}

	public void nbrTilesChanged(){
		iconWidth = icon.getWidth() / _0035_nbr_tiles;		
		iconAlignChanged();
	}

	public void iconAlignChanged(){
		iconHAlign = ListGen.instance().getIndexOf(H_ALIGN_2, _0036_icon_x_alignment);
		iconVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0037_icon_y_alignment);
		if(iconHAlign == LEFT){
			iconX = 0;
			setHorzTextBoxValues(iconWidth, _0130_width - iconWidth);
		}
		else {
			iconX = _0130_width - iconWidth;
			setHorzTextBoxValues(0, _0130_width - iconWidth);
		}
		switch(iconVAlign){
		case TOP:
			iconY = 0;
			break;
		case MIDDLE:
			iconY = (_0131_height - iconHeight)/2;
			break;
		case BOTTOM:
			iconY = _0131_height - iconHeight;
			break;
		}
//		propertyModel.hasBeenChanged();
	}

	public void iconChanged(){
		icon = this.getImageFromDataFolder(_0034_icon_file);
		if(icon != null){
			iconWidth = icon.getWidth() / _0035_nbr_tiles;
			iconHeight = icon.getHeight();
			nbr_tiles_show = true;
			icon_x_alignment_show = true;
			icon_y_alignment_show = true;
			iconAlignChanged();
			propertyModel.createProperties(this);
			propertyModel.hasBeenChanged();
		}
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		super.draw(g, paf, selected); // draw text
		if(icon != null)
			g.drawImage(icon, iconX, iconY, iconX + iconWidth, iconY + iconHeight, 
				0, 0, iconWidth, iconHeight, null);
	}

}
