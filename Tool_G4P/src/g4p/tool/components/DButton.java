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
public class DButton extends DCoreText {

	transient protected RectangularShape face;
	transient BufferedImage icon = null;
	transient float mitre = 6.0f;
	
	int imgWidth, imgHeight, imgAlign, imgX, imgY;
	int textWidth, textHeight, textAlign, textX, textY, textMin, textMax;
	
	public String 		_0031_xtAlignment = "CENTER";
	transient public 	EditorBase xtAlignment_editor = new EditorJComboBox(H_ALIGN);
	public Boolean 		xtAlignment_edit = true;
	public Boolean 		xtAlignment_show = true;
	public String 		xtAlignment_label = "Horz text alignment";
	public String 		xtAlignment_updater = "textAlignChanged";

	public String 		_0032_ytAlignment = "MIDDLE";
	transient public 	EditorBase ytAlignment_editor = new EditorJComboBox(V_ALIGN);
	public Boolean 		ytAlignment_edit = true;
	public Boolean 		ytAlignment_show = true;
	public String 		ytAlignment_label = "Vert text alignment";
	public String 		ytAlignment_updater = "textAlignChanged";

	public Boolean 		_0035_icon  = false;
	public Boolean 		icon_edit = true;
	public Boolean 		icon_show = true;
	public String 		icon_updater = "updateIconUsage";
	public String 		icon_label = "Icon?";

	public int	 		_0036_nbr_images = 3;
	public Boolean 		nbr_images_edit = true;
	public Boolean 		nbr_images_show = false;
	public String 		nbr_images_label = "Nbr of tiles in icon";
	public String 		nbr_images_updater = "nbrImagesChanged";
	public Validator 	nbr_images_validator = Validator.getValidator(int.class, 1, 3);

	public String 		_0037_filename = "";
	transient public 	EditorJFileChooser filename_editor = new EditorJFileChooser();
	public Boolean 		filename_edit = true;
	public Boolean 		filename_show = false;
	public String 		filename_label = "Image filename";
	public String 		filename_updater = "iconChanged";

	public String 		_0038_imgAlignment = "LEFT";
	transient public 	EditorBase imgAlignment_editor = new EditorJComboBox(H_ALIGN);
	public Boolean 		imgAlignment_edit = true;
	public Boolean 		imgAlignment_show = false;
	public String 		imgAlignment_label = "Image alignment";
	public String 		imgAlignment_updater = "iconAlignChanged";


