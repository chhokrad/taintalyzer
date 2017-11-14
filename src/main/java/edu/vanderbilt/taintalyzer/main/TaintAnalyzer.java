package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import edu.vanderbilt.taintalyzer.main.SourceCodeInstrumentor.addInstrumentations;

public class TaintAnalyzer {

	final static String APP_SOURCE = "application-source-zipped";
	final static String APP_ENTRY = "application-entry";
	final static String APP_ARG = "application-arguments";
	final static String APP_DEP = "application-dependencies";
	final static String APP_COMPILER_OPTIONS = "application-compiler-options";
	final static String APP_NAME = "application-name";

	final static String CG_ROOT = "generated-code";
	final static String ARCHIVE_ZIP = "zip";
	final static String CG_SRC = "src";
	final static String CG_SRC_INST = "01-src-instrumented";
	final static String CG_SRC_INST_SMJ = "src-main-java";
	final static String CG_SRC_INST_SMR = "src-main-resources";
	final static String CG_SRC_INST_STJ = "src-test-java";
	final static String CG_SCRIPTS = "scripts";
	final static String CG_JSON = "json";

	private static Options getOptions() {

		Options options = new Options();

		Option SourceZipFile = new Option("s", APP_SOURCE, true,
				"Path to user instrumented source files");
		SourceZipFile.setRequired(true);
		options.addOption(SourceZipFile);

		Option MainClassName = new Option("e", APP_ENTRY, true,
				"Path to main class file");
		MainClassName.setRequired(true);
		options.addOption(MainClassName);

		Option ProjectName = new Option("n", APP_NAME, true, "Name of Project");
		MainClassName.setRequired(true);
		options.addOption(MainClassName);

		Option MainClassArgs = new Option("a", APP_ARG, true,
				"Application arguments");
		MainClassArgs.setRequired(false);
		options.addOption(MainClassArgs);

		Option ReferenceJars = new Option("d", APP_DEP, true,
				"Path to external archives or class files");
		ReferenceJars.setRequired(false);
		options.addOption(ReferenceJars);

		Option JRE_Args = new Option("c", APP_COMPILER_OPTIONS, true,
				"Compiler options for the application");
		JRE_Args.setRequired(false);
		options.addOption(JRE_Args);

		return options;
	}

	public static void main(String[] args) throws Exception {

		String taintHome = System.getenv("TAINT_HOME");
		if (taintHome == null) {
			System.out
					.println("Please set your TAINT_HOME environment variable");
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
		String nameOption = cmd.getOptionValue(APP_NAME);

		String argOption = new String();
		if (cmd.getOptionValues(APP_ARG) != null)
			argOption = cmd.getOptionValue(APP_ARG);
		String dependencyOption = new String();
		if (cmd.getOptionValues(APP_DEP) != null)
			dependencyOption = cmd.getOptionValue(APP_DEP);
		String compilerOption = new String();
		if (cmd.getOptionValue(APP_COMPILER_OPTIONS) != null)
			compilerOption = cmd.getOptionValue(APP_COMPILER_OPTIONS);

		HashMap<String, File> dirPaths = createDirs(sourceOption);

		Path phosphorTargetPath = Paths.get(taintHome, "phosphor", "Phosphor",
				"target");
		Path instrumentedJREPath = phosphorTargetPath.resolve(Paths.get(
				"jre-inst-ctrl-multi", "bin", "java"));
		Path phosphorJARPath = phosphorTargetPath
				.resolve("Phosphor-0.0.4-SNAPSHOT.jar");

		ZipFileProcessor.extract(dirPaths);


		 copyFiles(dirPaths);
		addInstrumentations my_instrumentor = new addInstrumentations();
		Files.walkFileTree(dirPaths.get(TaintAnalyzer.CG_SRC_INST_SMJ).toPath(),
				my_instrumentor);
		PomGenerator.createPOM(dirPaths, dependencyOption);

	}

	private static void copyFiles(HashMap<String, File> dirPaths)
			throws IOException {
		File SrcDirectory = dirPaths.get(TaintAnalyzer.CG_SRC);
		if (SrcDirectory.isDirectory()) {
			for (File file : SrcDirectory.listFiles())
				FileUtils.copyDirectory(file, dirPaths.get(TaintAnalyzer.CG_SRC_INST_SMJ));
		}
	}

	private static HashMap<String, File> createDirs(String zipFilePath)
			throws Exception {
		HashMap<String, File> paths = new HashMap<>();
		String[] dirs = { CG_SRC, CG_SRC_INST, CG_SCRIPTS,
				CG_JSON };
		Path cgRoot = Paths.get(zipFilePath).toAbsolutePath().getParent()
				.resolve(CG_ROOT);
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
		
		Path SMJ = Paths.get("src", "main", "java");
		Path STJ = Paths.get("src", "test", "java");
		Path SMR = Paths.get("src", "main", "resources");
		File SMJ_dir = Paths.get(paths.get(CG_SRC_INST).toString())
				.resolve(SMJ).toFile();
		File STJ_dir = Paths.get(paths.get(CG_SRC_INST).toString())
				.resolve(STJ).toFile();
		File SMR_dir = Paths.get(paths.get(CG_SRC_INST).toString())
				.resolve(SMR).toFile();
		SMJ_dir.mkdirs();
		STJ_dir.mkdirs();
		SMR_dir.mkdirs();
		paths.put(CG_SRC_INST_SMJ, SMJ_dir);
		paths.put(CG_SRC_INST_STJ, STJ_dir);
		paths.put(CG_SRC_INST_SMR, SMR_dir);
		
		return paths;
	}
}
