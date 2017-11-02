package edu.vanderbilt.taintalayzer.utility;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ClassUtils;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.vanderbilt.taintalayzer.tainter.RecursiveMultiTainter;

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

	public static ArrayList<Object> flattenArrayUntil1D(Object o) {
		ArrayList<Object> result = new ArrayList<Object>();
		if (o.getClass().isArray()) {
			if (Array.getLength(o) == 0)
				return result;
			Object o2 = Array.get(o, 0);
			if (o2.getClass().isArray()) {
				for (int index = 0; index < Array.getLength(o); index++) {
					ArrayList<Object> res = flattenArrayUntil1D(Array.get(o, index));
					result.addAll(res);
				}
			} else {
				result.add(o);
			}
		} else {
			result.add(o);
		}
		return result;
	}

	public static boolean isTaintedPrimitive(Object o, int i) throws Exception {
		boolean flag = false;
		switch (ClassUtils.wrapperToPrimitive(Array.get(o, i).getClass()).getName()) {
		case "int":
			if (MultiTainter.getTaint(Array.getInt(o, i)) != null)
				flag = true;
			break;
		case "long":
			if (MultiTainter.getTaint(Array.getLong(o, i)) != null)
				flag = true;
			break;
		case "boolean":
			if (MultiTainter.getTaint(Array.getBoolean(o, i)) != null)
				flag = true;
			break;
		case "short":
			if (MultiTainter.getTaint(Array.getShort(o, i)) != null)
				flag = true;
			break;
		case "double":
			if (MultiTainter.getTaint(Array.getDouble(o, i)) != null)
				flag = true;
			break;
		case "byte":
			if (MultiTainter.getTaint(Array.getByte(o, i)) != null)
				flag = true;
			break;
		case "char":
			if (MultiTainter.getTaint(Array.getChar(o, i)) != null)
				flag = true;
			break;
		case "float":
			if (MultiTainter.getTaint(Array.getFloat(o, i)) != null)
				flag = true;
			break;
		default:
			if (MultiTainter.getTaint(Array.get(o, i)) != null)
				flag = true;
			break;
		}
		return flag;
	}

	public static HashMap<Integer, Integer> getStatTaints(Object o) throws Exception {
		return getStat(o, true);
	}

	public static HashMap<Integer, Integer> getStatObjects(Object o) throws Exception {
		return getStat(o, false);
	}

	private static HashMap<Integer, Integer> getStat(Object o, boolean isTaintCounter) throws Exception {
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();

		if (o == null)
			return result;

		if (ClassUtils.isPrimitiveOrWrapper(o.getClass())) {
			if (!isTaintCounter || MultiTainter.getTaint(o) != null) {
				result.put(0, 1);
			}
			return result;
		}

		if (o.getClass().isArray() && RecursiveMultiTainter.isPrimitiveArray(o)) {
			int counter = 0;
			ArrayList<Object> res = ObjectCounter.flattenArrayUntil1D(o);
			for (Object a : res) {
				for (int i = 0; i < Array.getLength(a); i++) {
					if (!isTaintCounter || isTaintedPrimitive(a, i)) {
						counter++;
					}
				}
			}
			if (counter > 0)
				result.put(0, counter);
			return result;
		}

		if (o.getClass().isArray()) {
			if (Array.getLength(o) == 0) {
				return result;
			}

			for (Object o2 : flattenArray(o)) {
				HashMap<Integer, Integer> res;
				res = getStat(o2, isTaintCounter);
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
				HashMap<Integer, Integer> res = getStat(f.get(o), isTaintCounter);
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
