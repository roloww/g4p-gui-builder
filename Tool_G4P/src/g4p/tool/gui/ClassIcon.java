package g4p.tool.gui;

import java.util.HashMap;

import javax.swing.Icon;

public class ClassIcon {

	
	private static ClassIcon instance = null;
	
	public static ClassIcon instance(){
		if(instance == null){
			instance = new ClassIcon();
		}
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap<Class, Icon> icons = null;
	
	private Icon unknown = null;
	
	@SuppressWarnings("rawtypes")
	private ClassIcon(){
		icons = new HashMap<Class, Icon>();
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private void addElement(Class c, String fname){
		icons.put(c, new javax.swing.ImageIcon(getClass().getResource("/g4p/" + fname)));
	}
	
	public void addElement(@SuppressWarnings("rawtypes") Class c, Icon icon){
		icons.put(c, icon);
	}
	
	public Icon getIcon(@SuppressWarnings("rawtypes") Class c){
		Icon icon = icons.get(c);
		return (icon == null) ? unknown : icon;
	}
	
}
