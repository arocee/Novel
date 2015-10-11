package com.novel.model.admin;

import java.util.List;

@SuppressWarnings("serial")
public class Log implements java.io.Serializable {

	private int id;
	private String log;
	private List<String> fileNames;
	
	public Log (){
		
	}
	
	public Log(int id, String log, List<String> fileNames) {
		this.id = id;
		this.log = log;
		this.fileNames = fileNames;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLog() {
		return log;
	}
	
	public void setLog(String log) {
		this.log = log;
	}
	
	public List<String> getFileNames() {
		return fileNames;
	}
	
	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}
}
