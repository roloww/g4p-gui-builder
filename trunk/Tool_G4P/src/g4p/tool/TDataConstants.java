package g4p.tool;


public interface TDataConstants {

	// Validator constants
	public final int COMPONENT_NAME 	= 	0x00000020;
	public final int COMPONENT_NAME_0 	= 	0x00000021;
	public final int COLOUR_SCHEME 		= 	0x00000022;
	public final int CURSOR_CHANGER 	=	0x00000023;
	public final int SLIDER_SKIN 		=	0x00000024;
	public final int RENDERER	 		=	0x00000025;
	public final int KNOB_CTRL	 		=	0x00000026;

	public final int VALID				=	0x00000030;
	public final int INVALID_LENGTH		=	0x00000031;
	public final int FIRST_CHAR_INVALID	=	0x00000032;
	public final int HAS_A_SPACE		=	0x00000033;
	public final int INVALID_CHAR		=	0x00000034;
	public final int UNAVAILABLE		=	0x00000035;

	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Capture code and size patterns
	 */
	
	public final String CODE_TAG		=	"(//_CODE_:.*:\\d{6}:)";
	// The pattern is used to find the width and height from the size() statement
	// I have shamelessly taken this code from ProcessingJS by florian jenett
	// because it is way beyond my knowledge of regular expressions. 
	public final String SK_SIZE			=	"(?:^|\\s|;)size\\s*\\(\\s*(\\S+)\\s*,\\s*(\\d+),?\\s*([^\\)]*)\\s*\\)";
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Event handler method patterns
	 */
	
	//	public void addEventHandler(Object obj, String methodName){
	public final String ADD_HANDLER			=	"  {0}.addEventHandler({1}, \"{2}\");\n";

	public final String ADD_DRAW_HANDLER	=	"  {0}.addDrawHandler({1}, \"{2}\");\n";
	public final String ADD_MOUSE_HANDLER	=	"  {0}.addMouseHandler({1}, \"{2}\");\n";
	public final String ADD_PRE_HANDLER		=	"  {0}.addPreHandler({1}, \"{2}\");\n";
	public final String ADD_POST_HANDLER	=	"  {0}.addPostHandler({1}, \"{2}\");\n";

	// 0 = event method name  :  1/2 = component name/id
	public final String METHOD_START_0	=	"void {0}() [ //_CODE_:{1}:{2}:\n";
	
	// 0 = event method name  :  1/2 = parameter type/name : 3/4 =  component name/id
	public final String METHOD_START_1	=	"void {0}({1} {2}) [ //_CODE_:{3}:{4}:\n";
	
	// 0 = event method name  :  1/2 = parameter type/name : 1/3 =  parameter type/name : 4/5 =  component name/id	
	public final String METHOD_START_2	=	"void {0}({1} {2}, {1} {3}) [ //_CODE_:{4}:{5}:\n";
	
	// 0 = event method name  :  1/2 = component name/id	
	public final String WIN_DRAW		=	"void {0}(GWinApplet appc, GWinData data) [ //_CODE_:{1}:{2}:\n";
	public final String WIN_MOUSE		=	"void {0}(GWinApplet appc, GWinData data, MouseEvent mevent) [ //_CODE_:{1}:{2}:\n";
	public final String WIN_PRE			=	"void {0}(GWinApplet appc, GWinData data) [ //_CODE_:{1}:{2}:\n";
	public final String WIN_POST		=	"void {0}(GWinApplet appc, GWinData data) [ //_CODE_:{1}:{2}:\n";
	
	// 0 = component name  : 1 = id
	public final String METHOD_END 		=	"] //_CODE_:{0}:{1}:\n\n";


	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Creator and initialisation patterns
	 */

	// 		GWindow(PApplet theApplet, String name, int x, int y, int w, int h, boolean noFrame, String mode)
	public final String CTOR_WINDOW_1		=	"  {0} = new GWindow({1}, \"{2}\", {3}, {4}, {5}, {6}, {7}, {8});\n";
	
	//		GButton(PApplet theApplet, String text, int x, int y, int width, int height)
	public final String CTOR_GBUTTON_1		=	"  {0} = new GButton({1}, \"{2}\", {3}, {4}, {5}, {6});\n";
	
	//		GButton(PApplet theApplet, String imgFile, int nbrImages, int x, int y, int width, int height)
	public final String CTOR_GBUTTON_2		=	"  {0} = new GButton({1}, \"{2}\", {3}, {4}, {5}, {6}, {7});\n";
	
	//		GButton(PApplet theApplet, String text, String imgFile, int nbrImages, int x, int y, int width, int height)
	public final String CTOR_GBUTTON_3		=	"  {0} = new GButton({1}, \"{2}\", \"{3}\", {4}, {5}, {6}, {7}, {8});\n";
	
	//		GCheckbox(PApplet theApplet, String text, int x, int y, int width)
	public final String CTOR_GCHECKBOX		=	"  {0} = new GCheckbox({1}, \"{2}\", {3}, {4}, {5});\n";

