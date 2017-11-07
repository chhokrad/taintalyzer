package edu.vanderbilt.taintalyzer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalyzer.tainter.RecursiveMultiTainterBFS_;
import edu.vanderbilt.taintalyzer.test.sample.AnotherStruct;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_arr;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_arr2D;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_arr2D_final;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_arr_final;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_final;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref2D;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref2D_final;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_array;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_array2D;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_array2D_final;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_array_final;
import edu.vanderbilt.taintalyzer.test.sample.Foo.MyStruct_ref_final;
import edu.vanderbilt.taintalyzer.utility.ObjectCounter;
import edu.vanderbilt.taintalyzer.utility.TaintCounterDFS;

public class RecursiveMultiTainterBFSTest {

	Taint<String> t = new Taint<String>("tainted_recursive");
	RecursiveMultiTainterBFS_ R = new RecursiveMultiTainterBFS_(Integer.MAX_VALUE, Integer.MAX_VALUE);
	TaintCounterDFS o;

	@Before
	public void setUp() throws Exception {
		o = new TaintCounterDFS();
	}

	@Test
	public void PrimitiveArraysTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {

		int[] arr_i = { 1, 2, 3 };
		long[] arr_j = { 1, 2, 3 };
		boolean[] arr_z = { true, false, true };
		short[] arr_s = { 1, 2, 3 };
		double[] arr_d = { 1.1, 2.2, 3.3 };
		byte[] arr_b = { 0, 1, 0, 0 };
		char[] arr_c = { '1', '2', '3' };
		float[] arr_f = { 1, 3, 5 };

		R.taintObjects(arr_i, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_i[0]));
		assertNotNull(MultiTainter.getTaint(arr_i[1]));
		assertEquals(R.getData().getresults(), o.getdata());
		assertEquals(ObjectCounter.getStatTaints(arr_i), o.getdata());

		R.taintObjects(arr_j, t);
		o.findTaintObjects(arr_j);
		assertNotNull(MultiTainter.getTaint(arr_j[0]));
		assertNotNull(MultiTainter.getTaint(arr_j[1]));
		assertEquals(R.getData().getresults(), o.getdata());
		assertEquals(ObjectCounter.getStatTaints(arr_j), o.getdata());

		R.taintObjects(arr_z, t);
		o.findTaintObjects(arr_z);
		assertNotNull(MultiTainter.getTaint(arr_z[0]));
		assertNotNull(MultiTainter.getTaint(arr_z[1]));
		assertEquals(R.getData().getresults(), o.getdata());
		assertEquals(ObjectCounter.getStatTaints(arr_z), o.getdata());

