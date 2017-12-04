package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import edu.vanderbilt.taintalyzer.utility.DependencyInfo;

public class PomGenerator {

	private PomGenerator() {
	}

	public static String commandGenerator(DependencyInfo dependency) {

		return  "mvn install:install-file -Dfile="
				+ dependency.getPath() + " -DgroupId="
				+ dependency.getGroup_id() + " -DartifactId="
				+ dependency.getArtifact_id() + " -Dversion="
				+ dependency.getVersion() + " -Dpackaging=jar";
	}

	public static void installDependency(DependencyInfo dependency)
			throws IOException, InterruptedException {
		Runtime r = Runtime.getRuntime();
		System.out.println(commandGenerator(dependency));
		Process p = r.exec(commandGenerator(dependency));
		p.waitFor();

	}

	public static Plugin jarAssemblyPlugin(Map<String, String> OptionMap) {
		Plugin plugin_jar = new Plugin();
		plugin_jar.setArtifactId("maven-assembly-plugin");
		plugin_jar.setGroupId("org.apache.maven.plugins");
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
		pluginexecution.setPhase("package");
		pluginexecution.addGoal("single");
		pluginexecution.setConfiguration(configuration);
		plugin_jar.addExecution(pluginexecution);
		return plugin_jar;
	}

	public static Plugin compilerPlugin() {
		Plugin plugin_comp = new Plugin();
		Xpp3Dom source = new Xpp3Dom("source");
		source.setValue("1.8");
		Xpp3Dom target = new Xpp3Dom("target");
		target.setValue("1.8");
		Xpp3Dom configuration_comp = new Xpp3Dom("configuration");
		configuration_comp.addChild(source);
		configuration_comp.addChild(target);
		plugin_comp.setConfiguration(configuration_comp);
		plugin_comp.setVersion("3.7.0");
		plugin_comp.setArtifactId("maven-compiler-plugin");
		return plugin_comp;
	}

	public static Plugin copyDependecnyPlugin() {
		Plugin plugin_copy = new Plugin();
		plugin_copy.setGroupId("org.apache.maven.plugins");
		plugin_copy.setArtifactId("maven-dependency-plugin");
		PluginExecution pluginExecution = new PluginExecution();
		pluginExecution.setId("copy-dependencies");
		pluginExecution.setPhase("prepare-package");
		pluginExecution.addGoal("copy-dependencies");
		Xpp3Dom outputDirectory = new Xpp3Dom("outputDirectory");
		outputDirectory.setValue("${project.build.directory}/libs");
		Xpp3Dom configuration = new Xpp3Dom("configuration");
		configuration.addChild(outputDirectory);
		pluginExecution.setConfiguration(configuration);
		plugin_copy.addExecution(pluginExecution);
		return plugin_copy;

	}

	public static Plugin jarShadePlugin(Map<String, String> OptionMap) {
		Plugin plugin_shadeJar = new Plugin();
		plugin_shadeJar.setGroupId("org.apache.maven.plugins");
		plugin_shadeJar.setArtifactId("maven-shade-plugin");
		Xpp3Dom mainClass = new Xpp3Dom("mainClass");
		mainClass.setValue(OptionMap.get(TaintAnalyzer.APP_ENTRY));
		Xpp3Dom transformer = new Xpp3Dom("transformer");
		transformer
				.setAttribute("implementation",
						"org.apache.maven.plugins.shade.resource.ManifestResourceTransformer");
		transformer.addChild(mainClass);
		Xpp3Dom transformers = new Xpp3Dom("transformers");
		transformers.addChild(transformer);
		Xpp3Dom configuration = new Xpp3Dom("configuration");
		configuration.addChild(transformers);

		PluginExecution execution = new PluginExecution();
		execution.setPhase("package");
		execution.addGoal("shade");

		plugin_shadeJar.setConfiguration(configuration);
		plugin_shadeJar.addExecution(execution);

		return plugin_shadeJar;
	}

	public static Plugin jarPlugin(Map<String, String> OptionMap) {
		Plugin plugin_jar = new Plugin();
		Xpp3Dom mainClass = new Xpp3Dom("mainClass");
		mainClass.setValue(OptionMap.get(TaintAnalyzer.APP_ENTRY));
		Xpp3Dom classpathPrefix = new Xpp3Dom("classpathPrefix");
		classpathPrefix.setValue("libs/");
		Xpp3Dom addClasspath = new Xpp3Dom("addClasspath");
		addClasspath.setValue("true");
		Xpp3Dom manifest = new Xpp3Dom("manifest");
		manifest.addChild(addClasspath);
		manifest.addChild(classpathPrefix);
		manifest.addChild(mainClass);
		Xpp3Dom archive = new Xpp3Dom("archive");
		archive.addChild(manifest);

		Xpp3Dom configuration = new Xpp3Dom("configuration");
		configuration.addChild(archive);
		plugin_jar.setConfiguration(configuration);
		plugin_jar.setArtifactId("maven-jar-plugin");
		plugin_jar.setGroupId("org.apache.maven.plugins");
		return plugin_jar;

	}

	public static void createPOM(Map<String, File> dirPaths,
			Map<String, String> OptionMap) throws IOException,
			InterruptedException {
		Model model = new Model();
		model.setGroupId("com.stac");
		model.setArtifactId(OptionMap.get(TaintAnalyzer.APP_NAME));
		model.setVersion("0.0.1");
		model.setModelVersion("4.0.0");
		model.addProperty("project.build.sourceEncoding", "UTF-8");

		int counter = 1;
		for (File file : getDependencies(OptionMap.get(TaintAnalyzer.APP_DEP))) {
			System.out.println("dependency#" + counter + " : " + file);
			DependencyInfo dependency_info = new DependencyInfo("1",
					"dependency_" + String.valueOf(counter),
					OptionMap.get(TaintAnalyzer.APP_NAME),
					file.toString());
			installDependency(dependency_info);
			Dependency dependency = new Dependency();
			dependency.setGroupId(OptionMap.get(TaintAnalyzer.APP_NAME));
			dependency.setArtifactId("dependency_" + String.valueOf(counter));
			dependency.setVersion("1");
			model.addDependency(dependency);
			counter++;
		}

		Build build = new Build();
		build.addPlugin(compilerPlugin());
		build.addPlugin(jarAssemblyPlugin(OptionMap));
		model.setBuild(build);
		new MavenXpp3Writer().write(
				new FileWriter(dirPaths.get(TaintAnalyzer.CG_SRC_INST)
						+ File.separator + "pom.xml"), model);
	}

	private static ArrayList<File> getDependencies(String ClassPath) {
		ArrayList<File> files = new ArrayList<>();
		if (ClassPath.length() > 0) {
			String[] dependencies = ClassPath.split(File.pathSeparator);
			Arrays.stream(dependencies).forEach(
					dependency -> files.add(new File(dependency)));
		}
		return files;
	}

}
