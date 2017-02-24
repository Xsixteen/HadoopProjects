package com.eulicny.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSCsvFileWriter {
	private String filename;
	private OutputStream os;
	private BufferedWriter bw;
	
	public HDFSCsvFileWriter(String file) {
		this.filename	= file;
	}
	
	public void open() throws Exception {
		   Configuration conf = new Configuration();
		    FileSystem fs = FileSystem.get(new URI(Constants.CONST_HDFS_LOCATION), conf);;
		    
			Path file = new Path(this.filename);
			System.out.println("Output file location="+ this.filename);
			
			this.os = fs.create(file, true);
			this.bw = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
	}
	
	public void close() {
		try {
			this.bw.close();
			this.os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLine(String line) throws IOException {
	 
		bw.write(line + "\n");
	
	}
}
