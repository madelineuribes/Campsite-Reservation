package com.techelevator.campground.model.JDBC;

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
	
	@Override
	public List<Reservation> getAvailableSite(String campId, String arrivInput, String departInput) {
		List<Reservation> reservationSiteList = new ArrayList<>();
		String sqlGetAllSites = "SELECT * FROM reservation INNER JOIN site ON reservation.site_id = site.site_id " + 
				"INNER JOIN campground ON site.campground_id = campground.campground_id WHERE site.campground_id = ? " + 
				"AND from_date > ? AND to_date < ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllSites, campId, arrivInput, departInput);	
		
		return reservationSiteList;
	}
	
	@Override
	public void formatSiteReservationTable(List<Reservation> reservationSiteList, List<Site> siteList, List<Campground> campInfoList) {
		System.out.println("\nResults Matching Your Search Criteria");
		String format = "%1$-2s|%2$-32s|%3$-10s|%4$-10s|%5$-10s|%6$-10s|\n";
		System.out.format(format, "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");
		for (int j = 0; j < reservationSiteList.size(); j++) {
			System.out.format(format, reservationSiteList.get(j).getSiteId(), siteList.get(j).getMaxOccupancy(), siteList.get(j).isAccessible(),
					siteList.get(j).getMaxRvLength(), siteList.get(j).isUtilities(), campInfoList.get(j).getDailyFee() + "\n");
		}
	}

}















