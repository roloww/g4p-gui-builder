package g4p.tool;

import java.io.File;

public interface TFileConstants {

	public final String SEP = File.separator;
	
	// Relative to sketch folder
	public final String PDE_TAB_PRETTY_NAME 	= "gui";
	public final String PDE_TAB_NAME 			= PDE_TAB_PRETTY_NAME + ".pde";
	public final String CONFIG_FOLDER 			= "GUI_BUILDER_DATA";
	
	public final String MODEL_FILENAME 			= CONFIG_FOLDER + SEP + "gui_model.serialised";
	
	// These are relative to the processing sketch folder
	public final String G4P_TOOL_DATA_FOLDER 	= "tools" + SEP + "##name##" + SEP + "data";
	public final String G4P_LIB 				= "guicomponents" + SEP + "library" + SEP + "guicomponents.jar";
	public final String GUI_PDE_BASE			= G4P_TOOL_DATA_FOLDER + SEP + "gui_base.txt";
}
