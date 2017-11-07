package edu.vanderbilt.taintalyzer.main;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Taint_Analyzer {

	public static void main(String[] args) throws Exception {

		Options options = new Options();

		Option Instrumented_JRE = new Option("I0", "Instrumented_JRE", true,
				"Path To Instrumented JRE Creted By Phosphor");
		Instrumented_JRE.setRequired(true);
		options.addOption(Instrumented_JRE);

		Option Phosphor_Jar = new Option("I1", "Phosphor_JAR", true, "Path To Phosphor Jar");
		Phosphor_Jar.setRequired(true);
		options.addOption(Phosphor_Jar);

		Option SourceZipFile = new Option("I2", "Zipped_Source_Files", true, "Path To User Instrumented Source Files");
		SourceZipFile.setRequired(true);
		options.addOption(SourceZipFile);

		Option MainClassName = new Option("I3", "Main_Class_Name", true, "Path To Main Class File");
		MainClassName.setRequired(true);
		options.addOption(MainClassName);

		Option MainClassArgs = new Option("I4", "Main_Class_Args", true, "Application Arguments");
		MainClassArgs.setRequired(false);
		// MainClassArgs.setArgs(Option.UNLIMITED_VALUES);
		options.addOption(MainClassArgs);

		Option ReferenceJars = new Option("I5", "ClassPath", true, "Path to external archives or class files");
		ReferenceJars.setRequired(false);
		// ReferenceJars.setArgs(Option.UNLIMITED_VALUES);
		options.addOption(ReferenceJars);

		Option HelperJar = new Option("I6", "Helper", true, "Helper Jar for Instrumented Application");
		HelperJar.setRequired(false);
		// HelperJar.setArgs(Option.UNLIMITED_VALUES);
		options.addOption(HelperJar);

		Option JRE_Args = new Option("I7", "Compiler_Options", true, "Compiler Options for the Application");
		JRE_Args.setRequired(false);
		options.addOption(JRE_Args);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("TainT Analayzer", options);

			System.exit(1);
			return;
		}

		String Instrumented_JRE_val = cmd.getOptionValue("Instrumented_JRE");
		String Phosphor_Jar_val = cmd.getOptionValue("Phosphor_JAR");
		String SourceZipFile_val = cmd.getOptionValue("Zipped_Source_Files");
		String MainCLassFle_val = cmd.getOptionValue("Main_Class_Name");

		String MainClassArgs_val = new String();
		if (cmd.getOptionValues("Main_Class_Args") != null)
			MainClassArgs_val = cmd.getOptionValue("Main_Class_Args");

		String ReferenceJars_val = new String();
		if (cmd.getOptionValue("References") != null)
			ReferenceJars_val = cmd.getOptionValue("References");

		String HelperJars_val = new String();
		if (cmd.getOptionValue("Helper") != null)
			HelperJars_val = cmd.getOptionValue("Helper");

		String JRE_Args_val = new String();
		if (cmd.getOptionValue("JAVA_Args") != null)
			JRE_Args_val = cmd.getOptionValue("JAVA_Args");

		String classPath_val = new String(
				ReferenceJars_val + File.pathSeparator + HelperJars_val + File.pathSeparator + Phosphor_Jar_val);
		try {
			new ZipFileProcessor(Instrumented_JRE_val, Phosphor_Jar_val, SourceZipFile_val, MainCLassFle_val,
					MainClassArgs_val, classPath_val, JRE_Args_val);
		} catch (Exception E) {
			E.printStackTrace();
		}

	}

}
