package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
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
import com.techelevator.campground.model.Reservation;
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
	}

	public void handleFourthLevel(String parkChoice, List<Campground> campInfoList) {
		System.out.println("Search for Campground Reservation");
		campgroundDAO.formatCamgroundTable(parkChoice, campInfoList);

		while (true) {
			String userCampChoice = getUserInput("\nWhich campground (enter 0 to cancel)? ");
			long result = Long.parseLong(userCampChoice);
			if (result == 0) {
				// If 0
			}
			LocalDate arrivalDate = getDateInput("What is the arrival date? (MM/DD/YYYY) ");
			LocalDate departureDate = getDateInput("What is the departure date? (MM/DD/YYYY) ");
			long diff = ChronoUnit.DAYS.between(arrivalDate, departureDate);

			if (diff < 1) {
				System.out.println("Please select another time range");
			} else {
				String format = "%1$-7s|%2$-10s|%3$-12s|%4$-13s|%5$-7s|%6$-4s|\n";
				System.out.format(format, "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost");
				List<Site> reservationSiteList = siteDAO.getAvailableSite(result, arrivalDate, departureDate);
				siteDAO.formatSiteReservationTable(reservationSiteList);
				if (reservationSiteList.size() == 0) {
					System.out.println("No Avaliable Sites. Please try again.");
				} else {
					// add reservation
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
				handleFourthLevel(parkChoice, campInfoList);
			}

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
		System.out.println("*--------------------------------*");
		System.out.println("National Park Campsite Reservation");
		System.out.println("*--------------------------------*");
		System.out.println("Select a Park for Further Details");
		mainMenuOptions = parkDAO.getParkStringArray(parkDAO.getAllParks());

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
