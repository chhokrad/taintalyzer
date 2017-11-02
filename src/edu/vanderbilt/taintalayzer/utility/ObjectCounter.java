package edu.vanderbilt.taintalayzer.utility;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ClassUtils;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;

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
		if (o.getClass().isArray()) {
			if (Array.getLength(o) == 0) {
				return result;
			}
			if (isTaintCounter && !Array.get(o, 0).getClass().isArray()) {
				int counter = 0;
				System.out.println(Array.get(o, 0).getClass().getSimpleName() + ":  ");
				for (int i = 0; i < Array.getLength(o); i++) {
					Class clazz = ClassUtils.wrapperToPrimitive(Array.get(o, i).getClass());
					if (clazz == int.class) {
						System.out.println("clazz-int");
						System.out.println(MultiTainter.getTaint(Array.getInt(o, i)));
						if (MultiTainter.getTaint(Array.getInt(o, i)) != null) {
							System.out.println("tainted-int");
							counter++;
						} else {
							System.out.println("But why-int");
						}
					} else if (clazz == long.class) {
						if (MultiTainter.getTaint(Array.getLong(o, i)) != null) {
							counter++;
						}
					} else if (clazz == boolean.class) {
						if (MultiTainter.getTaint(Array.getBoolean(o, i)) != null) {
							counter++;
						}
					} else if (clazz == short.class) {
						if (MultiTainter.getTaint(Array.getShort(o, i)) != null) {
							counter++;
						}
					} else if (clazz == double.class) {
						if (MultiTainter.getTaint(Array.getDouble(o, i)) != null) {
							counter++;
						}
					} else if (clazz == byte.class) {
						if (MultiTainter.getTaint(Array.getByte(o, i)) != null) {
							counter++;
						}
					} else if (clazz == char.class) {
						if (MultiTainter.getTaint(Array.getChar(o, i)) != null) {
							counter++;
						}
					} else if (clazz == float.class) {
						if (MultiTainter.getTaint(Array.getFloat(o, i)) != null) {
							counter++;
						}
					} else {
						if (MultiTainter.getTaint(Array.get(o, i)) != null)
							counter++;
					}
				}
				if (counter != 0)
					result.put(0, counter);
				return result;
			} else {
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
			}

		} else {
			if (!isTaintCounter || MultiTainter.getTaint(o) != null) {
				result.put(0, 1);
			}
			for (Field f : o.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (Modifier.isFinal(f.getModifiers()))
					continue;
				// System.out.println(f.getName());
				HashMap<Integer, Integer> res = getStat(f.get(o), isTaintCounter);
				// System.out.println(res);
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
