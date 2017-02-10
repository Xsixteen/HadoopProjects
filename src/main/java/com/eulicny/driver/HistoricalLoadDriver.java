package com.eulicny.driver;

import com.eulicny.hdfstools.FileModification;
import com.eulicny.hdfstools.HDFSFolderReader;

public class HistoricalLoadDriver {
	
	public static void main(String[] args) {
		System.out.println("Historical Load Driver");
		FileModification fileModification = new FileModification();
		HDFSFolderReader hdfsReader	= new HDFSFolderReader("/user/worker/warehouse/stockprocessor/historic/input/landingzone", "/user/worker/warehouse/stockprocessor/historic/output/");
		hdfsReader.processDir(fileModification);
	}

}
