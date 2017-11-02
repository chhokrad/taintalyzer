package classes;

import java.util.HashMap;
import java.util.Set;

public class ResultsMap {
	private HashMap<Integer, Integer> Results =  new HashMap<Integer, Integer>();

	public void updateKeyValueTo(int key, int value)
	{
		this.Results.put(key, value);
	}
	
	public void updateKeyValueBy(int key, int value)
	{
		if (this.Results.containsKey(key))
			this.Results.put(key, value + this.Results.get(key));
		else
			updateKeyValueTo(key, value);
	}
	
	public Set<Integer> getKeySet()
	{
		return Results.keySet();
	}
	
	public int get(int key)
	{
		return Results.get(key);
	}
}
