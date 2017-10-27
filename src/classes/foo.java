package classes;

public class foo {
	
	public static class myStruct{
		public int i = 10;
		public long j = 10;
		public boolean z = false;
		public short s = 1;
		public double d = 12.34;
		public byte b = 0;
		public char c = '1';
		public float f = 34;
	}
	
	
	public static class myStruct_arr{
		public int[] arr_i = {1,2,3};
		public long[] arr_j = {1,2,3};
		public boolean[] arr_z = {true, false, true};
		public short[] arr_s = {1,2,3};
		public double[] arr_d = {1.1, 2.2, 3.3};
		public byte[] arr_b = {0,1,0,0};
		public char[] arr_c = {'1','2','3'};
		public float[] arr_f = {1,2,3};
	}
	
	public static class myStruct_ref{
		public myStruct m1 = new myStruct();
		public myStruct_arr m2 = new myStruct_arr();
	}
	
	public static class myStruct_ref_array{
		public myStruct_ref[] m3 = {new myStruct_ref(), new myStruct_ref()};
	}
	
	
	public static class myStruct_arr2D{
		public int[][] arr_i = {{1},{3}};
		public long[][] arr_j = {{1},{3}};
		public boolean[][] arr_z = {{true}, {true}};
		public short[][] arr_s = {{1},{3}};
		public double[][] arr_d = {{1.1},{1.3}};
		public byte[][] arr_b = {{0},{0}};
		public char[][] arr_c = {{1},{3}};
		public float[][] arr_f = {{1},{3}};
	}
	
	public static class myStruct_ref2D{
		public myStruct m1 = new myStruct();
		public myStruct_arr2D m2 = new myStruct_arr2D();
	}
	
	public static class myStruct_ref_array2D{
		public myStruct_ref2D[] m3 = {new myStruct_ref2D(), new myStruct_ref2D()};
	}
}