	//		GLabel(PApplet theApplet, String text, int x, int y, int width, int height) {
	public final String CTOR_GLABEL		=	"  {0} = new GLabel({1}, \"{2}\", {3}, {4}, {5}, {6});\n";

	//		GOption(PApplet theApplet, String text, int x, int y, int width){
	public final String CTOR_GOPTION		=	"  {0} = new GOption({1}, \"{2}\", {3}, {4}, {5});\n";
	public final String ADD_OPTION			=	"  {0}.addOption({1});\n";
	public final String SEL_OPTION			=	"  {0}.setSelected({1});\n";
	
	//		GOptionGroup()
	public final String CTOR_GOPTIONGROUP	=	"  {0} = new GOptionGroup();\n";
	
	
	//		GPanel(PApplet theApplet, String text, int x, int y, int width, int height){
	public final String CTOR_GPANEL			=	"  {0} = new GPanel({1}, \"{2}\", {3}, {4}, {5}, {6});\n";

	//		GTextField(PApplet theApplet, String text, int x, int y, int width, int height, boolean multiLine){
	public final String CTOR_GTEXTFIELD		=	"  {0} = new GTextField({1}, \"{2}\", {3}, {4}, {5}, {6}, {7});\n";

	//		GHorzSlider(PApplet theApplet, int x, int y, int width, int height){
	public final String CTOR_GHORZSLIDER	=	"  {0} = new GHorzSlider({1}, {2}, {3}, {4}, {5});\n";
	public final String CTOR_GVERTSLIDER	=	"  {0} = new GVertSlider({1}, {2}, {3}, {4}, {5});\n";
	public final String SET_LIMITS			=	"  {0}.setLimits({1}, {2}, {3});\n";

	//		GWSlider(PApplet theApplet, String skin, int x, int y, int length) {
	public final String CTOR_GWSLIDER		=	"  {0} = new GWSlider({1}, \"{2}\", {3}, {4}, {5});\n";
	public final String SET_F_LIMITS		=	"  {0}.setLimits({1}f, {2}f, {3}f);\n";

	//		GTimer(PApplet theApplet, Object obj, String methodName, int interval){
	public final String CTOR_GTIMER			=	"  {0} = new GTimer({1}, {2}, \"{3}\", {4});\n";
	public final String START_TIMER_0		=	"  {0}.start();\n";
	public final String START_TIMER_1		=	"  {0}.start({1});\n";
	

	//		GKnob(PApplet theApplet, int x, int y, int size, int arcStart, int arcEnd) {
	public final String CTOR_GKNOB			=	"  {0} = new GKnob({1}, {2}, {3}, {4}, {5}, {6});\n";
	//		GKnobOval(PApplet theApplet, int x, int y, int width, int height, int arcStart, int arcEnd) {
	public final String CTOR_GKNOBOVAL		=	"  {0} = new GKnobOval({1}, {2}, {3}, {4}, {5}, {6}, {7});\n";
	public final String SET_ARC_ONLY		=	"  {0}.setRotArcOnly({1});\n";
	public final String SET_NBR_TICKS		=	"  {0}.setNbrTickMarks({1});\n";
	public final String SET_CONTROLLER		=	"  {0}.setControlMode(GKnob.CTRL_{1});\n";

	

			
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Default test code patterns
	 */
	public final String TIME 				= 	"+ System.currentTimeMillis()%10000 );\n";
	public final String CODE_ANY			=	"  println(\"{0} - {1} event occured \" " + TIME;
	public final String CODE_TIMER			=	"  println(\"{0} - {1} timer event occured at \" " + TIME;
	public final String CODE_GBUTTON		=	"  println(\"{0} - button clicked \" " + TIME;
	public final String CODE_GCHECKBOX		=	"  println(\"{0} - checkbox selected \" " + TIME;
	public final String CODE_GOPTION		=	"  println(\"{0} - option selected \" " + TIME;
	public final String CODE_GPANEL			=	"  println(\"{0} - panel collapsed or expanded \" " + TIME;
	public final String CODE_GTEXTFIELD		=	"  println(\"{0} - change or enter key pressed in textfield \" " + TIME;
	public final String CODE_GWINDOW_DRAW	=	"  appc.background(200,255,200);\n";
	public final String CODE_GWINDOW_MOUSE	=	"  println(\"{0} - mouse event \" " + TIME;
	public final String CODE_GWINDOW_PEE	=	"  println(\"{0} - pre method called \" " + TIME;
	public final String CODE_GWINDOW_POST	=	"  println(\"{0} - post method called \" " + TIME;

	public final String INDENT				=	"  ";
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Add to window pattern
	 */
	
//	//  0 = window name  :  1 = control name
//	public final String ADD_TO_WINDOW		= "  {0}.add({1});\n";
	//  0 = parent name  :  1 = control name
	public final String ADD_A_CHILD		= "  {0}.add({1});\n";
	
}
