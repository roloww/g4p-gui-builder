package g4p.tool.components;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IdGen {

	private static IdGen instance;
	private static int low  = 100000;
	private static int high = 999999;
	private static Random rnd;
	
	public static IdGen instance(){
		if(instance == null){
			instance = new IdGen();
		}
		return instance;
	}
	
	private Set<Integer> ids;

	private IdGen(){
		ids = new HashSet<Integer>();
		rnd = new Random();
	}
	
	public boolean used(Integer id){
		return ids.contains(id);
	}

	public void add(Integer id){
		ids.add(id);
		System.out.println("ID " + id + "   ADDED size= " + ids.size());
	}
	
	public void remove(Integer id){
		ids.remove(id);
	}

	public void reset(){
		ids.clear();
	}
	
	public Integer getNext(){
		Integer id;
		do{
			id = new Integer((int) (Math.random() * (high - low) + low));
		} while(ids.contains(id));
		ids.add(id);
		System.out.println("ID " + id + "   created " + ids.size());
		return id;
	}
	
	public String get_size(){
		return "" + ids.size();
	}
}
