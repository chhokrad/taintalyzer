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
	
	public static class MyStruct_final {
		final public int i = 10;
		final public long j = 10;
		final public boolean z = false;
		final public short s = 1;
		final public double d = 12.34;
		final public byte b = 0;
		final public char c = '1';
		final public float f = 34;
	}
	
	public static class MyStruct_arr{
		public int[] arr_i = {1,2,3};
		public long[] arr_j = {1,2,3};
		public boolean[] arr_z = {true, false, true};
		public short[] arr_s = {1,2,3};
		public double[] arr_d = {1.1, 2.2, 3.3};
		public byte[] arr_b = {0,1,0,0};
		public char[] arr_c = {'1','2','3'};
		public float[] arr_f = {1,2,3};
	}
	
	public static class MyStruct_arr_final{
		final public int[] arr_i = {1,2,3};
		final public long[] arr_j = {1,2,3};
		final public boolean[] arr_z = {true, false, true};
		final public short[] arr_s = {1,2,3};
		final public double[] arr_d = {1.1, 2.2, 3.3};
		final public byte[] arr_b = {0,1,0,0};
		final public char[] arr_c = {'1','2','3'};
		final public float[] arr_f = {1,2,3};
	}
	
	
	public static class MyStruct_ref{
		public MyStruct m1 = new MyStruct();
		public MyStruct_arr m2 = new MyStruct_arr();
	}
	
	public static class MyStruct_ref_final{
		final public MyStruct_final m1 = new MyStruct_final();
		final public MyStruct_arr_final m2 = new MyStruct_arr_final();
	}
	
	public static class MyStruct_ref_array{
		public MyStruct_ref[] m3 = {new MyStruct_ref(), new MyStruct_ref()};
	}
	
	public static class MyStruct_ref_array_final{
		final public MyStruct_ref_final[] m3 = {new MyStruct_ref_final(), new MyStruct_ref_final()};
	}
	
	
	public static class MyStruct_arr2D{
		public int[][] arr_i = {{1},{3}};
		public long[][] arr_j = {{1},{3}};
		public boolean[][] arr_z = {{true}, {true}};
		public short[][] arr_s = {{1},{3}};
		public double[][] arr_d = {{1.1},{1.3}};
		public byte[][] arr_b = {{0},{0}};
		public char[][] arr_c = {{1},{3}};
		public float[][] arr_f = {{1},{3}};
	}
	
	public static class MyStruct_arr2D_final{
		final public int[][] arr_i = {{1},{3}};
		final public long[][] arr_j = {{1},{3}};
		final public boolean[][] arr_z = {{true}, {true}};
		final public short[][] arr_s = {{1},{3}};
		final public double[][] arr_d = {{1.1},{1.3}};
		final public byte[][] arr_b = {{0},{0}};
		final public char[][] arr_c = {{1},{3}};
		final public float[][] arr_f = {{1},{3}};
	}
	
	public static class MyStruct_ref2D{
		public MyStruct m1 = new MyStruct();
		public MyStruct_arr2D m2 = new MyStruct_arr2D();
	}
	
	public static class MyStruct_ref2D_final{
		final public MyStruct_final m1 = new MyStruct_final();
		final public MyStruct_arr2D_final m2 = new MyStruct_arr2D_final();
	}
	
	public static class MyStruct_ref_array2D{
		public MyStruct_ref2D[][] m3 = {{new MyStruct_ref2D()}, {new MyStruct_ref2D()}};
	}
	
	public static class MyStruct_ref_array2D_final{
		final public MyStruct_ref2D_final[][] m3 = {{new MyStruct_ref2D_final()}, {new MyStruct_ref2D_final()}};
	}
	
}
