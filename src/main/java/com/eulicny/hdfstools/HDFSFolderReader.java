package com.eulicny.hdfstools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSFolderReader {
	
	final static String CONST_HDFS_LOCATION = "hdfs://ericdev01.aegisdoctrine.local:8020";
	
	private String outputFilepath;
	private String inputFilepath;
	
	public HDFSFolderReader(String inputFilepath, String outputFilepath) {
		this.outputFilepath	= outputFilepath;
		this.inputFilepath  = inputFilepath;
	}
	
	
	public void processDir(IHdfsFileModification hdfsFileModification) {
	    Configuration conf = new Configuration();
	    FileSystem fs;
		try {
			fs = FileSystem.get(new URI(CONST_HDFS_LOCATION), conf);
	
		    FileStatus[] fileStatus = fs.listStatus(new Path(CONST_HDFS_LOCATION + this.inputFilepath));
		    for(FileStatus status : fileStatus){
		    	if(status.isFile()) {
			    	//process each file
			        System.out.println("Processing File: " + status.getPath().toString());
			        hdfsFileModification.setCurrentFile(status.getPath().toString());
			        BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(status.getPath())));
	                String line;
	                //Custom Transform
	                hdfsFileModification.transform();
	                line=br.readLine();
                    hdfsFileModification.readInLine(line);
	                while (line != null){
	                        line=br.readLine();
	                        hdfsFileModification.readInLine(line);
	                }
	                
	                //Now writeout to the file
	        		String output = hdfsFileModification.writeOut();
	        		Path file = new Path(this.outputFilepath + hdfsFileModification.getOutputFileName());
	        		System.out.println("Output file location="+ this.outputFilepath + hdfsFileModification.getOutputFileName());
	        		if ( fs.exists( file )) { fs.delete( file, true ); } 
	        		
	        		OutputStream os = fs.create(file);
	        		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
	        		bw.write(output);
	        		bw.close();
	        		os.close();
	        		hdfsFileModification.clear();
		    	}
		    }
    		fs.close();

		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
