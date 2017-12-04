package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	final static String APP_DEP = "application-dependencies";
	final static String APP_NAME = "application-name";

	final static String CG_ROOT = "generated-code";
	final static String CG_SRC = "src-uninstrumented";
	final static String CG_SRC_INST = "src-instrumented";
	final static String CG_SRC_INST_SMJ = Paths.get("src", "main", "java")
			.toString();
	final static String CG_SRC_INST_SMR = Paths.get("src", "main", "resources")
			.toString();
	final static String CG_SRC_INST_STJ = Paths.get("src", "test", "java")
			.toString();
	final static String CG_JSON = "json";

	public static void main(String[] args) throws Exception {

		Map<String, String> OptionsMap = parseOptionstoMap(args);
		Map<String, File> dirPathsMap = createDirs(OptionsMap.get(APP_SOURCE));

		ZipFileProcessor.extract(dirPathsMap, OptionsMap);
		copyFiles(dirPathsMap);

		addInstrumentations instrumentor = new addInstrumentations();
		instrumentor.setOutputPath(dirPathsMap.get(CG_JSON).toString());

		Files.walkFileTree(dirPathsMap.get(CG_SRC_INST_SMJ).toPath(),
				instrumentor);
		PomGenerator.createPOM(dirPathsMap, OptionsMap);
	}

	private static Map<String, String> parseOptionstoMap(String[] args) {

		Path taintPath = null;

		if (System.getenv("TAINT_HOME").isEmpty())
			System.exit(-1);
		else
			taintPath = Paths.get(System.getenv("TAINT_HOME"), "target",
					"taintalyzer-0.0.1-SNAPSHOT-jar-with-dependencies.jar");

		Options options = new Options();

		Option SourceZipFile = new Option("s", APP_SOURCE, true,
				"Path to user instrumented source files");
		SourceZipFile.setRequired(true);
		options.addOption(SourceZipFile);

		Option MainClassName = new Option("e", APP_ENTRY, true,
				"Path to main class file");
		MainClassName.setRequired(true);
		options.addOption(MainClassName);

		Option ProjectName = new Option("n", APP_NAME, true,
				"Name of Application");
		ProjectName.setRequired(true);
		options.addOption(ProjectName);

		Option ReferenceJars = new Option("d", APP_DEP, true,
				"Path to external archives or class files");
		ReferenceJars.setRequired(false);
		options.addOption(ReferenceJars);

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
		String dependencyOption = new String(taintPath.toString()
				+ File.pathSeparatorChar);
		if (cmd.getOptionValues(APP_DEP) != null)
			dependencyOption = dependencyOption.concat(cmd
					.getOptionValue(APP_DEP));
		else
			dependencyOption = dependencyOption.substring(0,
					dependencyOption.length() - 1);
		HashMap<String, String> OptionMap = new HashMap<String, String>();
		OptionMap.put(APP_SOURCE, sourceOption);
		OptionMap.put(APP_ENTRY, entryOption);
		OptionMap.put(APP_NAME, nameOption);
		OptionMap.put(APP_DEP, dependencyOption);

		return Collections.unmodifiableMap(OptionMap);
	}

	private static Map<String, File> createDirs(String zipFilePath)
			throws Exception {
		HashMap<String, File> paths = new HashMap<>();
		String[] dirs = { CG_SRC, CG_SRC_INST, CG_JSON };
		String[] sub_dirs = { CG_SRC_INST_SMJ, CG_SRC_INST_STJ, CG_SRC_INST_SMR };
		Path cgRoot = Paths.get(zipFilePath).toAbsolutePath().getParent()
				.resolve(CG_ROOT);
		Path appRoot = cgRoot.resolve(CG_SRC_INST);

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

		Arrays.stream(sub_dirs).forEach(sub_dir -> {
			try {
				File f = appRoot.resolve(sub_dir).toFile();
				FileUtils.forceMkdir(f);
				paths.put(sub_dir, f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return Collections.unmodifiableMap(paths);

	}

	private static void copyFiles(Map<String, File> dirPaths)
			throws IOException {
		File SrcDirectory = dirPaths.get(TaintAnalyzer.CG_SRC);
		if (SrcDirectory.isDirectory()) {
			for (File file : SrcDirectory.listFiles())
				FileUtils.copyDirectory(file,
						dirPaths.get(TaintAnalyzer.CG_SRC_INST_SMJ));
		}
	}

}
