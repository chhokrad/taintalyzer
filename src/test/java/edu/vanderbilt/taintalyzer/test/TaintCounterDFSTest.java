package edu.vanderbilt.taintalyzer.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalyzer.tainter.RecursiveMultiTainter;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_arr;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_arr2D;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref2D;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_array;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_array2D;
import edu.vanderbilt.taintalyzer.utility.TaintCounterDFS;

public class TaintCounterDFSTest {

	public Taint<String> t = new Taint<String>("tainted_recursively");
	HashMap<Integer, Integer> expectedResult;


	@Before
	public void setUp() throws Exception {
		expectedResult = new HashMap<Integer, Integer>();
	}

	@Test
	public void CheckPrimitive() throws Exception
	{
		int i = 0;
		i = MultiTainter.taintedInt(i, "tainted_directly");
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(i);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0,  1);
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckPrimitiveArray() throws Exception
	{
		int[] m = {1,2,3,4};
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0,  4);
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckPrimitiveArrayNull() throws Exception
	{
		int[] m = null;
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		assertEquals(expectedResult, o.getdata());
	}


	@Test
	public void CheckPrimitiveArray2D() throws Exception
	{
		int[][] m = {{1,2,3,4}, {1,2,3,4}};
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		expectedResult.put(0, 8);
		assertEquals(0, o.checkStacklevl());
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckObjectWithPrimitive() throws Exception
	{
		MyStruct m = new MyStruct();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 8);
		assertEquals(expectedResult, o.getdata());

	}

	@Test
	public void CheckObjectWithPrimitiveArray() throws Exception
	{
		// This test fails due to bug in Phosphor JRE
		MyStruct_arr m = new MyStruct_arr();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 25);
		assertEquals(expectedResult, o.getdata());

	}

	@Test
	public void CheckObjectWithPrimitiveArray2D() throws Exception
	{
		MyStruct_arr2D m = new MyStruct_arr2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 16);
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckMyStruct_ref() throws Exception
	{
		MyStruct_ref m =  new MyStruct_ref();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 33);
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckMyStruct_ref2D() throws Exception
	{
		MyStruct_ref2D m =  new MyStruct_ref2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 24);
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckMyStruct_ref_array() throws Exception
	{
		MyStruct_ref_array m =  new MyStruct_ref_array();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 4);
		expectedResult.put(3, 66);
		assertEquals(expectedResult, o.getdata());
	}

	@Test
	public void CheckMyStruct_ref_array2D() throws Exception
	{
		MyStruct_ref_array2D m =  new MyStruct_ref_array2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, t);
		TaintCounterDFS o = new TaintCounterDFS();
		o.findTaintObjects(m);
		assertEquals(0, o.checkStacklevl());
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 4);
		expectedResult.put(3, 48);
		assertEquals(expectedResult, o.getdata());
	}

}