		R.taintObjects(arr_s, t);
		o.findTaintObjects(arr_s);
		assertNotNull(MultiTainter.getTaint(arr_s[0]));
		assertNotNull(MultiTainter.getTaint(arr_s[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_d, t);
		o.findTaintObjects(arr_d);
		assertNotNull(MultiTainter.getTaint(arr_d[0]));
		assertNotNull(MultiTainter.getTaint(arr_d[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_b, t);
		o.findTaintObjects(arr_b);
		assertNotNull(MultiTainter.getTaint(arr_b[0]));
		assertNotNull(MultiTainter.getTaint(arr_b[1]));

		R.taintObjects(arr_c, t);
		o.findTaintObjects(arr_c);
		assertNotNull(MultiTainter.getTaint(arr_c[0]));
		assertNotNull(MultiTainter.getTaint(arr_c[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_f, t);
		o.findTaintObjects(arr_f);
		assertNotNull(MultiTainter.getTaint(arr_f[0]));
		assertNotNull(MultiTainter.getTaint(arr_f[1]));
		assertEquals(R.getData().getresults(), o.getdata());

	}

	@Test
	public void PrimtiveArrays2DTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		int[][] arr_i = { { 1, 2, 3 }, { 1, 2, 3 } };
		long[][] arr_j = { { 1, 2, 3 }, { 1, 2, 3 } };
		boolean[][] arr_z = { { true, false, true }, { true, false, true } };
		short[][] arr_s = { { 1, 2, 3 }, { 1, 2, 3 } };
		double[][] arr_d = { { 1.1, 2.2, 3.3 }, { 1.1, 2.2, 3.3 } };
		byte[][] arr_b = { { 0, 1, 0, 0 }, { 0, 1, 0, 0 } };
		char[][] arr_c = { { '1', '2', '3' }, { '1', '2', '3' } };
		float[][] arr_f = { { 1, 3, 5 }, { 1, 3, 5 } };

		R.taintObjects(arr_i, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_i[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_j, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_j[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_z, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_z[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_s, t);
		o.findTaintObjects(arr_s);
		assertNotNull(MultiTainter.getTaint(arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_s[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_d, t);
		o.findTaintObjects(arr_d);
		assertNotNull(MultiTainter.getTaint(arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_d[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_b, t);
		o.findTaintObjects(arr_b);
		assertNotNull(MultiTainter.getTaint(arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_b[1][0]));

		R.taintObjects(arr_c, t);
		o.findTaintObjects(arr_c);
		assertNotNull(MultiTainter.getTaint(arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_c[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_f, t);
		o.findTaintObjects(arr_f);
		assertNotNull(MultiTainter.getTaint(arr_f[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_f[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void PrimtiveArraysNullTaintedRecursive() throws Exception {
		int[] arr_i = null;
		R.taintObjects(arr_i, t);
		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTAintedRescursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct m = new MyStruct();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.i));
		assertNotNull(MultiTainter.getTaint(m.b));
		assertNotNull(MultiTainter.getTaint(m.c));
		assertNotNull(MultiTainter.getTaint(m.d));
		assertNotNull(MultiTainter.getTaint(m.j));
		assertNotNull(MultiTainter.getTaint(m.s));
		assertNotNull(MultiTainter.getTaint(m.z));
		assertNotNull(MultiTainter.getTaint(m.f));

		assertEquals(R.getData().getresults(), o.getdata());

	}

	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArrays()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_arr m = new MyStruct_arr();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArrays2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_arr2D m = new MyStruct_arr2D();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferences()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref m = new MyStruct_ref();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m1));
		assertNotNull(MultiTainter.getTaint(m.m1.i));
		assertNotNull(MultiTainter.getTaint(m.m1.b));
		assertNotNull(MultiTainter.getTaint(m.m1.c));
		assertNotNull(MultiTainter.getTaint(m.m1.d));
		assertNotNull(MultiTainter.getTaint(m.m1.j));
		assertNotNull(MultiTainter.getTaint(m.m1.s));
		assertNotNull(MultiTainter.getTaint(m.m1.z));
		assertNotNull(MultiTainter.getTaint(m.m1.f));

		assertNotNull(MultiTainter.getTaint(m.m2));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferences2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref2D m = new MyStruct_ref2D();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m1));
		assertNotNull(MultiTainter.getTaint(m.m1.i));
		assertNotNull(MultiTainter.getTaint(m.m1.b));
		assertNotNull(MultiTainter.getTaint(m.m1.c));
		assertNotNull(MultiTainter.getTaint(m.m1.d));
		assertNotNull(MultiTainter.getTaint(m.m1.j));
		assertNotNull(MultiTainter.getTaint(m.m1.s));
		assertNotNull(MultiTainter.getTaint(m.m1.z));
		assertNotNull(MultiTainter.getTaint(m.m1.f));

		assertNotNull(MultiTainter.getTaint(m.m2));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectArrayTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref[] m = { new MyStruct_ref(), new MyStruct_ref() };

		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m[0]));
		assertNotNull(MultiTainter.getTaint(m[0].m1));
		assertNotNull(MultiTainter.getTaint(m[0].m1.i));
		assertNotNull(MultiTainter.getTaint(m[0].m1.b));
		assertNotNull(MultiTainter.getTaint(m[0].m1.c));
		assertNotNull(MultiTainter.getTaint(m[0].m1.d));
		assertNotNull(MultiTainter.getTaint(m[0].m1.j));
		assertNotNull(MultiTainter.getTaint(m[0].m1.s));
		assertNotNull(MultiTainter.getTaint(m[0].m1.z));
		assertNotNull(MultiTainter.getTaint(m[0].m1.f));

		assertNotNull(MultiTainter.getTaint(m[1]));
		assertNotNull(MultiTainter.getTaint(m[1].m2));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectArrayTaintedRecursive2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref2D[][] m = { { new MyStruct_ref2D() }, { new MyStruct_ref2D() } };

		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m[0][0]));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.i));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.b));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.c));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.d));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.j));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.s));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.z));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1.f));

		assertNotNull(MultiTainter.getTaint(m[1][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_f[0][0]));
		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferenceArrays()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref_array m = new MyStruct_ref_array();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m3[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.i));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.b));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.c));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.d));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.j));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.s));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.z));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1.f));

		assertNotNull(MultiTainter.getTaint(m.m3[1]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferenceArrays2D() throws Exception {
		MyStruct_ref_array2D m = new MyStruct_ref_array2D();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m3[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.i));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.b));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.c));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.d));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.j));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.s));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.z));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1.f));

		assertNotNull(MultiTainter.getTaint(m.m3[1][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
//		assertEquals(ObjectCounter.getStatTaints(m), o.getdata());
	}

	@Test
	public void PrivateFieldTest() throws Exception {
		AnotherStruct m = new AnotherStruct();
		R.taintObjects(m, t);
		o.findTaintObjects(m);
		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.getB()));
		assertNotNull(MultiTainter.getTaint(m.getC()));
		assertNotNull(MultiTainter.getTaint(m.getD()));
		assertNotNull(MultiTainter.getTaint(m.getF()));
		assertNotNull(MultiTainter.getTaint(m.getI()));
		assertNotNull(MultiTainter.getTaint(m.getJ()));
		assertNotNull(MultiTainter.getTaint(m.getS()));
		assertNotNull(MultiTainter.getTaint(m.isZ()));

		assertNotNull(MultiTainter.getTaint(m.getArr_b()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_c()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_d()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_f()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_i()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_j()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_s()[0]));
		assertNotNull(MultiTainter.getTaint(m.getArr_z()[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void PrimitiveArraysTaintedRecursiveFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {

		final int[] arr_i = { 1, 2, 3 };
		final long[] arr_j = { 1, 2, 3 };
		final boolean[] arr_z = { true, false, true };
		final short[] arr_s = { 1, 2, 3 };
		final double[] arr_d = { 1.1, 2.2, 3.3 };
		final byte[] arr_b = { 0, 1, 0, 0 };
		final char[] arr_c = { '1', '2', '3' };
		final float[] arr_f = { 1, 3, 5 };

		R.taintObjects(arr_i, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_i[0]));
		assertNotNull(MultiTainter.getTaint(arr_i[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_j, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_j[0]));
		assertNotNull(MultiTainter.getTaint(arr_j[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_z, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_z[0]));
		assertNotNull(MultiTainter.getTaint(arr_z[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_s, t);
		o.findTaintObjects(arr_s);
		assertNotNull(MultiTainter.getTaint(arr_s[0]));
		assertNotNull(MultiTainter.getTaint(arr_s[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_d, t);
		o.findTaintObjects(arr_d);
		assertNotNull(MultiTainter.getTaint(arr_d[0]));
		assertNotNull(MultiTainter.getTaint(arr_d[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_b, t);
		o.findTaintObjects(arr_b);
		assertNotNull(MultiTainter.getTaint(arr_b[0]));
		assertNotNull(MultiTainter.getTaint(arr_b[1]));

		R.taintObjects(arr_c, t);
		o.findTaintObjects(arr_c);
		assertNotNull(MultiTainter.getTaint(arr_c[0]));
		assertNotNull(MultiTainter.getTaint(arr_c[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_f, t);
		o.findTaintObjects(arr_f);
		assertNotNull(MultiTainter.getTaint(arr_f[0]));
		assertNotNull(MultiTainter.getTaint(arr_f[1]));
		assertEquals(R.getData().getresults(), o.getdata());

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void PrimtiveArrays2DTaintedRecursiveFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		final int[][] arr_i = { { 1, 2, 3 }, { 1, 2, 3 } };
		final long[][] arr_j = { { 1, 2, 3 }, { 1, 2, 3 } };
		final boolean[][] arr_z = { { true, false, true }, { true, false, true } };
		final short[][] arr_s = { { 1, 2, 3 }, { 1, 2, 3 } };
		final double[][] arr_d = { { 1.1, 2.2, 3.3 }, { 1.1, 2.2, 3.3 } };
		final byte[][] arr_b = { { 0, 1, 0, 0 }, { 0, 1, 0, 0 } };
		final char[][] arr_c = { { '1', '2', '3' }, { '1', '2', '3' } };
		final float[][] arr_f = { { 1, 3, 5 }, { 1, 3, 5 } };

		R.taintObjects(arr_i, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_i[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_j, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_j[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_z, t);
		o.findTaintObjects(arr_i);
		assertNotNull(MultiTainter.getTaint(arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_z[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_s, t);
		o.findTaintObjects(arr_s);
		assertNotNull(MultiTainter.getTaint(arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_s[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_d, t);
		o.findTaintObjects(arr_d);
		assertNotNull(MultiTainter.getTaint(arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_d[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_b, t);
		o.findTaintObjects(arr_b);
		assertNotNull(MultiTainter.getTaint(arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_b[1][0]));

		R.taintObjects(arr_c, t);
		o.findTaintObjects(arr_c);
		assertNotNull(MultiTainter.getTaint(arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_c[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		R.taintObjects(arr_f, t);
		o.findTaintObjects(arr_f);
		assertNotNull(MultiTainter.getTaint(arr_f[0][0]));
		assertNotNull(MultiTainter.getTaint(arr_f[1][0]));
		assertEquals(R.getData().getresults(), o.getdata());

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTAintedRescursiveFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		final MyStruct_final m = new MyStruct_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));
		assertNull(MultiTainter.getTaint(m.i));
		assertNull(MultiTainter.getTaint(m.b));
		assertNull(MultiTainter.getTaint(m.c));
		assertNull(MultiTainter.getTaint(m.d));
		assertNull(MultiTainter.getTaint(m.j));
		assertNull(MultiTainter.getTaint(m.s));
		assertNull(MultiTainter.getTaint(m.z));
		assertNull(MultiTainter.getTaint(m.f));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArraysFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		final MyStruct_arr_final m = new MyStruct_arr_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArrays2DFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		final MyStruct_arr2D_final m = new MyStruct_arr2D_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferencesFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		final MyStruct_ref_final m = new MyStruct_ref_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m1));
		assertNull(MultiTainter.getTaint(m.m1.i));
		assertNull(MultiTainter.getTaint(m.m1.b));
		assertNull(MultiTainter.getTaint(m.m1.c));
		assertNull(MultiTainter.getTaint(m.m1.d));
		assertNull(MultiTainter.getTaint(m.m1.j));
		assertNull(MultiTainter.getTaint(m.m1.s));
		assertNull(MultiTainter.getTaint(m.m1.z));
		assertNull(MultiTainter.getTaint(m.m1.f));

		assertNotNull(MultiTainter.getTaint(m.m2));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferences2DFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		final MyStruct_ref2D_final m = new MyStruct_ref2D_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m1));
		assertNull(MultiTainter.getTaint(m.m1.i));
		assertNull(MultiTainter.getTaint(m.m1.b));
		assertNull(MultiTainter.getTaint(m.m1.c));
		assertNull(MultiTainter.getTaint(m.m1.d));
		assertNull(MultiTainter.getTaint(m.m1.j));
		assertNull(MultiTainter.getTaint(m.m1.s));
		assertNull(MultiTainter.getTaint(m.m1.z));
		assertNull(MultiTainter.getTaint(m.m1.f));

		assertNotNull(MultiTainter.getTaint(m.m2));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m2.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectArrayTaintedRecursiveFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref_final[] m = { new MyStruct_ref_final(), new MyStruct_ref_final() };

		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m[0]));
		assertNotNull(MultiTainter.getTaint(m[0].m1));
		assertNull(MultiTainter.getTaint(m[0].m1.i));
		assertNull(MultiTainter.getTaint(m[0].m1.b));
		assertNull(MultiTainter.getTaint(m[0].m1.c));
		assertNull(MultiTainter.getTaint(m[0].m1.d));
		assertNull(MultiTainter.getTaint(m[0].m1.j));
		assertNull(MultiTainter.getTaint(m[0].m1.s));
		assertNull(MultiTainter.getTaint(m[0].m1.z));
		assertNull(MultiTainter.getTaint(m[0].m1.f));

		assertNotNull(MultiTainter.getTaint(m[1]));
		assertNotNull(MultiTainter.getTaint(m[1].m2));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m[1].m2.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectArrayTaintedRecursive2DFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref2D_final[][] m = { { new MyStruct_ref2D_final() }, { new MyStruct_ref2D_final() } };

		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m[0][0]));
		assertNotNull(MultiTainter.getTaint(m[0][0].m1));
		assertNull(MultiTainter.getTaint(m[0][0].m1.i));
		assertNull(MultiTainter.getTaint(m[0][0].m1.b));
		assertNull(MultiTainter.getTaint(m[0][0].m1.c));
		assertNull(MultiTainter.getTaint(m[0][0].m1.d));
		assertNull(MultiTainter.getTaint(m[0][0].m1.j));
		assertNull(MultiTainter.getTaint(m[0][0].m1.s));
		assertNull(MultiTainter.getTaint(m[0][0].m1.z));
		assertNull(MultiTainter.getTaint(m[0][0].m1.f));

		assertNotNull(MultiTainter.getTaint(m[1][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m[1][0].m2.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferenceArraysFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref_array_final m = new MyStruct_ref_array_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m3[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[0].m1));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.i));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.b));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.c));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.d));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.j));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.s));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.z));
		assertNull(MultiTainter.getTaint(m.m3[0].m1.f));

		assertNotNull(MultiTainter.getTaint(m.m3[1]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1].m2.arr_f[0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferenceArrays2DFinal()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		MyStruct_ref_array2D_final m = new MyStruct_ref_array2D_final();
		R.taintObjects(m, t);
		o.findTaintObjects(m);

		assertNotNull(MultiTainter.getTaint(m));

		assertNotNull(MultiTainter.getTaint(m.m3[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[0][0].m1));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.i));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.b));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.c));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.d));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.j));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.s));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.z));
		assertNull(MultiTainter.getTaint(m.m3[0][0].m1.f));

		assertNotNull(MultiTainter.getTaint(m.m3[1][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.m3[1][0].m2.arr_f[0][0]));

		assertEquals(R.getData().getresults(), o.getdata());
	}
}
