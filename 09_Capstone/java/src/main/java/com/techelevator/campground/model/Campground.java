package com.techelevator.campground.model;

public class Campground {

	private int camp_id;
	private int park_id;
	private String name;
	private int open_from;
	private int open_to;
	private double daily_fee;
	
	public int getCamp_id() {
		return camp_id;
	}
	
	public void setCamp_id(int camp_id) {
		this.camp_id = camp_id;
	}
	
	public int getPark_id() {
		return park_id;
	}
	
	public void setPark_id(int park_id) {
		this.park_id = park_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getOpen_from() {
		return open_from;
	}
	
	public void setOpen_from(int open_from) {
		this.open_from = open_from;
	}
	
	public int getOpen_to() {
		return open_to;
	}
	
	public void setOpen_to(int open_to) {
		this.open_to = open_to;
	}
	
	public double getDaily_fee() {
		return daily_fee;
	}
	
	public void setDaily_fee(double daily_fee) {
		this.daily_fee = daily_fee;
	}
	
}