	public DButton(){
		super();
		componentClass = "GButton";
		set_name(NameGen.instance().getNext("button"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		_0020_text = "Face text";
		_0130_width = 80;
		_0131_height = 30;
		text_tooltip = "text to show on button";
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, mitre, mitre);
		textWidth = GuiDesigner.metrics().stringWidth(_0020_text);
		textHeight = GuiDesigner.metrics().getHeight();
		calculateAlignmentValues();
	}

	public void updatedInGUI(){
		calculateAlignmentValues();
	}
	
	public void textChanged(){
		textWidth = GuiDesigner.metrics().stringWidth(_0020_text);
		validateButtonSize();
		calculateAlignmentValues();
		propertyModel.hasBeenChanged();
	}

	public void textAlignChanged(){
		calculateAlignmentValues();	
		propertyModel.hasBeenChanged();
	}
	
	public void updateIconUsage(){
		nbr_images_show = filename_show = imgAlignment_show = _0035_icon;
		calculateAlignmentValues();
		propertyModel.createProperties(this);
		propertyModel.hasBeenChanged();
	}
	
	public void iconChanged(){
		icon = this.getImageFromDataFolder(_0037_filename);
		if(icon != null){
			imgWidth = icon.getWidth() / _0036_nbr_images;
			imgHeight = icon.getHeight();
			validateButtonSize();
			calculateAlignmentValues();
			propertyModel.hasBeenChanged();
		}
	}

	public void iconAlignChanged(){
		calculateAlignmentValues();		
		propertyModel.hasBeenChanged();
	}
	
	public void nbrImagesChanged(){
		if(icon == null && _0037_filename.length() > 0)
			icon = this.getImageFromDataFolder(_0037_filename);
		if(icon != null){
			imgWidth = icon.getWidth() / _0036_nbr_images;
			validateButtonSize();
			calculateAlignmentValues();
			propertyModel.hasBeenChanged();
		}
	}
	
	public void validateButtonSize(){
		_0130_width = Math.max(_0130_width, imgWidth + textWidth);
		_0131_height = Math.max(_0131_height, imgHeight);
	}
		
	public void calculateAlignmentValues(){
		textMax = _0130_width;
		int mode = getMode();
		// STEP 1
		// Set image x position based on image alignment.
		if((mode & 2) == 2) { // has an image
			// If there is text force image to left hand side
			if(_0020_text.length() > 0 && _0038_imgAlignment.equalsIgnoreCase("CENTER")){
				_0038_imgAlignment = "LEFT";
				imgAlignment_editor.setSelected(_0038_imgAlignment);
				propertyModel.hasBeenChanged();
			}
			if(_0038_imgAlignment.equalsIgnoreCase("LEFT")){
				imgX = 0;
				textMin = imgWidth + 2;
				textMax = _0130_width;
			}
			else if(_0038_imgAlignment.equalsIgnoreCase("RIGHT")){
				imgX = _0130_width - imgWidth;
				textMin = 0;
				textMax = imgX - 2;
			}
			else if(_0038_imgAlignment.equalsIgnoreCase("CENTER")){
				imgX = (_0130_width - imgWidth)/2;
				// No text for centred image
			}
			imgY = (_0131_height - imgHeight)/2;
		}
		// STEP 2
		// calculate text position
		if((mode & 1) == 1){
			// Horizontal
			if(_0031_xtAlignment.equalsIgnoreCase("LEFT")){
				textX = textMin;
			}
			else if(_0031_xtAlignment.equalsIgnoreCase("RIGHT")){
				textX = textMax - textWidth;
			}
			else if(_0031_xtAlignment.equalsIgnoreCase("CENTER")){
				textX = textMin + (textMax - textMin - textWidth)/2;
			}
			// Vertical
			if(_0032_ytAlignment.equalsIgnoreCase("TOP")){
				textY = textHeight + 1;
			}
			else if(_0032_ytAlignment.equalsIgnoreCase("MIDDLE")){
				textY = (_0131_height + textHeight)/2;
			}
			else if(_0032_ytAlignment.equalsIgnoreCase("BOTTOM")){
				textY = _0131_height - 1;
			}
		}
	}
	

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);

		((RoundRectangle2D) face).setRoundRect(0, 0, _0130_width, _0131_height, mitre, mitre);	
		g.setStroke(stdStroke);
		g.setColor(btnBack);
		g.fill(face);			
		g.setColor(blackEdge);
		g.draw(face);
		if(_0020_text.length() > 0){
			g.setColor(txfFore);
			g.drawString(_0020_text, textX, textY );
		}
		else {
			g.setColor(blackEdge);
			g.drawString(_0010_name, textX, textY );			
		}
		if(_0035_icon && icon != null){
			g.drawImage(icon, imgX, imgY, imgX + imgWidth, imgY + imgHeight, 
					0, 0, imgWidth, imgHeight, null);
		}
		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s = "";
		switch(getMode()){
		case 1:
			s = Messages.build(CTOR_GBUTTON_1, _0010_name, "this",
					_0020_text, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
			break;
		case 2:
			s = Messages.build(CTOR_GBUTTON_2, _0010_name, "this", 
					_0037_filename, _0036_nbr_images, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
			break;
		case 3:
			s = Messages.build(CTOR_GBUTTON_3, _0010_name, "this",  _0020_text,
					_0037_filename, _0036_nbr_images, $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
			break;
		}
		s += Messages.build(BTN_TEXT_ALIGN, _0010_name, _0031_xtAlignment, _0032_ytAlignment);
		if(_0035_icon && icon != null)
			s += Messages.build(BTN_ICON_ALIGN, _0010_name, _0038_imgAlignment);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
	}
	
	private int getMode(){
		// 1 = text used  : 2 = icon used  :  4 = filename provided
		int mode = (_0020_text.length() > 0) ? 1 : 0;
		mode += (_0035_icon == true && icon != null && _0037_filename.length() > 0) ? 2 : 0;
		return mode;
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		filename_editor = new EditorJFileChooser();
		xtAlignment_editor = new EditorJComboBox(H_ALIGN);
		ytAlignment_editor = new EditorJComboBox(V_ALIGN);
		imgAlignment_editor = new EditorJComboBox(H_ALIGN);
		if(_0037_filename.length() > 0)
			icon = getImageFromDataFolder(_0037_filename);
		mitre = 6.0f;
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, mitre, mitre);
	}
}
