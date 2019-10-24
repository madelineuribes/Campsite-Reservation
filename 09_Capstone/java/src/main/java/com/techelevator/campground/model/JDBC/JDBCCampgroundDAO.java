package com.techelevator.campground.model.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getAllCampgrounds(String parkChoice) {
		List<Campground> campInfoList = new ArrayList<>();
		String sqlGetAllCampgrounds = "SELECT * FROM campground INNER JOIN park ON park.park_id = campground.park_id " + 
				"WHERE park.name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds, parkChoice);
		while (results.next()) {
			Campground tempCamp = new Campground();
			tempCamp.setCamp_id(results.getInt("campground_id"));
			tempCamp.setPark_id(results.getInt("park_id"));
			tempCamp.setName(results.getString("name"));
			tempCamp.setOpen_from(results.getInt("open_from_mm"));
			tempCamp.setOpen_to(results.getInt("open_to_mm"));
			tempCamp.setDaily_fee(results.getDouble("daily_fee"));
			campInfoList.add(tempCamp);
		}
		return campInfoList;
	}
}
