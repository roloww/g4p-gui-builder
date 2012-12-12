package g4p.tool.components;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DTextArea extends DTextField {

	public Boolean 		_0036_vert_scrollbar = false;
	public String		vert_scrollbar_label = "Horizontal scrollbar?";
	public Boolean 		vert_scrollbar_edit = true;
	public Boolean 		vert_scrollbar_show = true;


	public DTextArea(){
		super();
		componentClass = "GTextArea";
		set_name(NameGen.instance().getNext("textarea"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_change"));
		_0130_width = 160;
		_0131_height = 80;
		_0060_opaque  = true;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		String sbpolicy = "G4P.SCROLLBARS_NONE";
		if(_0035_horz_scrollbar || _0036_vert_scrollbar){
			if(_0035_horz_scrollbar && _0036_vert_scrollbar)
				sbpolicy = "G4P.SCROLLBARS_BOTH";
			else if(_0035_horz_scrollbar)
				sbpolicy = "G4P.SCROLLBARS_HORIZONTAL_ONLY";
			else 
				sbpolicy = "G4P.SCROLLBARS_VERTICAL_ONLY";
			if(_0037_hide_scrollbar)
				sbpolicy += " | G4P.SCROLLBARS_AUTOHIDE";
		}
		s = Messages.build(CTOR_GTEXTAREA, _0010_name, window, 
				_0120_x, _0121_y, _0130_width, _0131_height, sbpolicy);
		if(_0030_text.length() > 0)
			s += Messages.build(SET_TEXT, _0010_name, _0030_text);
		if(_0031_dtext.length() > 0)
			s += Messages.build(SET_DEFAULT_TEXT, _0010_name, _0031_dtext);
		if(!_0060_opaque)
			s += Messages.build(SET_OPAQUE, _0060_opaque);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		if(_0060_opaque){
			g.setColor(DBase.jpalette[6]);
			g.fillRect(0, 0, _0130_width, _0131_height);
		}
		g.setColor(DBase.jpalette[6]);
		g.fillRect(1, 1, _0130_width-2, _0131_height-2);
		g.setStroke(stdStroke);

//		g.setStroke(stdStroke);
//		g.setColor(txfBack);
//		g.fillRect(0, 0, _0130_width, _0131_height);
//		g.setColor(blackEdge);
//		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.setColor(DBase.jpalette[2]);
		g.drawString(this._0010_name, 4, 12 );

		g.setColor(DBase.jpalette[3]);
		if(_0035_horz_scrollbar)
			g.fillRect(2, _0131_height - 12, _0130_width-4, 10);
		if(_0036_vert_scrollbar)
			g.fillRect(_0130_width - 12, 2, 10, _0131_height - 12);
		
		
		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(DASHED_EDGE_COLOR);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0130_width, _0131_height);		
		}

		g.setTransform(paf);
	}
	
	protected void read(){
		super.read();
	}

	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}
	
}
