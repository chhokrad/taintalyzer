package test;

import static org.junit.Assert.*;

import org.junit.Test;

import classes.AnotherStruct;
import classes.foo.myStruct;
import classes.foo.myStruct_arr;
import classes.foo.myStruct_arr2D;
import classes.foo.myStruct_arr2D_final;
import classes.foo.myStruct_arr_final;
import classes.foo.myStruct_final;
import classes.foo.myStruct_ref;
import classes.foo.myStruct_ref2D;
import classes.foo.myStruct_ref2D_final;
import classes.foo.myStruct_ref_array;
import classes.foo.myStruct_ref_array2D;
import classes.foo.myStruct_ref_array2D_final;
import classes.foo.myStruct_ref_array_final;
import classes.foo.myStruct_ref_final;
import tainter.RecursiveMultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class RecursiveMultiTainterTest {

	@Test
	public void PrimitiveArraysTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {

		int[] arr_i = { 1, 2, 3 };
		long[] arr_j = { 1, 2, 3 };
		boolean[] arr_z = { true, false, true };
		short[] arr_s = { 1, 2, 3 };
		double[] arr_d = { 1.1, 2.2, 3.3 };
		byte[] arr_b = { 0, 1, 0, 0 };
		char[] arr_c = { '1', '2', '3' };
		float[] arr_f = { 1, 3, 5 };

		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(arr_i, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_i[0]));
		assertNotNull(MultiTainter.getTaint(arr_i[1]));

		R.taintObjects(arr_j, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_j[0]));
		assertNotNull(MultiTainter.getTaint(arr_j[1]));

		R.taintObjects(arr_z, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_z[0]));
		assertNotNull(MultiTainter.getTaint(arr_z[1]));

		R.taintObjects(arr_s, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_s[0]));
		assertNotNull(MultiTainter.getTaint(arr_s[1]));

		R.taintObjects(arr_d, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_d[0]));
		assertNotNull(MultiTainter.getTaint(arr_d[1]));

		R.taintObjects(arr_b, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_b[0]));
		assertNotNull(MultiTainter.getTaint(arr_b[1]));

		R.taintObjects(arr_c, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_c[0]));
		assertNotNull(MultiTainter.getTaint(arr_c[1]));

		R.taintObjects(arr_f, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_f[0]));
		assertNotNull(MultiTainter.getTaint(arr_f[1]));

	}

	@Test
	public void PrimtiveArrays2DTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		int[][] arr_i = { { 1, 2, 3 }, { 1, 2, 3 } };
		long[][] arr_j = { { 1, 2, 3 }, { 1, 2, 3 } };
		boolean[][] arr_z = { { true, false, true }, { true, false, true } };
		short[][] arr_s = { { 1, 2, 3 }, { 1, 2, 3 } };
		double[][] arr_d = { { 1.1, 2.2, 3.3 }, { 1.1, 2.2, 3.3 } };
		byte[][] arr_b = { { 0, 1, 0, 0 }, { 0, 1, 0, 0 } };
		char[][] arr_c = { { '1', '2', '3' }, { '1', '2', '3' } };
		float[][] arr_f = { { 1, 3, 5 }, { 1, 3, 5 } };

		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(arr_i, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_i[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_i[1][1]));

		R.taintObjects(arr_j, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_j[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_j[1][1]));

		R.taintObjects(arr_z, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_z[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_z[1][1]));

		R.taintObjects(arr_s, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_s[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_s[1][1]));

		R.taintObjects(arr_d, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_d[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_d[1][1]));

		R.taintObjects(arr_b, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_b[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_b[1][1]));

		R.taintObjects(arr_c, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_c[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_c[1][1]));

		R.taintObjects(arr_f, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(arr_f[0][1]));
		assertNotNull(MultiTainter.getTaint(arr_f[1][1]));
	}

	@Test
	public void PrimtiveArraysNullTaintedRecursive() {
		int[] arr_i = null;
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		try {
			R.taintObjects(arr_i, new Taint<String>("tainted_recursive"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(e instanceof NullPointerException);
		}

	}

	@Test
	public void CustomObjectTAintedRescursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct m = new myStruct();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.i));
		assertNotNull(MultiTainter.getTaint(m.b));
		assertNotNull(MultiTainter.getTaint(m.c));
		assertNotNull(MultiTainter.getTaint(m.d));
		assertNotNull(MultiTainter.getTaint(m.j));
		assertNotNull(MultiTainter.getTaint(m.s));
		assertNotNull(MultiTainter.getTaint(m.z));
		assertNotNull(MultiTainter.getTaint(m.f));

	}

	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArrays()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_arr m = new myStruct_arr();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.arr_i[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_b[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_c[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_d[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_j[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_s[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_z[0]));
		assertNotNull(MultiTainter.getTaint(m.arr_f[0]));
	}

	@Test
	public void CustomObjectTaintedRescursiveWithPrimitiveArrays2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_arr2D m = new myStruct_arr2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.arr_i[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_b[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_c[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_d[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_j[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_s[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_z[0][0]));
		assertNotNull(MultiTainter.getTaint(m.arr_f[0][0]));
	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferences()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_ref m = new myStruct_ref();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferences2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_ref2D m = new myStruct_ref2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

	}

	@Test
	public void CustomObjectArrayTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_ref[] m = { new myStruct_ref(), new myStruct_ref() };

		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

	}

	@Test
	public void CustomObjectArrayTaintedRecursive2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_ref2D[][] m = { { new myStruct_ref2D() },
				{ new myStruct_ref2D() } };

		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferenceArrays()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_ref_array m = new myStruct_ref_array();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

	}

	@Test
	public void CustomObjectTaintedRecursiveWithReferenceArrays2D()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {
		myStruct_ref_array2D m = new myStruct_ref_array2D();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

	}
	
	@Test 
	public void PrivateFieldTest() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception{
		AnotherStruct m = new AnotherStruct();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));
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
		
	}

	
	public class RecursiveMultiTainterTestFinal {

		@Test
		public void PrimitiveArraysTaintedRecursive()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {

			final int[] arr_i = { 1, 2, 3 };
			final long[] arr_j = { 1, 2, 3 };
			final boolean[] arr_z = { true, false, true };
			final short[] arr_s = { 1, 2, 3 };
			final double[] arr_d = { 1.1, 2.2, 3.3 };
			final byte[] arr_b = { 0, 1, 0, 0 };
			final char[] arr_c = { '1', '2', '3' };
			final float[] arr_f = { 1, 3, 5 };

			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(arr_i, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_i[0]));
			assertNotNull(MultiTainter.getTaint(arr_i[1]));

			R.taintObjects(arr_j, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_j[0]));
			assertNotNull(MultiTainter.getTaint(arr_j[1]));

			R.taintObjects(arr_z, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_z[0]));
			assertNotNull(MultiTainter.getTaint(arr_z[1]));

			R.taintObjects(arr_s, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_s[0]));
			assertNotNull(MultiTainter.getTaint(arr_s[1]));

			R.taintObjects(arr_d, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_d[0]));
			assertNotNull(MultiTainter.getTaint(arr_d[1]));

			R.taintObjects(arr_b, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_b[0]));
			assertNotNull(MultiTainter.getTaint(arr_b[1]));

			R.taintObjects(arr_c, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_c[0]));
			assertNotNull(MultiTainter.getTaint(arr_c[1]));

			R.taintObjects(arr_f, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_f[0]));
			assertNotNull(MultiTainter.getTaint(arr_f[1]));

		}

	}
		
		@Test
		public void PrimtiveArrays2DTaintedRecursiveFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			final int[][] arr_i = { { 1, 2, 3 }, { 1, 2, 3 } };
			final long[][] arr_j = { { 1, 2, 3 }, { 1, 2, 3 } };
			final boolean[][] arr_z = { { true, false, true }, { true, false, true } };
			final short[][] arr_s = { { 1, 2, 3 }, { 1, 2, 3 } };
			final double[][] arr_d = { { 1.1, 2.2, 3.3 }, { 1.1, 2.2, 3.3 } };
			final byte[][] arr_b = { { 0, 1, 0, 0 }, { 0, 1, 0, 0 } };
			final char[][] arr_c = { { '1', '2', '3' }, { '1', '2', '3' } };
			final float[][] arr_f = { { 1, 3, 5 }, { 1, 3, 5 } };

			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(arr_i, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_i[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_i[1][1]));

			R.taintObjects(arr_j, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_j[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_j[1][1]));

			R.taintObjects(arr_z, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_z[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_z[1][1]));

			R.taintObjects(arr_s, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_s[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_s[1][1]));

			R.taintObjects(arr_d, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_d[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_d[1][1]));

			R.taintObjects(arr_b, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_b[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_b[1][1]));

			R.taintObjects(arr_c, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_c[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_c[1][1]));

			R.taintObjects(arr_f, new Taint<String>("tainted_recursive"));
			assertNotNull(MultiTainter.getTaint(arr_f[0][1]));
			assertNotNull(MultiTainter.getTaint(arr_f[1][1]));
		}
		
		


		@Test
		public void CustomObjectTAintedRescursiveFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			final myStruct_final m = new myStruct_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

			assertNotNull(MultiTainter.getTaint(m));
			assertNull(MultiTainter.getTaint(m.i));
			assertNull(MultiTainter.getTaint(m.b));
			assertNull(MultiTainter.getTaint(m.c));
			assertNull(MultiTainter.getTaint(m.d));
			assertNull(MultiTainter.getTaint(m.j));
			assertNull(MultiTainter.getTaint(m.s));
			assertNull(MultiTainter.getTaint(m.z));
			assertNull(MultiTainter.getTaint(m.f));

		}

		
		@Test
		public void CustomObjectTaintedRescursiveWithPrimitiveArraysFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			final myStruct_arr_final m = new myStruct_arr_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

			assertNotNull(MultiTainter.getTaint(m));
			assertNotNull(MultiTainter.getTaint(m.arr_i[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_b[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_c[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_d[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_j[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_s[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_z[0]));
			assertNotNull(MultiTainter.getTaint(m.arr_f[0]));
		}
		
		
		@Test
		public void CustomObjectTaintedRescursiveWithPrimitiveArrays2DFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			final myStruct_arr2D_final m = new myStruct_arr2D_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

			assertNotNull(MultiTainter.getTaint(m));
			assertNotNull(MultiTainter.getTaint(m.arr_i[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_b[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_c[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_d[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_j[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_s[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_z[0][0]));
			assertNotNull(MultiTainter.getTaint(m.arr_f[0][0]));
		}
		
		
		@Test
		public void CustomObjectTaintedRecursiveWithReferencesFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			final myStruct_ref_final m = new myStruct_ref_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

		}
		
		
		@Test
		public void CustomObjectTaintedRecursiveWithReferences2DFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			final myStruct_ref2D_final m = new myStruct_ref2D_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

		}
		
		
		@Test
		public void CustomObjectArrayTaintedRecursiveFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			myStruct_ref_final[] m = { new myStruct_ref_final(), new myStruct_ref_final() };

			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

		}
		
		
		@Test
		public void CustomObjectArrayTaintedRecursive2DFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			myStruct_ref2D_final[][] m = { { new myStruct_ref2D_final() },
					{ new myStruct_ref2D_final() } };

			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

		}
		
		
		@Test
		public void CustomObjectTaintedRecursiveWithReferenceArraysFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			myStruct_ref_array_final m = new myStruct_ref_array_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

		}
		
		
		@Test
		public void CustomObjectTaintedRecursiveWithReferenceArrays2DFinal()
				throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
				Exception {
			myStruct_ref_array2D_final m = new myStruct_ref_array2D_final();
			RecursiveMultiTainter R = new RecursiveMultiTainter();
			R.taintObjects(m, new Taint<String>("tainted_recursive"));

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

		}
	
	
}
