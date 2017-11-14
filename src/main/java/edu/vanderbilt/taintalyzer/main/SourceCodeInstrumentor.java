package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;
import edu.vanderbilt.taintalyzer.utility.JsonCreator;
import edu.vanderbilt.taintalyzer.utility.TaintEntry;

public class SourceCodeInstrumentor {

	public static class addInstrumentations extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws FileNotFoundException {
			if (attr.isRegularFile()) {
				String Instrumented_Code = addInstrumentation(new FileInputStream(file.toFile()));
				PrintWriter pw = new PrintWriter(file.toFile());
				pw.write(Instrumented_Code);
				pw.close();
			}
			return FileVisitResult.CONTINUE;
		}
	}

	private static String addInstrumentation(InputStream source_file) {
		CompilationUnit cu = JavaParser.parse(source_file);
		new MyVisitorAdapter<String>(cu);
		cu.addImport(Taint.class);
		cu.addImport(MultiTainter.class);
		cu.addImport(JsonCreator.class);
		cu.addImport(TaintEntry.class);
		return cu.toString();
	}
}
