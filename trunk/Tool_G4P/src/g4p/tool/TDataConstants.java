package g4p.tool;


public interface TDataConstants {

	// Validator constants
	int COMPONENT_NAME 		= 	0x00000020;
	int COMPONENT_NAME_0 	= 	0x00000021;
	int COLOUR_SCHEME 		= 	0x00000022;
	int CURSOR_CHANGER 		=	0x00000023;
	int SLIDER_SKIN 		=	0x00000024;
	int RENDERER	 		=	0x00000025;
	int KNOB_CTRL	 		=	0x00000026;
	int H_ALIGN_2	 		=	0x00000027;
	int H_ALIGN_3	 		=	0x00000028;
	int V_ALIGN	 			=	0x00000029;
	int VALUE_TYPE			=	0x0000002A;
	int TEXT_ORIENT			= 	0x0000002B;
	
	// Defunct
	int IMG_BUTTON_STYLE	=	0x0000102B;
	int BUTTON_STYLE		=	0x0000002A;

	
	int VALID				=	0x00000040;
	int INVALID_LENGTH		=	0x00000041;
	int FIRST_CHAR_INVALID	=	0x00000042;
	int HAS_A_SPACE			=	0x00000043;
	int INVALID_CHAR		=	0x00000044;
	int UNAVAILABLE			=	0x00000045;

	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Capture code and size patterns
	 */
	
	String CODE_TAG		=	"(//_CODE_:.*:\\d{6}:)";
	// The pattern is used to find the width and height from the size() statement
	// I have shamelessly taken this code from ProcessingJS by florian jenett
	// because it is way beyond my knowledge of regular expressions. 
	String SK_SIZE			=	"(?:^|\\s|;)size\\s*\\(\\s*(\\S+)\\s*,\\s*(\\d+),?\\s*([^\\)]*)\\s*\\)";
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Event handler method patterns
	 */
	
	//	public void addEventHandler(Object obj, String methodName){
	String ADD_HANDLER			=	"  {0}.addEventHandler({1}, \"{2}\");\n";

	String ADD_DRAW_HANDLER		=	"  {0}.addDrawHandler({1}, \"{2}\");\n";
	String ADD_MOUSE_HANDLER	=	"  {0}.addMouseHandler({1}, \"{2}\");\n";
	String ADD_KEY_HANDLER	=	"  {0}.addKeyHandler({1}, \"{2}\");\n";
	String ADD_PRE_HANDLER		=	"  {0}.addPreHandler({1}, \"{2}\");\n";
	String ADD_POST_HANDLER		=	"  {0}.addPostHandler({1}, \"{2}\");\n";

	// 0 = event method name  :  1/2 = component name/id
	String METHOD_START_0	=	"void {0}({1} source) [ //_CODE_:{2}:{3}:\n";
	
	// 0 = event method name  :  1/2 = parameter type/name : 3/4 =  component name/id

//	String METHOD_START_1	=	"void {0}({1} {2}) [ //_CODE_:{3}:{4}:\n";
	String METHOD_START_1	=	"void {0}({1} source, GEvent event) [ //_CODE_:{2}:{3}:\n";
	
	// 0 = event method name  :  1/2 = parameter type/name : 1/3 =  parameter type/name : 4/5 =  component name/id	
//	String METHOD_START_2	=	"void {0}({1} {2}, {1} {3}) [ //_CODE_:{4}:{5}:\n";
	
	// 0 = event method name  :  1/2 = component name/id	
	String WIN_DRAW			=	"synchronized void {0}(GWinApplet appc, GWinData data) [ //_CODE_:{1}:{2}:\n";
	String WIN_MOUSE		=	"synchronized void {0}(GWinApplet appc, GWinData data, MouseEvent mevent) [ //_CODE_:{1}:{2}:\n";
	String WIN_KEY			=	"synchronized void {0}(GWinApplet appc, GWinData data, KeyEvent kevent) [ //_CODE_:{1}:{2}:\n";
	String WIN_PRE			=	"synchronized void {0}(GWinApplet appc, GWinData data) [ //_CODE_:{1}:{2}:\n";
	String WIN_POST			=	"synchronized void {0}(GWinApplet appc, GWinData data) [ //_CODE_:{1}:{2}:\n";
	
	// 0 = component name  : 1 = id
	String METHOD_END 		=	"] //_CODE_:{0}:{1}:\n\n";


	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Creator and initialisation patterns
	 */
	String SET_SKETCH_TITLE		=	"  if(frame != null)\n    frame.setTitle(\"{0}\");\n";
	
