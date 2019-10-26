package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {

	public List<Campground> getAllCampgrounds(String parkChoice);

	public void formatCamgroundTable(String parkChoice, List<Campground> campInfoList);
	
}
