package g4p.tool.components;

import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.Validator;


@SuppressWarnings("serial")
public class DText extends DBase {

	
	public String 		_0029_text = "label text";
	public String 		text_label = "Text";
	public String 		text_tooltip = "component label text";
	public Boolean 		text_edit = true;
	public Boolean 		text_show = true;
	public Validator 	text_validator = Validator.getDefaultValidator(String.class);
	public String		text_updater = "textChanged";

	public String 		_0033_xtAlignment = "CENTER";
	transient public 	EditorBase xtAlignment_editor = new EditorJComboBox(H_ALIGN_3);
	public Boolean 		xtAlignment_edit = true;
	public Boolean 		xtAlignment_show = true;
	public String 		xtAlignment_label = "Horz text alignment";
	public String 		xtAlignment_updater = "textAlignChanged";

	public String 		_0034_ytAlignment = "MIDDLE";
	transient public 	EditorBase ytAlignment_editor = new EditorJComboBox(V_ALIGN);
	public Boolean 		ytAlignment_edit = true;
	public Boolean 		ytAlignment_show = true;
	public String 		ytAlignment_label = "Vert text alignment";
	public String 		ytAlignment_updater = "textAlignChanged";


	public DText(){
		super();
		selectable = true;
		resizeable = true;
		moveable = true;

//		name_label = "Variable name";
//		name_tooltip = "Use Java naming rules";
//		name_edit = true;
//		
//		x_edit = y_edit = true;
//		x_show = y_show = true;	
//		width_edit = height_edit = true;
//		width_show = height_show = true;
		_0130_width = 80;
		_0131_height = 22;
		eventHandler_edit = eventHandler_show = true;
		allowsChildren = false;
		
		
	}

	
	// Override this method if needed
	public void textChanged(){
		// Do nothing here
	}
	
	public String get_text(){
		return _0029_text;
	}

}
