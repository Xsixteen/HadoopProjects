package com.eulicny.contiguous;

public class YearMonthGainLoss {
	Double[] gainLoss = new Double[13];
	Integer year;

	public YearMonthGainLoss(Integer year) {
		this.year = year;
	}
	
	public void setMonthGainLoss(int month, Double gainLossValue) {
		this.gainLoss[month] = gainLossValue;
	}
	
	public Double getMonthGainLoss(int month) {
		return this.gainLoss[month];
	}
	
	public Double[] getGainLoss() {
		return gainLoss;
	}
	public void setGainLoss(Double[] gainLoss) {
		this.gainLoss = gainLoss;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	

}
