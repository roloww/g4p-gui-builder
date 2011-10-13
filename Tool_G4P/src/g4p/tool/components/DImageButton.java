package g4p.tool.components;

import g4p.tool.Messages;
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
	
	public int	 		_0041_nbr_image_files = 3;
	public Boolean 		nbr_image_files_edit = true;
	public Boolean 		nbr_image_files_show = true;
	public String 		nbr_image_files_label = "No. of image files";
	public String 		nbr_image_files_updater = "nbrImageFilesChanged";
	public Validator 	nbr_image_files_validator = Validator.getValidator(int.class, 1, 3);

	public String 		_0043_img_off = "";
	transient public 	EditorJFileChooser img_off_editor = new EditorJFileChooser();
	public Boolean 		img_off_edit = true;
	public Boolean 		img_off_show = true;
	public String 		img_off_label = "Off image filename";
	public String 		img_off_updater = "imageChanged";

	public String 		_0044_img_over = "";
	transient public 	EditorJFileChooser img_over_editor = new EditorJFileChooser();
	public Boolean 		img_over_edit = true;
	public Boolean 		img_over_show = true;
	public String 		img_over_label = "Over image filename";

	public String 		_0045_img_down = "";
	transient public 	EditorJFileChooser img_down_editor = new EditorJFileChooser();
	public Boolean 		img_down_edit = true;
	public Boolean 		img_down_show = true;
	public String 		img_down_label = "down image filename";

	public Boolean 		_0048_mask = false;
	public Boolean 		mask_edit = true;
	public Boolean 		mask_show = true;
	public String 		mask_updater = "updateMaskUsage";
	public String 		mask_label = "Has a mask image?";

	public String 		_0049_img_mask = "";
	transient public 	EditorJFileChooser img_mask_editor = new EditorJFileChooser();
	public Boolean 		img_mask_edit = true;
	public Boolean 		img_mask_show = false;
	public String 		img_mask_label = "Mask image filename";


	public int	 		_0046_nbr_images = 1;
	public Boolean 		nbr_images_edit = true;
	public Boolean 		nbr_images_show = false;
	public String 		nbr_images_label = "Nbr of tiles in image";
	public String 		nbr_images_updater = "nbrImagesChanged";
	public Validator 	nbr_images_validator = Validator.getValidator(int.class, 1, 3);

	
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
		String s = "";
		String mask = (_0048_mask && _0049_img_mask.length() > 0) ? _0049_img_mask : "null";
		String file0 = (_0043_img_off.length() > 0) ? _0043_img_off : "null";
		String file1 = (_0041_nbr_image_files >= 2 && _0044_img_over.length() > 0) ? _0044_img_over : "null";
		String file2 = (_0041_nbr_image_files >= 3 && _0045_img_down.length() > 0) ? _0045_img_down : "null";

		switch(_0041_nbr_image_files){
		case 1:
			s = Messages.build(CTOR_IMG_BUTTON_1, _0010_name, "this", mask, file0,
					$(_0046_nbr_images), $(_0120_x), $(_0121_y));
			break;
		case 2:
			s = Messages.build(CTOR_IMG_BUTTON_2, _0010_name, "this", mask, file0, file1,
					$(_0120_x), $(_0121_y));
			break;
		case 3:
			s = Messages.build(CTOR_IMG_BUTTON_3, _0010_name, "this", mask, file0, file1, file2, 
					$(_0120_x), $(_0121_y));
			break;
		}

		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
	}
	
//	//		GImageButton(PApplet theApplet, String maskFile, String imgFile, int nbrImages, int x, int y)
//	public final String CTOR_IMG_BUTTON_1	= 	"  {0} = new GImageButton({1}, \"{2}\", \"{3}\", {4}, {5}, {6});\n";
//	
//	// 		GImageButton(PApplet theApplet, String maskFile, String imgFiles[], int x, int y)
//	public final String CTOR_IMG_BUTTON_2	=	"  {0} = new GImageButton({1}, \"{2}\", \"{3}\", {4}, {5});\n";


	public void imageChanged(){
		image = getImageFromDataFolder(_0043_img_off);
		if(image != null){
			_0130_width = image.getWidth() / _0046_nbr_images;
			_0131_height = image.getHeight();
		}
	}
	
	private int nbrImages(){
		return (_0041_nbr_image_files == 1) ? _0046_nbr_images : 1;
	}
	
	public void nbrImagesChanged(){
		if(image != null){
			_0130_width = image.getWidth() / nbrImages();
			_0131_height = image.getHeight();
		}
		System.out.println("Number of images");
	}
	
	public void updateMaskUsage(){
		img_mask_show = _0048_mask;
		propertyModel.createProperties(this);
		propertyModel.hasBeenChanged();
	}
	
	public void nbrImageFilesChanged(){
		System.out.println("Number of image files = " + _0041_nbr_image_files);
		switch(_0041_nbr_image_files){
			case 1:
				img_over_show = img_down_show = false;
				nbr_images_show = true;
				if(image != null)
					_0130_width = image.getWidth() / nbrImages();
				break;
			case 2:
				img_over_show = true;
				img_down_show = false;
				nbr_images_show = false;
				if(image != null)
					_0130_width = image.getWidth();
				break;
			case 3:
				img_over_show = img_down_show = true;
				nbr_images_show = false;
				if(image != null)
					_0130_width = image.getWidth();
				break;
		}
		propertyModel.createProperties(this);
		propertyModel.hasBeenChanged();
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setColor(btnBack);
		if(image == null){
			g.drawImage(image, 0, 0, _0130_width, _0131_height, 0, 0, image.getWidth() / nbrImages(), _0131_height , null);
		}
		else {
			g.setStroke(heavyStroke);
			g.drawRect(0, 0, _0130_width, _0131_height);
		}

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
		img_off_editor = new EditorJFileChooser();
		img_over_editor = new EditorJFileChooser();
		img_down_editor = new EditorJFileChooser();
		img_mask_editor = new EditorJFileChooser();
		if(_0043_img_off.length() > 0)
			image = getImageFromDataFolder(_0043_img_off);
	}

	
}
