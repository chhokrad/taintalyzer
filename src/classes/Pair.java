package classes;

public class Pair {
	
	public Pair(int level, Object obj){
		this.level = level;
		this.obj = obj;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	private Object obj;
	private int level;
	
	

}
