package classes;

public class Foo {
	
	public static class MyStruct {
		public int i = 10;
		public long j = 10;
		public boolean z = false;
		public short s = 1;
		public double d = 12.34;
		public byte b = 0;
		public char c = '1';
		public float f = 34;
	}
	
	public static class myStruct_final {
		final public int i = 10;
		final public long j = 10;
		final public boolean z = false;
		final public short s = 1;
		final public double d = 12.34;
		final public byte b = 0;
		final public char c = '1';
		final public float f = 34;
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
	
	public static class myStruct_arr_final{
		final public int[] arr_i = {1,2,3};
		final public long[] arr_j = {1,2,3};
		final public boolean[] arr_z = {true, false, true};
		final public short[] arr_s = {1,2,3};
		final public double[] arr_d = {1.1, 2.2, 3.3};
		final public byte[] arr_b = {0,1,0,0};
		final public char[] arr_c = {'1','2','3'};
		final public float[] arr_f = {1,2,3};
	}
	
	
	public static class myStruct_ref{
		public MyStruct m1 = new MyStruct();
		public myStruct_arr m2 = new myStruct_arr();
	}
	
	public static class myStruct_ref_final{
		final public myStruct_final m1 = new myStruct_final();
		final public myStruct_arr_final m2 = new myStruct_arr_final();
	}
	
	public static class myStruct_ref_array{
		public myStruct_ref[] m3 = {new myStruct_ref(), new myStruct_ref()};
	}
	
	public static class myStruct_ref_array_final{
		final public myStruct_ref_final[] m3 = {new myStruct_ref_final(), new myStruct_ref_final()};
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
	
	public static class myStruct_arr2D_final{
		final public int[][] arr_i = {{1},{3}};
		final public long[][] arr_j = {{1},{3}};
		final public boolean[][] arr_z = {{true}, {true}};
		final public short[][] arr_s = {{1},{3}};
		final public double[][] arr_d = {{1.1},{1.3}};
		final public byte[][] arr_b = {{0},{0}};
		final public char[][] arr_c = {{1},{3}};
		final public float[][] arr_f = {{1},{3}};
	}
	
	public static class myStruct_ref2D{
		public MyStruct m1 = new MyStruct();
		public myStruct_arr2D m2 = new myStruct_arr2D();
	}
	
	public static class myStruct_ref2D_final{
		final public myStruct_final m1 = new myStruct_final();
		final public myStruct_arr2D_final m2 = new myStruct_arr2D_final();
	}
	
	public static class myStruct_ref_array2D{
		public myStruct_ref2D[][] m3 = {{new myStruct_ref2D()}, {new myStruct_ref2D()}};
	}
	
	public static class myStruct_ref_array2D_final{
		final public myStruct_ref2D_final[][] m3 = {{new myStruct_ref2D_final()}, {new myStruct_ref2D_final()}};
	}
	
}
