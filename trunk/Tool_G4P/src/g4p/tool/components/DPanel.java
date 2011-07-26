package g4p.tool.components;

public class DPanel extends DCore {

	public String _0010_title = "Panel title";

	public DPanel(){
		super();
		allowsChildren = true;
		setName(NameGen.instance().getNext("panel"));
	}

}
