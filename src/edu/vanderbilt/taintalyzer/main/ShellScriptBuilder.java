package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ShellScriptBuilder {

	public static void Generate(String classPath, String instrumentedJrePath, String phosphorPath, String mainClassPath,
			String mainClassArgs, String shellscriptPath, String JreArgs_val) throws FileNotFoundException {
		File temp = new File(shellscriptPath + File.separator + "instrumented_app.sh");
		try (PrintWriter P1 = new PrintWriter(temp)) {
			P1.write("#!/bin/bash\n");
			P1.write(new String(
					instrumentedJrePath + " -Xbootclasspath/a:" + phosphorPath + " -javaagent:" + phosphorPath + " "
							+ JreArgs_val + " -cp " + classPath + " " + mainClassPath + " " + mainClassArgs));
		}
	}

}
