package g4p.tool.components;

import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.EditorBase;
import g4p.tool.gui.propertygrid.EditorFileList;
import g4p.tool.gui.propertygrid.EditorJComboBox;
import g4p.tool.gui.propertygrid.EditorJFileChooser;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("serial")
public class DCombo extends DCore {

	

	public String height_updator = "heightChanged";
//	public int list_size = 0;
	
	
	public int	 		_0053_nbr_rows = 5;
	public Boolean 		nbr_rows_edit = true;
	public Boolean 		nbr_rows_show = true;
	public String 		nbr_rows_label = "Nbr of rows";
	public Validator 	nbr_rows_validator = Validator.getValidator(int.class, 2, 10);

	public String 		_0052_list_file;
	public Boolean 		list_file_edit = true;
	public Boolean 		list_file_show = true;
	transient public 	EditorBase list_file_editor = new EditorFileList();

	
	public DCombo(){
		super();
		componentClass = "GCombo";
		_0052_list_file = "list_" + id[0];
		set_name(NameGen.instance().getNext("combo"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));

		width_validator = Validator.getValidator(int.class, 40, 9999);
		_0131_height = GuiDesigner.metrics().getHeight() + 2;
		height_edit = false;
	}
	
	
	public void heightChanged(){
		_0131_height = GuiDesigner.metrics().getHeight() + 2;		
	}
	
	
	public void draw(Graphics2D g, AffineTransform paf, DBase selected){
		AffineTransform af = new AffineTransform(paf);
		af.translate(_0120_x, _0121_y);
		g.setTransform(af);
		
		g.setColor(comboBack);
		g.fillRect(0, 0, _0130_width, _0131_height);
		g.setColor(comboThumb);
		g.fillRect(_0130_width - _0131_height, 0, _0131_height, _0131_height);
		
		if(this == selected && _0053_nbr_rows > 1){
			g.setColor(comboDropBack);
			g.drawRect(0, _0131_height, _0130_width, _0131_height * (_0053_nbr_rows - 1));
			g.setColor(comboDropEdge);
			for(int i = 1; i < _0053_nbr_rows; i++)
				g.drawRect(0, _0131_height * i, _0130_width, _0131_height);
		}
		g.setColor(comboEdge);
		g.drawRect(0, 0, _0130_width, _0131_height);
		g.drawString(this._0010_name, 4, 12 );

		if(this == selected)
			drawSelector(g);

		g.setTransform(paf);
	}
	
	private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		NameGen.instance().add(_0010_name);
		IdGen.instance().add(id[0]);
		list_file_editor = new EditorFileList();
	}


}
