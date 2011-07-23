package g4p.tool.components;

import g4p.tool.gui.propertygrid.Validator;

/**
 * This is the base class for all components that ahve a viual intefeace.
 * 
 * @author Peter Lager
 *
 */
public abstract class DCore extends DBase {

	public String _1005_name = "name";
	public Validator name_validator = Validator.getValidator("COMPONENT_NAME");

	public int _1020_width = 100;
	public Validator width_validator = Validator.getValidator(int.class, 20, 9999);
	public int _1021_height = 20;
	public Validator height_validator = width_validator;


	public DCore(){
		super();
	}
	
	public void setName(String name){
		_1005_name = name;
	}
	
	public void setWidth(int width){
		_1020_width = width;
	}
	
	public void setHeight(int height){
		_1021_height = height;
	}
	
	public String toString(){
//		return Messages.build("Name {0}, X {1},  Y {2},  Width {3,}  Height {4},  Visible {5} ", _1005_name, _1010_x, _1011_y, _1020_width, _1021_height, x_validator);
//		return Messages.build("Name {0}, X {1},  Y {2},  Width {3,}  Height {4},  Visible {5} ", _1005_name, _1010_x, _1011_y, _1020_width, _1021_height, _1200_visible);
		return _1005_name;
	}

}