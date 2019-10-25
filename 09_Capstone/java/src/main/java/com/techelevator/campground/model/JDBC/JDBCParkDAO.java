package com.techelevator.campground.model.JDBC;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> parkNames = new ArrayList<>();
		String sqlGetAllParks = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while (results.next()) {
			Park tempPark = new Park();
			tempPark.setName(results.getString("name"));
			parkNames.add(tempPark);
		}
		return parkNames;
	}
	
	// Function to convert ArrayList<String> to String[] 
	@Override
	public String[] getParkStringArray(List<Park> parkNames) 
	{ 
	    String parkNameString[] = new String[parkNames.size() + 1]; 
	 
	    for (int j = 0; j < parkNames.size(); j++) { 
	    	parkNameString[j] = parkNames.get(j).getName(); 
	    } 
	    parkNameString[parkNameString.length - 1] = "Quit";
	    return parkNameString; 
	}
	
	@Override
	public Park displayParkInfo(String name) {
		Park parkInfo = new Park();
		String sqlGetAllParks = "SELECT * FROM park WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks, name);
		while (results.next()) {
			parkInfo.setId(results.getInt("park_id"));
			parkInfo.setName(results.getString("name"));
			parkInfo.setLocation(results.getString("location"));
			parkInfo.setArea(results.getInt("area"));
			parkInfo.setVisitors(results.getInt("visitors"));
			parkInfo.setEstablishDate(results.getString("establish_date"));
			parkInfo.setDescription(results.getString("description"));
		}
		return parkInfo;
	}

}

