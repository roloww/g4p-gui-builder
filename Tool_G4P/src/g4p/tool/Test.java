package g4p.tool;

import g4p.tool.components.DButton;
import g4p.tool.components.DOption;

public class Test implements TDataConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DOption b = new DOption();
		System.out.println(b.get_declaration());
		System.out.println(b.get_event_header());
		System.out.println(b.get_event_end());
		
		
		Messages.println(CTOR_GBUTTON_3, "btnStarter", "this", "Start", "image.png", 1, 30, 40, 100, 22);
		
	}

}
