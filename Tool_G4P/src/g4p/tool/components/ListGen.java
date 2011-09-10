package g4p.tool.components;

import g4p.tool.TDataConstants;

import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;

public class ListGen implements TDataConstants {


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

	private ListGen(){
		cbList = new HashMap<Integer, DefaultComboBoxModel>();
		makeColourSchemeSelection(COLOUR_SCHEME);
		makeCursorShapeSelection(CURSOR_CHANGER);
		makeGWSliderSkinSelection(SLIDER_SKIN);
		makeRendererSelection(RENDERER);
	}
	
	private void makeRendererSelection(int type) {
		String[] s = new String[] {"P2D", "JAVA2D", "P3D", "OPENGL"};		
		cbList.put(type,  new DefaultComboBoxModel(s));
	}

	private void makeColourSchemeSelection(int type){
		String[] s = new String[] { "BLUE_SCHEME", "GREEN_SCHEME", 
				"RED_SCHEME", "PURPLE_SCHEME", "YELLOW_SCHEME", 
				"CYAN_SCHEME", "GREY_SCHEME" };
		cbList.put(type,  new DefaultComboBoxModel(s));
	}
	
	private void makeCursorShapeSelection(int type){
		String[] s = new String[] { "ARROW", "CROSS", 
				"HAND", "MOVE", "TEXT", "WAIT" };
		cbList.put(type,  new DefaultComboBoxModel(s));
	}

	private void makeGWSliderSkinSelection(int type){
		String[] s = new String[] { "gwSlider", "blue18px", 
				"green_red20px", "purple18px", "red_yellow18px" };
		cbList.put(type,  new DefaultComboBoxModel(s));
	}

	
	public DefaultComboBoxModel getComboBoxModel(int key){
		return cbList.get(key);
	}

}