	String SET_TEXT				=	"  {0}.setText(\"{1}\");\n";
	String SET_DEFAULT_TEXT		=	"  {0}.setDefaultText(\"{1}\");\n";
	String SET_TEXT_ALIGN		=	"  {0}.setTextAlign(GAlign.{1}, GAlign.{2});\n";
	String SET_ICON				= 	"  {0}.setIcon(\"{1}\", {2}, GAlign.{3}, GAlign.{4});\n";
	String SET_ICON_ALIGN		=	"  {0}.setIconAlign(GAlign.{1}, GAlign.{2});\n";
	String SET_TEXT_BOLD		=	"  {0}.setTextBold();\n";
	String SET_TEXT_ITALIC		=	"  {0}.setTextItalic();\n";
	String SET_OPAQUE			=	"  {0}.setOpaque({1});\n";

	
	// 		GWindow(PApplet theApplet, String name, int x, int y, int w, int h, boolean noFrame, String mode)
	String CTOR_WINDOW_1		=	"  {0} = new GWindow({1}, \"{2}\", {3}, {4}, {5}, {6}, {7}, {8});\n";
	
	//		GButton(PApplet theApplet, String text, int x, int y, int width, int height)
	String CTOR_GBUTTON		=	"  {0} = new GButton({1}, {2}, {3}, {4}, {5});\n";
	
	//		GImageButton(PApplet theApplet, int x, int y, int w, int h, Sting[] files, String maskFile)
	//																x	 y    w   h     fl    mf
	String CTOR_IMG_BTN_XYWHFM	= 	"  {0} = new GImageButton({1}, {2}, {3}, {4}, {5}, {6}, \"{7}\");\n";
	//																x	 y    w   h     fl
	String CTOR_IMG_BTN_XYWHF	= 	"  {0} = new GImageButton({1}, {2}, {3}, {4}, {5}, {6});\n";
	//																x	 y   fl    mf
	String CTOR_IMG_BTN_XYFM	= 	"  {0} = new GImageButton({1}, {2}, {3}, {4}, \"{5}\");\n";
	//																x	 y   fl
	String CTOR_IMG_BTN_XYF		= 	"  {0} = new GImageButton({1}, {2}, {3}, {4});\n";

	
	// 		GImageButton(PApplet theApplet, String maskFile, String imgFiles[], int x, int y)
//	String CTOR_IMG_BUTTON_9	=	"  {0} = new GImageButton({1}, {2}, new String[] <{3}, {4} >, {5}, {6});\n";
//	String CTOR_IMG_BUTTON_8	=	"  {0} = new GImageButton({1}, {2}, new String[] <{3}, {4}, {5} >, {6}, {7});\n";

	String FILE_LIST			=   "  new String[] [ \"{0}\", \"{1}\", \"{2}\" } ";

	
	String BTN_TEXT_ALIGN		=  	"  {0}.setTextAlign(GAlign.{1} | GAlign.{2});\n";
	String BTN_ICON_ALIGN		=  	"  {0}.setImageAlign(GAlign.{1});\n";
	
	//		GCheckbox(PApplet theApplet, int x, int y, int width, int height)
	String CTOR_GCHECKBOX		=	"  {0} = new GCheckbox({1}, {2}, {3}, {4}, {5});\n";

	//		GLabel(PApplet theApplet, String text, int x, int y, int width, int height) {
	String CTOR_GLABEL			=	"  {0} = new GLabel({1}, {2}, {3}, {4}, {5});\n";
	String CTOR_SPAD			=	"  {0} = new GSketchPad({1}, {2}, {3}, {4}, {5});\n";

	//		GOption(PApplet theApplet, String text, int x, int y, int width){
	String CTOR_GOPTION			=	"  {0} = new GOption({1}, {2}, {3}, {4}, {5});\n";
	String SEL_OPTION			=	"  {0}.setSelected({1});\n";
	
	//		GOptionGroup()
	String CTOR_GOPTIONGROUP	=	"  {0} = new GToggleGroup();\n";
	
	
	//		GPanel(PApplet theApplet, String text, int x, int y, int width, int height){
	String CTOR_GPANEL			=	"  {0} = new GPanel({1}, {2}, {3}, {4}, {5}, \"{6}\");\n";
	String COLLAPSED			=	"  {0}.setCollapsed({1});\n";

	//		GTextField(PApplet theApplet, String text, int x, int y, int width, int height, boolean multiLine){
	String CTOR_GTEXTFIELD		=	"  {0} = new GTextField({1}, {2}, {3}, {4}, {5}, {6});\n";
	String CTOR_GTEXTAREA		=	"  {0} = new GTextArea({1}, {2}, {3}, {4}, {5}, {6});\n";
	String SBAR_POLICY			= "";
	
