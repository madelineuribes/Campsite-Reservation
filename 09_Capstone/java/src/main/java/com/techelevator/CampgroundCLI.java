package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.JDBC.JDBCParkDAO;

public class CampgroundCLI {

	private JDBCParkDAO tempPark;
	private ParkDAO parkDAO;

	private String[] mainMenuOptions;

	private Menu menu;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
		tempPark = new JDBCParkDAO(datasource);
		menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(datasource);
	}

	public void run() {
		mainMenuOptions = tempPark.getParkStringArray(tempPark.getAllParks());

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(mainMenuOptions);

			if (choice.equals(mainMenuOptions[mainMenuOptions.length - 1])) {
				System.out.println("Thank you, goodbye");
				System.exit(0);
			}
			for (int i = 0; i < mainMenuOptions.length; i++) {
				if (choice.contentEquals(mainMenuOptions[i])) {
					String name = mainMenuOptions[i];
					Park parkInfo = parkDAO.displayParkInfo(name);
					System.out.print(parkInfo.getName() + "\nLocation: " + parkInfo.getLocation() + "\nArea: " + parkInfo.getArea() + "\nEstablished: " + parkInfo.getEstablishDate() + "\nVisitors: " + parkInfo.getVisitors() + "\nDescription: " + parkInfo.getDescription() + "\n");
				}
			}
		}
	}
}
