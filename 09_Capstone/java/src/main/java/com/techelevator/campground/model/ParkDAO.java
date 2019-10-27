package com.techelevator.campground.model;

import java.util.List;

public interface ParkDAO {

	public List<Park> getAllParks();
	
	public String[] getParkStringArray(List<Park> parkNames);
	
	public Park getParkInfo(String name);
	
	public void displayParkInfo(Park parkInfo);
	
}
