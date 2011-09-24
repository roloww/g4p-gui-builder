/**
 * GUI form designer for G4P.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */
package g4p.tool;

import g4p.tool.gui.GuiDesigner;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import processing.app.Base;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.core.PApplet;

/**
 * 
 * @author Peter Lager
 *
 */
public class G4PTool implements Tool, TFileConstants {

	// Keep track of the editor using this sketch
	private processing.app.Editor editor;
	// keep track of the FUI designer for this sketch
	private GuiDesigner dframe;

	private boolean g4p_error_shown = false;
	
	public String getMenuTitle() {
		return "GUI builder";
	}

	/**
	 * Get version string of this tool
	 * @return revision number string
	 */
	public String getVersion(){
		return "##version##";
	}
	
	/**
	 * Get version number of this tool as an integer with the form  <br>
	 * MMmmii <br>
	 * M = Major revision <br>
	 * m = minor revision <br>
	 * i = internal revision <br>
	 * @return version number as int
	 */
	public int getVersionNo(){
		String n[] = "##version##".split("[\\.]");
		int[] vnp = new int[3];
		for(int i = 0; i < n.length; i++){
			try {
				vnp[i] = Integer.parseInt(n[i]);
			}
			catch(Exception excp){
			}
		}
		return ((vnp[0] * 100) + vnp[1]) * 100 + vnp[2];
	}
	
	/**
	 * Called once first time the tool is called
	 */
	public void init(processing.app.Editor theEditor) {
		this.editor = theEditor;
	}

	/**
	 * This is executed every time we launch the tool using the menu item in Processing IDE
	 * 
	 */
	public void run() {
		Base base = editor.getBase();
		Sketch sketch = editor.getSketch();
		File sketchFolder = sketch.getFolder();
		File sketchbookFolder = base.getSketchbookFolder();

		// Provide a warning (first time only) if G4P is not loaded
		if (!g4p_error_shown && !g4pJarExists(base.getSketchbookLibrariesFolder())) {
			Base.showWarning("GUI Builder error", "Although you can use this tool the sketch created will not \nwork because the G4P library needs to be installed.\nSee G4P at http://www.lagers.org.uk/g4p/", null);
			g4p_error_shown = true;
		}
		// The tool is not open so create the designer window
		if (dframe == null) {
			// If the gui.pde tab does not exist create it
			if (!guiTabExists(sketch)) {
				sketch.addFile(new File(sketchbookFolder, G4P_TOOL_DATA_FOLDER + SEP + PDE_TAB_NAME));
			}
			// Create data folder if necessary
			sketch.prepareDataFolder();
			
			// Create a sub-folder called 'GUI_BUILDER_DATA' inside the sketch folder if
			// it doesn't already exist
			File configFolder = new File(sketchFolder, CONFIG_FOLDER);
			if (!configFolder.exists()) {
				configFolder.mkdir();
			}
			dframe = new GuiDesigner(editor);
			System.out.println("##name## Version ##version## created by ##author##");
		} 

		System.out.println("Version     = "+ getVersion());
		System.out.println("Version No  = "+ getVersionNo());
		// Design window exists so make visible, open to normal size
		// and bring to front.
		dframe.setVisible(true);
		dframe.setExtendedState(JFrame.NORMAL);
		dframe.toFront();
	}

	/**
	 * See if the G4P library has been installed in the SketchBook libraries folder correctly
	 * @param sketchbookLibrariesFolder
	 * @return true if found else false
	 */
	private boolean g4pJarExists(File sketchbookLibrariesFolder) {
		File f = new File(sketchbookLibrariesFolder, G4P_LIB);
		return f.exists();
	}

	/**
	 * See if the gui.pde tab has been created already if not
	 * @param sketch
	 * @return
	 */
	private boolean guiTabExists(Sketch sketch) {
		File f = new File(sketch.getFolder(), PDE_TAB_NAME);
		return f.exists();
	}
}
