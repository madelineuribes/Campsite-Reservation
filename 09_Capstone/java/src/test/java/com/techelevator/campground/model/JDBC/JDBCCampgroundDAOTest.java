package com.techelevator.campground.model.JDBC;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;


public class JDBCCampgroundDAOTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private CampgroundDAO dao;

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setUp() throws Exception {
		dao = new JDBCCampgroundDAO(dataSource);
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Test
	public void getAllCampgroundsInArchesTest() {
		List<Campground> campgroundList = dao.getAllCampgrounds("Arches");
		
		assertEquals(3, campgroundList.size());
		
		Campground testCampground = campgroundList.get(0);
		assertEquals("Devil's Garden", testCampground.getName());
		
		testCampground = campgroundList.get(0);
		assertEquals(1, testCampground.getOpenFrom());
		
		testCampground = campgroundList.get(1);
		assertEquals("Canyon Wren Group Site", testCampground.getName());
		
		testCampground = campgroundList.get(2);
		assertEquals("Juniper Group Site", testCampground.getName());
	}
	
	@Test
	public void getAllCampgroundsInAcadiaTest() {
		List<Campground> campgroundList = dao.getAllCampgrounds("Acadia");
		
		assertEquals(3, campgroundList.size());
		
		Campground testCampground = campgroundList.get(0);
		assertEquals("Blackwoods", testCampground.getName());
		
		testCampground = campgroundList.get(0);
		assertEquals(1, testCampground.getOpenFrom());
		
		testCampground = campgroundList.get(1);
		assertEquals("Seawall", testCampground.getName());
		
		testCampground = campgroundList.get(2);
		assertEquals("Schoodic Woods", testCampground.getName());
	}
	
	@Test
	public void getAllCampgroundsInCuyahogaValleyTest() {
		List<Campground> campgroundList = dao.getAllCampgrounds("Cuyahoga Valley");
		
		assertEquals(1, campgroundList.size());
		
		Campground testCampground = campgroundList.get(0);
		assertEquals("The Unnamed Primitive Campsites", testCampground.getName());
		
		testCampground = campgroundList.get(0);
		assertEquals(5, testCampground.getOpenFrom());
	}

	@Test
	public void getAllCampgroundsNoPark() {
		List<Campground> campgroundList = dao.getAllCampgrounds("Mountain Park");
		assertEquals(0, campgroundList.size());
	}
	
	@Test public void convertToMonthTest() {
		JDBCCampgroundDAO testCampground = new JDBCCampgroundDAO(dataSource);
		assertEquals("January", testCampground.convertToMonth(1));
		assertEquals("July", testCampground.convertToMonth(7));
		assertEquals("December", testCampground.convertToMonth(12));
	}
}
