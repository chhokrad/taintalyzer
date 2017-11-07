package edu.vanderbilt.taintanalyzer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;



public class SourceCodeInstrumentor {
	private SourceCodeInstrumentor() {}
	public static String  AddInstrumentations(String source_file, String Path){
		
		 CompilationUnit cu = JavaParser.parse(source_file);
		 MyVisitorAdapter<String> T1 = new MyVisitorAdapter<String>(cu, Path);
		 cu.addImport("edu.columbia.cs.psl.phosphor.runtime.Taint;", false, false);
		 cu.addImport("edu.columbia.cs.psl.phosphor.runtime.MultiTainter;", false, false);
		 cu.addImport("helper.JsonCreator;", false, false);
		 cu.addImport("helper.TaintEntry;", false, false);
		return cu.toString();
	}	
}
