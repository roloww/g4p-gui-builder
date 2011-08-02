package g4p.tool;

import java.io.File;

public interface GTconstants {

	public final String SLASH = File.separator;
	
	// Relative to sketch folder
	public final String PDE_TAB_NAME 			= "gui.pde";
	public final String CONFIG_FOLDER 			= "GUI_BUILD_DATA";
	public final String CONFIG_FILENAME 		= CONFIG_FOLDER + SLASH + "gui_config.txt";
	
	// Relative to sketchBook folder
	public final String G4P_TOOL_DATA_FOLDER 	= "tools" + SLASH + "##name##" + SLASH + "data";
	public final String G4P_LIB 				= "guicomponents" + SLASH + "library" + SLASH + "guicomponents.jar";


	// Validator constants
	public final int COMPONENT_NAME 	= 	0x00000020;
	public final int COLOUR_SCHEME 		= 	0x00000021;
	public final int CURSOR_OVER 		=	0x00000022;
	
	public final int VALID				=	0x00000030;
	public final int INVALID_LENGTH		=	0x00000031;
	public final int FIRST_CHAR_INVALID	=	0x00000032;
	public final int HAS_A_SPACE		=	0x00000033;
	public final int INVALID_CHAR		=	0x00000034;
	public final int UNAVAILABLE		=	0x00000035;
	
	// Window view modes
	public final int INACTIVE			=	0x00000100;
	public final int MOVE				=	0x00000101;
	public final int RESIZE				=	0x00000102;	
	
	// Mouse over constants
	public final int OVER_NONE			=	0x00000000;
	public final int OVER_COMP			=	0x00000200;
	public final int OVER_HORZ			=	0x00000201;
	public final int OVER_VERT			=	0x00000202;
	public final int OVER_DIAG			=	0x00000203;
	
	public final int HANDLE_SIZE		=	5;
}
