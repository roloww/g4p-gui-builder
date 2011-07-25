package g4p.tool.components;

public class DSketchWindow extends DCore {

	
	public Boolean name_edit = new Boolean(false);
	
	
	public DSketchWindow(){
		super();
		_1005_name = "Main Display";
		allowsChildren = true;
	}

	public String toString(){
		return "Window (" + _1005_name +")";
	}

}
