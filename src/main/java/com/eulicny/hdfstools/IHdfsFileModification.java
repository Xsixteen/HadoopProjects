package com.eulicny.hdfstools;

public interface IHdfsFileModification {
	
	public void readInLine(String line);
	
	public String writeOut();
	
	public void setCurrentFile(String filename);
	
	public String getOutputFileName();
	
	public void transform();
	
	public void clear();
}
