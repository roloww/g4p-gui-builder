package g4p.tool.controls;

import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class DImageToggleButton extends DBase {

	transient BufferedImage image = null;

	public String 		_0042_img_off = "";
	transient public 	EditorJFileChooser img_off_editor = new EditorJFileChooser();
	public Boolean 		img_off_edit = true;
	public Boolean 		img_off_show = true;
	public String 		img_off_label = "Mouse off image";
	public String 		img_off_updater = "imageChanged_off";
	
	public String 		_0044_img_over = "";
	transient public 	EditorJFileChooser img_over_editor = new EditorJFileChooser();
	public Boolean 		img_over_edit = true;
	public Boolean 		img_over_show = false;
	public String 		img_over_label = "Mouse over image";
	public String 		img_over_updater = "imageChanged_over";

	public int 			_0052_cols = 1;
	public String 		cols_label = "No. of tiles horizontally";
	public Boolean 		cols_edit = true;
	public Boolean 		cols_show = true;
	public Validator 	cols_validator = Validator.getValidator(int.class, 1, 100);

	public int 			_0053_rows = 1;
	public String 		rows_label = "No. of tiles vertically";
	public Boolean 		rows_edit = true;
	public Boolean 		rows_show = true;
	public Validator 	rows_validator = Validator.getValidator(int.class, 1, 100);

	
	public DImageToggleButton(){
		super();
		componentClass = "GImageToggleButton";
		set_name(NameGen.instance().getNext("imgTogButton"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_click"));

		width_edit = height_edit = true;
		width_show = height_show = true;
		opaque_show = false;
	}


}
