package edu.vanderbilt.taintalayzer.test;

import org.junit.Ignore;
import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalayzer.tainter.RecursiveMultiTainter;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr2D;
import edu.vanderbilt.taintalayzer.utility.ObjectCounterDFS;

public class ObjectCounterDFSTest {
	
	public Taint<String> t = new Taint<String>("tainted_recursively");
	public static class MyStruct{
		public int i = 10;
		public long j = 10;
		public boolean z = false;
		public short s = 1;
		public double d = 12.34;
		public byte b = 0;
		public char c = '1';
		public float f = 34;
		
	}
	
	public static class MyStruct_arr {
		public int[] arr_i = {1,2,3};
		public long[] arr_j = {1,2,3};
		public boolean[] arr_z = {true, false, true};
		public short[] arr_s = {1,2,3};
		public double[] arr_d = {1.1, 2.2, 3.3};
		public byte[] arr_b = {0, 1, 0, 0};
		public char[] arr_c = {'1','2','3'};
		public float[] arr_f = {1,2,3};
	}
	
	@Ignore 
	public void CheckPrimitive() throws Exception
	{
		int i = 0;
		i = MultiTainter.taintedInt(i, "tainted_directly");
		System.out.print(MultiTainter.getTaint(i));
		System.out.println("");
		ObjectCounterDFS o = new ObjectCounterDFS();
		o.taintObjects(i, null);
	}
	
	
	@Ignore 
	public void CheckObjectWithPrimitive() throws Exception
	{
		MyStruct m = new MyStruct();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		ObjectCounterDFS o = new ObjectCounterDFS();
		o.taintObjects(m, t);
		
	}
	
	@Ignore
	public void CheckPrimitiveArray() throws Exception
	{
		int[] m = {1,2,3,4};
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		ObjectCounterDFS o = new ObjectCounterDFS();
		o.taintObjects(m, t);
		
	}
	
	@Ignore
	public void CheckPrimitiveArray2D() throws Exception
	{
		int[][] m = {{1,2,3,4}, {1,2,3,4}};
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		ObjectCounterDFS o = new ObjectCounterDFS();
		o.taintObjects(m, t);
		
	}
	
	@Ignore 
	public void CheckObjectWithPrimitiveArray() throws Exception
	{
		MyStruct_arr m = new MyStruct_arr();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		ObjectCounterDFS o = new ObjectCounterDFS();
		o.taintObjects(m, t);
		
	}
	
	@Ignore 
	public void CheckObjectWithPrimitiveArray2D() throws Exception
	{
		MyStruct_arr2D m = new MyStruct_arr2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		ObjectCounterDFS o = new ObjectCounterDFS();
		o.taintObjects(m, t);
		
	}

}
