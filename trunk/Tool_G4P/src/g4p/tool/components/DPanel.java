package g4p.tool.components;

public class DPanel extends DCoreText {

	public DPanel(){
		super();
		allowsChildren = true;
		set_name(NameGen.instance().getNext("panel"));
//		System.out.println("ctor DPanel()   " + _0005_name);
	}

}
