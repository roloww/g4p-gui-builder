package g4p.tool;

import g4p.tool.components.DButton;

public class Test implements TDataConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DButton b = new DButton();
		System.out.println(b.get_declaration());
		System.out.println(b.get_event_header());
		
	}

}
