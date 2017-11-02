package edu.vanderbilt.taintalayzer.utility;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ClassUtils;

public class ObjectCounter {

	public static ArrayList<Object> flattenArray(Object o) {
		ArrayList<Object> result = new ArrayList<Object>();
		if (o.getClass().isArray()) {
			for (int index = 0; index < Array.getLength(o); index++) {
				ArrayList<Object> res = flattenArray(Array.get(o, index));
				result.addAll(res);
			}
		} else {
			result.add(o);
		}
		return result;
	}

	public static HashMap<Integer, Integer> getStat(Object o) throws Exception {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		if (o == null)
			return result;

		if (ClassUtils.isPrimitiveOrWrapper(o.getClass())) {
			result.put(0, 1);
			return result;
		}
		if (o.getClass().isArray()) {
			for (Object o2 : flattenArray(o)) {
				HashMap<Integer, Integer> res = getStat(o2);
				for (Integer key : res.keySet()) {
					if (result.containsKey(key)) {
						result.put(key, result.get(key) + res.get(key));
					} else {
						result.put(key, res.get(key));
					}
				}
			}
		} else {
			result.put(0, 1);
			for (Field f : o.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (Modifier.isFinal(f.getModifiers()))
					continue;
				HashMap<Integer, Integer> res = getStat(f.get(o));
				for (Integer key : res.keySet()) {
					if (result.containsKey(key + 1)) {
						result.put(key + 1, result.get(key + 1) + res.get(key));
					} else {
						result.put(key + 1, res.get(key));
					}
				}
			}
		}
		return result;
	}

}
