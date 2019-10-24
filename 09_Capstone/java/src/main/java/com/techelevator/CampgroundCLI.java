package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.JDBC.JDBCCampgroundDAO;
import com.techelevator.campground.model.JDBC.JDBCParkDAO;

public class CampgroundCLI {

	private static final String MENU2_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MENU2_OPTION_SEARCH_RES = "Search for Reservation";
	private static final String MENU2_OPTION_RETURN = "Return to Previous Screen";
	private static final String[] MENU2_OPTIONS = { MENU2_OPTION_VIEW_CAMPGROUNDS, MENU2_OPTION_SEARCH_RES,
			MENU2_OPTION_RETURN };

	private JDBCParkDAO tempPark;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;

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
		campgroundDAO = new JDBCCampgroundDAO(datasource);
	}

	public void run() {
		mainMenuOptions = tempPark.getParkStringArray(tempPark.getAllParks());

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(mainMenuOptions);

			for (int i = 0; i < mainMenuOptions.length; i++) {
				if (choice.contentEquals(mainMenuOptions[i])) {
					String name = mainMenuOptions[i];
					Park parkInfo = parkDAO.displayParkInfo(name);
					System.out.print(parkInfo.getName() + "\nLocation: " + parkInfo.getLocation() + "\nArea: " + parkInfo.getArea() + "\nEstablished: " + parkInfo.getEstablishDate() + "\nVisitors: " + parkInfo.getVisitors() + "\nDescription: " + parkInfo.getDescription() + "\n");
			
					String menuTwoChoice = (String) menu.getChoiceFromOptions(MENU2_OPTIONS);
					
					if(menuTwoChoice.equals(MENU2_OPTION_VIEW_CAMPGROUNDS)) {
						List<Campground> campInfoList = campgroundDAO.getAllCampgrounds();
						System.out.println(parkInfo.getName() + " National Park Campgrounds");
						String format = "|%1$-10s|%2$-5s|%3$-5s|%4$-5s|\n";
						System.out.format(format, "Name", "Open", "Close", "Daily Fee");
						for(int j = 0; j < campInfoList.size(); j++) {
							System.out.format(format, campInfoList.get(j).getName(), campInfoList.get(j).getOpen_from(), campInfoList.get(j).getOpen_to(), campInfoList.get(j).getDaily_fee(), "\n");
						}
					} else if (menuTwoChoice.equals(MENU2_OPTION_SEARCH_RES)) {
						
					} else if (menuTwoChoice.equals(MENU2_OPTION_RETURN)) {
						
					}
						
				}

				if (choice.equals(mainMenuOptions[mainMenuOptions.length - 1])) {
					System.out.println("Thank you, goodbye");
					System.exit(0);
				}
			}
			
		}
	}
}
