package com.eulicny.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.eulicny.common.HiveDB;
import com.eulicny.contiguous.YearMonthDayClose;
import com.eulicny.contiguous.YearMonthGainLoss;

public class MonthDataFinder {
	
	public static void main(String[] args) {	
	     int numberOfMaxPostiveContigous = 0;
         int numberOfMaxNegativeContigous = 0;
         int positiveMaxCounter = 0;
         int negativeMaxCounter = 0;
         Integer maxContigPosYear 	= 0;
         Integer maxContigNegYear	= 0;
		String ticker 		= args[0];
		String begindate 	= args[1];
		
		HashMap<Integer, YearMonthDayClose>stockYearlyHashMap				= new HashMap<Integer, YearMonthDayClose>();
		YearMonthDayClose workingRow;	
		

		
		System.out.println("MonthDataFinder Driver Starting.  Ticker =" + ticker + " Begin Date=" + begindate);
		
		HiveDB hiveDB = new HiveDB();
	       try {
	         Statement statement = hiveDB.getConnection();
	         //print each row
	         ResultSet resultSet = statement.executeQuery("select symbol, syear, smonth, sday, close FROM (select year(symbol_date) as syear, month(symbol_date) as smonth, day(symbol_date) as sday, symbol, close from stockanalytics.HistoricalStockData WHERE symbol = '"+ticker+"' and symbol_date > '"+begindate+"') v1 order by syear desc");
	        
	         System.out.println("Processing Symbol="+ticker);
	         
	         while (resultSet.next()) {
	        	  Integer month 	= new Integer(Integer.parseInt(resultSet.getString(3)));
	        	  Integer year  	= new Integer(Integer.parseInt(resultSet.getString(2)));
	        	  Integer day		= new Integer(Integer.parseInt(resultSet.getString(4)));

	        	  Double close 	= new Double(Double.parseDouble(resultSet.getString(5)));
	        	  
	        	  workingRow = (stockYearlyHashMap.get(year) == null ? new YearMonthDayClose(year) : stockYearlyHashMap.get(year));

	        	  workingRow.setMonthDayClose(month, day, close);
	        	  stockYearlyHashMap.put(year, workingRow);
	     
	       	}
	         
	        System.out.println("Years in HashMap "+ stockYearlyHashMap.size());
	        
	        //Process the set of values
	       Object[] keys = stockYearlyHashMap.keySet().toArray();
	       Arrays.sort(keys);
	       
	       for(Object yearKey : keys) {
	    	   
	    	    int year = (Integer) yearKey;
	    	    YearMonthDayClose monthValueProcess  = stockYearlyHashMap.get(yearKey);
	        
	       
	            //Process the entire year.
	            for(int i=1; i < 13 ; i++) {
		            Double startClose = 0.0;
		            Double endClose	  = 0.0;
		            
		            //Ascertain End date
		            for(int j = 31; j > 0; j--) {
		            	if((Double) monthValueProcess.getMonthDayClose(i, j) != null) {
		            		endClose = (Double) monthValueProcess.getMonthDayClose(i, j);
		            		break;
		            	}

		            }
			        //Ascertain start date
		            for(int k = 0; k < 31; k++) {
		            	if((Double) monthValueProcess.getMonthDayClose(i, k) != null) {
		            		startClose = (Double) monthValueProcess.getMonthDayClose(i, k);
		            		break;
		            	}
	
			            
		           }
				   System.out.println("Symbol= " + ticker + " Year= " + year + " Month = " + i +" Month Start="+startClose + " Month End=" + endClose + " Difference=" +(endClose - startClose));
				   statement.executeQuery("INSERT INTO TABLE stockanalytics.HistoricalStockDataStartEnd_stage VALUES ('"+year+"','"+i+"','"+startClose+"','"+endClose+"', '"+(endClose - startClose)+", '"+ticker+"')"); 


	            }  
	            

	            	        
	        System.out.println("Total Max Contiguous Runs="+ numberOfMaxPostiveContigous + " Around Year= "+ maxContigPosYear + " Total Max Negative Contiguous Runs=" +numberOfMaxNegativeContigous + " Around Year=" + maxContigNegYear);
	        
	        statement.close(); //close statement
	       }
	        
	      } catch (ClassNotFoundException e) {
	         System.out.println("ClassNotFound Exception: " + e.getMessage());
	      } catch (SQLException e) {
	         System.out.println("SQL Exception: " + e.getMessage());
	      }
	}
}
