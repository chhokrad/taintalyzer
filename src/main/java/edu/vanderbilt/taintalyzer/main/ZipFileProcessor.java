package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileProcessor {

	public static void extract(Map<String, File> dirPaths,
			Map<String, String> OptionsMap) throws Exception {

		ZipFile zipFile = new ZipFile(OptionsMap.get(TaintAnalyzer.APP_SOURCE));
		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (!entry.isDirectory()) {
				File temp = new File(dirPaths.get(TaintAnalyzer.CG_SRC)
						+ File.separator + entry.getName());
				if (!temp.getParentFile().exists())
					temp.getParentFile().mkdirs();
				Files.copy(zipFile.getInputStream(entry), temp.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			}
		}
		zipFile.close();
	}
}
