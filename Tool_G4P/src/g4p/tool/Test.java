package g4p.tool;

import g4p.tool.components.DButton;
import g4p.tool.components.DKnob;
import g4p.tool.components.DOption;
import g4p.tool.components.DTextField;

public class Test implements TDataConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DOption op = new DOption();
		DButton btn = new DButton();
		DTextField txf = new DTextField();
		DKnob knb = new DKnob();

		System.out.println(op.get_event_method());
		System.out.println(btn.get_event_method());
		System.out.println(txf.get_event_method());
		System.out.println(knb.get_event_method());
		
		System.out.println();
		
		Messages.println(CTOR_GBUTTON_3, "btnStarter", "this", "Start", "image.png", 1, 30, 40, 100, 22);
		
		System.out.println(Integer.valueOf(123456));
	}

}
