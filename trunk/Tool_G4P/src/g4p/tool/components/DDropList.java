package g4p.tool.components;

import g4p.tool.Messages;
import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorStringList;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DDropList extends DBase {

	public int	 		_0053_nbr_rows = 3;
	public Boolean 		nbr_rows_edit = true;
	public Boolean 		nbr_rows_show = true;
	public String 		nbr_rows_label = "Max rows to show";
	public Validator 	nbr_rows_validator = Validator.getValidator(int.class, 2, 10);

	public int	 		_0054_selected = 0;
	public Boolean 		selected_edit = true;
	public Boolean 		selected_show = true;
	public String 		selected_label = "Selected row";
	public String 		selected_tooltip = "Rows start at zero";
	public Validator 	selected_validator = Validator.getValidator(int.class, 0, 50);

	public String 		_0052_list_file;
	public Boolean 		list_file_edit = true;
	public Boolean 		list_file_show = true;
	public String 		list_file_label = "Option list file";
	public String 		list_file_tooltip = "Do not change the filename!";
	transient public 	EditorBase list_file_editor = new EditorStringList();

	
	public DDropList(){
		super();
		componentClass = "GDropList";
		set_name(NameGen.instance().getNext("dropList"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_click"));

		_0052_list_file = "list_" + id[0];
		_0130_width = 90;
		_0131_height = 18;
		opaque_show = false;
		
		width_validator = Validator.getValidator(int.class, 40, 9999);
		height_validator = Validator.getValidator(int.class, 40, 9999);
		
	}
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		// Draw background for item list
		if(this == selected && _0053_nbr_rows > 1){
			g.setColor(DBase.jpalette[6]);
			g.drawRect(0, _0131_height, _0130_width, _0131_height * _0053_nbr_rows);
			g.setColor(comboDropEdge);
			for(int i = 1; i <= _0053_nbr_rows; i++)
				g.drawRect(0, _0131_height * i, _0130_width, _0131_height);
		}
		// Main text back
		g.setColor(DBase.jpalette[5]);
		g.fillRect(0, 0, _0130_width, _0131_height);
		// Draw thumb
		g.setColor(DBase.jpalette[0]);
		g.fillRect(_0130_width - _0131_height, 0, _0131_height, _0131_height);
		
		g.setColor(DBase.jpalette[2]);
		g.drawRect(0, 0, _0130_width, _0131_height);
		g.drawString(this._0010_name, 4, 12 );

		if(this == selected)
			drawSelector(g);
		else {
			g.setColor(dashedEdge);
			g.setStroke(dashed);
			g.drawRect(0, 0, _0130_width, _0131_height);		
		}
		g.setTransform(paf);
	}
	
	protected String get_creator(DBase parent, String window){
		String s = "";
		s = Messages.build(CTOR_DROPLIST, _0010_name, window, 
					 $(_0120_x), $(_0121_y), $(_0130_width), $(_0131_height * _0053_nbr_rows) , $(_0053_nbr_rows));	
		s += Messages.build(CTOR_SET_LIST,  _0010_name, _0052_list_file, $(_0054_selected));
		s += super.get_creator(parent, window);
		
		s += Messages.build(ADD_HANDLER, _0010_name, "this", _0012_eventHandler);
		return s;
	}
	
	protected void read(){
		super.read();
		list_file_editor = new EditorStringList();		
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		read();
	}


}
