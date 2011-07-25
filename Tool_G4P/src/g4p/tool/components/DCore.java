package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

public class DCore extends DBase {

	public Validator name_validator = Validator.getValidator("COMPONENT_NAME");

	public Validator x_validator = Validator.getValidator(int.class, -9999, 9999);
	public Validator y_validator = x_validator;
	public Validator width_validator = Validator.getValidator(int.class, 10, 999);
	public Validator height_validator = width_validator;

	/**
	 * 
	 */
	public DCore() {
		super();
	}

	public String toString(){
		return "Window (" + _0005_name +")";
	}

}
