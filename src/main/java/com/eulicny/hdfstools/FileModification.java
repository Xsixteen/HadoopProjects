package com.eulicny.hdfstools;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileModification implements IHdfsFileModification {
	ArrayList<String> newHeaders 		= new ArrayList();
	ArrayList<String> newStaticValues 	= new ArrayList();
	private String symbol;
	ArrayList<String> file	  			= new ArrayList();
	String headerFields;
	private boolean isHeader			= true;
	
	
	
	private int newHeaderIndex = 0 ;
	
	public void addField(String fieldName, String staticValue) {
		newHeaders.add(fieldName);
		newStaticValues.add(staticValue);
				
		newHeaderIndex++;
	}
	
	public void readHeader(String headerLine) {
		this.headerFields = headerLine;
	}
	
	public void readInLine(String line) {
		if(isHeader) {
			readHeader(line);
			isHeader	= false;
		} else
			file.add(line);
	}
	
	public String writeOut() {
		String outputText 		= "";
		String staticValues 	= "";
		String outputHeader 	= "";
		
		System.out.println("Number of Records=" + this.file.size());
		
		if(newHeaders.size() > 0 ) {
			for (String header : this.newHeaders)
				outputHeader = outputHeader + "," + header;
			
			for (String staticValue : this.newStaticValues)
				staticValues = staticValues + "," + staticValue;
			
			outputHeader = this.headerFields + outputHeader +  "\n";
	
		
		}
		
		for (String row : this.file) {
			if(row != null && !row.isEmpty())
				outputText = outputText + row + staticValues + "\n";  
		}
		
		return outputHeader + outputText;
	}

	@Override
	public void setCurrentFile(String filename) {
		String[] splitFile1 = filename.split("splitstock");
		String[] splitFile2 = splitFile1[1].split(Pattern.quote(".")); 
		
		this.symbol = splitFile2[1];

		System.out.println("Input Filename="+filename); 

		System.out.println ("Identified Symbol="+ this.symbol);

		
		System.out.println("Output File = " + this.symbol +".symfile");

		
	}

	@Override
	public String getOutputFileName() {
		return this.symbol + ".symfile";
	}

	@Override
	public void transform() {
		this.addField("Symbol", this.symbol);
	}

	@Override
	public void clear() {
		this.file	  			= new ArrayList();	
		this.newHeaders 		= new ArrayList();
		this.newStaticValues 	= new ArrayList();
		this.symbol				= "";
		this.headerFields		= "";
		this.isHeader			= true;
	}
}
