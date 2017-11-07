package edu.vanderbilt.taintalyzer.main;

import java.io.InputStream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class SourceCodeInstrumentor {

	public static String AddInstrumentations(InputStream source_file, String Path) {
		CompilationUnit cu = JavaParser.parse(source_file);
		new MyVisitorAdapter<String>(cu, Path);
		cu.addImport("edu.columbia.cs.psl.phosphor.runtime.Taint;", false, false);
		cu.addImport("edu.columbia.cs.psl.phosphor.runtime.MultiTainter;", false, false);
		cu.addImport("helper.JsonCreator;", false, false);
		cu.addImport("helper.TaintEntry;", false, false);
		return cu.toString();
	}
}
