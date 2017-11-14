package edu.vanderbilt.taintalyzer.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

public class ZipFileProcessor {

	public static void extract(HashMap<String, File> dirPaths) throws Exception {
		ZipFile zipFile = new ZipFile(dirPaths.get(TaintAnalyzer.ARCHIVE_ZIP));
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
