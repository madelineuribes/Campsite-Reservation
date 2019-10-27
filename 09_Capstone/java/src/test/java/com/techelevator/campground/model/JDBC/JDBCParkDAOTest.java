package com.techelevator.campground.model.JDBC;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAOTest {

	/*
	 * Using this particular implementation of DataSource so that every database
	 * interaction is part of the same database session and hence the same database
	 * transaction
	 */
	private static SingleConnectionDataSource dataSource;
	private ParkDAO dao;

	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	/*
	 * After all tests have finished running, this method will close the DataSource
	 */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		dao = new JDBCParkDAO(dataSource);
	}

	/*
	 * After each test, we rollback any changes that were made to the database so
	 * that everything is clean for the next test
	 */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/*
	 * This method provides access to the DataSource for subclasses so that they can
	 * instantiate a DAO for testing
	 */
	protected DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void getAllParksReturnsAllParks() {
		List<Park> parkList = dao.getAllParks();
		assertEquals(3, parkList.size());
	}

	@Test
	public void getParkStringArrayReturnsParkStringArray()
	{
		List<Park> parkList = new ArrayList<>();
		Park testPark = new Park();
		Park testPark2 = new Park();
		parkList.add(testPark);
		parkList.add(testPark2);
		String[] testParkString = dao.getParkStringArray(parkList);
		dao.getParkStringArray(parkList);
		assertEquals("Quit", testParkString[2]);
	}
	
	@Test
	public void getParkStringArrayReturnsParkStringArraySize()
	{
		List<Park> parkList = new ArrayList<>();
		Park testPark = new Park();
		Park testPark2 = new Park();
		parkList.add(testPark);
		parkList.add(testPark2);
		String[] testParkString = dao.getParkStringArray(parkList);
		dao.getParkStringArray(parkList);
		assertEquals(3, testParkString.length);
	}
	
	@Test
	public void displayParkInfoReturnsCorrectInfo() {
		Park testPark = dao.getParkInfo("Arches");   				// (2, "Arches", "Utah", 76518, 1284767, "1929-04-12", "" );
		assertEquals(1284767, testPark.getVisitors());
	}
	
	@Test
	public void displayParkInfoReturnsNotFound() {
		Park testPark = dao.getParkInfo("Glacier");   				// (2, "Arches", "Utah", 76518, 1284767, "1929-04-12", "" );
		assertEquals(0, testPark.getVisitors());
	}

}








