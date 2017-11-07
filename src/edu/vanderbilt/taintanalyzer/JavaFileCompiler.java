package edu.vanderbilt.taintanalyzer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JavaFileCompiler {

	private JavaFileCompiler() {
	}
	
	private static void processArgs(ArrayList<String> result, String toProcess)
	{
	    if (toProcess == null || toProcess.length() == 0) {
	    	return ;
	    }

	    final int normal = 0;
	    final int inQuote = 1;
	    final int inDoubleQuote = 2;
	    int state = normal;
	    final StringTokenizer tok = new StringTokenizer(toProcess, "\"\' ", true);
	    final StringBuilder current = new StringBuilder();
	    boolean lastTokenHasBeenQuoted = false;

	    while (tok.hasMoreTokens()) {
	        String nextTok = tok.nextToken();
	        switch (state) {
	        case inQuote:
	            if ("\'".equals(nextTok)) {
	                lastTokenHasBeenQuoted = true;
	                state = normal;
	            } else {
	                current.append(nextTok);
	            }
	            break;
	        case inDoubleQuote:
	            if ("\"".equals(nextTok)) {
	                lastTokenHasBeenQuoted = true;
	                state = normal;
	            } else {
	                current.append(nextTok);
	            }
	            break;
	        default:
	            if ("\'".equals(nextTok)) {
	                state = inQuote;
	            } else if ("\"".equals(nextTok)) {
	                state = inDoubleQuote;
	            } else if (" ".equals(nextTok)) {
	                if (lastTokenHasBeenQuoted || current.length() != 0) {
	                    result.add(current.toString());
	                    current.setLength(0);
	                }
	            } else {
	                current.append(nextTok);
	            }
	            lastTokenHasBeenQuoted = false;
	            break;
	        }
	    }
	    if (lastTokenHasBeenQuoted || current.length() != 0) {
	        result.add(current.toString());
	    }
	    if (state == inQuote || state == inDoubleQuote) {
	        throw new RuntimeException("unbalanced quotes in " + toProcess);
	    }
	}
	
	public static void compile(ArrayList<File> files, String Classpath, String OutputDir, ArrayList<File> files_, String JreArgs_val)
			throws Exception {
		
		/*
		files.add(new File("." + File.separator + "src" + File.separator + "TaintEntry.java"));
		files.add(new File("." + File.separator + "src" + File.separator + "TaintSerializer.java"));
		files.add(new File("." + File.separator + "src" + File.separator + "JsonCreator.java"));
		*/
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			throw new Exception("JDK required (running inside of JRE)");
		}
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, null, null);

		try {
			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(files);
			
			
			ArrayList<String> options = new ArrayList<>();
			options.add("-Xlint:unchecked");
			options.add("-d");
			options.add(OutputDir);
			if (Classpath.length() != 0) {
				options.add("-classpath");
				options.add(Classpath);
			}
			JavaFileCompiler.processArgs(options, JreArgs_val);
			options.forEach(option -> System.out.println(option));
			JavaCompiler.CompilationTask task = compiler.getTask(null,
					fileManager, diagnostics, options, null, compilationUnits);
			if (!task.call()) {
				Locale myLocale = Locale.getDefault();
				StringBuilder msg = new StringBuilder();
				msg.append("Cannot compile to Java bytecode:");
				for (Diagnostic<? extends JavaFileObject> err : diagnostics.getDiagnostics()) {
					msg.append('\n');
					msg.append(err.getKind());
					msg.append(": ");
					if (err.getSource() != null) {
						msg.append(err.getSource().getName());
					}
					msg.append(':');
					msg.append(err.getLineNumber());
					msg.append(": ");
					msg.append(err.getMessage(myLocale));
				}
				throw new Exception(msg.toString());
			}
			else
			{
				for(JavaFileObject jfo: fileManager.list(StandardLocation.CLASS_OUTPUT, "", Collections.singleton(JavaFileObject.Kind.CLASS), true)) {
					files_.add(new File(jfo.getName()));
				}
			}

		} finally {
			fileManager.close();
		}
	}
	
	

}
