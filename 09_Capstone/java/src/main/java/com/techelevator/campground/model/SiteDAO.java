package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

	public List<Site> getAvailableSite(long campId, LocalDate arrivInput, LocalDate departInput);
	
	public void formatSiteReservationTable(List<Site> reservationSiteList);
}
