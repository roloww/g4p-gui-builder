package g4p.tool;

import java.io.File;

public interface TFileConstants {

	public final String SEP = File.separator;
	
	// Relative to sketch folder
	public final String PDE_TAB_NAME 			= "gui.pde";
//	public final String CONFIG_FILENAME 		= "gui_config.txt";
	public final String CONFIG_FOLDER 			= "GUI_BUILD_DATA";
	
	public final String CONFIG_FILENAME 		= CONFIG_FOLDER + SEP + "gui_config.txt";
	public final String MODEL_FILENAME 			= CONFIG_FOLDER + SEP + "gui_model.txt";
	
	// These are relative to the processing sketch folder
	public final String G4P_TOOL_DATA_FOLDER 	= "tools" + SEP + "##name##" + SEP + "data";
	public final String G4P_LIB 				= "guicomponents" + SEP + "library" + SEP + "guicomponents.jar";

}
