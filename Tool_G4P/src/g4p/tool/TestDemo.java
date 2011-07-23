package g4p.tool;

import g4p.tool.components.DApplication;
import g4p.tool.components.DBase;
import g4p.tool.components.DButton;
import g4p.tool.components.DWindow;
import g4p.tool.gui.propertygrid.CtrlPropView;
import g4p.tool.gui.propertygrid.CtrlSketchModel;
import g4p.tool.gui.propertygrid.CtrlSketchView;
import g4p.tool.gui.propertygrid.Validator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;

public class TestDemo extends JFrame{


	public static JTable table;
	public static JTree tree;
	public static Set<String> words = new HashSet<String>();

	private JScrollPane paneTable, paneTree;

	private JPanel panel;
	public static DButton b;

	public TestDemo(){
		super("Property Grid testing");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void start001(){
		panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(600,400));

		this.add(panel);

		initWords();
		Validator.restricted = words;
		makeDataModel();

		paneTable = new JScrollPane(table);
		paneTable.setPreferredSize(new Dimension(300, 400));
		panel.add(paneTable, BorderLayout.EAST);

		paneTree = new JScrollPane(tree);
		panel.add(paneTree, BorderLayout.WEST);
		paneTree.setPreferredSize(new Dimension(300, 400));

		pack();
		setVisible(true);
	}

	private void makeDataModel() {
		DBase app = new DApplication();
		DWindow w1 = new DWindow();
		w1.setName("Main Display");
		app.add(w1);
		DWindow w2 = new DWindow();
		w2.setName("wndController");
		w2.setWidth(200);
		w2.setHeight(400);
		app.add(w2);

		b = new DButton();
		b.makeTableModel();
		w1.add(b);

		b = new DButton();
		w2.add(b);

		CtrlSketchModel ctm = new CtrlSketchModel(app);
		tree = new CtrlSketchView(ctm);
		table = new CtrlPropView(b.getTableModel());
	}

	public void initWords(){
		String w = "abstract continue for new switch assert default ";
		w += "goto package synchronized boolean do if private this ";
		w += "break double implements protected throw byte else ";
		w += "import public throws case enum instanceof return ";
		w += "transient catch extends int short try char final ";
                w += "interface static void class finally long strictfp ";
                w += "volatile const float native super while";
		String[] ws = w.split(" ");
		for(String s : ws)
			words.add(s);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test001();
	}

	public static void test001(){
		TestDemo that = new TestDemo();
		that.start001();
	}

}
