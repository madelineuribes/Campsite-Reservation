package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {

	public List<Campground> getAllCampgrounds(String parkChoice);
	
	public List<Campground> getAvailableCampgrounds(String campId, String arrivInput, String departInput);
}
