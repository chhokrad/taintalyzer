package test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

import classes.Foo.MyStruct;
import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import tainter.RecursiveMultiTainterBFS;

public class RecursiveMultiTainterBFSTest {

	@Test
	public void simpleMaxTaint() throws Exception {
		MyStruct ms = new MyStruct();
		RecursiveMultiTainterBFS rtbfs = new RecursiveMultiTainterBFS(Integer.MAX_VALUE, 3);
		rtbfs.taintObjects(ms, new Taint<String>("simple"));
		int counter = 0;
//		Object obj = (Object) ms;
		MyStruct obj = ms;
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
//			System.out.println(MultiTainter.getTaint(f.get(obj)));
			if (Modifier.isFinal(f.getModifiers())) continue;
			if (MultiTainter.getTaint(f.get(obj)) != null) counter++;
		}
		System.out.println(counter);
		assertEquals(counter, 3);
	}
	

}
