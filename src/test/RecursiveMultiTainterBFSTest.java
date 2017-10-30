package test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

import tainter.RecursiveMultiTainterBFS;
import classes.Foo.MyStruct;
import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class RecursiveMultiTainterBFSTest {
	
	public Taint getTaintPrimitive(Field f, Object obj) throws Exception
	{
		switch (f.getType().getName()) {
		case "int": return MultiTainter.getTaint(f.getInt(obj));
		case "long": return MultiTainter.getTaint(f.getLong(obj));
		case "boolean": return MultiTainter.getTaint(f.getBoolean(obj));
		case "short": return MultiTainter.getTaint(f.getShort(obj));
		case "double": return MultiTainter.getTaint(f.getDouble(obj));
		case "byte": return MultiTainter.getTaint(f.getByte(obj));
		case "char": return MultiTainter.getTaint(f.getChar(obj));
		case "float": return MultiTainter.getTaint(f.getFloat(obj));
		default: return null;
		}
	}
	
	@Test
	public void simpleMaxTaint() throws Exception {
		MyStruct ms = new MyStruct();
		RecursiveMultiTainterBFS rtbfs = new RecursiveMultiTainterBFS(Integer.MAX_VALUE, 3);
		rtbfs.taintObjects(ms, new Taint<String>("simple"));
		int counter = 0;		
		for (Field f : ms.getClass().getDeclaredFields()) {
			if (Modifier.isFinal(f.getModifiers())) continue;
			if (getTaintPrimitive(f, ms) != null) counter++;
		}
		assertEquals(counter, 3);
	}
}
