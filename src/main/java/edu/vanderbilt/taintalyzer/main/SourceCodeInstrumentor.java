package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class SourceCodeInstrumentor {

	public static void addInstrumentations(HashMap<String, File> dirPaths) throws Exception {
		for (File child : dirPaths.get(TaintAnalyzer.CG_00_SRC).listFiles()) {
			File f = new File(dirPaths.get(TaintAnalyzer.CG_01_SRC_INST) + File.separator + child.getName());
			PrintWriter pw = new PrintWriter(f);
			pw.write(addInstrumentation(new FileInputStream(child),
					dirPaths.get(TaintAnalyzer.CG_05_OUT_JSON).toString()));
			pw.close();
		}
	}

	private static String addInstrumentation(InputStream source_file, String Path) {
		CompilationUnit cu = JavaParser.parse(source_file);
		new MyVisitorAdapter<String>(cu, Path);
		cu.addImport("edu.columbia.cs.psl.phosphor.runtime.Taint;", false, false);
		cu.addImport("edu.columbia.cs.psl.phosphor.runtime.MultiTainter;", false, false);
		cu.addImport("helper.JsonCreator;", false, false);
		cu.addImport("helper.TaintEntry;", false, false);
		return cu.toString();
	}
}
