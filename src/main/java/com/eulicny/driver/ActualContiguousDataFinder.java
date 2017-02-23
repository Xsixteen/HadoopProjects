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
import com.eulicny.contiguous.YearMonthGainLoss;

public class ActualContiguousDataFinder {
	
	public static void main(String[] args) {	
	     int numberOfMaxPostiveContigous = 0;
         int numberOfMaxNegativeContigous = 0;
         int positiveMaxCounter = 0;
         int negativeMaxCounter = 0;
         Integer maxContigPosYear 	= 0;
         Integer maxContigNegYear	= 0;
		String ticker 		= args[0];
		String beginYear 	= args[1];
		
		HashMap<Integer, YearMonthGainLoss>stockYearlyHashMap				= new HashMap<Integer, YearMonthGainLoss>();
		YearMonthGainLoss workingRow;	
		
		System.out.println("ContiguousDataFinder Driver Starting.  Ticker =" + ticker + " Begin Date=" + beginYear);
		
		HiveDB hiveDB = new HiveDB();
	       try {
	         Statement statement = hiveDB.getConnection();
	         //print each row
	         ResultSet resultSet = statement.executeQuery("select symbol, year, month, difference FROM (select year, month, difference, symbol from stockanalytics.HistoricalStockDataStartEnd WHERE symbol = '"+ticker+"' and year > '"+beginYear+"') v1 GROUP BY month, year, symbol, difference order by year desc");
	        
	         System.out.println("Processing Symbol="+ticker);
	         
	         while (resultSet.next()) {
	        	  Integer month 		= new Integer(Integer.parseInt(resultSet.getString(3)));
	        	  Integer year  		= new Integer(Integer.parseInt(resultSet.getString(2)));
	        	  Double difference 	= new Double(Double.parseDouble(resultSet.getString(4)));
	        	  
	        	  workingRow = (stockYearlyHashMap.get(year) == null ? new YearMonthGainLoss(year) : stockYearlyHashMap.get(year));

	        	  workingRow.setMonthGainLoss(month, difference);
	        	  stockYearlyHashMap.put(year, workingRow);
	     
	       	}
	         
	        System.out.println("Years in HashMap "+ stockYearlyHashMap.size());
	        
	        //Process the set of values
	       Object[] keys = stockYearlyHashMap.keySet().toArray();
	       Arrays.sort(keys);
	       
	       for(Object yearKey : keys) {
	    	   
	    	    int year = (Integer) yearKey;
	    	    YearMonthGainLoss monthValueProcess  = stockYearlyHashMap.get(yearKey);
	            //YearMonthGainLoss monthValueProcess = (YearMonthGainLoss) pair.getValue();
	            double negativeAmount = 0, positiveAmount = 0;
	            int numberOfPostiveContigous = 0;
	            int numberOfNegativeContigous = 0;
	            int positiveCounter = 0;
	            int negativeCounter = 0;
	            
	       
	            //Process the entire year.
	            for(int i=1; i < 13 ; i++) {
		            Double gainLoss = (Double) monthValueProcess.getMonthGainLoss(i);
		            
		            if(gainLoss != null) {
					    if(gainLoss > 0) {
							positiveCounter++;
							positiveMaxCounter++;
							
							positiveAmount = positiveAmount + gainLoss;
		
							if(positiveCounter > numberOfPostiveContigous) 
								numberOfPostiveContigous = positiveCounter;
							
							negativeCounter = 0;
							
							    //Total Max Contiguous Run
							if(positiveMaxCounter > numberOfMaxPostiveContigous) {
								numberOfMaxPostiveContigous = positiveMaxCounter;
								maxContigPosYear = year; //(Integer) pair.getKey();	
							}
							
							negativeMaxCounter = 0;
						
						
					    } else {
							negativeAmount = negativeAmount + gainLoss;
							negativeCounter++;
							negativeMaxCounter++;
							if(negativeCounter > numberOfNegativeContigous) 
								numberOfNegativeContigous = negativeCounter;
							
							positiveCounter = 0;
							
							//Total Max Contiguous Run
							if(negativeMaxCounter > numberOfMaxNegativeContigous) {
								numberOfMaxNegativeContigous = negativeMaxCounter;
								maxContigNegYear = year; //(Integer) pair.getKey();	
							}
							
							positiveMaxCounter = 0;
							
					    }
		            }
		          
	            }
			    //statement.executeQuery("INSERT INTO TABLE stockanalytics.ContiguousRun VALUES ('"+ticker+"', "+monthValueProcess.getYear()+","+numberOfPostiveContigous+","+numberOfNegativeContigous+","+positiveAmount+","+negativeAmount+")");

	            System.out.println("Symbol= " + ticker + " Year= " + year + " Number of Positive Contigous = " + numberOfPostiveContigous + " Number of Negative Contigous = " + numberOfNegativeContigous + " Positivity Scale = " + positiveAmount + " Negativity Scale = " + negativeAmount);
	       }  
	            

	            	        
	        System.out.println("Total Max Contiguous Runs="+ numberOfMaxPostiveContigous + " Around Year= "+ maxContigPosYear + " Total Max Negative Contiguous Runs=" +numberOfMaxNegativeContigous + " Around Year=" + maxContigNegYear);
	        
	        statement.close(); //close statement

	        
	      } catch (ClassNotFoundException e) {
	         System.out.println("ClassNotFound Exception: " + e.getMessage());
	      } catch (SQLException e) {
	         System.out.println("SQL Exception: " + e.getMessage());
	      }
	}
}
