package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.event.TableModelEvent;

@SuppressWarnings("serial")
public class DButton extends DCoreText {

	transient protected RectangularShape face;
	transient float mitre = 6.0f;

	public Boolean 		_0017_icon  = false;
	public Boolean 		icon_edit = true;
	public Boolean 		icon_show = true;
	public String 		icon_updater = "updateIconUsage";
	public String 		icon_label = "Icon?";

	public int	 		_0018_nbr_images = 3;
	public Boolean 		nbr_images_edit = true;
	public Boolean 		nbr_images_show = false;
	public String 		nbr_images_label = "No. of frames in icon";
	public Validator 	nbr_images_validator = Validator.getValidator(int.class, 1, 3);

	public String 		_0019_filename = "";
	public Boolean 		filename_edit = true;
	public Boolean 		filename_show = false;
	public String 		filename_label = "Image filename";
	transient public EditorJFileChooser filename_editor = new EditorJFileChooser();

	public DButton(){
		super();
		componentClass = "GButton";
		set_name(NameGen.instance().getNext("button"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));
		_0015_text = "Button face text";
		_0024_width = 80;
		_0025_height = 20;
		text_tooltip = "text to show on button";
		face = new RoundRectangle2D.Float(0, 0, _0024_width, _0025_height, mitre, mitre);
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0020_x, _0021_y);
		g.setTransform(af);

		((RoundRectangle2D) face).setRoundRect(0, 0, _0024_width, _0025_height, mitre, mitre);	
		g.setStroke(stdStroke);
		g.setColor(btnBack);
		g.fill(face);			
		g.setColor(blackEdge);
		g.draw(face);
		g.drawString(this._0005_name, 6, _0025_height/2 +4 );
		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent){
		String s;
		s = Messages.build(CTOR_GBUTTON_1, _0005_name, "this", 
				_0015_text, _0020_x, _0021_y, _0024_width, _0025_height);
		s += Messages.build(ADD_HANDLER, _0005_name, "this", _0101_eventHandler);
		return s;
	}
	
	public void updateIconUsage(){
		nbr_images_show = filename_show = _0017_icon;
		propertyModel.createProperties(this);
		propertyModel.fireTableChanged(new TableModelEvent(propertyModel));
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0005_name);
		IdGen.instance().add(id[0]);
		filename_editor = new EditorJFileChooser();
		mitre = 6.0f;
		face = new RoundRectangle2D.Float(0, 0, _0024_width, _0025_height, mitre, mitre);
	}
}
