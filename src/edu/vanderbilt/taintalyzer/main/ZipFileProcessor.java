package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileProcessor {

	public ZipFileProcessor(String Instrumented_JRE_val, String Phosphor_Jar_val, String zipFilePath,
			String MainCLassFle_val, String MainClassArgs_val, String classPath, String JRE_Args_val)
			throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		ArrayList<File> files_to_be_compiled = new ArrayList<File>();
		ArrayList<File> files_to_be_archived = new ArrayList<File>();

		String ParentDir = new File(zipFilePath).getParent();
		String srcDir = new String(ParentDir + File.separator + "code_gen" + File.separator + "src");
		String src_instDirc = new String(ParentDir + File.separator + "code_gen" + File.separator + "src_inst");
		String scriptDir = new String(ParentDir + File.separator + "code_gen" + File.separator + "script_files");
		String classDir = new String(ParentDir + File.separator + "code_gen" + File.separator + "classes");
		String jarDir = new String(ParentDir + File.separator + "code_gen" + File.separator + "jar");
		String jsonDir = new String(ParentDir + File.separator + "code_gen" + File.separator + "json");
		this.createDirs(
				new ArrayList<String>(Arrays.asList(srcDir, src_instDirc, scriptDir, classDir, jarDir, jsonDir)));

		// classPath = this.validateClasspath(new String(ParentDir + File.pathSeparator
		// + classPath));
		classPath = this.validateClasspath(new String(classPath));
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String FileName = new String(srcDir + File.separator + entry.getName());
			String FileName_int = new String(src_instDirc + File.separator + entry.getName());
			Files.copy(zipFile.getInputStream(entry), Paths.get(FileName), StandardCopyOption.REPLACE_EXISTING);
			String inst_code = new String(
					SourceCodeInstrumentor.AddInstrumentations(zipFile.getInputStream(entry), jsonDir));
			try (PrintWriter P1 = new PrintWriter(FileName_int)) {
				P1.write(inst_code);
				files_to_be_compiled.add(new File(FileName_int));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		zipFile.close();

		try {
			JavaFileCompiler.compile(files_to_be_compiled, classPath, classDir, files_to_be_archived, JRE_Args_val);
			JarFileCreator.createJarArchive(jarDir, files_to_be_archived);
			String classPath_ = this.validateClasspath(new String(jarDir + File.separator + "instrumented_app.jar"
					+ File.pathSeparator + classPath + File.pathSeparator + classDir));
			ShellScriptBuilder.Generate(classPath_, Instrumented_JRE_val, Phosphor_Jar_val, MainCLassFle_val,
					MainClassArgs_val, scriptDir, JRE_Args_val);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createDir(String Path) {
		File temp = new File(Path);
		if (!temp.exists())
			temp.mkdirs();
		else {
			temp.delete();
			temp.mkdirs();
		}
	}

	private void createDirs(ArrayList<String> Paths) {
		Paths.forEach(path -> this.createDir(path));
	}

	private String validateClasspath(String Classpath) {
		String regex = "::";
		String replaceBy = ":";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(Classpath);
		String Classpath_ = new String(m.replaceAll(replaceBy));
		return Classpath_;

	}
}
