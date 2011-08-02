/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package g4p.tool.gui;

import g4p.tool.GTconstants;
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
public class WindowView extends JPanel 
implements  MouseListener, MouseMotionListener, GTconstants{

	private DBase window = null;
	private ITabView tabCtrl;

	private DBase isOver, selected;

	private MutableDBase mdb = new MutableDBase();
	
	private float scale = 1.0f;
	
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
		window.draw(g2, af, selected);
		g2.setTransform(orgAF);
	}

	public void isOver(MutableDBase m, int x, int y){
		scale = ((DWindow)window)._0014_Display_scale / 100.0f;
		int sx = Math.round(x / scale);
		int sy = Math.round(y / scale);
		mdb.reset();
		window.isOver(m, x, y);
	}
	
	public void setSelected(DBase comp){
		selected = comp;
	}
	
	public DBase isOver(int x, int y){
		scale = ((DWindow)window)._0014_Display_scale / 100.0f;
		int sx = Math.round(x / scale);
		int sy = Math.round(y / scale);
		mdb.reset();
		if(window.isOver(sx, sy)){
			findIsOver(mdb, window, sx, sy);
		}
		return (mdb == null) ? null : mdb.comp;
	}

	private void findIsOver(MutableDBase m, DBase comp, int sx, int sy){
		if(comp.isSelectable() && comp.isOver(sx, sy)){
			int a = comp.getSize();
			if(a < m.area){
				m.area = a;
				m.comp = comp;
			}
		}
		Enumeration<?> e = comp.children();
		while(e.hasMoreElements()){
			findIsOver(m, (DBase)e.nextElement(), sx - comp.get_x(), sy - comp.get_y());
		}
	}

	// A component has been updated
	public void UpdateComponent(DBase comp) {
		comp.update();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(isOver != selected){
			selected = isOver;
			tabCtrl.selectedComponentHasChanged(selected);
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		isOver = isOver(e.getX(), e.getY());
//		selected = isOver;
//		tabCtrl.selectedComponentHasChanged(selected);
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
		System.out.print(Messages.build("Mouse at [{0}, {1}]", e.getX(), e.getY()));
		System.out.println(" :: " + isOver(e.getX(), e.getY()));
		isOver = isOver(e.getX(), e.getY());
	}


	//	Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	//	setCursor(cursor);

	public static class MutableDBase{
		public DBase comp = null;
		public int area = Integer.MAX_VALUE;

		public MutableDBase(){	}
		
		public void reset(){
			comp = null;
			area = Integer.MAX_VALUE;
		}
		
		public void reset(DBase comp){
			this.comp = comp;
			area = comp.getSize();
		}
	}
}
