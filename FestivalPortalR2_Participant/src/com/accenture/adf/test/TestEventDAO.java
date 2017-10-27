package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test class for EventDAO class
 * 
 */
public class TestEventDAO {

	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	private ArrayList<Object[]> showAllEvents;
	private EventDAO dao;

	/**
	 * Sets up database connection before other methods are executed in this
	 * class
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpDatabaseConnection() throws Exception {
		connection = FERSDataConnection.createConnection();
	}

	/**
	 * Closes the database connection after all the methods are executed
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownDatabaseConnection() throws Exception {
		/**
		 * @TODO: Close connection object here  
		 */
	}

	/**
	 * Sets up the objects required in other methods
	 * 
	 * @throws Exception
	 */
	@Before

	public void setUp() throws Exception {
		showAllEvents = new ArrayList<Object[]>();
		dao = new EventDAO();
	}

	/**
	 * Deallocate the resources after execution of method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
	}

	/**
	 * Positive test case to test the method showAllEvents
	 */
	@Test
	public void testShowAllEvents_Positive() {
		/**
		 * @TODO: Call showAllEvents method and assert it for
		 * size of returned type list
		 */	
	ArrayList<Object[]> eventList = new ArrayList<Object[]>();

		try {
			eventList = dao.showAllEvents();
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(43, eventList.size());	
	}
	
	/**
	 * Junit test case to test positive case for updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions_Positive() {
		/**
		 * @TODO: Find out seats available for an event by opening a connection
		 * and calling the query SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?
		 * Call the updateEventDeletions for eventId
		 * Again find out the seats available for this event
		 * testSeatsAvailableBefore should be 1 more then testSeatsAvailableAfter
		 */
		String qry = "SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID=? AND EVENTSESSIONID=?;";
		try {
			connection = FERSDataConnection.createConnection();
			statement = connection.prepareStatement(qry);
			statement.setInt(1, 10024);
			statement.setInt(2, 10054);
			resultSet = statement.executeQuery();
			resultSet.next();
			
			int seats1 = resultSet.getInt(1);
			
			dao.updateEventDeletions(10024, 10054);
			
			
			statement = connection.prepareStatement(qry);
			statement.setInt(1, 10024);
			statement.setInt(2, 10054);
			resultSet = statement.executeQuery();
			resultSet.next();
			
			int seats2 = resultSet.getInt(1);
			
			
			assertEquals(++seats1, seats2);
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * Negative test case for method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions_Negative() {
		/**
		 * @TODO: Call updateEventDeletions for incorrect eventid and it should
		 * throw an exception
		 */
	boolean flag=false;
		try {
			dao.updateEventDeletions(024, 10054);
		} catch (ClassNotFoundException e) {
			flag = true;
		} catch (SQLException e) {
			flag = true;

		} catch (Exception e) {
			flag = true;

		}
			
		assertEquals(true, flag);	
	}

	/**
	 * Positive test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Positive() {
		/**
		 * @TODO: Find out seats available for an event by opening a connection
		 * and calling the query SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?
		 * Call the updateEventNominations for eventId
		 * Again find out the seats available for this event
		 * testSeatsAvailableBefore should be 1 less then testSeatsAvailableAfter
		 */	
	String qry = "SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTID=? AND EVENTSESSIONID=?;";
		try {
			connection = FERSDataConnection.createConnection();
			statement = connection.prepareStatement(qry);
			statement.setInt(1, 10024);
			statement.setInt(2, 10054);
			resultSet = statement.executeQuery();
			resultSet.next();
			
			int seats1 = resultSet.getInt(1);
			
			dao.updateEventNominations(10024, 10054);
			
			
			statement = connection.prepareStatement(qry);
			statement.setInt(1, 10024);
			statement.setInt(2, 10054);
			resultSet = statement.executeQuery();
			resultSet.next();
			
			int seats2 = resultSet.getInt(1);
			
			
			assertEquals(--seats1, seats2);
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Negative test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Negative() {
		/**
		 * @TODO: Call updateEventNominations for incorrect eventid and it should
		 * throw an exception
		 */	
	boolean flag=false;
		try {
			dao.updateEventDeletions(024, 10054);
		} catch (ClassNotFoundException e) {
			flag = true;
		} catch (SQLException e) {
			flag = true;

		} catch (Exception e) {
			flag = true;

		}
			
		assertEquals(true, flag);
		
	
	}

	/**
	 * Positive test case for method checkEventsofVisitor
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testCheckEventsOfVisitor_Positive() throws ClassNotFoundException, SQLException {
		/**
		 * @TODO: Create visitor object by setting appropriate values
		 * Call checkEventsofVisitor method by passing this visitor object and
		 * valid eventId
		 * Assert the value of return type 
		 */	
		EventDAO edao = new EventDAO();
		Visitor visitor = new Visitor();
		
		visitor.setVisitorId(1003);
		boolean status=edao.checkEventsofVisitor(visitor, 1002,10002);
		assertEquals(status,true);
	}
	
	/**
	 * Junit test case for getEventCoordinator
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testGetEventCoordinator() throws ClassNotFoundException, SQLException{
		/**
		 * @TODO: Call getEventCoordinator method
		 * Assert the size of return type arraylist
		 */		
		EventDAO edao = new EventDAO();
		
		assertEquals(edao.getEventCoordinator().size(),5);
	}
	
	/**
	 * Junit test case for getEvent
	 */
	@Test
	public void testGetEvent(){
		/**
		 * @TODO: Call getEvent method 
		 * Assert the returned Event type with the passed value of event id
		 */
		
		Event e1 = new Event();
	try {
		e1=dao.getEvent(10024,10054);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		assertEquals(10024, e1.getEventid());
		
	}	
	
	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testInsertEvent(){
		/**
		 * @TODO: Create Event object by setting appropriate values
		 * Call insertEvent method by passing this event object
		 * Assert the status of return type of this insertEvent method
		 */		
		Event e = new Event();
		int status=0;
		
		
		
		e.setDescription("des");
		e.setDuration("0022");
		e.setName("Event1");
		e.setEventtype("Type");
		e.setPlace("Place");
		e.setEventCoordinatorId(101);
		e.setSeatsavailable(1200);
		e.setEventSession(5);
		
		
		try {
			status = dao.insertEvent(e);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertEquals(1, status);
		
		
	}
	
	/**
	 * Junit test case for updateEvent
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testUpdateEvent() throws ClassNotFoundException, SQLException{
		/**
		 * @TODO: Fetch Event object by calling showAllEvents method
		 * Update the values of event object
		 * Call updateEvent method by passing this modified event as object
		 * Assert the status of return type of updateEvent method
		 */			
		EventDAO edao =  new EventDAO();
		Event event =  new Event();
		event.setEventid(1006);
		event.setDescription("My Event Desc");
		event.setDuration("3000-5000");
		event.setName("MyEvent");
		event.setPlace("Bangalore");
		event.setEventtype("Casual");
		event.setSessionId(10008);
		event.setSeatsavailable(100);
		int status=edao.updateEvent(event);
		assertTrue(status>0);
	}
	
	/**
	 * Junit test case for deleteEvent
	 */
	@Test
	public void testDeleteEvent(){
		/**
		 * @TODO: Fetch Event object by calling showAllEvents method		 * 
		 * Call deleteEvent method by passing this event id and event session id as object
		 * Assert the status of return type of updateEvent method
		 */		
		try {
			ArrayList<Object[]> e = dao.showAllEvents("Rose Parade");
			
			//e.get(0)[0];
			
			Event e1 =new Event();
			try {
				dao.deleteEvent(1001, 10001);
			} catch (FERSGenericException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
