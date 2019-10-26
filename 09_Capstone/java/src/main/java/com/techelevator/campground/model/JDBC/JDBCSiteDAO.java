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
	public List<Site> getAvailableSite(long campId, LocalDate arrivInput, LocalDate departInput) {
		List<Site> reservationSiteList = new ArrayList<>();
		
		String sqlGetAllSites = "SELECT site.site_id, site.campground_id, site.site_number, "
				+ "site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, c.daily_fee "
				+ "FROM site JOIN reservation r ON r.site_id = site.site_id JOIN campground c "
				+ "ON c.campground_id = site.campground_id "
				+ "WHERE site.site_id NOT IN "
				+ "(SELECT site.site_id FROM campground INNER JOIN site ON site.campground_id = campground.campground_id "
				+ "INNER JOIN reservation ON reservation.site_id = site.site_id  WHERE reservation.from_date <= ? "
				+ "AND reservation.to_date >= ? AND site.campground_id = ?) "
				+ "GROUP BY site.site_id, site.campground_id, c.daily_fee LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllSites, departInput, arrivInput, campId);	
		
		while (results.next()) {
			Site tempSite = new Site();
			tempSite.setSiteId(results.getInt("site_id"));
			tempSite.setCampgroundId(results.getInt("campground_id"));
			tempSite.setSiteNumber(results.getInt("site_number"));
			tempSite.setMaxOccupancy(results.getInt("max_occupancy"));
			tempSite.setAccessible(results.getBoolean("accessible"));
			tempSite.setMaxRvLength(results.getInt("max_rv_length"));
			tempSite.setUtilities(results.getBoolean("utilities"));
			tempSite.setDailyFee(results.getBigDecimal("daily_fee").setScale(2));
			reservationSiteList.add(tempSite);
		}
		
		return reservationSiteList;
	}
	
	@Override
	public void formatSiteReservationTable(List<Site> reservationSiteList) {
		System.out.println("\nResults Matching Your Search Criteria");
		String format = "|%1$-7s|%2$-10s|%3$-12s|%4$-13s|%5$-7s|%6$-10s|\n";
		System.out.format(format, "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");
		for (Site site : reservationSiteList) {
			String format2 = "|%1$-6s|%2$-10s|%3$-12s|%4$-13s|%5$-7s|%6$-10s|";
			System.out.format(format2, site.getSiteNumber(), site.getMaxOccupancy(), site.isAccessible(),
					site.getMaxRvLength(), site.isUtilities(), site.getDailyFee().longValue()+".00" + "\n");
			//(daysBetween * site.getDailyFee().longValue())+".00")
		}
	}
}
