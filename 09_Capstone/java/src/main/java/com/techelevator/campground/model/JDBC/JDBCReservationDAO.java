package com.techelevator.campground.model.JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Reservation> getAllReservations() {
		List<Reservation> allRes = new ArrayList<>();
		String sqlGetAllReservation = "SELECT * FROM reservation";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllReservation);
		
		while(results.next()) {
			Reservation tempRes = new Reservation();
			tempRes.setReservationId(results.getInt("reservation_id"));
			tempRes.setSiteId(results.getLong("site_id"));
			tempRes.setReservationName(results.getNString("name"));
			tempRes.setFromDate(results.getDate("from_date").toLocalDate());
			tempRes.setToDate(results.getDate("to_date").toLocalDate());
			tempRes.setCreatedDate(results.getDate("create_date").toLocalDate());
			allRes.add(tempRes);
		}
		return allRes;
	}

	public List<Reservation> getReservedSites(long siteId, LocalDate arrivalDate, LocalDate departureDate) {
		List<Reservation> reservedSitesList = new ArrayList<>();
		String sqlGetAllReservation = "SELECT * FROM reservation WHERE site_id = ? " + 
				"AND (from_date BETWEEN ? AND ?) OR (to_date BETWEEN ? AND ?)";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllReservation, siteId, arrivalDate, departureDate);
		
		while(results.next()) {
			Reservation tempRes = new Reservation();
			tempRes.setReservationId(results.getInt("reservation_id"));
			tempRes.setSiteId(results.getLong("site_id"));
			tempRes.setReservationName(results.getNString("name"));
			tempRes.setFromDate(results.getDate("from_date").toLocalDate());
			tempRes.setToDate(results.getDate("to_date").toLocalDate());
			tempRes.setCreatedDate(results.getDate("create_date").toLocalDate());
			reservedSitesList.add(tempRes);
		}
		
		return reservedSitesList;
	}

	public void getReservationById(long siteId, LocalDate arrivalDate, LocalDate departureDate,
		LocalDate createdDate, String userNameRes) {
		Reservation tempRes = new Reservation();

		tempRes.setCreatedDate(createdDate);
		tempRes.setFromDate(arrivalDate);
		tempRes.setToDate(departureDate);
		tempRes.setSiteId(siteId);
		tempRes.setReservationName(userNameRes);
		tempRes = createReservation(tempRes);
		Reservation newRes = selectReservationId(userNameRes);
		System.out.println("The reservation has been made. The confirmation ID is: " + newRes.getReservationId() + ".");
	}

	public Reservation createReservation(Reservation tempRes) {
		Reservation newRes = new Reservation();

		String sqlInsertRes = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date)"
				+ "VALUES(default,?,?,?,?,?)";
		jdbcTemplate.update(sqlInsertRes, tempRes.getSiteId(), tempRes.getReservationName(),
				tempRes.getFromDate(), tempRes.getToDate(), tempRes.getCreatedDate());

		return newRes;
	}
	
	public Reservation selectReservationId(String userNameRes) {
		Reservation newRes = new Reservation();
		String sqlString = "SELECT reservation_id FROM reservation WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString, userNameRes);
		
		if(results.next()) {
			newRes.setReservationId(results.getInt("reservation_id"));
		}
		return newRes;
	}
	
}
