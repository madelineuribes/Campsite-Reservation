package com.techelevator.campground.model.JDBC;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;

public class JDBCReservationDAO implements ReservationDAO{
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/*
	 * @Override public List<Reservation> getAvailableSite(String campId, String
	 * arrivInput, String departInput) { List<Reservation> reservationSiteList = new
	 * ArrayList<>(); Integer intCamp = Integer.parseInt(campId); LocalDate
	 * arriveDate = LocalDate.parse(arrivInput); LocalDate departDate =
	 * LocalDate.parse(departInput); String sqlGetAllSites =
	 * "SELECT * FROM site WHERE site_id " +
	 * "NOT IN (SELECT site.site_id FROM campground " +
	 * "INNER JOIN site ON site.campground_id = campground.campground_id " +
	 * "INNER JOIN reservation ON reservation.site_id = site.site_id " +
	 * "WHERE reservation.from_date <= ? " + "AND reservation.to_date >= ? " +
	 * "AND campground.campground_id = ?)"; SqlRowSet results =
	 * jdbcTemplate.queryForRowSet(sqlGetAllSites, departDate, arriveDate, intCamp);
	 * while (results.next()) { Reservation tempRes = new Reservation();
	 * tempRes.setSiteId(results.getInt("site_id"));
	 * tempRes.setReservationId(results.getInt("reservation_id"));
	 * tempRes.setFromDate(results.getString("from_date"));
	 * tempRes.setToDate(results.getString("to_date"));
	 * tempRes.setCreatedDate(results.getString("create_date"));
	 * tempRes.setReservationName(results.getString("name"));
	 * reservationSiteList.add(tempRes); } return reservationSiteList; }
	 * 
	 * @Override public void formatSiteReservationTable(List<Reservation>
	 * reservationSiteList) {
	 * System.out.println("\nResults Matching Your Search Criteria"); String format
	 * = "%1$-10s|%2$-10s|%3$-10s|%4$-10s|%5$-10s|%6$-10s|\n";
	 * System.out.format(format, "Site No.", "Max Occup.", "Accessible?",
	 * "Max RV Length", "Utility", "Cost"); for (int j = 0; j <
	 * reservationSiteList.size(); j++) { System.out.format(format,
	 * reservationSiteList.get(j).getSiteId(), siteList.get(j).getMaxOccupancy(),
	 * siteList.get(j).isAccessible(), siteList.get(j).getMaxRvLength(),
	 * siteList.get(j).isUtilities(), campInfoList.get(j).getDailyFee() + "\n"); } }
	 */

}











