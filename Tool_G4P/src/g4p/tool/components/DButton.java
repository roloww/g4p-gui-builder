package g4p.tool.components;

public class DButton extends DCoreText {
	
	public DButton(){
		super();
		set_name(NameGen.instance().getNext("button"));
	}
	
}
