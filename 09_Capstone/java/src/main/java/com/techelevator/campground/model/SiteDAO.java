package com.techelevator.campground.model;

import java.util.List;

public interface SiteDAO {
	
	public List<Site> getAvailableSite(String campId, String arrivInput, String departInput);

}
