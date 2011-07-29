/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g4p.tool.gui;

import g4p.tool.components.DBase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Enumeration;

import javax.swing.JPanel;

/**
 *
 * @author Peter
 */
public class WindowView extends JPanel {//implements Comparable{

	private DBase window = null;
	private ITabView tabCtrl;
	
	public WindowView(ITabView pane, DBase window){
		this.window = window;
		this.tabCtrl = pane;
		this.setBackground(Color.LIGHT_GRAY);
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

	// A component has been updated
	public void UpdateComponent(DBase comp) {
		// TODO Auto-generated method stub
		
	}


	
	
}
