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
		String sqlGetAllDepartments = "SELECT name FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllDepartments);
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
	    
	    // ArrayList to Array Conversion 
	    for (int j = 0; j < parkNames.size(); j++) { 
	        // Assign each value to String array 
	    	parkNameString[j] = parkNames.get(j).getName(); 
	    	// System.out.println("adding park name: " + parkNameString[j]);
	    } 
	    parkNameString[parkNameString.length - 1] = "quit";
	    return parkNameString; 
	}
	
	@Override
	public List<Park> displayParkInfo() {
		// TODO Auto-generated method stub
		return null;
	}




}
