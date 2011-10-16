package g4p.tool;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import processing.core.PApplet;

public class TestDialog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "mary\nhad\na lamb\nit's fleece\nwas\nas white as\nsnow";
		JTextArea c = new JTextArea(s,10,40);
		JScrollPane p = new JScrollPane(c, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		int r;
		r = JOptionPane.showConfirmDialog(null, p, "ComboBox List (1 option per line)", JOptionPane.OK_CANCEL_OPTION);
		if(r == JOptionPane.CANCEL_OPTION)
			System.out.println("CANCEL " + r);
		else {
			System.out.println("OK " + r);
		}
		System.out.println("\n\n"+c.getText());
		String[] lines = PApplet.split(c.getText(), "\n");
		for(String line : lines){
			line = line.replace('\\', ' ');
			line = line.replace('"', '\'');
			System.out.println(">> " + line);
		}
	
	}

}
