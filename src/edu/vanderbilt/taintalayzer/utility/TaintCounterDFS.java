package edu.vanderbilt.taintalayzer.utility;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.ClassUtils;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;

public class TaintCounterDFS {

	private ResultsMap data = new ResultsMap();
	private Stack<Integer> levelStack = new Stack<Integer>();

	public TaintCounterDFS() {
		levelStack.push(0);
	}

	public int checkStacklevl() {
		return this.levelStack.peek();
	}

	public HashMap<Integer, Integer> getdata() {
		return data.getresults();
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

	private void findTaintPrimitiveArray1D(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		if (obj.getClass().getComponentType().getTypeName() == int.class.getTypeName()) {
			int[] temp = (int[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}

		} else if (obj.getClass().getComponentType().getTypeName() == long.class.getTypeName()) {
			long[] temp = (long[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		} else if (obj.getClass().getComponentType().getTypeName() == boolean.class.getTypeName()) {
			boolean[] temp = (boolean[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		} else if (obj.getClass().getComponentType().getTypeName() == short.class.getTypeName()) {
			short[] temp = (short[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		} else if (obj.getClass().getComponentType().getTypeName() == double.class.getTypeName()) {
			double[] temp = (double[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		} else if (obj.getClass().getComponentType().getTypeName() == byte.class.getTypeName()) {
			byte[] temp = (byte[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		} else if (obj.getClass().getComponentType().getTypeName() == char.class.getTypeName()) {
			char[] temp = (char[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		} else if (obj.getClass().getComponentType().getTypeName() == float.class.getTypeName()) {
			float[] temp = (float[]) obj;
			for (int i = 0; i < Array.getLength(temp); i++) {
				System.out.println(MultiTainter.getTaint(temp[i]));
				if (MultiTainter.getTaint(temp[i]) != null)
					data.updateKeyValueBy(levelStack.peek(), 1);
			}
		}
	}

	private void findTaintPrimitiveArray(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass().getComponentType())) {
				this.findTaintPrimitiveArray1D(obj_);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}

	}

	private Object findTaintPrimitiveArrayWreturn(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass().getComponentType())) {
				this.findTaintPrimitiveArray1D(obj_);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}
		return obj;
	}

	private void findTaintCustomArray(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		for (int i = 0; i < Array.getLength(obj); i++)
			this.findTaintObjects_(Array.get(obj, i));
	}

	private void findTaintCustomObject(Object obj) throws Exception {
		System.out.println(obj.getClass().getName());
		System.out.println(MultiTainter.getTaint(obj));
		if (MultiTainter.getTaint(obj) != null)
			data.updateKeyValueBy(levelStack.peek(), 1);
		levelStack.push(levelStack.peek() + 1);
		for (Field f : obj.getClass().getDeclaredFields()) {
			System.out.println(f.getName() + " : ");
			f.setAccessible(true);
			if (ClassUtils.isPrimitiveOrWrapper(f.getType())) {
				if (!Modifier.isFinal(f.getModifiers())) {
					if (f.getType() == int.class) {
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getInt(obj)));
						if (MultiTainter.getTaint(f.getInt(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
					} else if (f.getType() == long.class) {
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getLong(obj)));
						if (MultiTainter.getTaint(f.getLong(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
					} else if (f.getType() == boolean.class) {
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getBoolean(obj)));
						if (MultiTainter.getTaint(f.getBoolean(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
					} else if (f.getType() == short.class) {
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getShort(obj)));
						if (MultiTainter.getTaint(f.getShort(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
					} else if (f.getType() == double.class) {
						if (MultiTainter.getTaint(f.getDouble(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getDouble(obj)));
					} else if (f.getType() == byte.class) {
						if (MultiTainter.getTaint(f.getByte(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getByte(obj)));
					} else if (f.getType() == char.class) {
						if (MultiTainter.getTaint(f.getChar(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getChar(obj)));
					} else if (f.getType() == float.class) {
						if (MultiTainter.getTaint(f.getFloat(obj)) != null)
							data.updateKeyValueBy(levelStack.peek(), 1);
						System.out.println(f.getName() + " " + MultiTainter.getTaint(f.getFloat(obj)));
					}
				}
			}
			// this case is not working due to bug in Phosphor
			else if ((f.get(obj)).getClass().isArray() && this.checkArrayType(f.get(obj))
					&& !this.checkArrayDimension(f.get(obj)))
				this.findTaintPrimitiveArrayWreturn(f.get(obj));
			else if ((f.get(obj)).getClass().isArray() && this.checkArrayType(f.get(obj))
					&& this.checkArrayDimension(f.get(obj)))
				this.findTaintPrimitiveArray(f.get(obj));
			else
				this.findTaintObjects_(f.get(obj));
		}
		levelStack.pop();
	}

	public void findTaintObjects(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		data = new ResultsMap();
		findTaintObjects_(obj);

	}

	private void findTaintObjects_(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {

		if (obj != null) {
			if (obj.getClass().isArray()) {
				boolean isPrimitive = this.checkArrayType(obj);
				if (isPrimitive)
					this.findTaintPrimitiveArray(obj);
				else
					this.findTaintCustomArray(obj);
			} else {
				if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
					if (MultiTainter.getTaint(obj) != null) {
						data.updateKeyValueBy(levelStack.peek(), 1);
						System.out.println(MultiTainter.getTaint(obj));
					}
				}

				else
					this.findTaintCustomObject(obj);
			}
		}
	}

}
