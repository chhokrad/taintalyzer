package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileProcessor {

	public static void extract(HashMap<String, File> dirPaths) throws Exception {

		ZipFile zipFile = new ZipFile(dirPaths.get(TaintAnalyzer.ARCHIVE_ZIP));
		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String FileName = new String(dirPaths.get(TaintAnalyzer.CG_00_SRC) + File.separator + entry.getName());
			Files.copy(zipFile.getInputStream(entry), Paths.get(FileName), StandardCopyOption.REPLACE_EXISTING);
		}
		zipFile.close();

	}

}
