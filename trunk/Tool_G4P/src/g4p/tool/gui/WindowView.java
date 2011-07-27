/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g4p.tool.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Enumeration;

import g4p.tool.components.DBase;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Peter
 */
public class WindowView extends JPanel {

	private DBase window = null;
	private JTabbedPane pane = null;
	
	public WindowView(JTabbedPane pane, DBase window){
		this.pane = pane;
		this.window = window;
		
		this.setBackground(Color.WHITE);
		
		System.out.println("Window " + this.getWidth() + "  "+this.getHeight());
	}
	
	public DBase getWindowComponent(){
		return window;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		window.draw(g2);
		Enumeration<?> e = window.children();
		while(e.hasMoreElements()){
			((DBase)e.nextElement()).draw(g2);
		}
	}
}
