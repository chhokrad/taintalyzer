package edu.vanderbilt.taintalyzer.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.tools.ToolProvider;


public class JarFileCreator {
	private JarFileCreator() { }
	
  public static int BUFFER_SIZE = 10240;
  
  protected  ArrayList<File> compileSrcFiles(ArrayList<File> files_to_be_compiled)
  {
	  ToolProvider.getSystemJavaCompiler();
	  return new ArrayList<File>();
  }
  
  protected static void createJarArchive(String jarDir, ArrayList<File> files_to_be_jared) {
    try {
      byte buffer[] = new byte[BUFFER_SIZE];
      // Open archive file
      File archiveFile = new File(jarDir + File.separator + "instrumented_app.jar");
      FileOutputStream stream = new FileOutputStream(archiveFile);
      Manifest manifest = new Manifest();
      manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
      
      JarOutputStream out = new JarOutputStream(stream, manifest);

      for (File myfile : files_to_be_jared) {
        if (myfile == null || !myfile.exists() || myfile.isDirectory())
        	continue; 
        JarEntry jarAdd = new JarEntry(myfile.getName());
        jarAdd.setTime(myfile.lastModified());
        out.putNextEntry(jarAdd);

        // Write file to archive
        FileInputStream in = new FileInputStream(myfile);
        while (true) {
          int nRead = in.read(buffer, 0, buffer.length);
          if (nRead <= 0)
            break;
          out.write(buffer, 0, nRead);
        }
        in.close();
      }

      out.close();
      stream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

