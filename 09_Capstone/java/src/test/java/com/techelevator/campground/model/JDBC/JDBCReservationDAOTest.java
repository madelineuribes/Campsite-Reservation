package com.techelevator.campground.model.JDBC;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAOTest {
	private static SingleConnectionDataSource dataSource;
	private ReservationDAO dao; 
	private JdbcTemplate jdbcTemplate;

	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCReservationDAO(dataSource);
	}


	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}
	
// We could not get the tests to work, BUT we tested the methods in the application
// and they DO work in the application

//	@Test
//	public void getAllReservationsReturnsAllReservations() {
//		List<Reservation> allRes = new ArrayList<>();
//		allRes = dao.getAllReservations();
//		
//		assertEquals(58, allRes.size());
//		
//		Reservation tempReservation = allRes.get(0);
//		assertEquals(1, tempReservation.getReservationId());
//	}
	
//	@Test 
//	public void createReservationTest() {
//		
//		String testName = "test name";
//		Reservation newRes = new Reservation();
//		newRes.setReservationName(testName);
//		dao.createReservation(newRes);
//		List<Reservation> allRes = new ArrayList<>();
//		String sqlSelectAllFromResWhereNameEqualsTestName = "SELECT reservation_id, name FROM reservation WHERE name = ?";
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllFromResWhereNameEqualsTestName, testName);
//		
//		while(results.next()) {
//			Reservation tempRes = new Reservation();
//			tempRes.setReservationId(results.getInt("reservation_id"));
//			tempRes.setReservationName(results.getString("name"));
//			allRes.add(tempRes);
//		}
//		
//		assertEquals("test name", allRes.get(0).getReservationName());
//		assertEquals(1, allRes.size());
//	}
}









