package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

public class DCoreText extends DBase {

	public String 		_0015_text = "Display text";
	public Boolean 		text_edit = true;
	public Boolean 		text_show = true;
	public Validator 	text_validator = Validator.getDefaultValidator(String.class);

	
	
	public DCoreText(){
		super();
		allowsChildren = false;
	}

	public String get_text(){
		return _0015_text;
	}
	
}
