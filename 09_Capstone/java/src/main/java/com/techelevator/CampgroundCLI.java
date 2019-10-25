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

	private static final String MENU3_OPTION_SEARCH_FOR_RESERVATION = "Search for Available Reservation";
	private static final String MENU3_OPTION_RETURN_TO_MENU2 = "Return to Previous Screen";
	private static final String[] MENU3_OPTION_SEARCH_RES = { MENU3_OPTION_SEARCH_FOR_RESERVATION,
			MENU3_OPTION_RETURN_TO_MENU2 };

	private JDBCParkDAO JDBCPark;
	private JDBCCampgroundDAO JDBCCamp;
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
		JDBCPark = new JDBCParkDAO(datasource);
		JDBCCamp = new JDBCCampgroundDAO(datasource);
		menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
	}

	public void handleThirdLevel(String parkChoice) {

		List<Campground> campInfoList = campgroundDAO.getAllCampgrounds(parkChoice);
		System.out.println("\n" + parkChoice + " National Park Campgrounds \n");
		String format = "%1$-2s|%2$-32s|%3$-10s|%4$-10s|%5$-10s|\n";
		System.out.format(format, "", "Name", "Open", "Close", "Daily Fee");
		String format1 = "%1$-2s|%2$-32s|%3$-10s|%4$-10s|%5$-10s|\n";
		System.out.println("--|-----------------------------------------------------------------|");
		for (int j = 0; j < campInfoList.size(); j++) {
			String openMonth = JDBCCamp.convertToMonth(campInfoList.get(j).getOpenFrom());
			String closeMonth = JDBCCamp.convertToMonth(campInfoList.get(j).getOpenTo());
			
			System.out.format(format1, "#" + (j + 1), campInfoList.get(j).getName(), openMonth,
					closeMonth, "$" + campInfoList.get(j).getDailyFee() +"0", "\n");
		}

		while (true) {
			String thirdChoice = (String) menu.getChoiceFromOptions(MENU3_OPTION_SEARCH_RES);

			if (thirdChoice.equals(MENU3_OPTION_RETURN_TO_MENU2)) {
				break;
			}
		}
	}

	public void handleSecondLevel(String parkChoice) {
		while (true) {
			Park parkInfo = parkDAO.displayParkInfo(parkChoice);
			System.out.println("\nPark Information");
			System.out.println(parkInfo.getName() + " National Park\n");
			String format1 = "%1$-15s%2$-10s";
			String format2 = "%1$-14s%2$-10s";
			System.out.format(format2, "Location:", parkInfo.getLocation());
			System.out.format(format1, "\nArea:", parkInfo.getArea() + " sq km");
			System.out.format(format1, "\nEstablished:", parkInfo.getEstablishDate());
			System.out.format(format1, "\nVisitors:", parkInfo.getVisitors());
			System.out.println("\n" + parkInfo.getDescription());
			
			// Display the second level of prompts:
			String secondChoice = (String) menu.getChoiceFromOptions(MENU2_OPTIONS);

			if (secondChoice.equals(MENU2_OPTION_VIEW_CAMPGROUNDS)) {
				handleThirdLevel(parkChoice);
			}

			if (secondChoice.equals(MENU2_OPTION_RETURN)) {
				break;
			}
		}
	}

	public void run() {
		System.out.println("National Park Campsite Reservation");
		System.out.println("Select a Park for Further Details");
		mainMenuOptions = JDBCPark.getParkStringArray(JDBCPark.getAllParks());

		// 1st level of the menu
		while (true) {

			String parkChoice = (String) menu.getChoiceFromOptions(mainMenuOptions);
			if (parkChoice.equals(mainMenuOptions[mainMenuOptions.length - 1])) {
				System.out.println("Thank you, Goodbye.");
				System.exit(0);
			} else {
				handleSecondLevel(parkChoice);
			}
		}

		
		
		
//		while (true) {
//			String choice = (String) menu.getChoiceFromOptions(mainMenuOptions);
//
//			for (int i = 0; i < mainMenuOptions.length; i++) {
//				// main menu
//				if (choice.contentEquals(mainMenuOptions[i])) {
//					String name = mainMenuOptions[i];
//					Park parkInfo = parkDAO.displayParkInfo(name);
//					System.out.print(parkInfo.getName() + "\nLocation: " + parkInfo.getLocation() + "\nArea: "
//							+ parkInfo.getArea() + "\nEstablished: " + parkInfo.getEstablishDate() + "\nVisitors: "
//							+ parkInfo.getVisitors() + "\nDescription: " + parkInfo.getDescription() + "\n");
//
//					// view campgrounds menu
//					String menuTwoChoice = (String) menu.getChoiceFromOptions(MENU2_OPTIONS);
//					while (true) {
//						if (menuTwoChoice.equals(MENU2_OPTION_VIEW_CAMPGROUNDS)) {
//							while (true) {
//								List<Campground> campInfoList = campgroundDAO.getAllCampgrounds();
//								System.out.println(parkInfo.getName() + " National Park Campgrounds");
//								String format = "|%1$-32s|%2$-5s|%3$-5s|%4$-10s|\n";
//								System.out.format(format, "Name", "Open", "Close", "Daily Fee");
//								for (int j = 0; j < campInfoList.size(); j++) {
//									System.out.format(format, campInfoList.get(j).getName(),
//											campInfoList.get(j).getOpen_from(), campInfoList.get(j).getOpen_to(),
//											campInfoList.get(j).getDaily_fee(), "\n");
//								}
//								// search
//								String menuThreeChoice = (String) menu.getChoiceFromOptions(MENU3_OPTION_SEARCH_RES);
//								if (menuThreeChoice.equals(MENU3_OPTION_SEARCH_FOR_RESERVATION)) {
//
//								} else if (menuThreeChoice.equals(MENU3_OPTION_RETURN_TO_MENU2)) {
//									//System.out.println(menu.getChoiceFromOptions(MENU2_OPTIONS));
//									break;
//								}
//							}
////							break;
//
//						} else if (menuTwoChoice.equals(MENU2_OPTION_SEARCH_RES)) {
//
//						} else if (menuTwoChoice.equals(MENU2_OPTION_RETURN)) {
//							break;
//						}
//					}
//				}
//
//				if (choice.equals(mainMenuOptions[mainMenuOptions.length - 1])) {
//					System.out.println("Thank you, goodbye");
//					System.exit(0);
//				}
//			}
//
//		}
	}
}
