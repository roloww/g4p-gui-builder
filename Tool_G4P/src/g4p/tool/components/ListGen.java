package g4p.tool.components;

import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;

public class ListGen {


	private static ListGen instance;

	public static ListGen instance(){
		if(instance == null){
			instance = new ListGen();
		}
		return instance;
	}

	// ==============================================================
	// ==============================================================

	private HashMap<String, DefaultComboBoxModel> lists;

	private ListGen(){
		lists = new HashMap<String, DefaultComboBoxModel>();
		lists.put("COLOUR_SCHEME", makeColourSchemeSection() );
	}

	private DefaultComboBoxModel makeColourSchemeSection(){
		String[] s = new String[] { "BLUE_SCHEME", "GREEN_SCHEME", 
				"RED_SCHEME", "PURPLE_SCHEME", "YELLOW_SCHEME", 
				"CYAN_SCHEME", "GREY_SCHEME" };
		return new DefaultComboBoxModel(s);
	}

	public DefaultComboBoxModel getModel(String key){
		System.out.println(lists.get(key));
		return lists.get(key);
	}
}
