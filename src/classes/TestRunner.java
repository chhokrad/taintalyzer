package classes;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.RecursiveMultiTainterTest;

public class TestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(RecursiveMultiTainterTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.getTrace());
		}
	}
}
