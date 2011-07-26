package g4p.tool.components;

public class DPanel extends DCoreText {

	
	public DPanel(){
		super();
		allowsChildren = true;
		setName(NameGen.instance().getNext("panel"));
	}

	public String toString(){
		return "Panel (" + _0005_name +")";
	}
}
