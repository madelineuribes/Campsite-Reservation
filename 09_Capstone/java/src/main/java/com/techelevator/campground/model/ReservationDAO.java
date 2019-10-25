package com.techelevator.campground.model;

import java.util.List;

public interface ReservationDAO {

	public List<Reservation> getAvailableSite(String campId, String arrivInput, String departInput);

	public void formatSiteReservationTable(List<Reservation> reservationSiteList, List<Site> siteList,
			List<Campground> campInfoList);
	
}
