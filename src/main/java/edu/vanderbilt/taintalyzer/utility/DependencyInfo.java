package edu.vanderbilt.taintalyzer.utility;

public class DependencyInfo {
	
	public DependencyInfo(String version, String artifact_id, String group_id, String path){
		this.version = version;
		this.artifact_id = artifact_id;
		this.group_id = group_id;
		this.path  = path;
	}
	
	private final String version;
	private final String artifact_id;
	private final String group_id;
	private final String path;
	
	
	public String getVersion() {
		return version;
	}
	public String getArtifact_id() {
		return artifact_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public String getPath(){
		return path;
	}

}
