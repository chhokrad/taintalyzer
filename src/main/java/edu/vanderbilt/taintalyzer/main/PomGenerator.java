package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class PomGenerator {

	private PomGenerator() {
	}

	public static void createPOM(Map<String, File> dirPaths,
			Map<String, String> OptionMap) throws IOException {
		Model model = new Model();
		model.setGroupId("com.stac");
		model.setArtifactId(OptionMap.get(TaintAnalyzer.APP_NAME));
		model.setVersion("0.0.1");
		model.setModelVersion("4.0.0");
		model.addProperty("project.build.sourceEncoding", "UTF-8");
		
		for (File file : getDependencies(OptionMap.get(TaintAnalyzer.APP_DEP))) {
			FileUtils.copyFileToDirectory(file,
					dirPaths.get(TaintAnalyzer.CG_SRC_INST_SMR));
			Dependency temp = new Dependency();
			temp.setGroupId(file.getName());
			temp.setArtifactId(file.getName());
			temp.setVersion("1");
			temp.setSystemPath(Paths.get("${pom.basedir}", "src", "main",
					"resources", file.getName()).toString());
			temp.setScope("system");
			model.addDependency(temp);
		}

		Dependency taint = new Dependency();
		taint.setGroupId("edu.vanderbilt.taintalyzer");
		taint.setArtifactId("taintalyzer");
		taint.setVersion("0.0.1-SNAPSHOT");
		model.addDependency(taint);

		Build build = new Build();
		Plugin plugin = new Plugin();

		Xpp3Dom mainClass = new Xpp3Dom("mainClass");
		mainClass.setValue(OptionMap.get(TaintAnalyzer.APP_ENTRY));

		Xpp3Dom manifest = new Xpp3Dom("manifest");
		manifest.addChild(mainClass);

		Xpp3Dom archive = new Xpp3Dom("archive");
		archive.addChild(manifest);

		Xpp3Dom descriptorRef = new Xpp3Dom("descriptorRef");
		descriptorRef.setValue("jar-with-dependencies");

		Xpp3Dom descriptorRefs = new Xpp3Dom("descriptorRefs");
		descriptorRefs.addChild(descriptorRef);

		Xpp3Dom configuration = new Xpp3Dom("configuration");
		configuration.addChild(archive);
		configuration.addChild(descriptorRefs);

		PluginExecution pluginexecution = new PluginExecution();
		pluginexecution.addGoal("single");
		pluginexecution.setPhase("package");
		pluginexecution.addGoal("single");
		pluginexecution.setConfiguration(configuration);

		plugin.addExecution(pluginexecution);
		plugin.setArtifactId("maven-assembly-plugin");
		plugin.setGroupId("org.apache.maven.plugins");

		build.addPlugin(plugin);
		model.setBuild(build);

		new MavenXpp3Writer().write(
				new FileWriter(dirPaths.get(TaintAnalyzer.CG_SRC_INST)
						+ File.separator + "pom.xml"), model);
	}

	private static ArrayList<File> getDependencies(String ClassPath) {
		ArrayList<File> files = new ArrayList<>();
		System.out.println(ClassPath);
		if (ClassPath.length() > 0) {
			String[] dependencies = ClassPath.split(File.pathSeparator);
			Arrays.stream(dependencies).forEach(
					dependency -> files.add(new File(dependency)));
		}
		return files;
	}

}
