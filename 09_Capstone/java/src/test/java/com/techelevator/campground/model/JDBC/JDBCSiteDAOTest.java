package com.techelevator.campground.model.JDBC;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAOTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private SiteDAO dao;

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
		dao = new JDBCSiteDAO(dataSource);
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
	public void getAvailableSiteReturnsAvailableSites() {
		String testArriv = "10/11/2019";
		String testDepart = "10/20/2019";
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate testArriv2 = LocalDate.parse(testArriv, inputFormat);
		LocalDate testDepart2 = LocalDate.parse(testDepart, inputFormat);
//		System.out.println(dao.getAvailableSite(1L, testArriv2, testDepart2));
		List<Site> reservationSiteList = dao.getAvailableSite(1L, testArriv2, testDepart2);
		
		assertEquals(5, reservationSiteList.size());
		assertEquals(1, reservationSiteList.get(0).getCampgroundId());
	}
}












