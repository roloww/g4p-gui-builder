package g4p.tool.gui.propertygrid;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

public class Cell_editor_JFileChooser extends CellEditor_Base {

	protected static JFileChooser component = null;


	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}


	public class ImageFilter extends FileFilter {

		//Accept all directories and all gif, jpg, tiff, or png files.
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			String extension = getExtension(f);
			if (extension != null) {
				if (extension.equals("tga") ||
						extension.equals("gif") ||
						extension.equals("jpeg") ||
						extension.equals("jpg") ||
						extension.equals("png")) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}

		/*/
		 * Get the extension of a file (make it lowercase
		 */
		public String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 &&  i < s.length() - 1) {
				ext = s.substring(i+1).toLowerCase();
			}
			return ext;
		}

		//The description of this filter
		public String getDescription() {
			return "Just Images";
		}
	}

	public class ImagePreview extends JComponent
	implements PropertyChangeListener {
		ImageIcon thumbnail = null;
		File file = null;

		public ImagePreview(JFileChooser fc) {
			setPreferredSize(new Dimension(100, 100));
			this.setMinimumSize(new Dimension(100,100));
			fc.addPropertyChangeListener(this);
		}

		public void loadImage() {
			if (file == null) {
				thumbnail = null;
				return;
			}

			//Don't use createImageIcon (which is a wrapper for getResource)
			//because the image we're trying to load is probably not one
			//of this program's own resources.
			ImageIcon tmpIcon = new ImageIcon(file.getPath());
			if (tmpIcon != null) {
				if (tmpIcon.getIconWidth() > 90) {
					thumbnail = new ImageIcon(tmpIcon.getImage().
							getScaledInstance(90, -1,
									Image.SCALE_SMOOTH));
				} else { //no need to miniaturize
					thumbnail = tmpIcon;
				}
			}
		}

		public void propertyChange(PropertyChangeEvent e) {
			boolean update = false;
			String prop = e.getPropertyName();

			//If the directory changed, don't show an image.
			if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
				file = null;
				update = true;

				//If a file became selected, find out which one.
			} else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
				file = (File) e.getNewValue();
				update = true;
			}

			//Update the preview accordingly.
			if (update) {
				thumbnail = null;
				if (isShowing()) {
					loadImage();
					repaint();
				}
			}
		}

		protected void paintComponent(Graphics g) {
			if (thumbnail == null) {
				loadImage();
			}
			if (thumbnail != null) {
				int x = getWidth()/2 - thumbnail.getIconWidth()/2;
				int y = getHeight()/2 - thumbnail.getIconHeight()/2;

				if (y < 0) {
					y = 0;
				}

				if (x < 5) {
					x = 5;
				}
				thumbnail.paintIcon(this, g, x, y);
			}
		}
	}

}
