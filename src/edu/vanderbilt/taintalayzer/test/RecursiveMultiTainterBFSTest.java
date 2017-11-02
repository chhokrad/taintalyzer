package edu.vanderbilt.taintalayzer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalayzer.tainter.RecursiveMultiTainter;
import edu.vanderbilt.taintalayzer.tainter.RecursiveMultiTainterBFS;
import edu.vanderbilt.taintalayzer.tainter.RecursiveMultiTainterBFS_;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref_array2D;

public class RecursiveMultiTainterBFSTest {
	
	public Taint getTaintPrimitive(Field f, Object obj) throws Exception
	{
		switch (f.getType().getName()) {
		case "int": return MultiTainter.getTaint(f.getInt(obj));
		case "long": return MultiTainter.getTaint(f.getLong(obj));
		case "boolean": return MultiTainter.getTaint(f.getBoolean(obj));
		case "short": return MultiTainter.getTaint(f.getShort(obj));
		case "double": return MultiTainter.getTaint(f.getDouble(obj));
		case "byte": return MultiTainter.getTaint(f.getByte(obj));
		case "char": return MultiTainter.getTaint(f.getChar(obj));
		case "float": return MultiTainter.getTaint(f.getFloat(obj));
		default: return null;
		}
	}
	
	public int getArrayTaints(Object obj)
	{
		int numTaints = 0;
		
		Queue<Object> myqueue = new LinkedList<Object>();
		myqueue.add(obj);
		while (!myqueue.isEmpty()) {
			Object obj_ = new Object();
			obj_ = myqueue.poll();
			if (ClassUtils.isPrimitiveOrWrapper(obj_.getClass()
					.getComponentType())) {
				numTaints += getArrayTaints1D(obj_); 
			} else {
				int length = Array.getLength(obj_);
				for (int index = 0; index < length; index++)
					myqueue.add(Array.get(obj_, index));
			}
		}
		return numTaints;
	}
		
	private int getArrayTaints1D(Object obj_) {
		// TODO Auto-generated method stub
		int numTaints = 0;
		int length = Array.getLength(obj_);
		for (int index = 0; index < length; index++)
		{
			System.out.println(obj_.getClass().getComponentType().getName());
			System.out.println(Array.get(obj_, index));
			System.out.println(MultiTainter.getTaint(Array.get(obj_, index)));
			if (MultiTainter.getTaint(Array.get(obj_, index)) != null) numTaints++;
		}
			
		return numTaints;
	}

	
	
	public void simpleMaxTaint() throws Exception {
		MyStruct ms = new MyStruct();
		RecursiveMultiTainterBFS rtbfs = new RecursiveMultiTainterBFS(Integer.MAX_VALUE, 3);
		rtbfs.taintObjects(ms, new Taint<String>("tainted_recursive"));
		int counter = 0;
		if (MultiTainter.getTaint(ms)!= null) counter++;
		for (Field f : ms.getClass().getDeclaredFields()) {
			if (Modifier.isFinal(f.getModifiers())) continue;
			if (getTaintPrimitive(f, ms) != null) counter++;
		}
		assertEquals(counter, 3);
	}
	
	
	
	public void simpleMaxTaintArray() throws Exception {
		MyStruct_arr ms = new MyStruct_arr();
		RecursiveMultiTainterBFS rtbfs = new RecursiveMultiTainterBFS(Integer.MAX_VALUE, 8);
		rtbfs.taintObjects(ms, new Taint<String>("simple"));
		int counter = 0;	
		if (MultiTainter.getTaint(ms) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_i[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_i[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_i[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_b[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_b[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_b[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_b[3]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_c[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_c[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_c[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_d[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_d[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_d[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_f[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_f[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_f[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_j[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_j[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_j[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_s[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_s[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_s[2]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_z[0]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_z[1]) != null) counter++;
		if (MultiTainter.getTaint(ms.arr_z[2]) != null) counter++;
		assertEquals(counter, 8);	
	}
	
	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArrays()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		MyStruct_ref_array2D m = new MyStruct_ref_array2D();
		RecursiveMultiTainterBFS_ R = new RecursiveMultiTainterBFS_(2, 5);
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

		//assertEquals(R.getCurrTaints(), 7);
		System.out.println(R.getData().getKeySet().contains(4));
	}
}
