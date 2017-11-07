package edu.vanderbilt.taintalyzer.utility;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.columbia.cs.psl.phosphor.struct.LinkedList.Node;


public class TaintEntrySerializer implements JsonSerializer<TaintEntry>{

	
	@Override
	public JsonElement serialize(TaintEntry T, Type arg1,
			JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("var_label", T.getLabel_());
		jsonObject.addProperty("loop_name", T.getLoop_name_());
		JsonArray jsonDependency = new JsonArray();
		Node<Object> CurrNode = T.getDependencies_().getFirst();
		while (CurrNode != null) {
			jsonDependency.add(new JsonPrimitive(CurrNode.entry.toString()));
			CurrNode = CurrNode.next;
		}
		jsonObject.add("Dependencies", jsonDependency);
		return jsonObject;
	}

}
