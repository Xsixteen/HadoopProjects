package com.eulicny.contiguous;

public class YearMonthDayClose {
	Double[][] close = new Double[13][32];
	Integer year;

	public YearMonthDayClose(Integer year) {
		this.year = year;
	}
	
	public void setMonthDayClose(int month, int day, Double close) {
		this.close[month][day] = close;
	}
	
	public Double getMonthDayClose(int month, int day) {
		return this.close[month][day];
	}
	
	public Double[][] getClose() {
		return close;
	}
	public void setClose(Double[][] close) {
		this.close = close;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	

}
