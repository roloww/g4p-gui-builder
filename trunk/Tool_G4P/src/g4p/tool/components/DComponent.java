package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

public class DComponent extends DCore {

	// Variable name

	public int _1010_x = 0;
	public Validator x_validator = Validator.getValidator(int.class, 0, 9999);
	public int _1011_y = 0;
	public Validator y_validator = x_validator;
	
	public boolean _1200_visible = true;

	
	public String toString(){
//		return Messages.build("Name {0}, X {1},  Y {2},  Width {3,}  Height {4},  Visible {5} ", _1005_name, _1010_x, _1011_y, _1020_width, _1021_height, x_validator);
//		return Messages.build("Name {0}, X {1},  Y {2},  Width {3,}  Height {4},  Visible {5} ", _1005_name, _1010_x, _1011_y, _1020_width, _1021_height, _1200_visible);
		return _1005_name;
	}
	
}
