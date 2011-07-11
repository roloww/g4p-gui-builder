package g4p.tool;

import java.io.File;

public interface GTconstants {

	public final String SLASH = File.separator;
	
	// Relative to sketch folder
	public final String PDE_TAB_NAME = "gui.pde";
	public final String CONFIG_FOLDER = "data" + SLASH + "_gui_builder";
	public final String CONFIG_FILENAME = CONFIG_FOLDER + SLASH + "gui_config.txt";
	
	// Relative to sketchBook folder
	public final String G4P_TOOL_DATA_FOLDER = "tools" + SLASH + "##name##" + SLASH + "data";
	public final String G4P_LIB = "guicomponents" + SLASH + "library" + SLASH + "guicomponents.jar";


}
