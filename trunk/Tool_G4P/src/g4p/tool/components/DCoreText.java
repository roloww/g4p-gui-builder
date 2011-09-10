package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

@SuppressWarnings("serial")
public class DCoreText extends DCore {

	public String 		_0015_text = "label text";
	public String 		text_label = "Text";
	public String 		text_tooltip = "component label text";
	public Boolean 		text_edit = true;
	public Boolean 		text_show = true;
	public Validator 	text_validator = Validator.getDefaultValidator(String.class);

	public DCoreText(){
		super();
//		System.out.println("\tDCoreText() ctor");
	}

	public String get_text(){
		return _0015_text;
	}

}
