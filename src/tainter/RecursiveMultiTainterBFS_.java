package tainter;

import helper.LevelObjPair;
import helper.LevelObjPairComparator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.lang3.ClassUtils;

import classes.ResultsMap;
import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class RecursiveMultiTainterBFS_ {
	private Queue<LevelObjPair> myQueue = new PriorityQueue<LevelObjPair>(
			new LevelObjPairComparator());
	private int MAX_LEVEL;
	private int MAX_TAINTS;
	private int CurrTaints = 0;
	private Taint<String> taint;
	private ResultsMap data = new ResultsMap();

	public RecursiveMultiTainterBFS_(int level, int MAX_TAINTS) {
		this.MAX_LEVEL = level;
		this.MAX_TAINTS = MAX_TAINTS;
		this.CurrTaints = 0;
	}

	public void taintObjects(Object obj, Taint<String> taint)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		this.CurrTaints = 0;
		this.taint = taint;
		this.printState();
		this.taintObjectsfiltered(new LevelObjPair(0, obj));
	}

	private void printState() {
		// TODO Auto-generated method stub
		System.out.println("State of Tainter is as follows");
		System.out.println("Number of Maximum Taints : " + this.MAX_TAINTS);
		System.out.println("Number of Maximum Levels : " + this.MAX_LEVEL);
		System.out.println("Number of Current Taints : " + this.CurrTaints);
		System.out.println("Taint label : " + this.taint.lbl.toString());
	}
	
	
	public void taintObjects(Object obj, Taint<String> taint, int max_level,
			int max_taints) throws ArrayIndexOutOfBoundsException,
			IllegalArgumentException, Exception {
		this.MAX_LEVEL = max_level;
		this.MAX_TAINTS = max_taints;
		this.taint = taint;
		this.taintObjects(obj, taint);
	}

	private void taintObjectsfiltered(LevelObjPair p_)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myQueue.add(p_);
		while (!this.myQueue.isEmpty()) {
			LevelObjPair p = this.myQueue.poll();
			if (p.getLevel() <= this.MAX_LEVEL
					&& this.CurrTaints < this.MAX_TAINTS) {
				Object obj = p.getObj();
				if (obj == null) {
					System.out.println("Skipping null object");
				}
				// Checking if the obj is an array
				if (obj.getClass().isArray()) {
					boolean isPrimitive = this.isPrimitiveArray(obj);
					// obj is an array of primitive types
					if (isPrimitive){
						int prev = this.CurrTaints;
						this.taintPrimitiveArrayfiltered(obj);
						int now = this.CurrTaints - prev;
						data.updateKeyValueBy(p.getLevel(), now);
					}
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
				if ((Array.getLength(obj_) + this.CurrTaints) <= this.MAX_TAINTS)
					this.taintPrimitiveArray1Dfiltered(obj_);
				else
					this.taintPrimitiveArray1DfilteredbyElement(obj_);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}

	}

	private void taintPrimitiveArray1DfilteredbyElement(Object obj)
			throws Exception {
		int ElementsToBeTainted = this.MAX_TAINTS - this.CurrTaints;
		this.CurrTaints += ElementsToBeTainted;
		String CompType = obj.getClass().getComponentType().getName();
		System.out.println(CompType);
		for (int i = 0; i < ElementsToBeTainted; i++) {
			switch (CompType) {
			case "int":
				int[] temp_int = (int[]) obj;
				temp_int[i] = MultiTainter.taintedInt(temp_int[i], taint.lbl);
				obj = temp_int;
				break;
			case "long":
				long[] temp_long = (long[]) obj;
				temp_long[i] = MultiTainter.taintedLong(temp_long[i], taint.lbl);
				obj = temp_long;
				break;
			case "boolean":
				boolean[] temp_boolean = (boolean[]) obj;
				temp_boolean[i] = MultiTainter.taintedBoolean(temp_boolean[i], taint.lbl);
				obj = temp_boolean;
				break;
			case "short":
				short[] temp_short = (short[]) obj;
				temp_short[i] = MultiTainter.taintedShort(temp_short[i], taint.lbl);
				obj = temp_short;
				break;
			case "double":
				double[] temp_double = (double[]) obj;
				temp_double[i] = MultiTainter.taintedDouble(temp_double[i], taint.lbl);
				obj = temp_double;
				break;
			case "byte":
				byte[] temp_byte = (byte[]) obj;
				temp_byte[i] = MultiTainter.taintedByte(temp_byte[i], taint.lbl);
				obj = temp_byte;
			case "char":
				char[] temp_char = (char[]) obj;
				temp_char[i] = MultiTainter.taintedChar(temp_char[i], taint.lbl);
				obj = temp_char;
				break;
			case "float":
				float[] temp_float = (float[]) obj;
				temp_float[i] = MultiTainter.taintedFloat(temp_float[i], taint.lbl);
				obj = temp_float;
				break;
			default: 
				this.CurrTaints -= ElementsToBeTainted;
				throw new Exception("Primtive Type Decoding Error");
			}
		}
	}

	private void taintPrimitiveArray1Dfiltered(Object obj)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		this.CurrTaints += Array.getLength(obj);
		if (obj.getClass().getComponentType().getTypeName() == int.class
				.getTypeName()) {
			obj = MultiTainter.taintedIntArray((int[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == long.class
				.getTypeName()) {
			obj = MultiTainter.taintedLongArray((long[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == boolean.class
				.getTypeName()) {
			obj = MultiTainter.taintedBooleanArray((boolean[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == short.class
				.getTypeName()) {
			obj = MultiTainter.taintedShortArray((short[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == double.class
				.getTypeName()) {
			obj = MultiTainter.taintedDoubleArray((double[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == byte.class
				.getTypeName()) {
			obj = MultiTainter.taintedByteArray((byte[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == char.class
				.getTypeName()) {
			obj = MultiTainter.taintedCharArray((char[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == float.class
				.getTypeName()) {
			obj = MultiTainter.taintedFloatArray((float[]) obj, taint);
		} else if (obj.getClass().getComponentType().getTypeName() == void.class
				.getTypeName()) {
			this.CurrTaints -= Array.getLength(obj);
			System.out.println("Skipping Void type");
		} else {
			this.CurrTaints -= Array.getLength(obj);
			throw new Exception("Primitive Array Type Decoding Error");
		}
	}

	private void taintCustomArrayfiltered(LevelObjPair p)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		for (int i = 0; i < Array.getLength(p.getObj()); i++)
			this.myQueue.add(new LevelObjPair(p.getLevel(), Array.get(
					p.getObj(), i)));
	}

	private void taintPrimitivefiltered(Object obj) {
		System.out
				.println("Recursive Multitainter is not suited for Primitive types");
	}

	private void taintCustomObjectfiltered(LevelObjPair p) throws Exception {
		MultiTainter.taintedObject(p.getObj(), taint);
		this.CurrTaints++;
		this.data.updateKeyValueBy(p.getLevel(), 1);
		if (p.getLevel() < this.MAX_LEVEL) {
			Object obj = p.getObj();
			for (Field f : obj.getClass().getDeclaredFields()) {
				if (this.CurrTaints >= this.MAX_TAINTS)
					break;
				f.setAccessible(true);
				if (ClassUtils.isPrimitiveOrWrapper(f.getType())) {
					if (!Modifier.isFinal(f.getModifiers())) {
						this.data.updateKeyValueBy(p.getLevel()+1, 1);
						if (f.getType() == int.class) {
							f.setInt(obj, MultiTainter.taintedInt(
									f.getInt(obj), taint.getLabel()));
							this.CurrTaints++;
						} else if (f.getType() == long.class) {
							f.setLong(obj, MultiTainter.taintedLong(
									f.getLong(obj), taint.getLabel()));
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
							f.setByte(obj, MultiTainter.taintedByte(
									f.getByte(obj), taint.getLabel()));
							this.CurrTaints++;
						} else if (f.getType() == char.class) {
							f.setChar(obj, MultiTainter.taintedChar(
									f.getChar(obj), taint.getLabel()));
							this.CurrTaints++;
						} else if (f.getType() == float.class) {
							f.setFloat(obj, MultiTainter.taintedFloat(
									f.getFloat(obj), taint.getLabel()));
							this.CurrTaints++;
						} else if (f.getType() == void.class) {
							System.out.println("Skipping void");
						} else {
							this.data.updateKeyValueBy(p.getLevel()+1, -1);
							throw new Exception("Primitive Type Decoding Error");
						}
					} else
						System.out.println("Skipping tainting a Final Field : "
								+ f.getType() + " " + f.getName() + " in "
								+ obj.getClass().getName());
				} else if ((f.get(obj)).getClass().isArray()
						&& this.isPrimitiveArray(f.get(obj))
						&& !this.isGreaterThanOneDimension(f.get(obj))){
					int prev = this.CurrTaints;
					f.set(obj,
							this.taintPrimitiveArrayWreturnfiltered(f.get(obj)));
					int now = this.CurrTaints;
					this.data.updateKeyValueBy(p.getLevel()+1, now-prev);
				}
				else if ((f.get(obj)).getClass().isArray()
						&& this.isPrimitiveArray(f.get(obj))
						&& this.isGreaterThanOneDimension(f.get(obj))){
					int prev = this.CurrTaints;
					this.taintPrimitiveArrayfiltered(f.get(obj));
					int now = this.CurrTaints;
					this.data.updateKeyValueBy(p.getLevel()+1, now-prev);
				}
				else
					this.myQueue.add(new LevelObjPair(p.getLevel() + 1, f
							.get(obj)));
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
				if ((Array.getLength(obj_) + this.CurrTaints) <= this.MAX_TAINTS)
					this.taintPrimitiveArray1Dfiltered(obj_);
				else
					this.taintPrimitiveArray1DfilteredbyElement(obj_);
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}
		return obj;
	}

	public int getMAX_LEVEL() {
		return MAX_LEVEL;
	}

	public int getMAX_TAINTS() {
		return MAX_TAINTS;
	}

	public int getCurrTaints() {
		return CurrTaints;
	}

	public ResultsMap getData() {
		return data;
	}
}
