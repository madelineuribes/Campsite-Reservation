package com.techelevator.campground.model.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Site> getAvailableSite(String campId, String arrivInput, String departInput) {
		List<Site> siteList = new ArrayList<>();
		String sqlGetAllSites = "SELECT * FROM reservation INNER JOIN site ON reservation.site_id = site.site_id " + 
				"INNER JOIN campground ON site.campground_id = campground.campground_id WHERE site.campground_id = ? " + 
				"AND from_date > ? AND to_date < ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllSites, campId, arrivInput, departInput);
		return siteList;
		
	}

}
