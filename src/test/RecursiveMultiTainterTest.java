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
	public void PrimitiveArraysTainted() {
		int[] a = { 1, 2, 3, 4, 5 };

		a = MultiTainter.taintedIntArray(a, new Taint<String>("tainted_phosphor"));

		assertNotNull(MultiTainter.getTaint(a[0]));
		assertNotNull(MultiTainter.getTaint(a[1]));
		assertNotNull(MultiTainter.getTaint(a[2]));
		assertNotNull(MultiTainter.getTaint(a[3]));
		assertNotNull(MultiTainter.getTaint(a[4]));
	}

	@Test
	public void PrimitiveArraysTaintedRecursive()
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException,
			Exception {

		int[] a = { 1, 2, 3, 4, 5 };
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(a, new Taint<String>("tainted_recursive"));

		assertNotNull(MultiTainter.getTaint(a[0]));
		assertNotNull(MultiTainter.getTaint(a[1]));
		assertNotNull(MultiTainter.getTaint(a[2]));
		assertNotNull(MultiTainter.getTaint(a[3]));
		assertNotNull(MultiTainter.getTaint(a[4]));
	}
	@Test
	public void primitiveTainted() {
		int i = 10;
		long j = 10;
		boolean z = false;
		short s = 1;
		double d = 12.34;
		byte b = 0;
		char c = '1';
		
		
		i = MultiTainter.taintedInt(i, "tainted_phosphor");
		j = MultiTainter.taintedLong(j, "tainted_phosphor");
		z = MultiTainter.taintedBoolean(z, "tainted_phosphor");
		s = MultiTainter.taintedShort(s, "tainted_phosphor");
		d = MultiTainter.taintedDouble(d, "tainted_phosphor");
		b = MultiTainter.taintedByte(b, "tainted_phosphor");
		c = MultiTainter.taintedChar(c, "tainted_phosphor");
		
		assertEquals(MultiTainter.getTaint(i).getLabel().toString(), "tainted_phosphor");
		assertEquals(MultiTainter.getTaint(j).getLabel().toString(), "tainted_phosphor");
		assertEquals(MultiTainter.getTaint(z).getLabel().toString(), "tainted_phosphor");
		assertEquals(MultiTainter.getTaint(s).getLabel().toString(), "tainted_phosphor");
		assertEquals(MultiTainter.getTaint(d).getLabel().toString(), "tainted_phosphor");
		assertEquals(MultiTainter.getTaint(b).getLabel().toString(), "tainted_phosphor");
		assertEquals(MultiTainter.getTaint(c).getLabel().toString(), "tainted_phosphor");
		
	}
	
	@Test
	public void primitiveTaintedRecursive() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception
	{
		int i = 10;
		long j = 10;
		boolean z = false;
		short s = 1;
		double d = 12.34;
		byte b = 0;
		char c = '1';
		
		new RecursiveMultiTainter().taintObjects(i, new Taint<String>("tainted_recursive"));
		new RecursiveMultiTainter().taintObjects(j, new Taint<String>("tainted_recursive"));
		new RecursiveMultiTainter().taintObjects(z, new Taint<String>("tainted_recursive"));
		new RecursiveMultiTainter().taintObjects(s, new Taint<String>("tainted_recursive"));
		new RecursiveMultiTainter().taintObjects(d, new Taint<String>("tainted_recursive"));
		new RecursiveMultiTainter().taintObjects(b, new Taint<String>("tainted_recursive"));
		new RecursiveMultiTainter().taintObjects(c, new Taint<String>("tainted_recursive"));
		
		
		assertNull(MultiTainter.getTaint(i));
		assertNull(MultiTainter.getTaint(j));
		assertNull(MultiTainter.getTaint(z));
		assertNull(MultiTainter.getTaint(s));
		assertNull(MultiTainter.getTaint(d));
		assertNull(MultiTainter.getTaint(b));
		assertNull(MultiTainter.getTaint(c));
	}
	
	@Test
	public void primitiveHetrogeneousCollectionTainted()
	{
		myStruct m = new myStruct();
		MultiTainter.taintedObject(m, new Taint<String>("tainted_phosphor"));
		assertNotNull(MultiTainter.getTaint(m));
		assertEquals(MultiTainter.getTaint(m).getLabel().toString(), "tainted_phosphor");
		assertNull(MultiTainter.getTaint(m.i));
		assertNull(MultiTainter.getTaint(m.j));
		assertNull(MultiTainter.getTaint(m.z));
		assertNull(MultiTainter.getTaint(m.s));
		assertNull(MultiTainter.getTaint(m.d));
		assertNull(MultiTainter.getTaint(m.b));
		assertNull(MultiTainter.getTaint(m.c));
	}
	
	@Test
	public void primitiveHetrogeneousCollectionTaintedRecursive() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception
	{
		myStruct m = new myStruct();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));
		assertNotNull(MultiTainter.getTaint(m));
		assertNotNull(MultiTainter.getTaint(m.i));
		assertNotNull(MultiTainter.getTaint(m.j));
		assertNotNull(MultiTainter.getTaint(m.z));
		assertNotNull(MultiTainter.getTaint(m.s));
		assertNotNull(MultiTainter.getTaint(m.d));
		assertNotNull(MultiTainter.getTaint(m.b));
		assertNotNull(MultiTainter.getTaint(m.c));
		
		assertEquals(MultiTainter.getTaint(m).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.i).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.j).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.z).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.s).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.d).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.b).getLabel().toString(), "tainted_recursive");
		assertEquals(MultiTainter.getTaint(m.c).getLabel().toString(), "tainted_recursive");
	}
	
	@Test
	public void primitiveArrayHetrogeneousCollectionTainted() 
	{
		myStruct_arr m = new myStruct_arr();
		MultiTainter.taintedObject(m, new Taint<String>("tainted_phosphor"));
		assertNotNull(MultiTainter.getTaint(m));
		assertEquals(MultiTainter.getTaint(m).getLabel().toString(), "tainted_phosphor");
		assertNull(MultiTainter.getTaint(m.arr_i));
		assertNull(MultiTainter.getTaint(m.arr_j));
		assertNull(MultiTainter.getTaint(m.arr_z));
		assertNull(MultiTainter.getTaint(m.arr_s));
		assertNull(MultiTainter.getTaint(m.arr_d));
		assertNull(MultiTainter.getTaint(m.arr_b));
		assertNull(MultiTainter.getTaint(m.arr_c));
		
	}
	
	@Test
	public void primitiveArrayHetrogeneousCollectionTaintedRecursive() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception 
	{
		
		System.out.println("running primitiveArrayHetrogeneousCollectionTaintedRecursive()");
		myStruct_arr m = new myStruct_arr();
		RecursiveMultiTainter R = new RecursiveMultiTainter();
		R.taintObjects(m, new Taint<String>("tainted_recursive"));
		
		assertNotNull(MultiTainter.getTaint(m));
		assertEquals(MultiTainter.getTaint(m).getLabel().toString(), "tainted_recursive");
		assertNull(MultiTainter.getTaint(m.arr_i));
		assertNull(MultiTainter.getTaint(m.arr_j));
		assertNull(MultiTainter.getTaint(m.arr_z));
		assertNull(MultiTainter.getTaint(m.arr_s));
		assertNull(MultiTainter.getTaint(m.arr_d));
		assertNull(MultiTainter.getTaint(m.arr_b));
		assertNull(MultiTainter.getTaint(m.arr_c));
		
		
	}
	
}
