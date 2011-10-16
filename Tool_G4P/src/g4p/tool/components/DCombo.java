package g4p.tool.components;

import g4p.tool.gui.GuiDesigner;
import g4p.tool.gui.propertygrid.Validator;

@SuppressWarnings("serial")
public class DCombo extends DCore {

	
	private String listFile;

	public String height_updator = "heightChanged";
	
	
	
	public int	 		_0053_nbr_rows = 1;
	public Boolean 		nbr_rows_edit = true;
	public Boolean 		nbr_rows_show = false;
	public String 		nbr_rows_label = "Nbr of rows";
//	public String 		nbr_rows_updater = "nbrRowsChanged";
	public Validator 	nbr_rows_validator = Validator.getValidator(int.class, 2, 10);

	public DCombo(){
		super();
		componentClass = "GCombo";
		listFile = "list" + id[0];
		set_name(NameGen.instance().getNext("option"));
		set_event_name(NameGen.instance().getNext(get_name()+ "_Click"));

		_0131_height = GuiDesigner.metrics().getHeight() + 2;
		height_edit = false;
	}
	
	
	public void heightChanged(){
		_0131_height = GuiDesigner.metrics().getHeight() + 2;		
	}
	
	
}
