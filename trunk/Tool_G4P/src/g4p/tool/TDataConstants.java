package g4p.tool;

public interface TDataConstants {

	// Validator constants
	public final int COMPONENT_NAME 	= 	0x00000020;
	public final int COLOUR_SCHEME 		= 	0x00000021;
	public final int CURSOR_CHANGER 	=	0x00000022;
	public final int SLIDER_SKIN 		=	0x00000023;
	public final int RENDERER	 		=	0x00000024;

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
	public final String SK_SIZE			=	"(?:^|\\s|;)size\\s*\\(\\s*(\\S+)\\s*,\\s*(\\d+),?\\s*([^\\)]*)\\s*\\)";
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Event handler method patterns
	 */
	
	//	public void addEventHandler(Object obj, String methodName){
	public final String ADD_HANDLER		=	"  {0}.addEventHandler({1}, \"{2}\");\n";
	
	// 0 = event method name  :  1/2 = parameter type/name : 3/4 =  component name/id
	public final String METHOD_START_1	=	"void {0}({1} {2}) [ //_CODE_:{3}:{4}:\n";
	
	// 0 = event method name  :  1/2 = parameter type/name : 1/3 =  parameter type/name : 4/5 =  component name/id	
	public final String METHOD_START_2	=	"void {0}({1} {2}, {1} {3}) [ //_CODE_:{4}:{5}:\n";
	
	// 0 = component name  : 1 = id
	public final String METHOD_END 		=	"] //_CODE_:{0}:{1}:\n\n";


	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Creator patterns
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

	//		GOption(PApplet theApplet, String text, int x, int y, int width){
	public final String CTOR_GOPTION		=	"  {0} = new GOption({1}, \"{2}\", {3}, {4}, {5});\n";
	public final String ADD_OPTION			=	"  {0}.addOption({1});\n";
	public final String SEL_OPTION			=	"  {0}.setSelected({1});\n";
	
	//		GOptionGroup()
	public final String CTOR_GOPTIONGROUP	=	"  {0} = new GOptionGroup();\n";
	
	
	//		GPanel(PApplet theApplet, String text, int x, int y, int width, int height){
	public final String CTOR_GPANEL			=	"  {0} = new GPanel({1}, \"{2}\", {3}, {4}, {5}, {6});\n";

	//		GTextField(PApplet theApplet, String text, int x, int y, int width, int height, boolean multiLine){
	public final String CTOR_GTEXTFIELD		=	"  {0} = new GButton({1}, \"{2}\", {3}, {4}, {5}, {6}, {7}, {8});\n";

	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Default test code patterns
	 */
	
	public final String CODE_ANY			=	"  println(\"{0} - {1} event occured\");\n";
	public final String CODE_GBUTTON		=	"  println(\"{0} - button clicked\");\n";
	public final String CODE_GCHECKBOX		=	"  println(\"{0} - checkbox selected\");\n";
	public final String CODE_GOPTION		=	"  println(\"{0} - option selected\");\n";
	public final String CODE_GPANEL			=	"  println(\"{0} - panel collapsed or expanded\");\n";
	public final String CODE_GTEXTFIELD		=	"  println(\"{0} - change or enter key pressed in textfield\");\n";

	public final String INDENT				=	"  ";
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * Add to window pattern
	 */
	
//	//  0 = window name  :  1 = control name
//	public final String ADD_TO_WINDOW		= "  {0}.add({1});\n";
	//  0 = parent name  :  1 = control name
	public final String ADD_A_CHILD		= "  {0}.add({1});\n";
	
}
