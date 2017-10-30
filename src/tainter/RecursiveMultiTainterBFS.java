package tainter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.ClassUtils;

import classes.myPair;
import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class RecursiveMultiTainterBFS {
	private Queue<myPair> myQueue = new LinkedList<myPair>();
	private int MAX_LEVEL;
	private int MAX_TAINTS;
	private int CurrTaints = 0;
	private Taint<String> taint;

	public RecursiveMultiTainterBFS(int level, int MAX_TAINTS) {
		this.MAX_LEVEL = level;
		this.MAX_TAINTS = MAX_TAINTS;
	}

	public void taintObjects(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		this.taint = taint;
		this.taintObjectsfiltered(new myPair(0, obj));
	}

	public void taintObjects(Object obj, Taint<String> taint, int max_level,
			int max_taints) throws ArrayIndexOutOfBoundsException,
			IllegalArgumentException, Exception {
		this.MAX_LEVEL = max_level;
		this.MAX_TAINTS = max_taints;
		this.taint = taint;
		this.taintObjects(obj, taint);
	}

	private void taintObjectsfiltered(myPair p_)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myQueue.add(p_);
		while (!this.myQueue.isEmpty()){
			myPair p = this.myQueue.poll();
			if (p.getLevel() <= this.MAX_LEVEL && this.CurrTaints < this.MAX_TAINTS) {
				Object obj = p.getObj();
				if (obj == null) {
					System.out.println("Skipping null object");
				}
				// Checking if the obj is an array
				if (obj.getClass().isArray()) {
					boolean isPrimitive = this.isPrimitiveArray(obj);
					// obj is an array of primitive types
					if (isPrimitive)
						this.taintPrimitiveArrayfiltered(obj);
					// obj is an array of custom types
					else
						this.taintCustomArrayfiltered(p);
					// obj is not an array
				} else {
					// obj is a primitive type or a wrapper associated with
					// primitive
					// type
					if (ClassUtils.isPrimitiveOrWrapper(obj.getClass()))
						this.taintPrimitivefiltered(obj);
					// obj is a reference to custom type
					else
						this.taintCustomObjectfiltered(p);
				}

			}
		}
		
	}
	
	private boolean isGreaterThanOneDimension(Object obj) {
		// Returns true if the dimension is greater than one, otherwise, false
		boolean dimension = false;
		if ((obj.getClass().getName()).lastIndexOf("[") > 0)
			dimension = true;
		return dimension;
	}

	private boolean isPrimitiveArray(Object obj) {
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
	
	private void taintPrimitiveArrayfiltered(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass()
					.getComponentType())) {
				this.taintPrimitiveArray1Dfiltered(obj_);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}

	}
	
	
	private void taintPrimitiveArray1Dfiltered(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		if (obj.getClass().getComponentType().getTypeName() == int.class
				.getTypeName()) {
			obj = MultiTainter.taintedIntArray((int[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == long.class
				.getTypeName()) {
			obj = MultiTainter.taintedLongArray((long[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == boolean.class
				.getTypeName()) {
			obj = MultiTainter.taintedBooleanArray((boolean[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == short.class
				.getTypeName()) {
			obj = MultiTainter.taintedShortArray((short[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == double.class
				.getTypeName()) {
			obj = MultiTainter.taintedDoubleArray((double[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == byte.class
				.getTypeName()) {
			obj = MultiTainter.taintedByteArray((byte[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == char.class
				.getTypeName()) {
			obj = MultiTainter.taintedCharArray((char[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == float.class
				.getTypeName()) {
			obj = MultiTainter.taintedFloatArray((float[]) obj, taint);
			this.CurrTaints+= Array.getLength(obj);
		} else if (obj.getClass().getComponentType().getTypeName() == void.class
				.getTypeName()) {
			System.out.println("Skipping Void type");
		} else {
			throw new Exception("Primitive Array Type Decoding Error");
		}
	}
	
	private void taintCustomArrayfiltered(myPair p)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		for (int i = 0; i < Array.getLength(p.getObj()); i++)
			this.myQueue.add(new myPair(p.getLevel(), Array.get(p.getObj(), i)));
	}
	
	
	private void taintPrimitivefiltered(Object obj) {
		System.out
				.println("Recursive Multitainter is not suited for Primitive types");
	}
	
	private void taintCustomObjectfiltered(myPair p)
			throws Exception {
		MultiTainter.taintedObject(p.getObj(), taint);
		this.CurrTaints++;
		if (p.getLevel() < this.MAX_LEVEL) {
			Object obj = p.getObj();
			for (Field f : obj.getClass().getDeclaredFields()) {
				if (this.CurrTaints >= this.MAX_TAINTS) break;
				f.setAccessible(true);
					if (ClassUtils.isPrimitiveOrWrapper(f.getType())) {
						if (!Modifier.isFinal(f.getModifiers())) {
							if (f.getType() == int.class) {
								f.setInt(
										obj,
										MultiTainter.taintedInt(f.getInt(obj),
												taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == long.class) {
								f.setLong(
										obj,
										MultiTainter.taintedLong(f.getLong(obj),
												taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == boolean.class) {
								f.setBoolean(obj, MultiTainter.taintedBoolean(
										f.getBoolean(obj), taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == short.class) {
								f.setShort(obj, MultiTainter.taintedShort(
										f.getShort(obj), taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == double.class) {
								f.setDouble(obj, MultiTainter.taintedDouble(
										f.getDouble(obj), taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == byte.class) {
								f.setByte(
										obj,
										MultiTainter.taintedByte(f.getByte(obj),
												taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == char.class) {
								f.setChar(
										obj,
										MultiTainter.taintedChar(f.getChar(obj),
												taint.getLabel()));
								this.CurrTaints++;
							} else if (f.getType() == float.class) {
								f.setFloat(obj, MultiTainter.taintedFloat(
										f.getFloat(obj), taint.getLabel()));
								this.CurrTaints++;
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
							&& this.isPrimitiveArray(f.get(obj)) && !this.isGreaterThanOneDimension(f.get(obj)))
						f.set(obj,
								this.taintPrimitiveArrayWreturnfiltered(f.get(obj)));
					else if ((f.get(obj)).getClass().isArray()
							&& this.isPrimitiveArray(f.get(obj)) && this.isGreaterThanOneDimension(f.get(obj)))
								this.taintPrimitiveArrayfiltered(f.get(obj));
					else
						this.myQueue.add(new myPair(p.getLevel()+1, f.get(obj)));
				}
		}

	}
	
	private Object taintPrimitiveArrayWreturnfiltered(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass()
					.getComponentType())) {
				this.taintPrimitiveArray1Dfiltered(obj_);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}
		return obj;
	}
	
}
