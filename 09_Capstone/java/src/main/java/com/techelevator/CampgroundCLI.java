package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.JDBC.JDBCCampgroundDAO;
import com.techelevator.campground.model.JDBC.JDBCParkDAO;
import com.techelevator.campground.model.JDBC.JDBCReservationDAO;
import com.techelevator.campground.model.JDBC.JDBCSiteDAO;

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

	private SiteDAO siteDAO;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;

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
		menu = new Menu(System.in, System.out);
		siteDAO = new JDBCSiteDAO(datasource);
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	public void handleFifthLevel(LocalDate arrivalDate, LocalDate departureDate) {
		while (true) {
			String userSiteChoice = getUserInput("\nWhich site should be reserved (enter 0 to cancel)? ");
			long userSiteChoiceNum = Long.parseLong(userSiteChoice);
			String userNameRes = getUserInput("\nWhat name should the reservation be made under? ");

			if (userSiteChoiceNum == 0) {
				break;
			} else {
				reservationDAO.getReservationById(userSiteChoiceNum, arrivalDate, departureDate, LocalDate.now(),
						userNameRes);
				System.out.println("Thank you!");
				System.exit(0);
			}
		}
	}

	public void handleFourthLevel(String parkChoice) {
		List<Campground> campInfoList = campgroundDAO.getAllCampgrounds(parkChoice);
		System.out.println("\n-*-Search for Campground Reservation-*-");
		campgroundDAO.formatCamgroundTable(parkChoice, campInfoList);

		while (true) {
			String userCampChoice = getUserInput("\nWhich campground (enter 0 to cancel)? ");
			long result = Long.parseLong(userCampChoice);
			if (result == 0) {
				break;
			}
			LocalDate arrivalDate = getDateInput("What is the arrival date? (MM/DD/YYYY) ");
			LocalDate departureDate = getDateInput("What is the departure date? (MM/DD/YYYY) ");
			long dateDiff = ChronoUnit.DAYS.between(arrivalDate, departureDate);

			if (dateDiff < 1) {
				System.out.println("Please select another time range");
			} else {
				List<Site> reservationSiteList = siteDAO.getAvailableSite(result, arrivalDate, departureDate);
				siteDAO.formatSiteReservationTable(reservationSiteList, dateDiff);
				
				if (reservationSiteList.size() == 0) {
					System.out.println("No Avaliable Sites. Please try again.");
					
				} else {
					handleFifthLevel(arrivalDate, departureDate);
				}
			}
		}
	}

	public void handleThirdLevel(String parkChoice) {
		List<Campground> campInfoList = campgroundDAO.getAllCampgrounds(parkChoice);
		campgroundDAO.formatCamgroundTable(parkChoice, campInfoList);

		while (true) {
			String thirdChoice = (String) menu.getChoiceFromOptions(MENU3_OPTION_SEARCH_RES);

			if (thirdChoice.equals(MENU3_OPTION_SEARCH_FOR_RESERVATION)) {
				handleFourthLevel(parkChoice);
			}
			if (thirdChoice.equals(MENU3_OPTION_RETURN_TO_MENU2)) {
				break;
			}
		}
	}

	public void handleSecondLevel(String parkChoice) {
		while (true) {
			Park parkInfo = parkDAO.getParkInfo(parkChoice);
			parkDAO.displayParkInfo(parkInfo);

			// Display the second level of prompts:
			String secondChoice = (String) menu.getChoiceFromOptions(MENU2_OPTIONS);

			if (secondChoice.equals(MENU2_OPTION_VIEW_CAMPGROUNDS)) {
				handleThirdLevel(parkChoice);
			}
			if (secondChoice.equals(MENU2_OPTION_SEARCH_RES)) {
				handleFourthLevel(parkChoice);
			}
			if (secondChoice.equals(MENU2_OPTION_RETURN)) {
				break;
			}
		}
	}

	public void run() {
		System.out.println("*--------------------------------*");
		System.out.println("National Park Campsite Reservation");
		System.out.println("*--------------------------------*");
		System.out.println("Select a Park for Further Details");
		mainMenuOptions = parkDAO.getParkStringArray(parkDAO.getAllParks());
		
		while (true) { // 1st level of the menu

			String parkChoice = (String) menu.getChoiceFromOptions(mainMenuOptions);
			if (parkChoice.equals(mainMenuOptions[mainMenuOptions.length - 1])) {
				System.out.println("Thank you, Goodbye.");
				System.exit(0);
			} else {
				handleSecondLevel(parkChoice);
			}
		}
	}

	private String getUserInput(String prompt) {
		String userInput = "";
		Scanner input = new Scanner(System.in);
		while (userInput.isEmpty()) {
			System.out.print(prompt + " >>> ");
			userInput = input.nextLine();
		}
		return userInput;
	}

	private LocalDate getDateInput(String prompt) {
		LocalDate userDate = null;
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		while (userDate == null) {
			try {
				String dateChoice = getUserInput(prompt);
				userDate = LocalDate.parse(dateChoice, inputFormat);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format.");
			}
		}
		return userDate;
	}
}