package g4p.tool.components;

import java.util.HashMap;

public class Code {
	
	private static Code instance;
	
	public static Code instance(){
		if(instance == null){
			instance = new Code();
		}
		return instance;
	}

	HashMap<Integer, String> snippets;
	
	
	private Code(){
		snippets = new HashMap<Integer, String>();
	}
	
	public void add(Integer id, String code){
		snippets.put(id, code);
	}
	
	public String get(Integer id){
		return snippets.get(id);
	}
	
	public void reset(){
		snippets.clear();
	}
	
	
}
