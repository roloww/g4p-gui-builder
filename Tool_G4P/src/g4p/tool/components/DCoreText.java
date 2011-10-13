package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

@SuppressWarnings("serial")
public class DCoreText extends DCore {

	public String 		_0020_text = "label text";
	public String 		text_label = "Text";
	public String 		text_tooltip = "component label text";
	public Boolean 		text_edit = true;
	public Boolean 		text_show = true;
	public Validator 	text_validator = Validator.getDefaultValidator(String.class);
	public String		text_updater = "textChanged";
	
	public DCoreText(){
		super();
	}

	// Override this method if needed
	public void textChanged(){
		// Do nothing here
	}
	
	public String get_text(){
		return _0020_text;
	}

}
