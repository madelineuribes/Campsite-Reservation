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
	private JDBCCampgroundDAO JDBCCamp;
	
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
			tempCamp.setCampId(results.getInt("campground_id"));
			tempCamp.setParkId(results.getInt("park_id"));
			tempCamp.setName(results.getString("name"));
			tempCamp.setOpenFrom(results.getInt("open_from_mm"));
			tempCamp.setOpenTo(results.getInt("open_to_mm"));
			tempCamp.setDailyFee(results.getDouble("daily_fee"));
			campInfoList.add(tempCamp);
		}
		return campInfoList;
	}
	
	public void formatCamgroundTable(String parkChoice, List<Campground> campInfoList) {
		System.out.println("\n" + parkChoice + " National Park Campgrounds \n");
		String format = "%1$-2s|%2$-32s|%3$-10s|%4$-10s|%5$-10s|\n";
		System.out.format(format, "", "Name", "Open", "Close", "Daily Fee");
		String format1 = "%1$-2s|%2$-32s|%3$-10s|%4$-10s|%5$-10s|\n";
		System.out.println("--|-----------------------------------------------------------------|");
		for (int j = 0; j < campInfoList.size(); j++) {
			String openMonth = convertToMonth(campInfoList.get(j).getOpenFrom());
			String closeMonth = convertToMonth(campInfoList.get(j).getOpenTo());
			
			System.out.format(format1, "#" + (j + 1), campInfoList.get(j).getName(), openMonth,
					closeMonth, "$" + campInfoList.get(j).getDailyFee() +"0", "\n");
		}
	}
	
	public String convertToMonth(int month_num) {
		String month = "";
		if(month_num == 1) {
			month = "January";
		} else if(month_num == 2) {
			month = "February";
		} else if(month_num == 3) {
			month = "March";
		} else if(month_num == 4) {
			month = "April";
		} else if(month_num == 5) {
			month = "May";
		} else if(month_num == 6) {
			month = "June";
		} else if(month_num == 7) {
			month = "July";
		} else if(month_num == 8) {
			month = "August";
		} else if(month_num == 9) {
			month = "September";
		} else if(month_num == 10) {
			month = "October";
		} else if(month_num == 11) {
			month = "November";
		} else if(month_num == 12) {
			month = "December";
		}
		return month;
	}
}
