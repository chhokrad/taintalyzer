package edu.vanderbilt.taintalayzer.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalayzer.tainter.RecursiveMultiTainter;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr2D;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr2DNoBool;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr2D_final;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arrNoBool;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr_final;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_final;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref2D;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref2D_final;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref_array;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref_array2D;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref_array2D_final;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref_array_final;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_ref_final;
import edu.vanderbilt.taintalayzer.utility.ObjectCounter;

public class ObjectCounterTest {

	MyStruct ms = new MyStruct();
	MyStruct_final msf = new MyStruct_final();
	MyStruct_arr msa = new MyStruct_arr();
	MyStruct_arrNoBool msanb = new MyStruct_arrNoBool();
	MyStruct_arr_final msaf = new MyStruct_arr_final();
	MyStruct_ref msr = new MyStruct_ref();
	MyStruct_ref_final msrf = new MyStruct_ref_final();
	MyStruct_ref_array msra = new MyStruct_ref_array();
	MyStruct_ref_array_final msraf = new MyStruct_ref_array_final();
	MyStruct_arr2D msa2d = new MyStruct_arr2D();
	MyStruct_arr2DNoBool msa2dnb = new MyStruct_arr2DNoBool();
	MyStruct_arr2D_final msa2df = new MyStruct_arr2D_final();
	MyStruct_ref2D msr2d = new MyStruct_ref2D();
	MyStruct_ref2D_final msr2df = new MyStruct_ref2D_final();
	MyStruct_ref_array2D msra2d = new MyStruct_ref_array2D();
	MyStruct_ref_array2D_final msra2df = new MyStruct_ref_array2D_final();
	HashMap<Integer, Integer> result;
	HashMap<Integer, Integer> numberOfObjects;
	HashMap<Integer, Integer> expectedResult;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		expectedResult = new HashMap<Integer, Integer>();
	}

	@Test
	public void testFlattenArray() {
		int x = 3;
		assertEquals(1, ObjectCounter.flattenArray(x).size());
		int[][] arr_i = { { 1 }, { 3 } };
		assertEquals(2, ObjectCounter.flattenArray(arr_i).size());
	}

	@Test
	public void testGetStat() throws Exception {
		result = ObjectCounter.getStatObjects(1);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
		result = ObjectCounter.getStatObjects(new int[1]);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStruct() throws Exception {
		expectedResult.put(0, 1);
		expectedResult.put(1, 8);
		result = ObjectCounter.getStatObjects(ms);
		assertEquals(expectedResult, result);
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		rmt.taintObjects(ms, new Taint<String>("ms"));
		result = ObjectCounter.getStatTaints(ms);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msf);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructArr() throws Exception {
		result = ObjectCounter.getStatObjects(msa);
		expectedResult.put(0, 1);
		expectedResult.put(1, 25);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructArrFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msaf);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRef() throws Exception {
		result = ObjectCounter.getStatObjects(msr);
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 33);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRefFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msrf);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRefArray() throws Exception {
		result = ObjectCounter.getStatObjects(msra);
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 4);
		expectedResult.put(3, 66);
		assertEquals(expectedResult, result);
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		rmt.taintObjects(msra, new Taint<String>("msra"));
		result = ObjectCounter.getStatTaints(msra);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRefArrayFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msraf);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructArr2D() throws Exception {
		result = ObjectCounter.getStatObjects(msa2d);
		expectedResult.put(0, 1);
		expectedResult.put(1, 16);
		assertEquals(expectedResult, result);
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		rmt.taintObjects(msa2d, new Taint<String>("msa2d"));
		result = ObjectCounter.getStatTaints(msa2d);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructArr2DFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msa2df);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRef2D() throws Exception {
		result = ObjectCounter.getStatObjects(msr2d);
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 24);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRef2DFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msr2df);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRefArray2D() throws Exception {
		result = ObjectCounter.getStatObjects(msra2d);
		expectedResult.put(0, 1);
		expectedResult.put(1, 2);
		expectedResult.put(2, 4);
		expectedResult.put(3, 48);
		assertEquals(expectedResult, result);
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		rmt.taintObjects(msra2d, new Taint<String>("msra2d"));
		result = ObjectCounter.getStatTaints(msra2d);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRefArray2DFinal() throws Exception {
		result = ObjectCounter.getStatObjects(msra2df);
		expectedResult.put(0, 1);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatEmptyArray() throws Exception {
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		int[] emptyArr = new int[0];
		rmt.taintObjects(emptyArr, new Taint<String>("empty"));
		result = ObjectCounter.getStatTaints(emptyArr);
		numberOfObjects = ObjectCounter.getStatObjects(emptyArr);
		assertEquals(expectedResult, result);
		assertEquals(numberOfObjects, result);
	}

	@Test
	public void testGetStatTaint1DArrPrimitive() throws Exception {
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		int[] arr_i = { 1, 2, 3 };
		rmt.taintObjects(arr_i, new Taint<String>("arr_i"));
		result = ObjectCounter.getStatTaints(arr_i);
		numberOfObjects = ObjectCounter.getStatObjects(arr_i);
		expectedResult.put(0, 3);
		assertEquals(expectedResult, result);
		assertEquals(numberOfObjects, result);
	}

	@Test
	public void testGetStatTaintMyStruct_arrNoBool() throws Exception {
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		rmt.taintObjects(msanb, new Taint<String>("msanb"));
		result = ObjectCounter.getStatTaints(msanb);
		numberOfObjects = ObjectCounter.getStatObjects(msanb);
		assertEquals(numberOfObjects, result);
	}

	@Test
	public void testGetStatTaintMyStruct_arr2DNoBool() throws Exception {
		RecursiveMultiTainter rmt = new RecursiveMultiTainter();
		rmt.taintObjects(msa2dnb, new Taint<String>("msa2dnb"));
		result = ObjectCounter.getStatTaints(msa2dnb);
		numberOfObjects = ObjectCounter.getStatObjects(msa2dnb);
	}

	@Test
	public void checkPrimitiveArray2D() throws Exception {
		int[][] m = { { 1, 2, 3, 4 }, { 1, 2, 3, 4 } };
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("2darray"));
		result = ObjectCounter.getStatTaints(m);
		expectedResult.put(0, 8);
		assertEquals(expectedResult, result);
	}

}
