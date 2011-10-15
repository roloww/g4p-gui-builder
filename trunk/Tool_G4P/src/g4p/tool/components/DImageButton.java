package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DImageButton extends DCore {

	transient BufferedImage image = null;
	
	int mem_nbr_tiles = 1;
	
	public String 		_0041_btn_style = "1 Image";
	transient public 	EditorBase btn_style_editor = new EditorJComboBox(IMG_BUTTON_STYLE);
	public Boolean 		btn_style_edit = true;
	public Boolean 		btn_style_show = true;
	public String 		btn_style_label = "Button style";
	public String 		btn_style_updater = "buttonStyleChanged";

	public String 		_0042_img_off = "";
	transient public 	EditorJFileChooser img_off_editor = new EditorJFileChooser();
	public Boolean 		img_off_edit = true;
	public Boolean 		img_off_show = true;
	public String 		img_off_label = "Image file";
	public String 		img_off_updater = "imageChanged";
	
	public int	 		_0043_nbr_tiles = 1;
	public Boolean 		nbr_tiles_edit = true;
	public Boolean 		nbr_tiles_show = false;
	public String 		nbr_tiles_label = "Nbr of tiles";
	public String 		nbr_tiles_updater = "nbrTilesChanged";
	public Validator 	nbr_tiles_validator = Validator.getValidator(int.class, 2, 3);

	public String 		_0044_img_over = "";
	transient public 	EditorJFileChooser img_over_editor = new EditorJFileChooser();
	public Boolean 		img_over_edit = true;
	public Boolean 		img_over_show = false;
	public String 		img_over_label = "Over image filename";

	public String 		_0045_img_down = "";
	transient public 	EditorJFileChooser img_down_editor = new EditorJFileChooser();
	public Boolean 		img_down_edit = true;
	public Boolean 		img_down_show = false;
	public String 		img_down_label = "Down image file";

	public Boolean 		_0048_mask = false;
	public Boolean 		mask_edit = true;
	public Boolean 		mask_show = true;
	public String 		mask_label = "Has a mask image?";
	public String 		mask_updater = "updateMaskUsage";

	public String 		_0049_img_mask = "";
	transient public 	EditorJFileChooser img_mask_editor = new EditorJFileChooser();
	public Boolean 		img_mask_edit = true;
	public Boolean 		img_mask_show = false;
	public String 		img_mask_label = "Mask file";

	
	public DImageButton(){
		super();
		componentClass = "GImageButton";
		set_name(NameGen.instance().getNext("imgButton"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		width_edit = height_edit = false;
		width_show = height_show = false;
		_0130_width = 80;
		_0131_height = 30;
		resizeable = false;
	}


	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s = "", f1 ="", f2 = "", f3 = "";
		// Get button style
		int style = ListGen.instance().getComboBoxModel(IMG_BUTTON_STYLE).getIndexOf(_0041_btn_style);
		// Get the image file parameters
		switch(style){
		case 3:
			f3 = (_0045_img_down.length() > 0) ? "\"" + _0045_img_down + "\"" : "null";
		case 2:
			f2 = (_0044_img_over.length() > 0) ? "\"" + _0044_img_over + "\"" : "null";
		default:
			f1 = (_0042_img_off.length() > 0) ? "\"" + _0042_img_off + "\"" : "null";			
		}
		// Get mask file parameter
		String mask = (_0048_mask && _0049_img_mask.length() > 0) ? "\"" + _0049_img_mask + "\"" : "null";

		switch(style){
		case 0:
		case 1:
			s = Messages.build(CTOR_IMG_BUTTON_1, _0010_name, "this", mask, f1,
					$(_0043_nbr_tiles), $(_0120_x), $(_0121_y));
			break;
		case 2:
			s = Messages.build(CTOR_IMG_BUTTON_2, _0010_name, "this", mask, f1, f2,
					$(_0120_x), $(_0121_y));
			s = s.replace('<', '{');
			s = s.replace('>', '}');
			break;
		case 3:
			s = Messages.build(CTOR_IMG_BUTTON_3, _0010_name, "this", mask, f1, f2, f3, 
					$(_0120_x), $(_0121_y));
			s = s.replace('<', '{');
			s = s.replace('>', '}');
			break;
		}
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
	}
		
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		g.setColor(btnBack);
		if(image != null){
			g.drawImage(image, 0, 0, _0130_width, _0131_height, 0, 0, _0130_width, _0131_height , null);
		}
		else {
			g.setStroke(heavyStroke);
			g.drawRect(0, 0, _0130_width, _0131_height);
		}
		
		if(this == selected)
			drawSelector(g);
		g.setTransform(paf);
	}

	public void buttonStyleChanged(){
		int style = ListGen.instance().getComboBoxModel(IMG_BUTTON_STYLE).getIndexOf(_0041_btn_style);
		switch(style){
		case 0: // Tiled image
			nbr_tiles_show = true;
			img_over_show = false;
			img_down_show = false;
			img_off_label = "Tiled Image";
			if(mem_nbr_tiles == 1)
				mem_nbr_tiles = _0043_nbr_tiles = 3;
			else
				_0043_nbr_tiles = mem_nbr_tiles;
			break;
		case 1: // 1 image
			nbr_tiles_show = false;
			img_over_show = false;
			img_down_show = false;
			img_off_label = "Single Image";
			mem_nbr_tiles = _0043_nbr_tiles;
			_0043_nbr_tiles = 1;
			break;
		case 2: // 2 images
			nbr_tiles_show = false;
			img_over_show = true;
			img_down_show = false;
			img_off_label = "OFF Image";
			img_over_label = "OVER/DOWN Image";
			mem_nbr_tiles = _0043_nbr_tiles;
			_0043_nbr_tiles = 1;
			break;
		case 3: // 3 images
			nbr_tiles_show = false;
			img_over_show = true;
			img_down_show = true;
			img_off_label = "OFF Image";
			img_over_label = "OVER Image";
			img_down_label = "DOWN Image";
			mem_nbr_tiles = _0043_nbr_tiles;
			_0043_nbr_tiles = 1;
			break;
		}
		_0130_width = (image == null) ? _0130_width : image.getWidth() / _0043_nbr_tiles;
		propertyModel.createProperties(this);
		propertyModel.hasBeenChanged();

	}
	
	public void imageChanged(){
		image = getImageFromDataFolder(_0042_img_off);
		if(image != null){
			_0130_width = image.getWidth() / _0043_nbr_tiles;
			_0131_height = image.getHeight();
		}
	}
	
	public void nbrTilesChanged(){
		if(image != null){
			_0130_width = image.getWidth() / _0043_nbr_tiles;
			_0131_height = image.getHeight();
		}
	}

	public void updateMaskUsage(){
		img_mask_show = _0048_mask;
		propertyModel.createProperties(this);
		propertyModel.hasBeenChanged();
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		btn_style_editor = new EditorJComboBox(IMG_BUTTON_STYLE);
		img_off_editor = new EditorJFileChooser();
		img_over_editor = new EditorJFileChooser();
		img_down_editor = new EditorJFileChooser();
		img_mask_editor = new EditorJFileChooser();
		if(_0042_img_off.length() > 0)
			image = getImageFromDataFolder(_0042_img_off);
	}

	
}
