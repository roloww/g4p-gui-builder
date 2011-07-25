package g4p.tool.components;


public final class DApplication extends DBase {

	public Boolean name_edit = false;
	public Boolean x_show = false;
	public Boolean y_show = false;
	public Boolean width_show = false;
	public Boolean height_show = false;
	
	/**
	 * 
	 */
	public DApplication() {
		super();
		allowsChildren = true;
		_0005_name = "APPLICATION";
	}


	public String toString(){
		return _0005_name;
	}
}
