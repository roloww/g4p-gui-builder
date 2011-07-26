package g4p.tool.components;

import java.util.HashSet;
import java.util.Set;

public class NameGen {

	private static NameGen instance;
	
	public static NameGen instance(){
		if(instance == null){
			instance = new NameGen();
		}
		return instance;
	}
	
	
	private Set<String> words;
	
	private NameGen(){
		words = new HashSet<String>();
        String w = "abstract continue for new switch assert default ";
        w += "goto package synchronized boolean do if private this ";
        w += "break double implements protected throw byte else ";
        w += "import public throws case enum instanceof return ";
        w += "transient catch extends int short try char final ";
        w += "interface static void class finally long strictfp ";
        w += "volatile const float native super while ";
        w += "Sketch_Display SKETCH APPLICATION APPLET ";
        String[] ws = w.split(" ");
        for (String s : ws) {
            words.add(s);
        }
	}
	
	/**
	 * See if a name is reserved
	 * @param name
	 * @return
	 */
	public boolean used(String name){
		return words.contains(name);
	}

	public void add(String name){
		words.add(name);
	}
	
	public void remove(String name){
		words.remove(name);
	}
	
	/**
	 * Get the next available name-number combination and add it to 
	 * the reserved list.
	 * 
	 * @param name
	 * @return
	 */
	public String getNext(String name){
		int nbr = 0;
		do {
			nbr++;
		} while(words.contains(name + nbr));
		name = name + nbr;
		words.add(name);
		return name;
	}
}
