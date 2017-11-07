package edu.vanderbilt.taintalayzer.utility;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonCreator {

	
	private static Set<TaintEntry> set = new HashSet<TaintEntry>();
	private static int set_size = set.size();

	private JsonCreator() {
		// TODO Auto-generated constructor stub
	}

	public static void append_json(String Path, TaintEntry T) {

		set.add(T);
		if (set.size() > set_size) {
			set_size = set.size();
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(TaintEntry.class, new TaintEntrySerializer());
			gsonBuilder.setPrettyPrinting();
		    Gson gson = gsonBuilder.create();
		    String jsonString = gson.toJson(T);
		    File jsonFile = new File(Path + File.separator + "output.json");
		    if (!jsonFile.exists())
		    {
		    	try {
					RandomAccessFile file = new RandomAccessFile(jsonFile, "rw");
					file.seek(file.length());
					file.write(("[" + jsonString + "]").getBytes());
					file.close();
					
				} catch ( IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    else
		    {
		    	try {
					RandomAccessFile file = new RandomAccessFile(jsonFile, "rw");
					file.seek(file.length()-1);
					file.write((",\n" + jsonString + "]").getBytes());
					file.close();
					
				} catch ( IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
	}

	

}
