package edu.vanderbilt.taintalayzer.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr2D;
import edu.vanderbilt.taintalayzer.test.sample.Foo.MyStruct_arr2D_final;
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
	MyStruct_arr_final msaf = new MyStruct_arr_final();
	MyStruct_ref msr = new MyStruct_ref();
	MyStruct_ref_final msrf = new MyStruct_ref_final();
	MyStruct_ref_array msra = new MyStruct_ref_array();
	MyStruct_ref_array_final msraf = new MyStruct_ref_array_final();
	MyStruct_arr2D msa2d = new MyStruct_arr2D();
	MyStruct_arr2D_final msa2df = new MyStruct_arr2D_final();
	MyStruct_ref2D msr2d = new MyStruct_ref2D();
	MyStruct_ref2D_final msr2df = new MyStruct_ref2D_final();
	MyStruct_ref_array2D msra2d = new MyStruct_ref_array2D();
	MyStruct_ref_array2D_final msra2df = new MyStruct_ref_array2D_final();
	HashMap<Integer, Integer> result;
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
		int[][] arr_i = {{1},{3}};
		assertEquals(2, ObjectCounter.flattenArray(arr_i).size());
	}
	
	@Test
	public void testGetStat() throws Exception {
		result = ObjectCounter.getStat(1);
		expectedResult.put(0,  1);
		assertEquals(expectedResult, result);
		
		result = ObjectCounter.getStat(new int[1]);
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStruct() throws Exception {
		expectedResult.put(0, 1);
		expectedResult.put(1, 8);
		result = ObjectCounter.getStat(ms);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testGetStatMyStructFinal() throws Exception {
	  result = ObjectCounter.getStat(msf);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructArr() throws Exception {
	  result = ObjectCounter.getStat(msa);
	  expectedResult.put(0, 1);
	  expectedResult.put(1, 25);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructArrFinal() throws Exception {
	  result = ObjectCounter.getStat(msaf);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRef() throws Exception {
	  result = ObjectCounter.getStat(msr);
	  expectedResult.put(0, 1);
	  expectedResult.put(1, 2);
	  expectedResult.put(2, 33);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRefFinal() throws Exception {
	  result = ObjectCounter.getStat(msrf);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}

	@Test
	public void testGetStatMyStructRefArray() throws Exception {
	  result = ObjectCounter.getStat(msra);
	  expectedResult.put(0, 1);
	  expectedResult.put(1, 2);
	  expectedResult.put(2, 4);
	  expectedResult.put(3, 66);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRefArrayFinal() throws Exception {
	  result = ObjectCounter.getStat(msraf);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructArr2D() throws Exception {
	  result = ObjectCounter.getStat(msa2d);
	  expectedResult.put(0, 1);
	  expectedResult.put(1, 16);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructArr2DFinal() throws Exception {
	  result = ObjectCounter.getStat(msa2df);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRef2D() throws Exception {
	  result = ObjectCounter.getStat(msr2d);
	  expectedResult.put(0, 1);
	  expectedResult.put(1, 2);
	  expectedResult.put(2, 24);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRef2DFinal() throws Exception {
	  result = ObjectCounter.getStat(msr2df);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRefArray2D() throws Exception {
	  result = ObjectCounter.getStat(msra2d);
	  expectedResult.put(0, 1);
	  expectedResult.put(1, 2);
	  expectedResult.put(2, 4);
	  expectedResult.put(3, 48);
	  assertEquals(expectedResult, result);
	}
	@Test
	public void testGetStatMyStructRefArray2DFinal() throws Exception {
	  result = ObjectCounter.getStat(msra2df);
	  expectedResult.put(0, 1);
	  assertEquals(expectedResult, result);
	}

	
}
