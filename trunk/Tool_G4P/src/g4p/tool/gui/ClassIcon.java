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
	
	@SuppressWarnings("unchecked")
	private HashMap<Class, Icon> icons = null;
	
	private Icon unknown = null;
	

	@SuppressWarnings("unchecked")
	private ClassIcon(){
		icons = new HashMap<Class, Icon>();
	}
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void addElement(Class c, String fname){
		icons.put(c, new javax.swing.ImageIcon(getClass().getResource("/g4p/" + fname)));
	}
	
	@SuppressWarnings("unchecked")
	public void addElement(Class c, Icon icon){
		icons.put(c, icon);
	}
	
	@SuppressWarnings("unchecked")
	public Icon getIcon(Class c){
		Icon icon = icons.get(c);
		return (icon == null) ? unknown : icon;
	}
	
}
