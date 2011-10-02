package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.Icon;
import javax.swing.event.TableModelEvent;

@SuppressWarnings("serial")
public class DButton extends DCoreText {

	transient protected RectangularShape face;
	transient float mitre = 6.0f;
	transient BufferedImage icon = null;
	
	public Boolean 		_0031_icon  = false;
	public Boolean 		icon_edit = true;
	public Boolean 		icon_show = true;
	public String 		icon_updater = "updateIconUsage";
	public String 		icon_label = "Icon?";

	public int	 		_0032_nbr_images = 3;
	public Boolean 		nbr_images_edit = true;
	public Boolean 		nbr_images_show = false;
	public String 		nbr_images_label = "No. of frames in icon";
	public String 		nbr_images_updater = "nbrImagesChanged";
	public Validator 	nbr_images_validator = Validator.getValidator(int.class, 1, 3);

	public String 		_0033_filename = "";
	public Boolean 		filename_edit = true;
	public Boolean 		filename_show = false;
	public String 		filename_label = "Image filename";
	public String 		filename_updater = "imageChanged";
	transient public EditorJFileChooser filename_editor = new EditorJFileChooser();

	public DButton(){
		super();
		componentClass = "GButton";
		set_name(NameGen.instance().getNext("button"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		_0020_text = "Button face text";
		_0130_width = 80;
		_0131_height = 20;
		text_tooltip = "text to show on button";
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, mitre, mitre);
	}

	public void imageChanged(){
		System.out.println("Image has changed");
		icon = this.getImageFromDataFolder(_0033_filename);
		int w = icon.getWidth() / _0032_nbr_images;
		if(w > _0130_width) 
			_0130_width = w;
		int h = icon.getHeight();
		if(h > _0131_height)
			_0131_height = h;
	}
	
	public void nbrImagesChanged(){
		System.out.println("Update nbr images");
		if(icon == null && _0033_filename.length() > 0)
			icon = this.getImageFromDataFolder(_0033_filename);
		int w = icon.getWidth() / _0032_nbr_images;
		if(w > _0130_width) 
			_0130_width = w;
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
		g.drawString(this._0010_name, 6, _0131_height/2 +4 );
		if(_0031_icon && icon != null){
			int w = icon.getWidth() / _0032_nbr_images;
			int h = icon.getHeight();
			int px = (_0130_width - w)/2;
			int py = (_0131_height - icon.getHeight())/2;
			g.drawImage(icon, px, py, px + w, py + h, 0, 0, w, h, null);
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
					_0020_text, _0120_x, _0121_y, _0130_width, _0131_height);
			break;
		case 2:
			s = Messages.build(CTOR_GBUTTON_2, _0010_name, "this", 
					_0033_filename, _0032_nbr_images, _0120_x, _0121_y, _0130_width, _0131_height);
			break;
		case 3:
			s = Messages.build(CTOR_GBUTTON_3, _0010_name, "this",  _0020_text,
					_0033_filename, _0032_nbr_images, _0120_x, _0121_y, _0130_width, _0131_height);
			break;
		}
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0701_eventHandler);
		return s;
	}
	
	private int getMode(){
		// 1 = text used  : 2 = icon used  :  4 = filename provided
		int test = (_0020_text.length() > 0) ? 1 : 0;
		test += (_0033_filename.length() > 0) ? 2 : 0;
		test += (_0031_icon == true) ? 4 : 0;
		// Text only     1, 3, 5 (0, 2, 4= no icon and empty text
		// Icon  only    6
		// Text & icon	 7
		int mode;
		switch(test){
		case 6:
			mode = 2;
			break;
		case 7:
			mode = 3;
			break;
		default:
			mode = 1;
		}
		return mode;
	}
	
	public void updateIconUsage(){
		nbr_images_show = filename_show = _0031_icon;
		propertyModel.createProperties(this);
		propertyModel.fireTableChanged(new TableModelEvent(propertyModel));
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		filename_editor = new EditorJFileChooser();
		mitre = 6.0f;
		face = new RoundRectangle2D.Float(0, 0, _0130_width, _0131_height, mitre, mitre);
	}
}