	//		GHorzSlider(PApplet theApplet, int x, int y, int width, int height){
	String CTOR_GSLIDER			=	"  {0} = new GSlider({1}, {2}, {3}, {4}, {5}, {6});\n";
	//											angle,  mode){	
	String MAKE_VERT		=	"  {0}.setRotation({1}, CORNER);\n";

	String SET_LIMITS			=	"  {0}.setLimits({1}, {2}, {3});\n";
	String SET_VALUE_TYPE		=	"  {0}.setNumberFormat(G4P.{1}, {2});\n";
	String SET_NBR_TICKS		=	"  {0}.setNbrTicks({1});\n";
	String SET_STICK_TICKS		=	"  {0}.setStickToTicks({1});\n";
	String SET_SHOW_TICKS		=	"  {0}.setShowTicks({1});\n";
	String SET_SHOW_VALUE		=	"  {0}.setShowValue({1});\n";
	String SET_SHOW_LIMITS		=	"  {0}.setShowLimits({1});\n";
	String SET_TEXT_ORIENT		=	"  {0}.setTextOrientation(G4P.{1});\n";
	String SET_EASING			=	"  {0}.setEasing({1});\n";
	
	
	//		GWSlider(PApplet theApplet, String skin, int x, int y, int length) {
	String CTOR_GCUSTOMSLIDER	=	"  {0} = new GCustomSlider({1}, {2}, {3}, {4}, {5}, \"{6}\");\n";

	//		GTimer(PApplet theApplet, Object obj, String methodName, int interval){
	String CTOR_GTIMER			=	"  {0} = new GTimer({1}, {2}, \"{3}\", {4});\n";
	String START_TIMER_0		=	"  {0}.start();\n";
	String START_TIMER_1		=	"  {0}.start({1});\n";
	String INIT_DELAY_TIMER		=	"  {0}.setInitialDelay({1});\n";
	

	//		GKnob(PApplet theApplet, int x, int y, int size, float grip ratio) {
	String CTOR_GKNOB			=	"  {0} = new GKnob({1}, {2}, {3}, {4}, {5}, {6});\n";
	//		GKnobOval(PApplet theApplet, int x, int y, int width, int height, int arcStart, int arcEnd) {
	String SET_TURN_RANGE		=	"  {0}.setTurnRange({1}, {2});\n";
	String SET_CONTROLLER		=	"  {0}.setTurnMode(GKnob.CTRL_{1});\n";
	String SET_DRAG_SENSITIVITY	=	"  {0}.setSensitivity({1});\n";
	String SET_SHOW_TRACK		=	"  {0}.setShowTrack({1});\n";
	String SET_SHOW_ARC_ONLY	=	"  {0}.setShowArcOnly({1});\n";
	String SET_OVER_ARC_ONLY	=	"  {0}.setOverArcOnly({1});\n";
	String SET_OVER_GRIP_ONLY	=	"  {0}.setIncludeOverBezel({1});\n";

	
	//													  app,   x     y    w    y    nbr
	String CTOR_DROPLIST		=	"  {0} = new GDropList({1}, {2}, {3}, {4}, {5}, {6});\n";
	String CTOR_SET_LIST		=	"  {0}.setItems(loadStrings(\"{1}\"), {2});\n";

			
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Default test code patterns
	 */
	String TIME 				= 	"+ System.currentTimeMillis()%10000000 );\n";
	String CODE_ANY				=	"  println(\"{0} - {1} event occured \" " + TIME;
	String CODE_TIMER			=	"  println(\"{0} - {1} timer event occured at \" " + TIME;
	String CODE_GBUTTON			=	"  println(\"{0} - button clicked \" " + TIME;
	String CODE_GCHECKBOX		=	"  println(\"{0} - checkbox selected \" " + TIME;
	String CODE_GOPTION			=	"  println(\"{0} - option selected \" " + TIME;
	String CODE_GPANEL			=	"  println(\"{0} - panel collapsed or expanded \" " + TIME;
	String CODE_GTEXTFIELD		=	"  println(\"{0} - change or enter key pressed in textfield \" " + TIME;
	String CODE_GWINDOW_DRAW	=	"  appc.background(230);\n";
	String CODE_GWINDOW_MOUSE	=	"  println(\"{0} - mouse event \" " + TIME;
	String CODE_GWINDOW_KEY		=	"  println(\"{0} - key event \" " + TIME;
	String CODE_GWINDOW_PEE		=	"  println(\"{0} - pre method called \" " + TIME;
	String CODE_GWINDOW_POST	=	"  println(\"{0} - post method called \" " + TIME;

	String INDENT				=	"  ";
	
	// Used by GOptionGroup and GPanel
	//  0 = parent name  :  1 = control name
	String ADD_A_CHILD		= "  {0}.addControl({1});\n";
	
}
