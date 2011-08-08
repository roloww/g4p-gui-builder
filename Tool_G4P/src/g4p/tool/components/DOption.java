package g4p.tool.components;

public class DOption extends DCoreSelectable {

	
	public DOption(){
		super();
		set_name(NameGen.instance().getNext("option"));
	}
	

}
