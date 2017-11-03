package edu.vanderbilt.taintalayzer.utility;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.ClassUtils;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class ObjectCounterDFS {

	public ObjectCounterDFS() {
	}

	private void taintPrimitive(Object obj, Taint<String> taint) {
		System.out
				.println("Recursive Multitainter is not suited for Primitive types");
	}

	private boolean checkArrayDimension(Object obj) {
		// Returns true if the dimension is greater than one, otherwise, false
		boolean dimension = false;
		if ((obj.getClass().getName()).lastIndexOf("[") > 0)
			dimension = true;
		return dimension;
	}

	private boolean checkArrayType(Object obj) {
		// Returns false is array is collection of non primitive types, and true
		// for primitive types
		String oracle = new String("IJZSDBCF");
		boolean type = false;
		String type_str = obj.getClass().getName();
		if ((type_str.length() - type_str.lastIndexOf("[")) == 2
				&& oracle.contains(type_str.substring(type_str.length() - 1)))
			type = true;
		return type;
	}

	private void taintPrimitiveArray1D(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		if (obj.getClass().getComponentType().getTypeName() == int.class
				.getTypeName()) {
			int[] temp = (int[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == long.class
				.getTypeName()) {
			long[] temp = (long[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == boolean.class
				.getTypeName()) {
			boolean[] temp = (boolean[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == short.class
				.getTypeName()) {
			short[] temp = (short[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == double.class
				.getTypeName()) {
			double[] temp = (double[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == byte.class
				.getTypeName()) {
			byte[] temp = (byte[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == char.class
				.getTypeName()) {
			char[] temp = (char[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == float.class
				.getTypeName()) {
			float[] temp = (float[])obj;
			for (int i = 0 ; i < Array.getLength(temp); i++)
				System.out.println(MultiTainter.getTaint(temp[i]));
		} else if (obj.getClass().getComponentType().getTypeName() == void.class
				.getTypeName()) {
			System.out.println("Skipping Void type");
		} else {
			// throw new Exception("Primitive Array Type Decoding Error");
		}
	}

	private void taintPrimitiveArray(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass()
					.getComponentType())) {
				this.taintPrimitiveArray1D(obj_, taint);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}

	}

	private Object taintPrimitiveArrayWreturn(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass()
					.getComponentType())) {
				this.taintPrimitiveArray1D(obj_, taint);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}
		return obj;
	}

	private void taintCustomArray(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		for (int i = 0; i < Array.getLength(obj); i++)
			this.taintObjects(Array.get(obj, i), taint);
	}

	private void taintCustomObject(Object obj, Taint<String> taint)
			throws Exception {
		System.out.println(obj.getClass().getName());
		System.out.println(MultiTainter.getTaint(obj));
		for (Field f : obj.getClass().getDeclaredFields()) {
			System.out.println(f.getName() + " : ");
			f.setAccessible(true);
				if (ClassUtils.isPrimitiveOrWrapper(f.getType())) {
					if (!Modifier.isFinal(f.getModifiers())) {
						if (f.getType() == int.class) {
									System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getInt(obj)));
						} else if (f.getType() == long.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getLong(obj)));
						} else if (f.getType() == boolean.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getBoolean(obj)));
						} else if (f.getType() == short.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getShort(obj)));
						} else if (f.getType() == double.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getDouble(obj)));
						} else if (f.getType() == byte.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getByte(obj)));
						} else if (f.getType() == char.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getChar(obj)));
						} else if (f.getType() == float.class) {
							System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getFloat(obj)));
						} else if (f.getType() == void.class) {
							System.out.println("Skipping void");
						} else {
							throw new Exception("Primitive Type Decoding Error");
						}
						}
					else
						System.out.println("Skipping tainting a Final Field : "
								+ f.getType() + " " + f.getName() + " in "
								+ obj.getClass().getName());
				} else if ((f.get(obj)).getClass().isArray()
						&& this.checkArrayType(f.get(obj)) && !this.checkArrayDimension(f.get(obj)))
					this.taintPrimitiveArrayWreturn(f.get(obj), taint);
				else if ((f.get(obj)).getClass().isArray()
						&& this.checkArrayType(f.get(obj)) && this.checkArrayDimension(f.get(obj)))
							this.taintPrimitiveArray(f.get(obj), taint);
				else
					this.taintObjects(f.get(obj), taint);
			} 
	}

	public void taintObjects(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {

		if (obj == null)
			throw new NullPointerException("Object can not be null");
		// Checking if the obj is an array
		if (obj.getClass().isArray()) {
			boolean isPrimitive = this.checkArrayType(obj);
			// obj is an array of primitive types
			if (isPrimitive)
				this.taintPrimitiveArray(obj, taint);
			// obj is an array of custom types
			else
				this.taintCustomArray(obj, taint);
			// obj is not an array
		} else {
			// obj is a primitive type or a wrapper associated with primitive
			// type
			if (ClassUtils.isPrimitiveOrWrapper(obj.getClass()))
			{
				System.out.println(MultiTainter.getTaint(obj));
			}
				
			// obj is a reference to custom type
			else
				this.taintCustomObject(obj, taint);
		}

	}

}
