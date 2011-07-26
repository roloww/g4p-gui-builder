package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

public final class DWindow extends DBase {

	public Validator name_validator = Validator.getValidator("COMPONENT_NAME");

	public Validator x_validator = Validator.getValidator(int.class, -9999, 9999);
	public Validator y_validator = x_validator;
	public Validator width_validator = Validator.getValidator(int.class, 20, 9999);
	public Validator height_validator = width_validator;

	public String _0010_title = "Window title";
	
	/**
	 * 
	 */
	public DWindow() {
		super();
		allowsChildren = true;
		setName(NameGen.instance().getNext("window"));
	}

	public String getTitle(){
		return _0010_title;
	}
	
	public String toString(){
		return "Window (" + _0005_name +")";
	}
}
