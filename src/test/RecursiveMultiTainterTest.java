package test;

import static org.junit.Assert.*;

import org.junit.Test;

import classes.foo.myStruct;
import classes.foo.myStruct_arr;
import tainter.RecursiveMultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class RecursiveMultiTainterTest {
	
	@Test
	public void PrimitiveArraysTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {

		int[] arr_i = {1,2,3};
		long[] arr_j = {1,2,3};
		boolean[] arr_z = {true, false, true};
		short[] arr_s = {1,2,3};
		double[] arr_d = {1.1, 2.2, 3.3};
		byte[] arr_b = {0,1,0,0};
		char[] arr_c = {'1','2','3'};
		float[] arr_f = {1,3,5};
		
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
	public void PrimtiveArrays2DTaintedRecursive() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception
	{
		int[][] arr_i = {{1,2,3},{1,2,3}} ;
		long[][] arr_j = {{1,2,3},{1,2,3}};
		boolean[][] arr_z = {{true, false, true},{true, false, true}};
		short[][] arr_s = {{1,2,3}, {1,2,3}};
		double[][] arr_d = {{1.1, 2.2, 3.3},{1.1, 2.2, 3.3}};
		byte[][] arr_b = {{0,1,0,0}, {0,1,0,0}};
		char[][] arr_c = {{'1','2','3'}, {'1','2','3'}};
		float[][] arr_f = {{1,3,5}, {1,3,5}};
		
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
	public void PrimtiveArraysNullTaintedRecursive(){
		int[] arr_i = null;
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		try {
			R.taintObjects(arr_i, new Taint<String>("tainted_recursive"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
}
