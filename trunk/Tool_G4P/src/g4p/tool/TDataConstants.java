package g4p.tool;

import processing.core.PApplet;

public interface TDataConstants {

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

	// 0 = event method name  :  1/2 = parameter type/name : 3/4 =  component name/id
	public final String METHOD_START_1	= "void {0}({1} {2}) [ //_CODE_:{3}:{4}:";
	
	// 0 = event method name  :  1/2 = parameter type/name : 1/3 =  parameter type/name : 4/5 =  component name/id	
	public final String METHOD_START_2	= "void {0}({1} {2}, {1} {3}) [ //_CODE_:{4}:{5}:";
	
	// 0 = component name  : 1 = id
	public final String METHOD_END 		= "] //_CODE_:{0}:{1}:";

	//		GButton(PApplet theApplet, String text, int x, int y, int width, int height){
	public final String CTOR_GBUTTON_1	=	"  {0} = new GButton({1}, \"{2}\", {3}, {4}, {5}, {6});";
	
	//		GButton(PApplet theApplet, String imgFile, int nbrImages, int x, int y, int width, int height){
	public final String CTOR_GBUTTON_2	=	"  {0} = new GButton({1}, \"{2}\", {3}, {4}, {5}, {6}, {7});";
	
	//		GButton(PApplet theApplet, String text, String imgFile, int nbrImages, int x, int y, int width, int height){
	public final String CTOR_GBUTTON_3	=	"  {0} = new GButton({1}, \"{2}\", \"{3}\", {4}, {5}, {6}, {7}, {8});";
	
	//		GCheckbox(PApplet theApplet, String text, int x, int y, int width){
	public final String CTOR_GCHECKBOX	=	"  {0} = new GCheckbox({1}, \"{2}\", {3}, {4}, {5});";

	//		GOption(PApplet theApplet, String text, int x, int y, int width){
	public final String CTOR_GOPTION	=	"  {0} = new GOption({1}, \"{2}\", {3}, {4}, {5});";

	//		GPanel(PApplet theApplet, String text, int x, int y, int width, int height){
	public final String CTOR_GPANEL	=	"  {0} = new GPanel({1}, \"{2}\", {3}, {4}, {5});";

	//		GTextField(PApplet theApplet, String text, int x, int y, int width, int height, boolean multiLine){
	public final String CTOR_GTEXTFIELD	=	"  {0} = new GButton({1}, \"{2}\", {3}, {4}, {5}, {6}, {7}, {8});";

	
}
