package g4p.tool.components;

/**
 * This class represents the who;e application. <br>
 * 
 * It will be the root node for the tree view and its children should only be windows.
 * 
 * @author Peter Lager
 *
 */
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
