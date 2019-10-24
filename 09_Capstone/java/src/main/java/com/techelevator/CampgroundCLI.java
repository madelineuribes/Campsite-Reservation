package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.JDBC.JDBCParkDAO;
public class CampgroundCLI {

	private JDBCParkDAO tempPark;
	
	private String[] mainMenuOptions ;

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
		menu = new Menu(System.in,System.out );
	}

	public void run() {
		mainMenuOptions = tempPark.getParkStringArray(tempPark.getAllParks());
		/* Debugger
		 * for (String n: mainMenuOptions) { System.out.println("in run: " + n); }
		 */
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(mainMenuOptions);
		}
		
	}
}
