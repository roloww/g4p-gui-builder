/**
 * you can put a one sentence description of your tool here.
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

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import processing.app.Base;
import processing.app.Sketch;
import processing.app.tools.Tool;

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

	public String getMenuTitle() {
		return "GUI builder";
	}

	public void init(processing.app.Editor theEditor) {
		this.editor = theEditor;
	}

	public void simpleRun() {
		if (dframe == null) {
			dframe = new GuiDesigner();
		}
		dframe.setVisible(true);
		dframe.toFront();
	}

	/**
	 * This is executed everytime we launch the tool using the menu item in Processing IDE
	 * 
	 */
	public void run() {
		Base base = editor.getBase();
		Sketch sketch = editor.getSketch();
		File sketchFolder = sketch.getFolder();
		File sketchbookFolder = base.getSketchbookFolder();

		// Cancel the whole thing if G4P is not loaded
		if (!g4pJarExists(base.getSketchbookLibrariesFolder())) {
			Base.showWarning("GUI Builder error", "This tool needs the G4P library to be installed.\nGet G4P from http://code.google.com/p/gui4processing/downloads", null);
			return;
		}
		// The tool is not open so create the designer window
		if (dframe == null) {
			// If the gui.pde tab does not exist create one
			if (!guiTabExists(sketch)) {
				sketch.addFile(new File(sketchbookFolder, G4P_TOOL_DATA_FOLDER + SEP + PDE_TAB_NAME));

				

			}
			// Create data folder if necessary
			sketch.prepareDataFolder();
			
			// See if there is a sub-folder in the data folder called '_gui_builder' stuff
			File configFolder = new File(sketchFolder, CONFIG_FOLDER);
			if (!configFolder.exists()) {
				configFolder.mkdir();
			}
			
			dframe = new GuiDesigner(editor);
		} 
		// Design window exists so make visible, open to normal size
		// and bring to front.
		dframe.setVisible(true);
		dframe.setExtendedState(JFrame.NORMAL);
		dframe.toFront();

		System.out.println("##name## Version ##version## created by ##author##");
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
