package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

public final class DSketchWindow extends DBase {

	public Boolean name_edit = false;
	public Boolean x_show = false;
	public Boolean y_show = false;
	public Boolean width_show = true;
	public Boolean height_show = true;
	
	public Validator width_validator = Validator.getValidator(int.class, 40, 9999);
	public Validator height_validator = width_validator;

	/**
	 * 
	 */
	public DSketchWindow() {
		super();
		allowsChildren = true;
		_0005_name = "Sketch_Display";
	}

	public String getTitle() { return _0005_name; }

	public String toString(){
		return "Window (" + _0005_name +")";
	}

}
