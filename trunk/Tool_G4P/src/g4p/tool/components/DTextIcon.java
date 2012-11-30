package g4p.tool.components;

import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

@SuppressWarnings("serial")
public class DTextIcon extends DText {

	public String 		_0035_icon_file = "";
	transient public 	EditorJFileChooser icon_file_editor = new EditorJFileChooser();
	public Boolean 		icon_file_edit = true;
	public Boolean 		icon_file_show = false;
	public String 		icon_file_label = "Icon file";
	public String 		icon_file_updater = "iconChanged";

	public int	 		_0037_nbr_tiles = 3;
	public Boolean 		nbr_tiles_edit = true;
	public Boolean 		nbr_tiles_show = false;
	public String 		nbr_tiles_label = "Nbr of tiles in icon";
	public String 		nbr_tiles_updater = "nbrTilesChanged";
	public Validator 	nbr_tiles_validator = Validator.getValidator(int.class, 1, 3);

	public String 		_0038_icon_alignment = "LEFT";
	transient public 	EditorBase icon_x_alignment_editor = new EditorJComboBox(H_ALIGN_2);
	public Boolean 		icon_x_alignment_edit = true;
	public Boolean 		icon_x_alignment_show = false;
	public String 		icon_x_alignment_label = "Icon horz alignment";
	public String 		icon_x_alignment_updater = "iconAlignChanged";

	public String 		_0039_icon_y_alignment = "LEFT";
	transient public 	EditorBase icon_y_alignment_editor = new EditorJComboBox(H_ALIGN_2);
	public Boolean 		icon_y_alignment_edit = true;
	public Boolean 		icon_y_alignment_show = false;
	public String 		icon_y_alignment_label = "Icon horz alignment";
	public String 		icon_y_alignment_updater = "iconAlignChanged";

	
	public DTextIcon(){
		super();
	}

	// Override this method if needed
	public void textChanged(){
		// Do nothing here
	}
	
	public String get_text(){
		return _0029_text;
	}

}
