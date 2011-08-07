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

	public final Color winBack 			= new Color(0,255,0,60);
	
	public final Color blackEdge 		= Color.black;
	public final Color greenEdge 		= new Color(0,64,0,60);

	public final Color solidCompBack 	= new Color(192,192,255);
	public final Color back100 			= new Color(100,100,255);
	public final Color pnlTabCol 		= new Color(50,50,255);

    public final Color lblBack 			= new Color(255,255,255,48);
    public final Color txfBack 			= Color.white;
    
    public final Color knbBezel 		= new Color(60,60,128,40);
    public final Color knbBack 			= solidCompBack;
    public final Color knbNeedle 		= Color.white;
    
    public final Color sdrBack 			= new Color(240,240,255);
    public final Color sdrThumb			= new Color(128,128,255);
    
}
