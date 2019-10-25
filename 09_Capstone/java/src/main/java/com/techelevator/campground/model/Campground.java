package com.techelevator.campground.model;

public class Campground {

	private int campId;
	private int parkId;
	private String name;
	private int openFrom;
	private int openTo;
	private double dailyFee;
	
	
	public int getCampId() {
		return campId;
	}
	
	public void setCampId(int campId) {
		this.campId = campId;
	}
	
	public int getParkId() {
		return parkId;
	}
	
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getOpenFrom() {
		return openFrom;
	}
	
	public void setOpenFrom(int openFrom) {
		this.openFrom = openFrom;
	}
	
	public int getOpenTo() {
		return openTo;
	}
	
	public void setOpenTo(int openTo) {
		this.openTo = openTo;
	}
	
	public double getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	
}
