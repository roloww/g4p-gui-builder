package g4p.tool.components;

public class DButton extends DCoreText {
	
	public DButton(){
		super();
		setName(NameGen.instance().getNext("button"));
	}
	
	public String toString(){
		return "Button (" + _0005_name +")";
	}
}
