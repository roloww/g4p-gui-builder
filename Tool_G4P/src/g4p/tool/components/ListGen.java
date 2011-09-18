package g4p.tool.components;

import g4p.tool.TDataConstants;

import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;

/**
 *  When adding a new type of list - need to modify the getValidator(type) mewethod to included it
 * @author Peter
 *
 */
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
		makeKnobControllerSelection(KNOB_CTRL);
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
	
	private void makeKnobControllerSelection(int type){
		String[] s = new String[] { "ANGULAR", "HORIZONTAL", "VERTICAL" };
		cbList.put(type,  new DefaultComboBoxModel(s));
	}

	// ================================================================
	
	public boolean hasComboModel(int type){
		return cbList.containsKey(type);
	}
	
	public DefaultComboBoxModel getComboBoxModel(int key){
		if(key == SLIDER_SKIN)
			System.out.println("Retrieving SKIN combo model");
		return cbList.get(key);
	}

}
