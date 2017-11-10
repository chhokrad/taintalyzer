package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

public class TaintAnalyzer {

	final static String APP_SOURCE = "application-source-zipped";
	final static String APP_ENTRY = "application-entry";
	final static String APP_ARG = "application-arguments";
	final static String APP_DEP = "application-dependencies";
	final static String APP_COMPILER_OPTIONS = "application-compiler-options";

	final static String CG_ROOT = "generated-code";
	final static String ARCHIVE_ZIP = "00-zip";
	final static String CG_00_SRC = "00-src";
	final static String CG_01_SRC_INST = "01-src-instrumented";
	final static String CG_02_INST_COMP = "02-inst-compiled";
	final static String CG_03_ARCH = "03-archived";
	final static String CG_04_SCRIPTS = "04-scripts";
	final static String CG_05_OUT_JSON = "05-json";

	private static Options getOptions() {

		Options options = new Options();

		Option SourceZipFile = new Option("s", APP_SOURCE, true, "Path to user instrumented source files");
		SourceZipFile.setRequired(true);
		options.addOption(SourceZipFile);

		Option MainClassName = new Option("e", APP_ENTRY, true, "Path to main class file");
		MainClassName.setRequired(true);
		options.addOption(MainClassName);

		Option MainClassArgs = new Option("a", APP_ARG, true, "Application arguments");
		MainClassArgs.setRequired(false);
		options.addOption(MainClassArgs);

		Option ReferenceJars = new Option("d", APP_DEP, true, "Path to external archives or class files");
		ReferenceJars.setRequired(false);
		options.addOption(ReferenceJars);

		Option JRE_Args = new Option("c", APP_COMPILER_OPTIONS, true, "Compiler options for the application");
		JRE_Args.setRequired(false);
		options.addOption(JRE_Args);

		return options;
	}

	public static void main(String[] args) throws Exception {

		String taintHome = System.getenv("TAINT_HOME");
		if (taintHome == null) {
			System.out.println("Please set your TAINT_HOME environment variable");
			System.exit(0);
		}

		Options options = getOptions();

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("taintalyzer", options);
			System.exit(1);
		}

		String sourceOption = cmd.getOptionValue(APP_SOURCE);
		String entryOption = cmd.getOptionValue(APP_ENTRY);
		String argOption = new String();
		if (cmd.getOptionValues(APP_ARG) != null)
			argOption = cmd.getOptionValue(APP_ARG);
		String dependencyOption = new String();
		if (cmd.getOptionValues(APP_ARG) != null)
			dependencyOption = cmd.getOptionValue(APP_ARG);
		String compilerOption = new String();
		if (cmd.getOptionValue(APP_COMPILER_OPTIONS) != null)
			compilerOption = cmd.getOptionValue(APP_COMPILER_OPTIONS);

		HashMap<String, File> dirPaths = createDirs(sourceOption);

		Path phosphorTargetPath = Paths.get(taintHome, "phosphor", "Phosphor", "target");
		Path instrumentedJREPath = phosphorTargetPath.resolve(Paths.get("jre-inst-ctrl-multi", "bin", "java"));
		Path phosphorJARPath = phosphorTargetPath.resolve("Phosphor-0.0.4-SNAPSHOT.jar");

		ZipFileProcessor.extract(dirPaths);
		SourceCodeInstrumentor.addInstrumentations(dirPaths);

	}

	private static HashMap<String, File> createDirs(String zipFilePath) throws Exception {
		HashMap<String, File> paths = new HashMap<>();
		String[] dirs = { CG_00_SRC, CG_01_SRC_INST, CG_02_INST_COMP, CG_03_ARCH, CG_04_SCRIPTS, CG_05_OUT_JSON };
		Path cgRoot = Paths.get(zipFilePath).toAbsolutePath().getParent().resolve(CG_ROOT);
		if (Files.exists(cgRoot))
			FileUtils.deleteDirectory(cgRoot.toFile());
		Arrays.stream(dirs).forEach(dir -> {
			try {
				File f = cgRoot.resolve(dir).toFile();
				FileUtils.forceMkdir(f);
				paths.put(dir, f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		paths.put(ARCHIVE_ZIP, new File(zipFilePath));
		return paths;
	}
}
