package g4p.tool.gui;

import g4p.tool.components.*;


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
	
	private HashMap<Class, Icon> icons = null;
	
	private Icon unknown = null;
	
	private ClassIcon(){
		icons = new HashMap<Class, Icon>();
		unknown = new javax.swing.ImageIcon(getClass().getResource("/g4p/toolX.png")); 
		addElement(DWindow.class, "toolWindow.png");
		addElement(DPanel.class, "toolPanel.png");
		addElement(DButton.class, "toolButton.png");
//		addElement(DImageButton.class, "toolButtonImg.png");
//		addElement(DLabel.class, "toolLabel.png");
//		addElement(DTextfield.class, "toolTextField.png");
//		addElement(DHorzSlider.class, "toolSliderH.png");
//		addElement(DVertSlider.class, "toolSliderV.png");
//		addElement(DCoolSlider.class, "toolCoolSlider.png");
//		addElement(DKnob.class, "toolKnob.png");
//		addElement(DCheckbox.class, "toolCheckbox.png");
//		addElement(DOption.class, "toolOption.png");
//		addElement(DOptGroup.class, "toolOptGroup.png");
//		addElement(DCombo.class, "toolCombo.png");
//		addElement(DTimer.class, "toolTimer.png");
//		addElement(DTimer.class, "toolTimer.png");
	}
	
	private void addElement(Class c, String fname){
		icons.put(c, new javax.swing.ImageIcon(getClass().getResource("/g4p/" + fname)));
	}
	
	public Icon getIcon(Class c){
		Icon icon = icons.get(c);
		return (icon == null) ? unknown : icon;
	}
}
