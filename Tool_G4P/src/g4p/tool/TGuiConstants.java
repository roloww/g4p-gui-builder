package g4p.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public interface TGuiConstants {
	
	
	// Selection box size
	public final int HANDLE_SIZE		= 5;
	
	// Mouse over constants
	public final int OVER_NONE			=	0x00000000;
	public final int OVER_COMP			=	0x00000200;
	public final int OVER_HORZ			=	0x00000201;
	public final int OVER_VERT			=	0x00000202;
	public final int OVER_DIAG			=	0x00000203;


	// Fonts
    public final Font displayFont 		= new Font("Sans Serif", Font.PLAIN, 11);

	// Drawing constants
	public final BasicStroke stdStroke 	= new BasicStroke(1.1f,
			BasicStroke.CAP_ROUND,	BasicStroke.JOIN_ROUND);
	
	public final BasicStroke selStroke 	= new BasicStroke(1.3f,
			BasicStroke.CAP_ROUND,	BasicStroke.JOIN_ROUND);
	
	public final BasicStroke needleStroke 	= new BasicStroke(2.3f,
			BasicStroke.CAP_ROUND,	BasicStroke.JOIN_ROUND);

	public final BasicStroke heavyStroke 	= new BasicStroke(2.8f,
			BasicStroke.CAP_ROUND,	BasicStroke.JOIN_ROUND);

	public final Color blackEdge 		= Color.black;
	public final Color greenEdge 		= new Color(0,64,0,60);

	public final Color winBack 			= new Color(64,255,64,60);
	
	public final Color btnBack 			= new Color(100,100,255);

	public final Color pnlTabCol 		= new Color(50,50,255);
	public final Color pnlBackOpaque 	= new Color(192,192,255);
	public final Color pnlBackClear 	= new Color(255,192,192,48);

    public final Color lblBack 			= new Color(255,255,255,60);
    
    public final Color txfBack 			= Color.white;
    public final Color txfFore 			= Color.cyan;
    
    public final Color knbBezel 		= new Color(60,60,128,40);
    public final Color knbBack 			= pnlBackOpaque;
    public final Color knbNeedle 		= Color.white;
    
    public final Color sdrBack 			= new Color(240,240,255);
    public final Color sdrThumb			= new Color(128,128,255);
    
    public final Color csdrBack			= new Color(0,0,0, 8);
    public final Color csdrBorder		= new Color(0,0,0, 32);
    public final Color csdrSlideBack	= new Color(220, 220, 220);
    public final Color csdrSlideBorder	= new Color(64, 64, 64);
    public final Color csdrThumb		= new Color(255,255,0);
    
    public final Color optBack 			= lblBack;
    public final Color optEdge			= Color.black;
    public final Color optFill			= Color.white;
    public final Color optDot			= new Color(64,64,160);
    public final int DOT_EDGE			=	10; 
    public final int DOT_SOLID			=	6; 
    
    public final Color actBarStroke		= Color.BLACK;
    public final Color actBarBack		= new Color(230,230,230);
    public final Color actBar0			= new Color(255,132,132);
    public final Color actBar1			= new Color(255,222,173);
    public final Color actBar2			= new Color(255,255,189);
    
    public final Color cboxBack			= lblBack;
    public final Color cboxEdge			= Color.black;
    public final Color cboxFill			= Color.white;
    public final int BOXSIZE			=	14; 
    public final int BOXINSET			=	3; 
    
    
    
    
}
