package com.techelevator.campground.model.JDBC;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Site> getAvailableSite(String campId, String arrivInput, String departInput) {
		List<Site> reservationSiteList = new ArrayList<>();
		Integer intCamp = Integer.parseInt(campId);
		LocalDate arriveDate = LocalDate.parse(arrivInput);
		LocalDate departDate = LocalDate.parse(departInput);
		
		String sqlGetAllSites = "SELECT site_number, max_occupancy, accessible, max_rv_length, utilities "
				+ "FROM site WHERE site_id "
				+ "NOT IN (SELECT site.site_id FROM campground "
				+ "INNER JOIN site ON site.campground_id = campground.campground_id "
				+ "INNER JOIN reservation ON reservation.site_id = site.site_id "
				+ "WHERE reservation.from_date <= ? "
				+ "AND reservation.to_date >= ? "
				+ "AND campground.campground_id = ?)";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllSites, departDate, arriveDate, intCamp);	
		
		while (results.next()) {
			Site tempSite = new Site();
			tempSite.setSiteNumber(results.getInt("site_number"));
			tempSite.setMaxOccupancy(results.getInt("max_occupancy"));
			tempSite.setAccessible(results.getBoolean("accessible"));
			tempSite.setMaxRvLength(results.getInt("max_rv_length"));
			tempSite.setUtilities(results.getBoolean("utilities"));
		}
		return reservationSiteList;
	}
	
	@Override
	public void formatSiteReservationTable(List<Site> reservationSiteList) {
		System.out.println("\nResults Matching Your Search Criteria");
		String format = "%1$-10s|%2$-10s|%3$-10s|%4$-10s|%5$-10s|%6$-10s|\n";
		System.out.format(format, "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");
		for (int j = 0; j < reservationSiteList.size(); j++) {
			System.out.println("in loop");
			System.out.format(format, reservationSiteList.get(j).getSiteNumber(), reservationSiteList.get(j).getMaxOccupancy(), reservationSiteList.get(j).isAccessible(),
					reservationSiteList.get(j).getMaxRvLength(), reservationSiteList.get(j).isUtilities() + "\n");
		}
	}
	

}
