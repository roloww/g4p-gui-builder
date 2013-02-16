package g4p.tool.controls;

import g4p.tool.Messages;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DTextArea extends DTextField {

	public Boolean 		_0187_vert_scrollbar = false;
	public String		vert_scrollbar_label = "Vertical scrollbar?";
	public Boolean 		vert_scrollbar_edit = true;
	public Boolean 		vert_scrollbar_show = true;


	public DTextArea(){
		super();
		componentClass = "GTextArea";
		set_name(NameGen.instance().getNext("textarea"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_change"));
		_0826_width = 160;
		_0827_height = 80;
		_0600_opaque  = true;
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		String sbpolicy = "G4P.SCROLLBARS_NONE";
		if(_0186_horz_scrollbar || _0187_vert_scrollbar){
			if(_0186_horz_scrollbar && _0187_vert_scrollbar)
				sbpolicy = "G4P.SCROLLBARS_BOTH";
			else if(_0186_horz_scrollbar)
				sbpolicy = "G4P.SCROLLBARS_HORIZONTAL_ONLY";
			else 
				sbpolicy = "G4P.SCROLLBARS_VERTICAL_ONLY";
			if(_0188_hide_scrollbar)
				sbpolicy += " | G4P.SCROLLBARS_AUTOHIDE";
		}
		s = Messages.build(CTOR_GTEXTAREA, _0010_name, window, 
				_0820_x, _0821_y, _0826_width, _0827_height, sbpolicy);
		if(_0130_text.length() > 0)
			s += Messages.build(SET_TEXT, _0010_name, _0130_text);
		if(_0132_dtext.length() > 0)
			s += Messages.build(SET_DEFAULT_TEXT, _0010_name, _0132_dtext);
		if(!_0600_opaque)
			s += Messages.build(SET_OPAQUE, _0600_opaque);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0020_eventHandler);
		return s;
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0820_x, _0821_y);
		g.setTransform(af);
		
		if(_0600_opaque){
			g.setColor(DBase.jpalette[6]);
			g.fillRect(0, 0, _0826_width, _0827_height);
		}
		g.setColor(DBase.jpalette[6]);
		g.fillRect(1, 1, _0826_width-2, _0827_height-2);
		g.setStroke(stdStroke);

//		g.setStroke(stdStroke);
//		g.setColor(txfBack);
//		g.fillRect(0, 0, _0130_width, _0131_height);
//		g.setColor(blackEdge);
//		g.drawRect(0, 0, _0130_width, _0131_height);
		
		g.setColor(DBase.jpalette[2]);
		g.drawString(this._0010_name, 4, 12 );

		g.setColor(DBase.jpalette[3]);
		if(_0186_horz_scrollbar)
			g.fillRect(2, _0827_height - 12, _0826_width-4, 10);
		if(_0187_vert_scrollbar)
			g.fillRect(_0826_width - 12, 2, 10, _0827_height - 12);
		
		
		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(DASHED_EDGE_COLOR);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0826_width, _0827_height);		
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