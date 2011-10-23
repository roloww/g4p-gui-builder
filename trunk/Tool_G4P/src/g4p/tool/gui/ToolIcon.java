package g4p.tool.gui;

import java.util.HashMap;

import javax.swing.Icon;

/**
 * Singleton class to manage icons used by the tool.
 * 
 * @author Peter Lager
 *
 */
public class ToolIcon {

	private static ToolIcon instance = null;
	
	public static ToolIcon instance(){
		if(instance == null){
			instance = new ToolIcon();
		}
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap<Class, Icon> classIcons = null;
	private HashMap<String, Icon> namedIcons = null;
	
	private Icon unknown = null;
	

	@SuppressWarnings({ "rawtypes" })
	private ToolIcon(){
		classIcons = new HashMap<Class, Icon>();
		namedIcons = new HashMap<String, Icon>();
	}
	
	
	public void addElement(String n, Icon icon){
		namedIcons.put(n, icon);
	}

	public Icon getIcon(String n){
		Icon icon = namedIcons.get(n);
		return (icon == null) ? unknown : icon;
	}

	@SuppressWarnings("rawtypes")
	public void addElement(Class c, Icon icon){
		classIcons.put(c, icon);
	}
	
	@SuppressWarnings("rawtypes")
	public Icon getIcon(Class c){
		Icon icon = classIcons.get(c);
		return (icon == null) ? unknown : icon;
	}
	
}
