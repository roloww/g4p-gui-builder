package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.ToolImage;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Enumeration;

@SuppressWarnings("serial")
public class DOption extends DCoreSelectable {

	public String selected_updater = "selectionChanged";

	public DOption(){
		super();
		text_label = "Option display text";
		componentClass = "GOption";
		set_name(NameGen.instance().getNext("option"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_clicked"));
		// Set up text
		_0030_text = "option text";
		_0031_text_x_alignment = "LEFT";
		textHAlign = ListGen.instance().getIndexOf(H_ALIGN_3, _0031_text_x_alignment);
		textVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0032_text_y_alignment);
		// Set up icon
		icon = ToolImage.getImage("OP_ICON");
		_0036_nbr_tiles = 2;
		_0037_icon_x_alignment = "LEFT";
		iconHAlign = ListGen.instance().getIndexOf(H_ALIGN_2, _0037_icon_x_alignment);
		iconVAlign = ListGen.instance().getIndexOf(V_ALIGN, _0038_icon_y_alignment);
		iconWidth = icon.getWidth() / _0036_nbr_tiles;
		iconHeight = icon.getHeight();
		icon_x_alignment_show = true;
		icon_y_alignment_show = true;
		icon_file_show = false;
		nbr_tiles_show = false;
		iconAlignChanged();
	}

	/**
	 * Get the creator statement var = new Foo(...);
	 * @return
	 */
	protected String get_creator(DBase parent, String window){
		String s;
		s = Messages.build(CTOR_GOPTION, _0010_name, window, 
				$(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height));
		s += super.get_creator(parent, window);
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);		
		return s;
	}

	protected boolean isIconAlignDefaults(){
		return _0037_icon_x_alignment.equals("LEFT") && _0038_icon_y_alignment.equals("MIDDLE");
	}

	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);

		if(_0060_opaque){
			g.setColor(DBase.jpalette[6]);
			g.fillRect(0, 0, _0130_width, _0131_height);
		}
		g.setStroke(stdStroke);

		super.draw(g, paf, selected);

		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(DASHED_EDGE_COLOR);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0130_width, _0131_height);		
		}
		g.setTransform(paf);
	}

	public void selectionChanged(){
		// test to see if this object has been selected
		// and if so de-select all then select this
		DToggleGroup optg = (DToggleGroup) this.getParent();
		DOption opt;
		Enumeration<?> e = optg.children();
		while(e.hasMoreElements()){
			opt = (DOption)e.nextElement();
			opt.setSelected(false);
			opt.iconNo = 0;
		}
		_0101_selected = true;
		iconNo = 1;
	}
	
	protected void read(){
		super.read();
		icon = ToolImage.getImage("OP_ICON");
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}
	
}
