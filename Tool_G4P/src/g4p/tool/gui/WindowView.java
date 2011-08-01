/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g4p.tool.gui;

import g4p.tool.Messages;
import g4p.tool.components.DBase;
import g4p.tool.components.DWindow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.Enumeration;

import javax.swing.JPanel;

/**
 *
 * @author Peter
 */
public class WindowView extends JPanel implements  MouseListener, MouseMotionListener {

	private DBase window = null;
	private ITabView tabCtrl;
	
	private DBase isOver, selected;
	private int area;
	
	public WindowView(ITabView pane, DBase window){
		this.window = window;
		this.tabCtrl = pane;
		this.setBackground(new Color(255,255,255));
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public DBase getWindowComponent(){
		return window;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		float scale = ((DWindow)window)._0014_Display_scale / 100.0f;
		AffineTransform orgAF = g2.getTransform();
		AffineTransform af = new AffineTransform(orgAF);
		af.scale(scale, scale);
		window.draw(g2, af);
		g2.setTransform(orgAF);
	}

	public DBase isOver(int x, int y){
		float scale = ((DWindow)window)._0014_Display_scale / 100.0f;
		int sx = Math.round(x / scale);
		int sy = Math.round(y / scale);
		
		area = Integer.MAX_VALUE;
		findIsOver(window);
		return null;
	}
	
	private void findIsOver(DBase comp){
		isOver = null;
		if(comp.isSelectable() ){
			int a = comp.get_width() * comp.get_height();
			if(a < area){
				area = a;
				isOver = comp;
			}
		}
		Enumeration<?> e = comp.children();
		while(e.hasMoreElements()){
			findIsOver((DBase)e.nextElement());
		}
	}
	
	// A component has been updated
	public void UpdateComponent(DBase comp) {
		comp.update();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Messages.println("Mouse at [{0}, {1}]", e.getX(), e.getY());
		isOver(e.getX(), e.getY());
	//	if(isOver != null)
			System.out.println(isOver);
	}


//	Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
//	setCursor(cursor);

	public class MutableDBase{
		public DBase comp;
		public int area;
	}
	
}
