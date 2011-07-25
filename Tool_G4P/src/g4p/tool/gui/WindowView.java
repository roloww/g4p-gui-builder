/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g4p.tool.gui;

import java.awt.Color;

import g4p.tool.components.DBase;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Peter
 */
public class WindowView extends JPanel {

	DBase window = null;
	JTabbedPane pane = null;
	
	
	void WindowView(JTabbedPane pane, DBase window){
		this.window = window;
		this.pane = pane;
		
		pane.addTab("XXX", this);
		this.setBackground(Color.WHITE);
		
		System.out.println("Window " + this.getWidth()+"  "+this.getHeight());
	}
	
}
