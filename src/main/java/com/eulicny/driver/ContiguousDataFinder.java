package com.eulicny.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.eulicny.common.HiveDB;
import com.eulicny.contiguous.YearMonthGainLoss;

public class ContiguousDataFinder {
	
	public static void main(String[] args) {	
		
		String ticker 		= args[0];
		String begindate 	= args[1];
		
		HashMap<Integer, YearMonthGainLoss>stockYearlyHashMap				= new HashMap<Integer, YearMonthGainLoss>();
		YearMonthGainLoss workingRow;	
		
		System.out.println("ContiguousDataFinder Driver Starting.  Ticker =" + ticker + " Begin Date=" + begindate);
		
		HiveDB hiveDB = new HiveDB();
	       try {
	         Statement statement = hiveDB.getConnection();
	         //print each row
	         ResultSet resultSet = statement.executeQuery("select symbol, syear, smonth, SUM(gainloss) FROM (select year(symbol_date) as syear, month(symbol_date) as smonth, gainloss, symbol from stockanalytics.HistoricalStockDataGainLoss WHERE symbol = '"+ticker+"' and symbol_date > '"+begindate+"') v1 GROUP BY smonth, syear, symbol order by syear desc");
	        
	         System.out.println("Processing Symbol="+ticker);
	         
	         while (resultSet.next()) {
	        	  Integer month 	= new Integer(Integer.parseInt(resultSet.getString(3)));
	        	  Integer year  	= new Integer(Integer.parseInt(resultSet.getString(2)));
	        	  Double gainLoss 	= new Double(Double.parseDouble(resultSet.getString(4)));
	        	  
	        	  workingRow = (stockYearlyHashMap.get(year) == null ? new YearMonthGainLoss(year) : stockYearlyHashMap.get(year));

	        	  workingRow.setMonthGainLoss(month, gainLoss);
	        	  stockYearlyHashMap.put(year, workingRow);
	     
	       	}
	         
	        System.out.println("Years in HashMap "+ stockYearlyHashMap.size());
	        
	        //Process the set of values
	        
	        Iterator it = stockYearlyHashMap.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pair 						= (Map.Entry)it.next();
	            YearMonthGainLoss monthValueProcess = (YearMonthGainLoss) pair.getValue();
	            double negativeAmount = 0, positiveAmount = 0;
	            int numberOfPostiveContigous = 0;
	            int numberOfNegativeContigous = 0;
	            int positiveCounter = 0;
	            int negativeCounter = 0;
	            
	            //Process the entire year.
	            for(int i=1; i < 13 ; i++) {
		            Double gainLoss = (Double) monthValueProcess.getMonthGainLoss(i);
		            
		            if(gainLoss > 0) {
		            	positiveCounter++;
		            	
		            	positiveAmount = positiveAmount + gainLoss;

		            	if(positiveCounter > numberOfPostiveContigous) 
		            		numberOfPostiveContigous = positiveCounter;
		            	
		            	negativeCounter = 0;
		            	
		            } else {
		            	negativeAmount = negativeAmount + gainLoss;
		            	negativeCounter++;
		            	
		            	if(negativeCounter > numberOfNegativeContigous) 
		            		numberOfNegativeContigous = negativeCounter;
		            	
		            	positiveCounter = 0;
		            	
		            }
		          
	            }
	            
	            System.out.println("Symbol= " + ticker + " Year= " + pair.getKey() + " Number of Positive Contigous = " + numberOfPostiveContigous + " Number of Negative Contigous = " + numberOfNegativeContigous + " Positivity Scale = " + positiveAmount + " Negativity Scale = " + negativeAmount);
	            
	            
		        statement.executeQuery("INSERT INTO TABLE stockanalytics.ContiguousRun VALUES ('"+ticker+"', "+monthValueProcess.getYear()+","+numberOfPostiveContigous+","+numberOfNegativeContigous+","+positiveAmount+","+negativeAmount+")");

	            
	           it.remove(); // avoids a ConcurrentModificationException
	        }
	        
	        statement.close(); //close statement

	        
	      } catch (ClassNotFoundException e) {
	         System.out.println("ClassNotFound Exception: " + e.getMessage());
	      } catch (SQLException e) {
	         System.out.println("SQL Exception: " + e.getMessage());
	      }
	}
}
