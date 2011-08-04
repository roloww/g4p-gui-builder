package g4p.tool.components;

import g4p.tool.GTconstants;

import java.util.Arrays;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerListModel;

public class ListGen implements GTconstants {


	private static ListGen instance;

	public static ListGen instance(){
		if(instance == null){
			instance = new ListGen();
		}
		return instance;
	}

	// ==============================================================
	// ==============================================================

	private HashMap<Integer, DefaultComboBoxModel> cbList;
	private HashMap<Integer, SpinnerListModel> spList;

	private ListGen(){
		cbList = new HashMap<Integer, DefaultComboBoxModel>();
		spList = new HashMap<Integer, SpinnerListModel>();
		makeColourSchemeSelection(COLOUR_SCHEME);
		makeCursorShapeSelection(CURSOR_OVER);
	}

	private void makeColourSchemeSelection(int type){
		String[] s = new String[] { "BLUE_SCHEME", "GREEN_SCHEME", 
				"RED_SCHEME", "PURPLE_SCHEME", "YELLOW_SCHEME", 
				"CYAN_SCHEME", "GREY_SCHEME" };
		cbList.put(type,  new DefaultComboBoxModel(s));
		spList.put(type, new SpinnerListModel(Arrays.asList(s)));
	}
	
	private void makeCursorShapeSelection(int type){
		String[] s = new String[] { "ARROW", "CROSS", 
				"HAND", "MOVE", "TEXT", "WAIT" };
		cbList.put(type,  new DefaultComboBoxModel(s));
		spList.put(type, new SpinnerListModel(Arrays.asList(s)));
	}

	public DefaultComboBoxModel getComboBoxModel(int key){
		return cbList.get(key);
	}
	
	public SpinnerListModel getSpinnerModel(int key){
		return spList.get(key);
	}

}
