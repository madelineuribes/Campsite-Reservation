package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public Reservation createReservation(Reservation tempRes);
	
	public void getReservationById(long siteId, LocalDate arrivalDate, LocalDate departureDate,
			LocalDate createdDate, String userNameRes);

//	public List<Reservation> getAvailableSite(String campId, String arrivInput, String departInput);

//	public void formatSiteReservationTable(List<Reservation> reservationSiteList);
	
}
