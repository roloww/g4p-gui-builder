package g4p.tool.components;

public class DCheckbox extends DCoreSelectable{

	
	public DCheckbox(){
		super();
		set_name(NameGen.instance().getNext("checkbox"));
	}

}
