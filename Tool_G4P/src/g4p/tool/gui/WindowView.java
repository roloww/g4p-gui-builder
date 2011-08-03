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
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

/**
 *
 * @author Peter
 */
public class WindowView extends JPanel 
implements  MouseListener, MouseMotionListener, GTconstants {

	private DBase window = null;
	private ITabView tabCtrl;

	private DBase selected;

	private MutableDBase mdb = new MutableDBase();

	private float scale = 1.0f;
	private int startX, startY;
	private int deltaX, deltaY;


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
		scale = ((DWindow)window)._0014_Display_scale / 100.0f;
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
		window.isOver(m, sx, sy);
		if(m.comp != null){
			m.orgX = m.comp.get_x();
			m.orgY = m.comp.get_y();
			m.orgW = m.comp.get_width();
			m.orgH = m.comp.get_height();
		}
	}

	public void setSelected(DBase comp){
		selected = comp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(mdb.comp != selected){
			selected = mdb.comp;
			tabCtrl.componentHasBeenSelected(selected);
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
		isOver(mdb, startX, startY);
		if(mdb.comp != null && mdb.comp.isSelectable()){
			selected = mdb.comp;
			tabCtrl.componentHasBeenSelected(selected);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(mdb.comp != null && mdb.comp.isResizeable()){
			deltaX = Math.round((e.getX() - startX) / scale);
			deltaY = Math.round((e.getY() - startY) / scale);
			if(mdb.comp.isResizeable()){
				switch(mdb.selID){
				case OVER_HORZ:
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					mdb.comp.set_width(mdb.orgW + deltaX);
					break;
				case OVER_VERT:
					setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
					mdb.comp.set_height(mdb.orgH + deltaY);
					break;
				case OVER_DIAG:
					setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
					mdb.comp.set_width(mdb.orgW + deltaX);
					mdb.comp.set_height(mdb.orgH + deltaY);
					break;
				}
			}
			if(mdb.comp.isMoveable() && mdb.selID == OVER_COMP){
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				mdb.comp.set_x(mdb.orgX + deltaX);
				mdb.comp.set_y(mdb.orgY + deltaY);
			}
			tabCtrl.selectedComponentPropertyChange(mdb.comp);
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public static class MutableDBase{
		public DBase comp = null;
		public int area = Integer.MAX_VALUE;
		public int selID = OVER_NONE;
		public int orgX,orgY,orgW,orgH;

		public MutableDBase(){	}

		public void reset(){
			comp = null;
			area = Integer.MAX_VALUE;
			selID = OVER_NONE;
		}

		public void reset(DBase comp){
			this.comp = comp;
			area = comp.getSize();
			selID = OVER_NONE;
		}

		public String toString(){
			return Messages.build("{0}   over  {1}", comp, selID);
		}
	}

}
